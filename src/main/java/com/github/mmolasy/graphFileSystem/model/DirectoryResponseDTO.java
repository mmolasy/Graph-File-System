package com.github.mmolasy.graphFileSystem.model;

import com.github.mmolasy.graphFileSystem.graph.DirectoryNode;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryResponseDTO {
    private Long id;
    private String directoryName;
    private Date creationDate;
    private Date lastUpdateDate;
    private Boolean isRoot;
    private Set<DirectoryResponseDTO> subDirectories = new HashSet();
    private Long parentDirectoryId;
    private Set<FileResponseDTO> files = new HashSet();

    public static DirectoryResponseDTO mapNodeToDTO(DirectoryNode directoryNode){

        DirectoryResponseDTO directoryResponseDTO = new DirectoryResponseDTO();
        directoryResponseDTO.setId(directoryNode.getId());
        directoryResponseDTO.setDirectoryName(directoryNode.getDirectoryName());
        directoryResponseDTO.setCreationDate(new Date(directoryNode.getCreationDate()));
        directoryResponseDTO.setLastUpdateDate(new Date(directoryNode.getLastUpdateDate()));
        directoryResponseDTO.setIsRoot(directoryNode.getIsRoot());
        directoryResponseDTO.setParentDirectoryId(directoryNode.getParentDirectory() != null ? directoryNode.getParentDirectory().getId() : null);
        directoryNode.getSubDirectories().forEach(subDirectory -> directoryResponseDTO.getSubDirectories().add(DirectoryResponseDTO.mapNodeToDTO(subDirectory)));
        directoryNode.getFiles().forEach(file -> directoryResponseDTO.getFiles().add(FileResponseDTO.mapNodeToDTO(file)));

        return directoryResponseDTO;
    }
}
