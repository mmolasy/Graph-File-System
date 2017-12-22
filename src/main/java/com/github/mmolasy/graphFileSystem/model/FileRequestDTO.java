package com.github.mmolasy.graphFileSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRequestDTO {
    private Long id;
    private Long directoryId;
    private String name;
    private byte [] bytes;
}
