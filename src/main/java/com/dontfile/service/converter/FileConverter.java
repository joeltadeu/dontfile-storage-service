package com.dontfile.service.converter;

import java.io.IOException;
import java.util.Base64;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class FileConverter {
  public String convertToString(Resource body) {
    try {
      return Base64.getEncoder().encodeToString(body.getContentAsByteArray());
    } catch (IOException e) {
      throw new RuntimeException("Error to convert file to a string");
    }
  }

  public Resource convertToResource(String type, String text) {
      byte[] bytes = Base64.getDecoder().decode(text);
      return new ByteArrayResource(bytes, type);
  }
}
