package com.server.file.util;

import lombok.Data;

/**
 * @author ygl
 * @create 2018-06-27
 * @DESCRIPTION
 **/
@Data
public class ByteUploadDto {

    private byte[] bytes;

    private String fileExtension;

}
