package io.github.feiyizhan.idcard.data;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static io.github.feiyizhan.idcard.data.CurrentZoneCodeData.*;
import static io.github.feiyizhan.idcard.data.HistoryZoneCodeData.HISTORY_ZONE_CODE_MAP;

/**
 * 区域编码工具类
 * @author 徐明龙 XuMingLong 2019-07-23
 **/
public class ZoneCodeUtils {

    /**
     * 随机返回一个指定地址下的有效的区县的区域编码
     * <p>
     * 可以提供省或者市，将会取省或市下随机的区县，默认或参数非法，则生成合法的随机地址；
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param address 地址，即省市县三级地区官方全称
     * @return java.lang.String
     */
    public static String getRandomZoneCode(String address){
        if(StringUtils.isBlank(address)){
            return getRandomZoneCode();
        }
        //获取地址对应的区域编码
        String zoneCode = getZoneCode(address);
        //地址无效，返回随机的区域编码
        if(zoneCode==null){
            return getRandomZoneCode();
        }
        //返回指定区域编码下的随机区域编码
        return getRandomZoneCodeByZoneCode(zoneCode);
    }

    /**
     * 随机返回一个当前市下有效的区县的区域编码
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param city 市
     * @return java.lang.String
     */
    public static String getRandomZoneCodeByCity(String city){
        if(StringUtils.isBlank(city)){
            return getRandomZoneCode();
        }
        String cityZoneCode = getZoneCode(city);
        //地址无效，返回随机的区域编码
        if(city==null){
            return getRandomZoneCode();
        }
        //区域编码不是市的区域编码，返回随机的区域编码
        if(!CURRENT_CITY_COUNTY_ZONE_CODE_MAP.containsKey(cityZoneCode)){
            return getRandomZoneCode();
        }
        //随机获取市下区县的区域编码
        return getRandomZoneCodeByCityZoneCode(cityZoneCode);
    }

    /**
     * 随机返回一个当前市区域编码下有效的区县的区域编码
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param cityZoneCode 市区域编码
     * @return java.lang.String
     */
    private static String getRandomZoneCodeByCityZoneCode(String cityZoneCode){
        //区域编码不是市的区域编码，直接返回
        if(!CURRENT_CITY_COUNTY_ZONE_CODE_MAP.containsKey(cityZoneCode)){
            return cityZoneCode;
        }
        //获取市下的县的区域编码
        List<String> countyList = CURRENT_CITY_COUNTY_ZONE_CODE_MAP.get(cityZoneCode);
        //如果市下没有任何区县，则直接返回市的区域编码
        if(CollectionUtils.isEmpty(countyList)){
            return cityZoneCode;
        }
        //随机获取市下区县的区域编码
        return countyList.get(RandomUtils.nextInt(0,countyList.size()));
    }

    /**
     * 随机返回一个当前省下有效的区县的区域编码
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param province 省的地址
     * @return java.lang.String
     */
    public static String getRandomZoneCodeByProvince(String province){
        if(StringUtils.isBlank(province)){
            return getRandomZoneCode();
        }
        String provinceZoneCode = getZoneCode(province);
        //地址无效，返回随机的区域编码
        if(provinceZoneCode==null){
            return getRandomZoneCode();
        }
        //区域编码不是是省的区域编码，返回随机的区域编码
        if(!CURRENT_PROVINCE_CITY_ZONE_CODE_MAP.containsKey(provinceZoneCode)){
            return getRandomZoneCode();
        }

        //随机获取省下区县的区域编码
        return getRandomZoneCodeByProvinceZoneCode(provinceZoneCode);
    }

    /**
     * 随机返回一个当前省区域编码下有效的区县的区域编码
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param provinceZoneCode 省的区域编码
     * @return java.lang.String
     */
    private static String getRandomZoneCodeByProvinceZoneCode(String provinceZoneCode){
        //区域编码不是省的区域编码，直接返回
        if(!CURRENT_PROVINCE_CITY_ZONE_CODE_MAP.containsKey(provinceZoneCode)){
            return provinceZoneCode;
        }
        //获取省下的区县的随机区域编码
        List<String> countyList = CURRENT_PROVINCE_COUNTY_ZONE_CODE_MAP.get(provinceZoneCode);
        //如果省下没有任何区县，则直接返回省的区域编码
        if(CollectionUtils.isEmpty(countyList)){
            return provinceZoneCode;
        }
        //随机获取省下的区县的区域编码
        return countyList.get(RandomUtils.nextInt(0,countyList.size()));
    }

    /**
     * 随机返回一个指定区域编码下有效的区域编码
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param zoneCode 区域编码
     * @return java.lang.String
     */
    private static String getRandomZoneCodeByZoneCode(String zoneCode){
        //判断是否为省的区域编码
        if(CURRENT_PROVINCE_CITY_ZONE_CODE_MAP.containsKey(zoneCode)){
            return getRandomZoneCodeByProvinceZoneCode(zoneCode);
        }
        //判断是否为市的区域编码
        if(CURRENT_CITY_COUNTY_ZONE_CODE_MAP.containsKey(zoneCode)){
            return getRandomZoneCodeByCityZoneCode(zoneCode);
        }
        return zoneCode;
    }

