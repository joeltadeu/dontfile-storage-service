package com.dontfile.service.info;

import lombok.Getter;
import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;

@Getter
public class FileInfoUtil {

  private final String fileName;
  private final String mimeType;
  private final String fileExtension;

  public FileInfoUtil(String uuid, Resource resource) {
    this.fileName = generateFileName(uuid, resource);
    this.mimeType = getFileMimeType(resource);
    this.fileExtension = getFileExtension(resource);
  }

  private String getFileExtension(Resource resource) {
    var mimeType = MimeTypeUtils.parseMimeType(getFileMimeType(resource));
    return mimeType.getSubtype();
  }

  private String getFileMimeType(Resource resource) {
    try {
      var tika = new Tika();
      return tika.detect(resource.getContentAsByteArray());
    } catch (IOException e) {
      throw new RuntimeException("Error to get MimeType from file");
    }
  }

  private String generateFileName(String uuid, Resource resource) {
    var mimeType = MimeTypeUtils.parseMimeType(getFileMimeType(resource));
    return uuid.concat(".").concat(mimeType.getSubtype());
  }
}
