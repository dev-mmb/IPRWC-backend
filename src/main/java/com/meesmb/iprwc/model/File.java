package com.meesmb.iprwc.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.sql.Blob;

@Entity
public class File {
    @Id
    @Column
    String filename;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
     byte[] data;

    @Column
    String fileType;


    public File() {
    }

    public File(String filename, byte[] data, String fileType) {
        this.filename = filename;
        this.data = data;
        this.fileType = fileType;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
