package br.com.hebaja.englishtrainingquizzes.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class FileChecksum {

    @PrimaryKey
    @NonNull
    private String fileName;
    private String checksum;

    public FileChecksum() {}

    @Ignore
    public FileChecksum(String fileName, String checksum) {
        this.fileName = fileName;
        this.checksum = checksum;
    }

    @Ignore
    public FileChecksum(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
    public String getChecksum() {
        return checksum;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}
