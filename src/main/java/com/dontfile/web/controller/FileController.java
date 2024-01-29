package com.dontfile.web.controller;

import com.dontfile.service.FileService;
import com.dontfile.web.dto.FileInfo;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/v1/files")
public class FileController {

  private final FileService service;

  public FileController(FileService service) {
    this.service = service;
  }

  @PostMapping
  ResponseEntity<Void> upload(@Valid @RequestBody Resource body) {
    var fileInfo = service.upload(body);
    var fileUri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(fileInfo.getId())
            .toUri();

    return ResponseEntity.created(fileUri).build();
  }

  @GetMapping("/{id}")
  ResponseEntity<Resource> download(@PathVariable Long id) {
    var fileInfo = service.findFindById(id);
    return ResponseEntity.ok()
        .contentType(MediaType.valueOf(fileInfo.getType()))
        .body(fileInfo.getResource());
  }

  @GetMapping()
  ResponseEntity<List<FileInfo>> findAll() {
    var files = service.findAll();
    return ResponseEntity.ok(files);
  }
}
