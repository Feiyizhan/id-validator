package io.github.feiyizhan.idcard;

import org.apache.commons.lang3.Range;

import java.util.*;

/**
 * 星座信息
 * @author 徐明龙 XuMingLong 2019-07-24
 **/
public class ConstellationUtils {

    /**
     * 星座Map
     */
    final static Map<Range<String>,String> CONSTELLATION_MAP = new HashMap<>(){
        {
            put(Range.between("01-20","02-18"),"水瓶座");
            put(Range.between("02-19","03-20"),"双鱼座");
            put(Range.between("03-21","04-19"),"白羊座");
            put(Range.between("04-20","05-20"),"金牛座");
            put(Range.between("05-21","06-21"),"双子座");
            put(Range.between("06-22","07-22"),"巨蟹座");
            put(Range.between("07-23","08-22"),"狮子座");
            put(Range.between("08-23","09-22"),"处女座");
            put(Range.between("09-23","10-23"),"天秤座");
            put(Range.between("10-24","11-22"),"天蝎座");
            put(Range.between("11-23","12-21"),"射手座");
            put(Range.between("12-22","01-19",
                    getComparator("12-22","01-19","01-01","12-31")),
                "摩羯座");
        }
    };


    /**
     * 获取比较器，支持12-20 ~ 01-19 格式的范围比较
     * @author 徐明龙 XuMingLong 2019-07-24
     * @param begin 范围的起始值
     * @param end 范围的结束值
     * @param roundBegin 环形数据的起始值
     * @param roundEnd 环形数据的结束值
     * @return java.util.Comparator<T>
     */
    private static <T extends Comparable<? super T>> Comparator<T> getComparator(T begin,T end,T roundBegin,T roundEnd){
        //不是环形数据比较器，直接返回空
        if(roundBegin==null ||roundEnd ==null ){
            return (Comparator<T>)Comparator.naturalOrder();
        }
        //否则返回一个环形数据比较器
        return (c1, c2)->{
            if(c1.compareTo(begin) >-1 && c1.compareTo(roundEnd) <1){
                if(c2.equals(end)){
                    return -1;
                }else{
                    return 1;
                }
            }else if(c1.compareTo(roundBegin) >-1 && c1.compareTo(end) <1){
                if(c2.equals(end)){
                    return -1;
                }else{
                    return 1;
                }

            }else{
                if(c2.equals(end)){
                    return 1;
                }else{
                    return -1;
                }
            }
        };
    }

    /**
     * 根据月日获取星座信息，
     * @author 徐明龙 XuMingLong 2019-07-24
     * @param monthAndDay 月日格式MM-dd
     * @return java.lang.String
     */
    public static String getConstellationByMonthAndDay(String monthAndDay){
        if(monthAndDay==null){
            return null;
        }
        for(Map.Entry<Range<String>,String> e:CONSTELLATION_MAP.entrySet()){
            if(e.getKey().contains(monthAndDay)){
                return e.getValue();
            }
        }
        return null;
    }




}