    /**
     * 随机返回一个当前有效的ZoneCode
     * @author 徐明龙 XuMingLong 2019-07-25
     * @return java.lang.String
     */
    public static String getRandomZoneCode(){
        int max = CURRENT_ZONE_CODE_MAP.size();
        String zoneCode =CURRENT_ZONE_CODE_LIST.get(RandomUtils.nextInt(0,max));
        return getRandomZoneCodeByZoneCode(zoneCode);
    }

    /**
     * 获取区域编码的描述,一个编号对应多个区域值时，取year在有效期范围内的值
     * <p>
     * 如果year为null 取有效期最晚的。
     * @author 徐明龙 XuMingLong 2019-07-24
     * @param zoneCode 区域编码
     * @param year 年
     * @return java.lang.String
     */
    public static String getZoneDescription(String zoneCode,Integer year) {
        if(year==null){
            return getZoneDescription(zoneCode);
        }
        String description = CURRENT_ZONE_CODE_MAP.get(zoneCode);
        if(description!=null){
            return description;
        }

        return Optional.ofNullable(HISTORY_ZONE_CODE_MAP.get(zoneCode)).map(r->
            r.values().stream()
                .filter(v->v.getEndYear() >=year && v.getStartYear() <=year)
                .findFirst().map(i->i.getAddress()).orElse(null)
        ).orElse(null);

    }

    /**
     * 获取区域的完整描述，一个编号对应多个区域值时，取year在有效期范围内的值
     * @author 徐明龙 XuMingLong 2019-07-24
     * @param zoneCode 区域编码
     * @param year 指定年份
     * @param separator 分隔符
     * @return java.lang.String
     */
    public static String getZoneFullDescription(String zoneCode, Integer year, String separator) {

        Function<String,String> getZoneDescriptionFun = null;
        if(year==null){
            getZoneDescriptionFun = r-> getZoneDescription(r);
        }else{
            getZoneDescriptionFun = r-> getZoneDescription(r,year);
        }

        String zoneDescription = getZoneDescriptionFun.apply(zoneCode);
        if (zoneDescription == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        //获取顶层的ZoneCode
        String topLevelZoneCode = String.join("", zoneCode.substring(0, 2), "0000");
        //如果顶层的编码和当前编码一致，则直接返回当前的编码的描述
        if (zoneCode.equals(topLevelZoneCode)) {
            return zoneDescription;
        } else {
            sb.append(getZoneDescriptionFun.apply(topLevelZoneCode)).append(separator);
        }

        //获取第二层的ZoneCode的描述
        String secondLevelZoneCode = String.join("", zoneCode.substring(0, 4), "00");
        //如果第二层的编码和当前编码不一致，则再拼上第二次的描述
        if (!zoneCode.equals(secondLevelZoneCode)) {
            Optional.ofNullable(getZoneDescriptionFun.apply(secondLevelZoneCode)).ifPresent(r->{
                sb.append(r).append(separator);
                }
            );
        }
        sb.append(zoneDescription);
        return sb.toString();
    }


    /**
     * 获取区域编码的描述,一个编号对应多个区域值时，取有效期最晚的。
     * @param zoneCode 区域编码
     * @return java.lang.String
     * @author 徐明龙 XuMingLong 2019-07-23
     */
    public static String getZoneDescription(String zoneCode) {
        String description = CURRENT_ZONE_CODE_MAP.get(zoneCode);
        if(description!=null){
            return description;
        }else{
            return Optional.ofNullable(HISTORY_ZONE_CODE_MAP.get(zoneCode)).map(r->
                r.values().stream()
                    .sorted(Comparator.comparing(HistoryZoneCodeData.ZoneCodeData::getEndYear)
                        .reversed())
                    .findFirst().map(i->i.getAddress()).orElse(null)
            ).orElse(null);
        }
    }

    /**
     * 判断区域编码是否存在
     * @param zoneCode 区域编码
     * @return boolean
     * @author 徐明龙 XuMingLong 2019-07-23
     */
    public static boolean isExisted(String zoneCode) {
        //判断当前map是否存在
        if(CURRENT_ZONE_CODE_MAP.containsKey(zoneCode)){
            return true;
        }
        //判断在历史map是否存在
        return HISTORY_ZONE_CODE_MAP.containsKey(zoneCode) ;
    }

    /**
     * 判断区域编码在当前区域编码表中存在
     * @author 徐明龙 XuMingLong 2019-07-24
     * @param zoneCode 区域编码
     * @return boolean
     */
    public static boolean isExistedInCurrent(String zoneCode){
        return CURRENT_ZONE_CODE_MAP.containsKey(zoneCode);
    }

    /**
     * 获取区域的完整描述，一个编号对应多个区域值时，取有效期最晚的。
     * @author 徐明龙 XuMingLong 2019-07-23
     * @param zoneCode 区域代码
     * @param separator 分隔符
     * @return java.lang.String
     */
    public static String getZoneFullDescription(String zoneCode, String separator) {
        return getZoneFullDescription(zoneCode,null,separator);
    }

    /**
     * 获取指定地址当前的区域编码
     * @author 徐明龙 XuMingLong 2019-07-25
     * @param address 省|市|县
     * @return java.lang.String
     */
    public static String getZoneCode(String address){
        return CURRENT_ADDRESS_MAP.get(StringUtils.trimToEmpty(address));
    }


}
