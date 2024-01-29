package com.dontfile.service;

import com.dontfile.service.integration.StoreTextService;
import com.dontfile.service.info.FileInfoUtil;
import com.dontfile.web.dto.FileInfo;
import com.dontfile.persistence.entity.FileInfoEntity;
import com.dontfile.persistence.repository.FileRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.dontfile.service.converter.FileConverter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class FileService {
  private final FileRepository repository;
  private final FileConverter converter;
  private final StoreTextService storeTextService;

  public FileService(
          FileRepository repository, FileConverter converter, StoreTextService storeTextService) {
    this.repository = repository;
    this.converter = converter;
    this.storeTextService = storeTextService;
  }

  public FileInfoEntity upload(Resource body) {
    var uuid = UUID.randomUUID().toString();
    var file = new FileInfoUtil(uuid, body);
    var fileInfo =
        FileInfoEntity.builder()
            .uuid(uuid)
            .createdAt(LocalDateTime.now())
            .name(file.getFileName())
            .type(file.getMimeType())
            .build();

    var fileAsString = converter.convertToString(body);
    var url = storeTextService.save(uuid, fileAsString);
    fileInfo.setUrl(url);
    repository.save(fileInfo);
    return fileInfo;
  }

  public FileInfo findFindById(Long id) {
    var fileInfoEntity = repository.findById(id).orElseThrow(RuntimeException::new);
    var contextType = fileInfoEntity.getType();
    var uuid = fileInfoEntity.getUuid();

    var text = storeTextService.get(uuid);
    var resource = converter.convertToResource(contextType, text);

    return FileInfo.toModel(fileInfoEntity, resource);
  }

  public List<FileInfo> findAll() {
    var files = repository.findAll();
    return FileInfo.toModel(files);
  }
}
