package com.dontfile.web.dto;

import static java.util.stream.Collectors.toList;

import com.dontfile.persistence.entity.FileInfoEntity;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
  private Long id;
  private String uuid;
  private LocalDateTime createdAt;
  private String name;
  private String url;
  private String type;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Resource resource;

  public static FileInfo toModel(FileInfoEntity file) {
    return FileInfo.builder()
        .id(file.getId())
        .uuid(file.getUuid())
        .createdAt(file.getCreatedAt())
        .name(file.getName())
        .type(file.getType())
        .url(file.getUrl())
        .build();
  }

  public static FileInfo toModel(FileInfoEntity fileEntity, Resource resource) {
    var fileInfo = toModel(fileEntity);
    fileInfo.setResource(resource);
    return fileInfo;
  }

  public static List<FileInfo> toModel(List<FileInfoEntity> files) {
    return files.stream().map(FileInfo::toModel).collect(toList());
  }
}
