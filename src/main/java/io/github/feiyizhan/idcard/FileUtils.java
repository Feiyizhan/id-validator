package io.github.feiyizhan.idcard;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件工具类
 * @author 徐明龙 XuMingLong 2019-07-23
 **/
public class FileUtils {

    /**
     * 获取输入流
     * @author 徐明龙 XuMingLong 2019-08-08
     * @param fileName 文件名
     * @return java.io.InputStream
     */
    public static InputStream getInputStream(String fileName) throws IOException {
        return FileUtils.class.getClassLoader().getResource(fileName).openStream();
    }
}
