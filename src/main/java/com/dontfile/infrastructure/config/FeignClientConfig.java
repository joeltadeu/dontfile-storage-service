package com.dontfile.infrastructure.config;

import com.dontfile.service.integration.client.GetTextFeignClient;
import com.dontfile.service.integration.client.SaveTextFeignClient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Logger;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
  @Value("${integrations.dontpad.url}")
  private String endpoint;

  private final ObjectFactory<HttpMessageConverters> messageConverters;

  public FeignClientConfig(ObjectFactory<HttpMessageConverters> messageConverters) {
    this.messageConverters = messageConverters;
  }

  ObjectMapper provideObjectMapper() {
    return new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(new PageJacksonModule());
  }

  @Bean
  SaveTextFeignClient saveTextFeignClient() {
    return Feign.builder()
        .encoder(new FormEncoder(new SpringEncoder(this.messageConverters)))
        .decoder(new JacksonDecoder(provideObjectMapper()))
        .logger(new Slf4jLogger(GetTextFeignClient.class))
        .logLevel(Logger.Level.FULL)
        .target(SaveTextFeignClient.class, endpoint);
  }

  @Bean
  GetTextFeignClient getTextFeignClient() {
    return Feign.builder()
        .encoder(new JacksonEncoder(provideObjectMapper()))
        .decoder(new JacksonDecoder(provideObjectMapper()))
        .logger(new Slf4jLogger(GetTextFeignClient.class))
        .logLevel(Logger.Level.FULL)
        .target(GetTextFeignClient.class, endpoint);
  }
}
