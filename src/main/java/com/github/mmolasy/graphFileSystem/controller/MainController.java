package com.github.mmolasy.graphFileSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class MainController {
    @GetMapping
    public String showDirectory(){
        return "redirect:/directory";
    }
}
