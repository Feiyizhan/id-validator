package io.github.feiyizhan.idcard;

import lombok.*;

import java.util.List;

/**
 * 身份证号信息
 * @author 徐明龙 XuMingLong 2019-07-24
 **/
@Getter@Setter@ToString@EqualsAndHashCode@NoArgsConstructor@AllArgsConstructor@Builder
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
