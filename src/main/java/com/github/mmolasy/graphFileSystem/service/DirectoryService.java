package com.github.mmolasy.graphFileSystem.service;

import com.github.mmolasy.graphFileSystem.graph.DirectoryNode;
import com.github.mmolasy.graphFileSystem.model.DirectoryRequestDTO;
import com.github.mmolasy.graphFileSystem.repository.DirectoryRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class DirectoryService {

    private final DirectoryRepository directoryRepository;

    public DirectoryNode getDirectoryRoot() throws Exception {
        DirectoryNode directoryNode = directoryRepository.findByIsRootTrue();
        if(directoryNode == null){
            throw new Exception("ROOT DIRECTORY DOES NOT EXISTS");
        }

        return directoryNode;
    }

    public DirectoryNode getDirectoryById(Long id) throws Exception {
        DirectoryNode directoryNode = directoryRepository.findOne(id);
        if(directoryNode == null){
            throw new Exception("DIRECTORY DOES NOT EXISTS");
        }

        return directoryNode;
    }

    public void initializeFileSystem(){
        DirectoryNode rootDirectory = directoryRepository.findByIsRootTrue();
        if(rootDirectory == null) {
            DirectoryNode directoryNode = new DirectoryNode();
            directoryNode.setCreationDate(System.currentTimeMillis());
            directoryNode.setIsRoot(true);
            directoryNode.setDirectoryName("root");
            directoryRepository.save(directoryNode);
        }
    }

    public void destroyFileSystem(){
        directoryRepository.destroyFileSystem();
    }

    public DirectoryNode addDirectory(DirectoryRequestDTO directoryDTO) throws Exception {
        DirectoryNode newDirectory = directoryRepository.createDirectory(directoryDTO.getParentId(), directoryDTO.getName());
        if(newDirectory == null){
            throw new Exception("UNABLE TO ADD DIRECTORY");
        }
        return newDirectory;
    }

    public void removeDirectory(Long id) throws Exception {
        directoryRepository.deleteDirectoryById(id);
    }
}
