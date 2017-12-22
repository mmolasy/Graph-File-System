package com.github.mmolasy.graphFileSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectoryRequestDTO {
    private Long id;
    private Long parentId;
    private String name;
}
