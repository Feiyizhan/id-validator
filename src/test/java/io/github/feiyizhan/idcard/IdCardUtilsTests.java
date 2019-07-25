package io.github.feiyizhan.idcard;

import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Test;

/**
 * 身份证号工具类测试代码
 * @author 徐明龙 XuMingLong 2019-07-23
 * @program: id-validator
 **/
@Log4j2
public class IdCardUtilsTests {

    @Test
    public void testValidIdCard(){
        Assert.assertTrue(IdCardUtils.isValid("13053519860730352X"));
    }

    @Test
    public void testGetIdCardInfo(){
        System.out.println(IdCardUtils.getIdCardInfo("13053519860730352X"));
    }

    @Test
    public void testUpgradeId(){
        System.out.println(IdCardUtils.getIdCardInfo("610104620927690"));
        String newIdCard = IdCardUtils.upgradeId("610104620927690");
        System.out.println(newIdCard);
        System.out.println(IdCardUtils.getIdCardInfo(newIdCard));

    }
}
