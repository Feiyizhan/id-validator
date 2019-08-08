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
        Assert.assertTrue(IdCardUtils.isValid("130223198605246136"));
    }

    @Test
    public void testGetIdCardInfo(){
        System.out.println(IdCardUtils.getIdCardInfo("130223198605246136"));
        System.out.println(IdCardUtils.getIdCardInfo("13053519860730352X"));
        System.out.println(IdCardUtils.getIdCardInfo("511123410630163"));
        System.out.println(IdCardUtils.getIdCardInfo("140830201911296054"));

    }

    @Test
    public void testUpgradeId(){
        System.out.println(IdCardUtils.getIdCardInfo("610104620927690"));
        String newIdCard = IdCardUtils.upgradeId("610104620927690");
        System.out.println(newIdCard);
        System.out.println(IdCardUtils.getIdCardInfo(newIdCard));

    }

    @Test
    public void TestFakeId(){
        //测试完全随机
        for(int i=0;i<10;i++){
            String newIdCard = IdCardUtils.fakeId(null,null,null,null,null,null);
            System.out.println(newIdCard);
            System.out.println(IdCardUtils.getIdCardInfo(newIdCard));
        }

    }

    @Test
    public void TestFakeIdWith15Bit(){
        //测试随机生成15位
        for(int i=0;i<10;i++){
            String newIdCard = IdCardUtils.fakeId(false,null,null,null,null,null);
            System.out.println(newIdCard);
            System.out.println(IdCardUtils.getIdCardInfo(newIdCard));
        }
    }

    @Test
    public void TestFakeIdWithProvince(){
        //测试随机生成指定省的
        for(int i=0;i<10;i++){
            String newIdCard = IdCardUtils.fakeId(true,"广东省",null,null,null,null);
            System.out.println(newIdCard);
            System.out.println(IdCardUtils.getIdCardInfo(newIdCard));
        }
        //测试随机生成指定直辖市的
        for(int i=0;i<10;i++){
            String newIdCard = IdCardUtils.fakeId(true,"上海市",null,null,null,null);
            System.out.println(newIdCard);
            System.out.println(IdCardUtils.getIdCardInfo(newIdCard));
        }
        //测试随机生成指定台湾
        for(int i=0;i<10;i++){
            String newIdCard = IdCardUtils.fakeId(true,"台湾省",null,null,null,null);
            System.out.println(newIdCard);
            System.out.println(IdCardUtils.getIdCardInfo(newIdCard));
        }
    }

    @Test
    public void TestFakeIdWithCity(){
        //测试随机生成指定市的
        for(int i=0;i<10;i++){
            String newIdCard = IdCardUtils.fakeId(true,"宁波市",null,null,null,null);
            System.out.println(newIdCard);
            System.out.println(IdCardUtils.getIdCardInfo(newIdCard));
        }
    }
    @Test
    public void TestFakeIdWithCounty(){
        //测试随机生成指定直辖市区的
        for(int i=0;i<10;i++){
            String newIdCard = IdCardUtils.fakeId(true,"静安区",null,null,null,null);
            System.out.println(newIdCard);
            System.out.println(IdCardUtils.getIdCardInfo(newIdCard));
        }

        //测试随机生成指定县的
        for(int i=0;i<10;i++){
            String newIdCard = IdCardUtils.fakeId(true,"新丰县",null,null,null,null);
            System.out.println(newIdCard);
            System.out.println(IdCardUtils.getIdCardInfo(newIdCard));
        }
    }

    @Test
    public void TestFakeIdWithYear(){
        //测试随机生成指定直辖市区的
        for(int i=0;i<10;i++){
            String newIdCard = IdCardUtils.fakeId(true,"静安区",2019,null,null,null);
            System.out.println(newIdCard);
            System.out.println(IdCardUtils.getIdCardInfo(newIdCard));
        }
    }

    @Test
    public void TestFakeIdWithMonth(){
        //测试随机生成指定直辖市区的
        for(int i=0;i<10;i++){
            String newIdCard = IdCardUtils.fakeId(true,"静安区",2019,2,null,null);
            System.out.println(newIdCard);
            System.out.println(IdCardUtils.getIdCardInfo(newIdCard));
        }
    }

    @Test
    public void TestFakeIdWithDay(){
        //测试随机生成指定直辖市区的
        for(int i=0;i<10;i++){
            String newIdCard = IdCardUtils.fakeId(true,"静安区",2019,3,15,null);
            System.out.println(newIdCard);
            System.out.println(IdCardUtils.getIdCardInfo(newIdCard));
        }
    }

    @Test
    public void TestFakeIdWithSex(){
        //测试随机生成指定直辖市区的男性
        for(int i=0;i<10;i++){
            String newIdCard = IdCardUtils.fakeId(true,"静安区",2019,3,15,1);
            System.out.println(newIdCard);
            System.out.println(IdCardUtils.getIdCardInfo(newIdCard));
        }
        //测试随机生成指定直辖市区的女性
        for(int i=0;i<10;i++){
            String newIdCard = IdCardUtils.fakeId(true,"静安区",2019,3,15,0);
            System.out.println(newIdCard);
            System.out.println(IdCardUtils.getIdCardInfo(newIdCard));
        }
    }
}
