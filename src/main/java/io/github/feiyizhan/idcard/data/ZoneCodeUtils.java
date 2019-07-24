package io.github.feiyizhan.idcard.data;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

import static io.github.feiyizhan.idcard.data.CurrentZoneCodeData.CURRENT_ZONE_CODE_MAP;
import static io.github.feiyizhan.idcard.data.HistoryZoneCodeData.HISTORY_ZONE_CODE_MAP;

/**
 * 区域编码工具类
 * @author 徐明龙 XuMingLong 2019-07-23
 **/
public class ZoneCodeUtils {

    /**
     * 获取区域编码的描述,一个编号对应多个区域值时，取year在有效期范围内的值
     * <br/> 如果year为null 取有效期最晚的。
     * @author 徐明龙 XuMingLong 2019-07-24
     * @param zoneCode
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
     * @param zoneCode
     * @param year
     * @param separator
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
            sb.append( getZoneDescriptionFun.apply(secondLevelZoneCode)).append(separator);
        }
        sb.append(zoneDescription);
        return sb.toString();
    }


    /**
     * 获取区域编码的描述,一个编号对应多个区域值时，取有效期最晚的。
     * @param zoneCode
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
     * @param zoneCode
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
     * @param zoneCode
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



}
