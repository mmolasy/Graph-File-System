package com.github.mmolasy.graphFileSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRequestDTO {
    private Long id;
    private Long directoryId;
    private String name;
    private byte [] bytes;

    public FileRequestDTO(Long directoryId, String name, byte [] bytes){
        this.directoryId = directoryId;
        this.name = name;
        this.bytes = bytes;
    }
}
