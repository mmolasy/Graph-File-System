package com.github.mmolasy.graphFileSystem.controller;

import com.github.mmolasy.graphFileSystem.graph.DirectoryNode;
import com.github.mmolasy.graphFileSystem.model.DirectoryRequestDTO;
import com.github.mmolasy.graphFileSystem.service.DirectoryService;
import lombok.Data;
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
        modelAndView.addObject("directory", directoryNode);
        modelAndView.setViewName("directory");

        return modelAndView;
    }
    @GetMapping("directory")
    public ModelAndView getDirectoryTreeStartingFromRoot() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        DirectoryNode directoryNode = directoryService.getDirectoryRoot();
        modelAndView.addObject("directory", directoryNode);
        modelAndView.setViewName("directory");

        return modelAndView;
    }

    @PutMapping("directory")
    public ModelAndView addDirectory(@RequestBody DirectoryRequestDTO directoryRequestDTO) throws Exception {
        if(directoryRequestDTO == null){
            throw new Exception("INVALID REQUEST");
        }
        ModelAndView modelAndView = new ModelAndView();
        DirectoryNode newDirectory = directoryService.addDirectory(directoryRequestDTO);
        modelAndView.addObject("directory", newDirectory);
        modelAndView.setViewName("directory");
        return modelAndView;
    }

    @DeleteMapping("directory")
    public String deleteDirectory(@RequestBody DirectoryRequestDTO directoryRequestDTO) throws Exception {
        if(directoryRequestDTO == null){
            throw new Exception("INVALID REQUEST");
        }
        directoryService.removeDirectory(directoryRequestDTO.getId());
        return "redirect:/directory/"+directoryRequestDTO.getParentId();
    }
}
