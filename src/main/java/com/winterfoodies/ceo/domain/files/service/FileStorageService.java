package com.winterfoodies.ceo.domain.files.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    private final ResourceLoader resourceLoader;

    public void store(MultipartFile file) throws Exception{
        boolean wellStored = true;
        log.info("파일 업로드 시작");

        Path root = Paths.get(resourceLoader.getResource("file:/Users/hyunsoo/tmp").getURI());
        System.out.println(root.toString());

        Path destinationFile = root.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();


        System.out.println(destinationFile.getFileName());
        System.out.println(destinationFile);
        System.out.println(destinationFile.getParent().getFileName());

        InputStream inputStream = file.getInputStream();
        try{
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            wellStored = false;
            log.error("파일 업로드 에러입니다.");
        }finally {
            inputStream.close();
        }

        if(wellStored){
            log.info("파일 업로드 진행 완료");
        }
    }
}
