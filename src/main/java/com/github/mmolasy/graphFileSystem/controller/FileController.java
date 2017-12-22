package com.github.mmolasy.graphFileSystem.controller;

import com.github.mmolasy.graphFileSystem.graph.FileNode;
import com.github.mmolasy.graphFileSystem.model.FileRequestDTO;
import com.github.mmolasy.graphFileSystem.service.FileService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Data
public class FileController {

    private final FileService fileService;

    @GetMapping("file/{id}")
    public ModelAndView getFile(@PathVariable("id") Long id) throws Exception {
        FileNode fileNode = fileService.getFile(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("file");
        modelAndView.addObject("file", fileNode);
        return modelAndView;
    }

    @PutMapping("file")
    public String addFile(@RequestBody FileRequestDTO fileRequestDTO) throws Exception {
        if(fileRequestDTO == null){
            throw new Exception("INVALID REQUEST");
        }
        fileService.addFile(fileRequestDTO);
        return "redirect:/directory/"+fileRequestDTO.getDirectoryId();
    }

    @DeleteMapping("file")
    public String deleteFile(@RequestBody FileRequestDTO fileRequestDTO) throws Exception {
        if(fileRequestDTO == null){
            throw new Exception("INVALID REQUEST");
        }
        fileService.deleteFile(fileRequestDTO.getId());
        return "redirect:/directory/"+fileRequestDTO.getDirectoryId();
    }
}
