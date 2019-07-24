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
    <version>1.0</version>
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


### 升级身份证号码
15 位号码升级为 18 位：


## 参考资料

* [中华人民共和国公民身份号码](https://zh.wikipedia.org/wiki/中华人民共和国公民身份号码)

* [中华人民共和国民政部：行政区划代码](http://www.mca.gov.cn/article/sj/xzqh/)

* [中华人民共和国行政区划代码历史数据集](https://github.com/jxlwqq/address-code-of-china)

* [国务院办公厅关于印发《港澳台居民居住证申领发放办法》的通知](http://www.gov.cn/zhengce/content/2018-08/19/content_5314865.htm)

* [港澳台居民居住证](https://zh.wikipedia.org/wiki/港澳台居民居住证)


## License
MIT
