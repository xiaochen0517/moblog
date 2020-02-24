package com.moblog.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/28
 * @version 0.1
 */
public class SystemUtil {

    /**
     * 获取当前时间
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    /**
     * 获取随机文件名
     * @param fileName
     * @return
     */
    public static String getRandomName(String fileName) {
        int index = fileName.lastIndexOf(".");
        String houzhui = fileName.substring(index);//获取后缀名
        String uuidFileName = UUID.randomUUID().toString().replace("-", "") + houzhui;
        return uuidFileName;
    }

    /**
     * 从uri中获取文件名
     * @param fileUri
     * @return
     */
    public static String getFileName(String fileUri){
        String[] strings = fileUri.split("/");
        return strings[strings.length-1];
    }

}
