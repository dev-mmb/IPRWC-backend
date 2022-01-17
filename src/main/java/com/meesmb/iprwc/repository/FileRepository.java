package com.meesmb.iprwc.repository;

import com.meesmb.iprwc.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, String> {
    Optional<File> findByFilename(String name);

}
