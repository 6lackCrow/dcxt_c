package xyz.crowxx.dcxtcomplete.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class AppStorageService {
    @Resource
    FileSystemStorageService fileSystemStorageService;

    // 存储文件 并且 返回 uuid.ext

    public String store(MultipartFile file){
        String[] split = file.getOriginalFilename().trim().split("\\.");
        String uuid = UUID.randomUUID().toString().replace("-","");
        String file_id = uuid + "." + split[split.length - 1];
        fileSystemStorageService.store(file_id,file);
        return file_id;
    }

    // 读取文件 并且 返回
    public org.springframework.core.io.Resource load(String file_id){
        return fileSystemStorageService.loadAsResource(file_id);
    }

    public Boolean deleteFileByFileName(String fileName){
        return fileSystemStorageService.deleteFileByFilename(fileName);
    }

}
