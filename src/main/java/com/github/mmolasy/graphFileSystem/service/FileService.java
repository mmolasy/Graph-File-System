package com.github.mmolasy.graphFileSystem.service;

import com.github.mmolasy.graphFileSystem.graph.FileNode;
import com.github.mmolasy.graphFileSystem.model.FileRequestDTO;
import com.github.mmolasy.graphFileSystem.repository.FileRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class FileService {

    private final FileRepository fileRepository;

    public FileNode addFile(FileRequestDTO fileRequestDTO) throws Exception {
        FileNode fileNode = fileRepository.createFile(fileRequestDTO.getDirectoryId(), fileRequestDTO.getName(), fileRequestDTO.getBytes());
        if(fileNode == null){
            throw new Exception("UNABLE TO CREATE FILE");
        }

        return fileNode;
    }

    public void deleteFile(Long fileId) throws Exception {
        if(!Boolean.TRUE.equals(fileRepository.deleteFileById(fileId))){
            throw new Exception("UNABLE TO DELETE FILE");
        }
    }

    public FileNode getFile(Long fileId) throws Exception {
        FileNode fileNode = fileRepository.findFileById(fileId);
        if(fileNode == null){
            throw new Exception("FILE DOES NOT EXIST");
        }
        return fileNode;
    }
}
