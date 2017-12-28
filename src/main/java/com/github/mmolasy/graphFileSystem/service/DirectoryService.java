package com.github.mmolasy.graphFileSystem.service;

import com.github.mmolasy.graphFileSystem.graph.DirectoryNode;
import com.github.mmolasy.graphFileSystem.model.DirectoryRequestDTO;
import com.github.mmolasy.graphFileSystem.repository.DirectoryRepository;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@Data
public class DirectoryService {

    private final DirectoryRepository directoryRepository;

    public DirectoryNode getDirectoryRoot() throws Exception {
        DirectoryNode directoryNode = directoryRepository.findByIsRootTrue();
        System.out.println("dir "+directoryNode);
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
        directoryNode.getFiles().stream().forEach(file -> {
            byte[] encodeBase64 = Base64.encodeBase64(file.getContent());
            try {
                String base64Encoded = new String(encodeBase64, "UTF-8");
                System.out.println("content "+base64Encoded);
                file.setBase64(base64Encoded);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
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
        if(!Boolean.TRUE.equals(directoryRepository.deleteDirectoryById(id))){
            throw new Exception("UNABLE TO REMOVE DIRECTORY");
        }
    }
}
