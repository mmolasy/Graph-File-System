package com.github.mmolasy.graphFileSystem.model;

import com.github.mmolasy.graphFileSystem.graph.FileNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDTO {
    private Long id;
    private String name;
    private Date creationDate;
    private Date lastUpdateDate;
    private String base64;

    public static FileResponseDTO mapNodeToDTO(FileNode fileNode){

        FileResponseDTO fileResponseDTO = new FileResponseDTO();
        fileResponseDTO.setId(fileNode.getId());
        fileResponseDTO.setName(fileNode.getName());
        fileResponseDTO.setCreationDate(new Date(fileNode.getCreationDate()));
        fileResponseDTO.setLastUpdateDate(new Date(fileNode.getLastUpdateDate()));
        byte[] encodeBase64 = Base64.encodeBase64(fileNode.getContent());
        try {
            fileResponseDTO.setBase64(new String(encodeBase64, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return fileResponseDTO;
    }
}
