package com.server.file.util;

/**
 * @author ygl
 * @create 2018-06-27
 * @DESCRIPTION
 **/
public class ResponseUtil {

    public static ResponseInfo OK(Object data){
        ResponseInfo info = new ResponseInfo();
        info.setErrcode("0");
        info.setData(data);
        return info;
    }

    public static ResponseInfo ERROR(Object data){
        ResponseInfo info = new ResponseInfo();
        info.setErrcode("-1");
        info.setData(data);
        return info;
    }

}
