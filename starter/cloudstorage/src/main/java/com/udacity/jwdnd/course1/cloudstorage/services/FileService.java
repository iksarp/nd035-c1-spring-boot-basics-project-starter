package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileMapper mapper;

    public FileService(FileMapper mapper) {
        this.mapper = mapper;
    }

    public void addFile(File file) throws Exception {
        if (getFile(file.getUserid(), file.getFilename()) != null) {
            throw new Exception("File with this name already exists");
        }
        if (mapper.insert(file) < 0) {
            throw new Exception("Internal error: Could not upload the file");
        }
    }

    public void deleteFile(String userId, String filename) throws Exception {
        if (getFile(userId, filename) == null) {
            throw new Exception("No such file to delete.");
        }
        if (mapper.delete(userId, filename) < 0) {
            throw new Exception("Internal error: Could not delete the file");
        }
    }

    public File getFile(String userId, String filename) {
        return mapper.getFile(userId, filename);
    }

    public List<File> getFiles(String userId) {
        return mapper.getFiles(userId);
    }
}
