package com.dontfile.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "fileinfo")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@Entity
public class FileInfoEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String uuid;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  private String name;
  private String url;
  private String type;
}
