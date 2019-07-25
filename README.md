# id-validator Java版本
**中华人民共和国居民身份证**、**中华人民共和国港澳居民居住证**以及**中华人民共和国台湾居民居住证**号码验证工具（Java 版）支持 15 位与 18 位号码。
S版）

Chinese Mainland Personal ID Card Validation


* [Python 版本](https://github.com/jxlwqq/id-validator.py)
* [Ruby 版本](https://github.com/renyijiu/id_validator)
* [JavaScript 版本](https://github.com/mc-zone/IDValidator)
* [PHP 版本](https://github.com/jxlwqq/id-validator)
* [Java 版本](https://github.com/Feiyizhan/id-validator)



[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)

## 感谢
[jxlwqq](https://github.com/jxlwqq)

## 引入

```xml
  <dependency>
    <groupId>io.github.Feiyizhan</groupId>
    <artifactId>id-validator</artifactId>
    <version>1.2</version>
  </dependency>
```


### 验证身份证号合法性

验证身份证号是否合法，合法返回 `true`，不合法返回 `false`：

```java
public class IdCardUtilsTests {

    @Test
    public void testValidIdCard(){
        Assert.assertTrue(IdCardUtils.isValid("13053519860730352X"));
    }
}
```

### 获取身份证号信息
```java
    @Test
    public void testGetIdCardInfo(){
        System.out.println(IdCardUtils.getIdCardInfo("13053519860730352X"));
    }

```

返回结果：
```java
public class IdCardInfo {

    /**
     * 区域编码
     */
    private String zoneCode;

    /**
     * 地址码是否废弃，1 为废弃的，0 为正在使用的
     */
    private Boolean abandoned;

    /**
     * 地址，例如：广东省深圳市盐田区
     */
    private String address;

    /**
     * 省市区三级列表,例如： ["广东省", "深圳市", "盐田区"]
     */
    private List<String> addressList;

    /**
     * 出生日期，格式：yyyy-MM-dd,例如：1999-01-10
     */
    private String birthday;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 星座,例如：水瓶座
     */
    private String constellation;

    /**
     * 生肖,例如： 卯兔
     */
    private String chineseZodiac;

    /**
     * 性别，1 为男性，0 为女性
     */
    private Integer sex;

    /**
     * 号码长度, 15或18位
     */
    private Integer length;

    /**
     * 校验码
     */
    private String checkBit;

}

```


> 注：判断地址码是否废弃的依据是[中华人民共和国行政区划代码历史数据集](https://github.com/jxlwqq/address-code-of-china)，本数据集的采集源来自：[中华人民共和国民政部](http://www.mca.gov.cn/article/sj/xzqh//1980/)，每年更新一次。本数据集采用 csv 格式存储，方便大家进行数据分析或者开发其他语言的版本。
### 生成可通过校验的假数据
伪造符合校验的身份证：
参数说明：

- `is18Bit` 是否生成 18 位号码，默认为`true`,`false`生成15位号码;

- `address` 地址，即省市县三级地区官方全称，如北京市、台湾省、香港特别行政区、深圳市、黄浦区等，
 可以只提供省或者市，将会取省或市下随机的区县，默认或参数非法，则生成合法的随机地址；

- `year` 出生日期-年，合法的值为1900~本年，默认或参数非法，则随机取1900~`本年`的年；

- `month` 出生日期-月,合法的值为1-12，默认或参数非法，则随机取1~12的月；
  如果是随机到的或指定的年份为本年，则随机取1~`本月`的月。
  
- `day` 出生日期-日,合法的值为1-31, 默认或参数非法，则随机取1~31的日；
 1. 如果随机到年月为本年月，且如果指定的日大于`当天`,则随机取1~`当天`的日；
 2. 如果随机到的年月不是本年月，且如果指定的日大于`所在月的最后一天`,则随机取1~`所在月的最后一天`的日;
 3. 否则使用指定的日；
- `sex` 性别，`1`为男，`0`为女，默认或参数非法，则生成合法的随机性别；


```java
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

```


### 升级身份证号码
15 位号码升级为 18 位：

```java
 @Test
    public void testUpgradeId(){
        System.out.println(IdCardUtils.getIdCardInfo("610104620927690"));
        String newIdCard = IdCardUtils.upgradeId("610104620927690");
        System.out.println(newIdCard);
        System.out.println(IdCardUtils.getIdCardInfo(newIdCard));

    }

```

## 参考资料

* [中华人民共和国公民身份号码](https://zh.wikipedia.org/wiki/中华人民共和国公民身份号码)

* [中华人民共和国民政部：行政区划代码](http://www.mca.gov.cn/article/sj/xzqh/)

* [中华人民共和国行政区划代码历史数据集](https://github.com/jxlwqq/address-code-of-china)

* [国务院办公厅关于印发《港澳台居民居住证申领发放办法》的通知](http://www.gov.cn/zhengce/content/2018-08/19/content_5314865.htm)

* [港澳台居民居住证](https://zh.wikipedia.org/wiki/港澳台居民居住证)


## License
MIT
