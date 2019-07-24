package io.github.feiyizhan.idcard;

import java.util.Arrays;
import java.util.List;

/**
 * 生肖信息
 * @author 徐明龙 XuMingLong 2019-07-24
 **/
public class ChineseZodiacUtils {

    final static List<String> CHINESE_ZODIAC_LIST = Arrays.asList("子鼠","丑牛","寅虎","卯兔","辰龙","巳蛇","午马","未羊","申猴","酉鸡","戌狗","亥猪");

    /**
     * 获取生肖
     * @author 徐明龙 XuMingLong 2019-07-24
     * @param year
     * @return java.lang.String
     */
    public static String getChineseZodiac(int year){
        if(year <0){
            return null;
        }
        return CHINESE_ZODIAC_LIST.get((year-700)%12);
    }
}
