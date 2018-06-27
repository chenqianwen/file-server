package com.server.file.support;

import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.server.file.util.ResponseInfo;
import com.server.file.util.ResponseUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author ygl
 * @create 2018-06-04
 * @DESCRIPTION
 **/
@Component
public class FastDFSClientWrapper {

    @Autowired
    private FastFileStorageClient storageClient;

    @Value("${imageUrl.prefix}")
    private String imageUrlPrefix;


    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return getResAccessUrl(storePath);
    }

    /**
     * 将一段字符串生成一个文件上传
     *
     * @param content       文件内容
     * @param fileExtension
     * @return
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        return getResAccessUrl(storePath);
    }

    public String uploadFile(byte[] buff, String fileExtension) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(buff);
            StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
            return getResAccessUrl(storePath);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 封装图片URL地址
     *
     * @param storePath
     * @return group1/M00/00/00/CtM3C1sU1LuASI1GAAAADpoj_ao222.txt
     */
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = storePath.getFullPath();
        return fileUrl;
    }


    /**
     * 封装图片完整URL地址
     *
     * @param accessUrl
     * @return http://10.211.55.11:8888/group1/M00/00/00/CtM3C1sU1LuASI1GAAAADpoj_ao222.txt
     */
    public String getPathUrl(String accessUrl) {
        if (StringUtils.isNotEmpty(imageUrlPrefix)) {
            return imageUrlPrefix + accessUrl;
        }
        return "";
    }


    public FileInfo queryFileInfo(String fileUrl) {
        StorePath storePath = StorePath.praseFromUrl(fileUrl);
        String groupName = storePath.getGroup();
        String path = storePath.getPath();
        FileInfo fileInfo = storageClient.queryFileInfo(groupName, path);
        return fileInfo;
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件访问地址
     * @return
     */
    public ResponseInfo deleteFile(String fileUrl){
        if (StringUtils.isEmpty(fileUrl)) {
            return ResponseUtil.ERROR("文件地址为空");
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            String groupName = storePath.getGroup();
            String path = storePath.getPath();
            FileInfo fileInfo = storageClient.queryFileInfo(groupName, path);
            if (fileInfo == null) {
                return ResponseUtil.ERROR("文件不存在");
            }
            storageClient.deleteFile(groupName, path);
            return ResponseUtil.OK("删除成功");
        } catch (Exception e){
            return ResponseUtil.ERROR(e.getMessage());
        }
    }
}
