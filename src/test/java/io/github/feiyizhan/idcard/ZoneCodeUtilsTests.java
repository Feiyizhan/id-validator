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
        String fullDescription = ZoneCodeUtils.getZoneFullDescription("622627","-");
        System.out.println(fullDescription);
    }
}
