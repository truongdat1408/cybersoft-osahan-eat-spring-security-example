package com.cybersoft.osahaneat.controller;

import com.cybersoft.osahaneat.payload.ResponseData;
import com.cybersoft.osahaneat.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    FileStorageService fileStorageService;
    //form-data: stream -> khó trình bày theo kiểu cấu trúc
    //request body: Chuyển file về base64 -> sẽ bị x1.5 dung lượng file
    // - Truyền bao nhiêu file cũng được và dễ xử lý
    // - Dung lượng file lớn
    //Lưu ở database: file phải chuyển về base64, file phải là dạng byte -> Ko khuyến khích
    //Lưu ở ổ đĩa: file upload sẽ được lưu vào ổ đĩa cứng -> Luôn sử dụng
    @PostMapping
    public ResponseEntity<?> addMenu(MultipartFile file) {
        System.out.println("Kiem tra: " + file.getOriginalFilename());
        ResponseData responseData = new ResponseData();
        boolean isSuccess = fileStorageService.saveFiles(file);
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        System.out.println("filename: " + filename);
        Resource file = fileStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(file);
    }
}
