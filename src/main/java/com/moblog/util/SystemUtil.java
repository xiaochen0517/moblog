package com.moblog.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/28
 * @version 0.1
 */
public class SystemUtil {

    public static String getNowTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

}
