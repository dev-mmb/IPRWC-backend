package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.FileStorageService;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.http_response.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    @CrossOrigin(origins = "*")
    @PostMapping("product_image")
    public HTTPResponse<UploadFileResponse> productImageUpload(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return HTTPResponse.returnSuccess(new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize()));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("product_image")
    public ResponseEntity<Resource> getProductImage(@RequestParam("name") String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // silentyl fail for now...
        }
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
