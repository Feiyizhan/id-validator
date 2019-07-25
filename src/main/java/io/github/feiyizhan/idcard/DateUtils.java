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
     * <p>
     *  1、如果随机到年月为本年月，且如果指定的日大于`当天`,则随机取1~`当天`的日;
     * <p>
     *  2、如果随机到的年月不是本年月，且如果指定的日大于`所在月的最后一天`,则随机取1~`所在月的最后一天`的日;
     * <p>
     *  3、否则使用指定的日；
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
        //取本月最后一天
        LocalDate lastDay = getLastDayForMonth(LocalDate.of(year,month,1));
        //取有效的最后一天
        int validLastDay = 0;
        if(year==currentYear && month == currentMonth){
            validLastDay = currentDay;
        }else{
            validLastDay = lastDay.getDayOfMonth();
        }
        //如果指定的day无效，则重新设置day
        if(day==null || day<1 || day>validLastDay ){
            day = RandomUtils.nextInt(1,validLastDay+1);
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
