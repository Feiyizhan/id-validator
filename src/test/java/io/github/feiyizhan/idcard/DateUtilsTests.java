package io.github.feiyizhan.idcard;

import org.junit.Test;

/**
 * 日期工具类测试类
 * @author 徐明龙 XuMingLong 2019-07-25
 **/
public class DateUtilsTests {

    @Test
    public void testGetRandomDate(){
        for(int i=0;i<30;i++){
            System.out.println(DateUtils.getRandomDate(null,null,null));
        }
    }

    @Test
    public void testGetRandomDateByYear(){
        for(int i=0;i<30;i++){
            System.out.println(DateUtils.getRandomDate(1900,null,null));
        }
    }

    @Test
    public void testGetRandomDateByMonth(){
        for(int i=0;i<30;i++){
            System.out.println(DateUtils.getRandomDate(null,2,null));
        }
    }

    @Test
    public void testGetRandomDateByDay(){
        for(int i=0;i<30;i++){
            System.out.println(DateUtils.getRandomDate(null,null,1));
        }
    }

    @Test
    public void testGetRandomDateByYearMonth(){
        for(int i=0;i<30;i++){
            System.out.println(DateUtils.getRandomDate(1900,2,null));
        }
    }

    @Test
    public void testGetRandomDateByYearDay(){
        for(int i=0;i<30;i++){
            System.out.println(DateUtils.getRandomDate(1900,null,1));
        }
    }

    @Test
    public void testGetRandomDateByMonthDay(){
        for(int i=0;i<30;i++){
            System.out.println(DateUtils.getRandomDate(null,2,1));
        }
    }

}
