package com.cybersoft.osahaneat.service;

import com.cybersoft.osahaneat.service.imp.FileStorageServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService implements FileStorageServiceImp {
    @Value("${upload.path}")
    private String path;

    private Path root;

    @Override
    public boolean saveFiles(MultipartFile file) {
        init();
        //resolve: là dấu /
        try {
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING); //uploads/
            return true;
        } catch (IOException e) {
            System.out.println("Error save file: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            init();
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    private void init() {
        try {
            root = Paths.get(path);
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
        } catch (IOException e) {
            System.out.println("Error create root folder: " + e.getMessage());
        }
    }
}
