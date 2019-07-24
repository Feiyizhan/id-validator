package io.github.feiyizhan.idcard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * 日期工具类
 * @author 徐明龙 XuMingLong 2019-07-24
 **/
public class DateUtils {

    /**
     * 日期格式化器 yyyyMMdd
     * @author 徐明龙 XuMingLong 2019-07-23
     */
    public static final DateTimeFormatter DATE_FORMAT_1 = new DateTimeFormatterBuilder().appendPattern("yyyyMMdd")
        .toFormatter();

    /**
     * 日期格式化器 yyyy-MM-dd
     * @author 徐明龙 XuMingLong 2019-07-23
     */
    public static final DateTimeFormatter DATE_FORMAT_2 = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd")
        .toFormatter();

    /**
     * 转换为日期对象，转换失败返回null
     * @author 徐明龙 XuMingLong 2019-07-23
     * @param dateStr 日期字符串
     * @param formatter 日期格式化器
     * @return java.time.LocalDate
     */
    public static LocalDate convertToDate(String dateStr,DateTimeFormatter formatter){
        try {
            return LocalDate.parse(dateStr,formatter);
        }catch (Exception e){
            return null;
        }
    }
}
