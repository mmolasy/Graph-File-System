package com.github.mmolasy.graphFileSystem.controller;

import com.github.mmolasy.graphFileSystem.graph.FileNode;
import com.github.mmolasy.graphFileSystem.model.FileRequestDTO;
import com.github.mmolasy.graphFileSystem.model.FileResponseDTO;
import com.github.mmolasy.graphFileSystem.service.FileService;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
        modelAndView.addObject("file", FileResponseDTO.mapNodeToDTO(fileNode));
        return modelAndView;
    }

    @PostMapping(value = "file", consumes = {"multipart/form-data"})
    public String addFile(@RequestParam("directoryId") Long directoryId, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) throws Exception {
        FileRequestDTO fileRequestDTO = new FileRequestDTO(directoryId, name, file.getBytes());
        fileService.addFile(fileRequestDTO);
        return "redirect:/directory/"+fileRequestDTO.getDirectoryId();
    }

    @PostMapping(value = "file/delete")
    public String deleteFile(FileRequestDTO fileRequestDTO) throws Exception {
        if(fileRequestDTO == null){
            throw new Exception("INVALID REQUEST");
        }
        fileService.deleteFile(fileRequestDTO.getId());
        return "redirect:/directory/"+fileRequestDTO.getDirectoryId();
    }
}
