package com.dontfile.service.integration.client;

import com.dontfile.web.dto.DontpadResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.stereotype.Component;

@Component
public interface GetTextFeignClient {
  @RequestLine("GET /{id}.body.json?lastModified=0")
  @Headers({
    "Content-Type: application/x-www-form-urlencoded",
    "authority: api.dontpad.com",
    "accept: application/json, text/javascript, */*; q=0.01",
    "accept-language: en-US,en;q=0.9,pt-BR;q=0.8,pt;q=0.7",
    "cache-control: no-cache",
    "content-type: application/x-www-form-urlencoded;charset=UTF-8",
    "dnt: 1",
    "origin: https://dontpad.com",
    "pragma: no-cache",
    "referer: https://dontpad.com/",
    "sec-ch-ua: '\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"'",
    "sec-ch-ua-mobile: ?0",
    "sec-ch-ua-platform: \"macOS\"",
    "sec-fetch-dest: empty",
    "sec-fetch-mode: cors",
    "sec-fetch-site: same-site",
    "user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
  })
  DontpadResponse get(@Param String id);
}
