package xyz.crowxx.dcxtcomplete.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService {

    @Value("${xyz.crowxx.dcxt.filepath}")
    String rootLocationStr;
    // property
    // 单文件 替换  ctrl + r replace
    // 单文件查找  ctrl + f  find
    public Path getRootLocation() {
        if (rootLocation == null){
            init();
        }
        return rootLocation;
    }

    public void setRootLocation(Path rootLocation) {
        this.rootLocation = rootLocation;
    }

    private Path rootLocation;


    public FileSystemStorageService() {
//        rootLocation = Paths.get(rootLocationStr);

        // 懒加载  lazy load

//        init();
    }

    public void store(String filename, MultipartFile file) {
        if (filename == null){
            filename = StringUtils.cleanPath(file.getOriginalFilename());
        }
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new RuntimeException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.getRootLocation().resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }

    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new RuntimeException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.getRootLocation().resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.getRootLocation(), 1)
                    .filter(path -> !path.equals(this.getRootLocation()))
                    .map(this.getRootLocation()::relativize);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to read stored files", e);
        }

    }

    public Path load(String filename) {
        return getRootLocation().resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(getRootLocation().toFile());
    }

    public Boolean deleteFileByFilename(String filename){
        Boolean flag = false;
        String imgPath = rootLocationStr + "/" + filename;
        File file = new File(imgPath);
        if (file.exists()){
            file.delete();
            flag = true;
        }
        return flag;
    }

    public void init() {
        try {
            rootLocation = Paths.get(rootLocationStr);
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Could not initialize storage", e);
        }
    }


}

