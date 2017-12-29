package com.github.mmolasy.graphFileSystem.controller;

import com.github.mmolasy.graphFileSystem.graph.DirectoryNode;
import com.github.mmolasy.graphFileSystem.model.DirectoryRequestDTO;
import com.github.mmolasy.graphFileSystem.model.DirectoryResponseDTO;
import com.github.mmolasy.graphFileSystem.model.FileRequestDTO;
import com.github.mmolasy.graphFileSystem.service.DirectoryService;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Data
public class DirectoryController {

    private final DirectoryService directoryService;

    @GetMapping("directory/{id}")
    public ModelAndView getDirectoryTreeStartingFromDirectory(@PathVariable(value = "id") Long id) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        DirectoryNode directoryNode = directoryService.getDirectoryById(id);
        modelAndView.addObject("directory", DirectoryResponseDTO.mapNodeToDTO(directoryNode));
        modelAndView.addObject("directoryRequestDTO", new DirectoryRequestDTO());
        modelAndView.addObject("fileRequestDTO", new FileRequestDTO());
        modelAndView.setViewName("directory");

        return modelAndView;
    }

    @GetMapping("directory")
    public ModelAndView getDirectoryTreeStartingFromRoot() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        DirectoryNode directoryNode = directoryService.getDirectoryRoot();
        modelAndView.addObject("directory", DirectoryResponseDTO.mapNodeToDTO(directoryNode));
        modelAndView.addObject("directoryRequestDTO", new DirectoryRequestDTO());
        modelAndView.addObject("fileRequestDTO", new FileRequestDTO());
        modelAndView.setViewName("directory");

        return modelAndView;
    }

    @PostMapping(value = "directory", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addDirectory(DirectoryRequestDTO directoryRequestDTO) throws Exception {
        if(directoryRequestDTO == null){
            throw new Exception("INVALID REQUEST");
        }
        DirectoryNode newDirectory = directoryService.addDirectory(directoryRequestDTO);

        return "redirect:/directory/"+newDirectory.getId();
    }

    @PostMapping("directory/delete")
    public String deleteDirectory(@RequestBody DirectoryRequestDTO directoryRequestDTO) throws Exception {
        if(directoryRequestDTO == null){
            throw new Exception("INVALID REQUEST");
        }
        directoryService.removeDirectory(directoryRequestDTO.getId());
        return "redirect:/directory/"+directoryRequestDTO.getParentId();
    }

    @GetMapping("init")
    public void init() {
        directoryService.initializeFileSystem();
    }

    @GetMapping("destroy")
    public void destroy() {
        directoryService.destroyFileSystem();
    }
}
