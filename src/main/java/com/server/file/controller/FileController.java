package com.server.file.controller;

import com.github.tobato.fastdfs.domain.FileInfo;
import com.server.file.support.FastDFSClientWrapper;
import com.server.file.util.ByteUploadDto;
import com.server.file.util.ResponseInfo;
import com.server.file.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author ygl
 * @create 2018-06-04
 * @DESCRIPTION
 **/
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FastDFSClientWrapper dfsClient;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        String pathUrl = null;
        try {
            pathUrl = dfsClient.getPathUrl(dfsClient.uploadFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathUrl;
    }


    @PostMapping("/byte/upload")
    public ResponseInfo upload(@RequestBody ByteUploadDto uploadDto) {
        byte[] bytes = uploadDto.getBytes();
        if (bytes == null) {
            ResponseUtil.ERROR("字节数据为空");
        }
        String fileExtension = uploadDto.getFileExtension();
        if (StringUtils.isEmpty(fileExtension)) {
            ResponseUtil.ERROR("字节数据为空");
        }
        String url;
        try {
            url = dfsClient.uploadFile(bytes, fileExtension);
            return ResponseUtil.OK(dfsClient.getPathUrl(url));
        } catch (Exception e) {
            return ResponseUtil.ERROR(e.getMessage());
        }
    }


    @GetMapping
    public FileInfo queryFileInfo(String fileUrl) throws IOException {
        FileInfo fileInfo = dfsClient.queryFileInfo(fileUrl);
        return fileInfo;
    }

    @GetMapping("/delete")
    public Object delete(@RequestParam String fileUrl){
        return dfsClient.deleteFile(fileUrl);
    }
}