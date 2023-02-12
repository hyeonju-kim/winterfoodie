package com.winterfoodies.ceo.domain.files.controller;


import com.winterfoodies.ceo.domain.files.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/view/file")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    @PostMapping ("")
    public String submit(@RequestParam("file") MultipartFile file, Model model){
        try {
            fileStorageService.store(file);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return "OK";
    }
}
