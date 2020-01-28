package com.moblog.util;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/28
 * @version 0.1
 */
public class Log {

    public static void d(String title, String msg){
        System.out.println(SystemUtil.getNowTime()+":D:"+title+":"+msg);
    }

    public static void d(String title, int msg){
        System.out.println(SystemUtil.getNowTime()+":D:"+title+":"+msg);
    }

    public static void e(String title, String msg){
        System.err.println(SystemUtil.getNowTime()+":D:"+title+":"+msg);
    }

    public static void e(String title, int msg){
        System.err.println(SystemUtil.getNowTime()+":D:"+title+":"+msg);
    }

}
