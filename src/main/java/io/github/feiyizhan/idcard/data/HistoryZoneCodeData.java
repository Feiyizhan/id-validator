package io.github.feiyizhan.idcard.data;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.feiyizhan.idcard.FileUtils;
import io.github.feiyizhan.idcard.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 行政区划代码（地址码）
 * 中华人民共和国民政部权威数据
 * <p>
 * 注1：台湾省、香港特别行政区和澳门特别行政区暂缺地市和区县信息
 * <p>
 * 注2：每月发布的区划变更表是根据区划变更地的统计人员在统计信息系统更新后的情况所绘制，与区划变更文件发布的时间有一定的延迟性，但在每年的最后一次发布变更情况后与区划全年变更文件保持一致。
 * <p>
 * Data Source: http://www.mca.gov.cn/article/sj/xzqh/
 * @author 徐明龙 XuMingLong 2019-07-23
 * @program: id-validator
 * @description: 历史区域编码数据
 **/
@Log4j2
public class HistoryZoneCodeData {

    /**
     * 当前区域编码Map
     * @author 徐明龙 XuMingLong 2019-07-23
     */
    final static Map<String, Map<Integer,ZoneCodeData>> HISTORY_ZONE_CODE_MAP = new LinkedHashMap<>();

    static{
        //加载数据
        try (FileChannel fc = new FileInputStream(FileUtils.getFile("history_zone_code_data_201905.json")).getChannel()) {
            ByteBuffer buff = ByteBuffer.allocate((int) fc.size());
            fc.read(buff);
            buff.flip();
            String str = new String(buff.array(), "utf-8");
            LinkedHashMap<String, List<ZoneCodeData>> dataMap = JsonUtils.JsonToObject(str, new TypeReference<LinkedHashMap<String, List<ZoneCodeData>>>() {});
            for (Map.Entry<String,List<ZoneCodeData>> e : dataMap.entrySet()){
                for(ZoneCodeData z:e.getValue()){
                    z.setCode(e.getKey());
                }
                Map<Integer,ZoneCodeData> map = e.getValue().stream().collect(Collectors.toMap(
                    ZoneCodeData::getEndYear,
                    Function.identity(),
                    (v1,v2)->v1,
                    LinkedHashMap::new
                ));
                HISTORY_ZONE_CODE_MAP.put(e.getKey(),map);
            }

        } catch (FileNotFoundException e) {
            log.warn("历史区域编码数据文件不存在",e);
        } catch (IOException e) {
            log.warn("读取历史区域编码数据文件失败",e);
        }
    }



    /**
     * 区域数据对象
     * @author 徐明龙 XuMingLong 2019-07-23
     */
    @Getter@Setter@ToString
    public static class ZoneCodeData{
        String code;
        String address;
        int startYear;
        int endYear;

    }

}
