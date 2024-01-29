package com.dontfile.service.integration;

import com.dontfile.service.integration.client.GetTextFeignClient;
import com.dontfile.service.integration.client.SaveTextFeignClient;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StoreTextService {

  private final String endpoint;
  private final GetTextFeignClient getTextClient;
  private final SaveTextFeignClient saveTextClient;

    public StoreTextService(
      @Value("${integrations.dontpad.url}") String endpoint,
      GetTextFeignClient getTextClient,
      SaveTextFeignClient saveTextClient) {
    this.getTextClient = getTextClient;
    this.saveTextClient = saveTextClient;
    this.endpoint = endpoint.concat("/%s");
  }

  public String save(String uuid, String text) {
    // call to create a new and empty dontpad page with the uuid
    getTextClient.get(uuid);

    Map<String, String> form = new HashMap<>();
    form.put("text", text);
    form.put("force", "false");
    form.put("lastModified", String.valueOf(Instant.now().toEpochMilli()));
    saveTextClient.save(uuid, form);

    return endpoint.formatted(uuid);
  }

  public String get(String uuid) {
    var response = getTextClient.get(uuid);
    return response.body();
  }

}
