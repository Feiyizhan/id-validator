package io.github.feiyizhan.idcard;

import io.github.feiyizhan.idcard.data.ZoneCodeUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static io.github.feiyizhan.idcard.DateUtils.*;

/**
 *
 * 身份证工具类
 * 1、号码的结构
 * 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。从左至右依次为：六位数字地址码，
 * 八位数字出生日期码，三位数字顺序码和一位数字校验码。
 * 2、地址码(前六位数）
 * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
 * 3、出生日期码（第七位至十四位）
 * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
 * 4、顺序码（第十五位至十七位）
 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，
 * 顺序码的奇数分配给男性，偶数分配给女性。
 * 5、校验码（第十八位数）
 * （1）十七位数字本体码加权求和公式 S = Sum(Ai Wi), i = 0, , 16 ，先对前17位数字的权求和 ;
 * Ai:表示第i位置上的身份证号码数字值; Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
 * （2）计算模 Y = mod(S, 11)
 * （3）通过模( 0 1 2 3 4 5 6 7 8 9 10)得到对应的校验码 Y:1 0 X 9 8 7 6 5 4 3 2
 *
 * @author 徐明龙 XuMingLong 2019-07-23
 **/
public class IdCardUtils {

    /**
     * 加权值
     * @author 徐明龙 XuMingLong 2019-07-23
     */
    private final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 校验位
     * @author 徐明龙 XuMingLong 2019-07-23
     */
    private final static char[] PARITY_BIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};


    /**
     * 身份证允许的最小日期
     * @author 徐明龙 XuMingLong 2019-07-23
     */
    private static final LocalDate MIN_DATE = convertToDate("19000101",DATE_FORMAT_1);

    /**
     * 生成可通过校验的假数据
     * <p>
     * is18Bit 是否生成 18 位号码，默认为 true,false生成15位号码;
     * <p>
     * address 地址，即省市县三级地区官方全称，如北京市、台湾省、香港特别行政区、深圳市、黄浦区等，
     * 可以只提供省或者市，将会取省或市下随机的区县，默认或参数非法，则生成合法的随机地址；
     * <p>
     * year 出生日期-年，合法的值为1900~本年，默认或参数非法，则随机取1900~本年的年；
     * <p>
     * month 出生日期-月,合法的值为1-12，默认或参数非法，则随机取1~12的月；
     * <p>
     * day 日,合法的值为1-31, 默认或参数非法，则随机取1~31的日；
     * <p>
     *  1、如果随机到年月为本年月，且如果指定的日大于`当天`,则随机取1~`当天`的日;
     * <p>
     *  2、如果随机到的年月不是本年月，且如果指定的日大于`所在月的最后一天`,则随机取1~`所在月的最后一天`的日;
     * <p>
     *  3、否则使用指定的日；
     * <p>
     * sex 性别，1为男，0为女，默认或参数非法，则生成合法的随机性别；
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param is18Bit 是否生成 18 位号码
     * @param address 地址，即省市县三级地区官方全称
     * @param year 出生日期-年
     * @param month 出生日期-月
     * @param day 出生日期-日
     * @param sex 性别
     * @return java.lang.String
     */
    public static String fakeId(Boolean is18Bit,String address,Integer year,Integer month,Integer day,Integer sex ){
        //设置长度
        int length = BooleanUtils.isNotFalse(is18Bit)?18:15;
        //获取区域编码
        String zoneCode = ZoneCodeUtils.getRandomZoneCode(address);
        //获取出生日期
        LocalDate birthday = DateUtils.getRandomDate(year,month,day);
        //获取顺序码
        int sequenceCode = RandomUtils.nextInt(1,999);
        sex = sex==null || (sex >1 && sex <0) ? RandomUtils.nextInt(0,2):sex;
        int evenOrOdd = (sequenceCode&1);
        if(sex == 1 && evenOrOdd == 1 ){
            //需要偶数，但随机到奇数
            sequenceCode+=1;
        }else if(sex==0 && evenOrOdd == 0){
            //需要奇数，但随机到偶数
            sequenceCode+=1;
        }
        //计算校验码
        StringBuilder sb = new StringBuilder();
        sb.append(zoneCode);
        if(length==15){
            sb.append(birthday.format(DATE_FORMAT_1).substring(2));
        }else{
            sb.append(birthday.format(DATE_FORMAT_1));
        }
        sb.append(String.format("%03d",sequenceCode));
        //转换为字符数组
        if(length==18){
            final char[] idCardArray = new char[18];
            sb.getChars(0,17,idCardArray,0);
            sb.setLength(0);
            //获取校验码
            idCardArray[17] = getParityBit(idCardArray);
            return new String(idCardArray);
        }else{
            return sb.toString();
        }
    }


    /**
     * 升级15位身份证为18位，如果已经是18位身份证，直接返回，如果身份证校验失败，返回null
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param idCard 身份证号码
     * @return java.lang.String
     */
    public static String upgradeId(String idCard){
        if(!isValid(idCard)){
            return null;
        }
        if(idCard.length()==18){
            return idCard;
        }

        /*
         * 升级算法为: 出生日期转换为8位的日期，再重新计算出校验位
         * <p>
         * 6位日期转换为8位日期时，前两位的年设置为19，因此对于1900年之前的出生的人身份证会处理结果会不正确，现在应该没有19世纪的人了吧:-)
         */
        //先升级为17
        StringBuilder sb = new StringBuilder();
        sb.append(idCard.substring(0,6)).append("19").append(idCard.substring(6));
        //转换为字符数组
        final char[] idCardArray = new char[18];
        sb.getChars(0,17,idCardArray,0);
        sb.setLength(0);
        //获取校验码
        idCardArray[17] = getParityBit(idCardArray);
        return new String(idCardArray);
    }


    /**
     * 获取身份证信息，如果身份证无效返回null
     * @author 徐明龙 XuMingLong 2019-07-24
     * @param idCard 身份证号码
     * @return io.github.feiyizhan.idcard.IdCardInfo
     */
    public static IdCardInfo getIdCardInfo(String idCard){
        if(!isValid(idCard)){
            return null;
        }
        //获取长度
        int length = idCard.length();
        //获取出生日期
        String birthdayStr = getBirthday(idCard);
        //获取LocalDate 对象的出生日期
        LocalDate birthday = convertToDate(birthdayStr, DATE_FORMAT_1);
        //获取年龄
        int age = (int)ChronoUnit.YEARS.between(birthday,getToday());
        //获取区域编码
        String zoneCode = idCard.substring(0, 6);
        //获取区域信息
        String address = ZoneCodeUtils.getZoneFullDescription(zoneCode,birthday.get(ChronoField.YEAR),"-");
        List<String> addressList = Arrays.asList(StringUtils.split(address,"-"));
        address = StringUtils.remove(address,"-");
        //获取顺序码
        String sequenceCode = getSequenceCode(idCard);
        //获取性别
        int sex = Integer.parseInt(sequenceCode)&1;
        //获取生肖
        String chineseZodiac = ChineseZodiacUtils.getChineseZodiac(birthday.get(ChronoField.YEAR));
        //获取星座
        String constellation = ConstellationUtils.getConstellationByMonthAndDay(birthdayStr.substring(4));
        //获取校验码
        String checkBit = null;
        if(length==18){
            checkBit = idCard.substring(length-1).toUpperCase();
        }
        //判断是否废弃
        boolean abandoned = !ZoneCodeUtils.isExistedInCurrent(zoneCode);
        IdCardInfo info = IdCardInfo.builder()
            .zoneCode(zoneCode)
            .birthday(birthday.format(DATE_FORMAT_2))
            .age(age)
            .address(address)
            .addressList(addressList)
            .chineseZodiac(chineseZodiac)
            .constellation(constellation)
            .length(length)
            .sex(sex)
            .checkBit(checkBit)
            .abandoned(abandoned)
            .build();


        return info;
    }


    /**
     * 校验身份证号是否有效，支持15位和18位身份证,不验证地区编号，因为地区编号数据目前没有一个准确的数据源
     * @author 徐明龙 XuMingLong 2020-03-26
     * @param idCard 身份证号码
     * @return boolean
     */
    public static boolean isValidWithDoNotVerifyRegionCode(String idCard){
        //先校验身份证号长度
        
        if (idCard == null || (idCard.length() != 15 && idCard.length() != 18)){
            return false;
        }
        if(idCard.length()==15){
            return isValid_15(idCard,false);
        }else{
            return isValid_18(idCard,false);
        }

    }


    /**
     * 校验身份证号是否有效，支持15位和18位身份证
     * @author 徐明龙 XuMingLong 2019-07-23
     * @param idCard 身份证号码
     * @return boolean
     */
    @Deprecated
    public static boolean isValid(String idCard){
        //先校验身份证号长度
        if (idCard == null || (idCard.length() != 15 && idCard.length() != 18)){
            return false;
        }
        if(idCard.length()==15){
            return isValid_15(idCard,true);
        }else{
            return isValid_18(idCard,true);
        }

    }

    /**
     * 校验15位身份证号码
     * @author 徐明龙 XuMingLong 2019-07-23
     * @param idCard 身份证号码
     * @param verifyRegionCode  是否验证地区编号
     * @return boolean
     */
    private static boolean isValid_15(String idCard,boolean verifyRegionCode){

        //转换为字符数组
        final char[] idCardArray = idCard.toCharArray();
        for (int i = 0; i < idCardArray.length; i++) {
            if (idCardArray[i] < '0' || idCardArray[i] > '9'){
                return false;
            }
        }

        //校验区位码
        if(verifyRegionCode){
            String zoneCode = idCard.substring(0, 6);
            if(!ZoneCodeUtils.isExisted(zoneCode)){
                return false;
            }
        }
        //校验日期
        if(!checkBirthDay(getBirthday(idCard))){
            return false;
        }
        return true;
    }

    /**
     * 校验18位身份证号码
     * @author 徐明龙 XuMingLong 2019-07-23
     * @param idCard 身份证号码
     * @param verifyRegionCode  是否验证地区编号
     * @return boolean
     */
    private static boolean isValid_18(String idCard,boolean verifyRegionCode){
        //转换为大写的字符数组
        final char[] idCardArray = idCard.toUpperCase().toCharArray();

        //检查每一位的是否有效，并计算前17位的权和
        int sumPower = 0;
        for (int i = 0; i < idCardArray.length; i++) {
            if (i == idCardArray.length - 1 && idCardArray[i] == 'X'){
                break;
            }
            if (idCardArray[i] < '0' || idCardArray[i] > '9'){
                return false;
            }
            if (i < idCardArray.length - 1) {
                sumPower += (idCardArray[i] - '0') * POWER_LIST[i];
            }
        }

        //校验区位码
        if(verifyRegionCode){
            String zoneCode = idCard.substring(0, 6);
            if(!ZoneCodeUtils.isExisted(zoneCode)){
                return false;
            }
        }

        //校验日期,日期必须有效，且不能大于当天，不能小于1900-01-01
        if(!checkBirthDay(getBirthday(idCard))){
            return false;
        }

        //校验"校验位"
        return idCardArray[idCardArray.length - 1] == PARITY_BIT[sumPower % 11];
    }

    /**
     * 获取顺序码
     * @author 徐明龙 XuMingLong 2019-07-24
     * @param idCard 身份证号码
     * @return java.lang.String
     */
    private static String getSequenceCode(String idCard){
        return idCard.length()==18?idCard.substring(14, 17):idCard.substring(12);
    }

    /**
     * 获取身份证上出生日期
     * @author 徐明龙 XuMingLong 2019-07-24
     * @param idCard 身份证号码
     * @return java.lang.String
     */
    private static String getBirthday(String idCard){
        return idCard.length()==18?idCard.substring(6, 14):String.join("","19",idCard.substring(6, 12));
    }

    /**
     * 检查出生日期，校验日期,日期必须有效，且不能大于当天，不能小于1900-01-01
     * @author 徐明龙 XuMingLong 2019-07-23
     * @param dateStr 出生日期字符串
     * @return boolean
     */
    private static boolean checkBirthDay(String dateStr){
        LocalDate date = convertToDate(dateStr, DATE_FORMAT_1);
        return !(date==null|| date.isAfter(getToday()) || date.isBefore(MIN_DATE));
    }


    /**
     * 获取日历中的两位数的年
     * @author 徐明龙 XuMingLong 2019-07-23
     * @return int
     */
    private static int getYear2BitWithCalendar() {
        //获取当前系统日落
        GregorianCalendar curDay = new GregorianCalendar(TimeZone.getTimeZone("UTC+8"));
        //获取日历中的年
        int curYear = curDay.get(Calendar.YEAR);
        //获取两位数的年
        int year2bit = Integer.parseInt(String.valueOf(curYear).substring(2));
        return year2bit;
    }

    /**
     * 获取校验码
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param idCardArray
     * @return char
     */
    private static char getParityBit(final char[] idCardArray){
        //计算校验位
        int sumPower = 0;
        for (int i = 0; i < 17; i++) {
            sumPower += (idCardArray[i] - '0') * POWER_LIST[i];
        }
        //获取校验码
        return PARITY_BIT[sumPower % 11];
    }

}
