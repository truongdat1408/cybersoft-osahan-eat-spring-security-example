package com.cybersoft.osahaneat.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageServiceImp {
    boolean saveFiles(MultipartFile file);
    Resource load(String filename);
}
