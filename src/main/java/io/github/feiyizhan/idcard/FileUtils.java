package io.github.feiyizhan.idcard;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 文件工具类
 * @author 徐明龙 XuMingLong 2019-07-23
 **/
public class FileUtils {

    public static File getFile(String fileName){

        File file= new File(fileName);
        if(file.exists()) {
            return file;
        }
        URL url = FileUtils.class.getClassLoader().getResource(fileName);
        if(url!=null){
            try {
                fileName = URLDecoder.decode(url.getFile(),"UTF-8");
            } catch (UnsupportedEncodingException e) {}
        }

        file= new File(fileName);
        return file;
    }
}
