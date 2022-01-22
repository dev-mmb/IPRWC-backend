package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.exception.FileNotFoundException;
import com.meesmb.iprwc.exception.FileStorageException;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.model.File;
import com.meesmb.iprwc.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileStorageDao {
    @Autowired
    FileRepository fileRepository;

    public HTTPResponse<String> storeFile(MultipartFile file) {
        // Normalize file name
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null)
            return HTTPResponse.returnFailure("could not get original filename");
        String fileName = StringUtils.cleanPath(originalFileName);

        // Check if the file's name contains invalid characters
        if(fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        try {
            File f = new File(fileName, file.getBytes(), file.getContentType());
            fileRepository.save(f);
            return HTTPResponse.returnSuccess(fileName);
        } catch (IOException e) {
            return HTTPResponse.returnFailure(e.getMessage());
        }
    }

    public ResponseEntity<Resource> loadFileAsResource(String fileName) throws Exception {
        Optional<File> f = fileRepository.findByFilename(fileName);
        if (f.isEmpty()) {
            throw new FileNotFoundException("File with name: " + fileName + " not found");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(f.get().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.get().getFilename() + "\"")
                .body(new ByteArrayResource(f.get().getData()));
    }
}
