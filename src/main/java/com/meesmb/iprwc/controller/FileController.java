package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.FileStorageService;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.http_response.UploadFileResponse;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// guide: https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/
@RestController
public class FileController {
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/product_image")
    public HTTPResponse<String> productImageUpload(@RequestParam("file") MultipartFile file) {
        String n = fileStorageService.storeFile(file);

        return HTTPResponse.returnSuccess(n);
    }

    @GetMapping("/product_image/get/{fileName}")
    public ResponseEntity<Resource> getProductImage(@PathVariable("fileName") String fileName, HttpServletRequest request) throws Exception {
        return fileStorageService.loadFileAsResource(fileName);
    }

}
