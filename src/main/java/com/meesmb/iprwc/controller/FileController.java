package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.FileStorageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

// guide: https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/
@RestController
public class FileController {

    @Autowired
    private FileStorageDao fileStorageService;

    @PostMapping("/product_image")
    public ResponseEntity<String> productImageUpload(@RequestParam("file") MultipartFile file) {
        return fileStorageService.storeFile(file);
    }

    @GetMapping("/product_image/get/{fileName}")
    public ResponseEntity<Resource> getProductImage(@PathVariable("fileName") String fileName) throws Exception {
        return fileStorageService.loadFileAsResource(fileName);
    }

}
