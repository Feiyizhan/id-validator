package io.github.feiyizhan.idcard;

import io.github.feiyizhan.idcard.data.ZoneCodeUtils;
import org.junit.Test;

/**
 * 区域编码工具测试类
 * @author 徐明龙 XuMingLong 2019-07-23
 **/
public class ZoneCodeUtilsTests {

    @Test
    public void testGetZoneFullDescription(){
        String fullDescription = ZoneCodeUtils.getZoneFullDescription("362502","-");
        System.out.println(fullDescription);
    }

    @Test
    public void testGetRandomZoneCode(){
        for(int i=0;i<20;i++){
            System.out.println(ZoneCodeUtils.getRandomZoneCode("广东省"));
        }
        for(int i=0;i<20;i++){
            System.out.println(ZoneCodeUtils.getRandomZoneCode("上海市"));
        }
        for(int i=0;i<20;i++){
            System.out.println(ZoneCodeUtils.getRandomZoneCode("台湾省"));
        }
        for(int i=0;i<20;i++){
            System.out.println(ZoneCodeUtils.getRandomZoneCode("测试省"));
        }
    }
}
