package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by icepoint on 8/15/15.
 */
public class SomeUtil {

//获取不带扩展名的名字
    public  String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }
    //获取文件扩展名
    public String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    //获取当前时间
    public  String getTimenow(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }
}
