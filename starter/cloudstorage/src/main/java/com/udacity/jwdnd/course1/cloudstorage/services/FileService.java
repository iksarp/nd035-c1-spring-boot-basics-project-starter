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
        if (mapper.getFile(file.getFilename()) != null) {
            throw new Exception("File with this name already exists");
        }
        if (mapper.insert(file) < 0) {
            throw new Exception("Internal error: Could not upload the file");
        }
    }

    public void deleteFile(String filename) throws Exception {
        if (mapper.getFile(filename) == null) {
            throw new Exception("No such file to delete.");
        }
        if (mapper.delete(filename) < 0) {
            throw new Exception("Internal error: Could not delete the file");
        }
    }

    public File getFile(String filename) {
        return mapper.getFile(filename);
    }

    public List<File> getFiles() {
        return mapper.getFiles();
    }
}
