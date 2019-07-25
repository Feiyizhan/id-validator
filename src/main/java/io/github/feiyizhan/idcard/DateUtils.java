package io.github.feiyizhan.idcard;

import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

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

    /**
     * 随机生成日期
     * <p>
     * year 年，合法的值为1900~本年，默认或参数非法，则随机取1900~本年的年；
     * <p>
     * month 月,合法的值为1-12，默认或参数非法，则随机取1~12的月；
     * <p>
     * day 日,合法的值为1-31, 默认或参数非法，则随机取1~31的日；
     * 如果是随机到的月或者指定的月为2月份：
     * <p>
     *  1、如果没有指定日，则随机生成有效的2月份的日期
     *  2、如果指定了日，但指定的日不是2月份有效的日期，则随机生成有效的2月份的日期
     *  3、如果指定了日期，器指定的日是2月份有效的日期，使用该日期
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param year 年
     * @param month 月
     * @param day 日
     * @return java.time.LocalDate
     */
    public static LocalDate getRandomDate(Integer year,Integer month,Integer day){
        LocalDate now = getToday();
        int currentYear = now.get(ChronoField.YEAR);
        int currentMonth = now.get(ChronoField.MONTH_OF_YEAR);
        int currentDay = now.get(ChronoField.DAY_OF_MONTH);
        if(year == null || year <1900 || year > currentYear){
            year = RandomUtils.nextInt(1900,currentYear+1);
        }
        if(month == null || month <1 || month > 12){
            if(year==currentYear){
                month = RandomUtils.nextInt(1,currentMonth);
            }else{
                month = RandomUtils.nextInt(1,13);
            }
        }
        if(day==null || day<1 || day>31){
            LocalDate date = LocalDate.of(year,month,1);
            if(year==currentYear && month == currentMonth){
                day = RandomUtils.nextInt(1,currentDay+1);
            }else{
                day = RandomUtils.nextInt(1,getLastDayForMonth(date).getDayOfMonth()+1);
            }
        }
        return LocalDate.of(year,month,day);


    }

    /**
     * 随机生成日期
     * <p>
     * year 年，合法的值为1900~本年，默认或参数非法，则随机取1900~本年的年；
     * <p>
     * month 月,合法的值为1-12，默认或参数非法，则随机取1~12的月；
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param year 年
     * @param month 月
     * @return java.time.LocalDate
     */
    public static LocalDate getRandomDate(Integer year,Integer month){
        return getRandomDate(year,month,null);
    }

    /**
     * 随机生成日期
     * <p>
     * year 年，合法的值为1900~本年，默认或参数非法，则随机取1900~本年的年；
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param year 年
     * @return java.time.LocalDate
     */
    public static LocalDate getRandomDateByYear(Integer year){
        return getRandomDate(year,null,null);
    }

    /**
     * 获取东八区的当前日期
     * @author 徐明龙 XuMingLong 2019-07-23
     * @return java.time.LocalDate
     */
    public static LocalDate getToday(){
        return LocalDate.now(ZoneId.of("UTC+8"));
    }


    /**
     * 获取指定日期的当月第一天
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param date 日期
     * @return java.time.LocalDate
     */
    public static LocalDate getFirstDayForMonth(LocalDate date){
        if(date==null){
            return null;
        }
        return LocalDate.of(date.getYear(),date.getMonthValue(),1);
    }

    /**
     * 获取指定日期的当月最后一天
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param date 日期
     * @return java.time.LocalDate
     */
    public static LocalDate getLastDayForMonth(LocalDate date){
        if(date==null){
            return null;
        }
        LocalDate nextMonth = date.plusMonths(1);
        LocalDate nextMonthFirstDay = getFirstDayForMonth(nextMonth);
        LocalDate lastDay = nextMonthFirstDay.minusDays(1);
        return LocalDate.of(lastDay.getYear(),lastDay.getMonthValue(),lastDay.getDayOfMonth());
    }




}
