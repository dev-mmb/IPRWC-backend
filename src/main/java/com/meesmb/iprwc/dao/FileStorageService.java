package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.exception.FileNotFoundException;
import com.meesmb.iprwc.exception.FileStorageException;
import com.meesmb.iprwc.model.File;
import com.meesmb.iprwc.property.FileStorageProperties;
import com.meesmb.iprwc.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileStorageService {
    @Autowired
    FileStorageProperties fileStorageProperties;
    @Autowired
    FileRepository fileRepository;

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Check if the file's name contains invalid characters
        if(fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        try {
            File f = new File(fileName, file.getBytes(), file.getContentType());

            fileRepository.save(f);
            return fileName;
        } catch (IOException e) {
            return "";
        }
    }

    public ResponseEntity<Resource> loadFileAsResource(String fileName) throws Exception {
        Optional<File> f = fileRepository.findByFilename(fileName);
        if (f.isEmpty()) {
            throw new Exception("Error downloading file");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(f.get().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.get().getFilename() + "\"")
                .body(new ByteArrayResource(f.get().getData()));
    }
}
