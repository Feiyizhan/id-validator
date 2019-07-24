
package io.github.feiyizhan.idcard;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;

/**
 * JSON Util类
 * 
 * @author 徐明龙 XuMingLong
 * @createDate 2018-04-03
 */
@Log4j2
public class JsonUtils {

    private JsonUtils() {};
    // Jackson Object Mapper对象
    private static ObjectMapper OM = new ObjectMapper();
    static {
        // 设置忽略不存在的属性
        OM.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //设置下划线转驼峰
        OM.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }


    /**
     * 转换对象为JSON字符串，失败返回空字符串。
     * 
     * @author 徐明龙 XuMingLong
     * @createDate 2018-04-03
     * @param obj
     * @return
     */
    public static String objectToJson(Object obj) {
        try {
            return OM.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("转换对象为JSON字符串失败",e);
        }
        return "";
    }


    /**
     * 转换JSON字符串为对象，失败返回null。
     * 
     * @author 徐明龙 XuMingLong
     * @createDate 2018-04-03
     * @param json
     * @param javaType
     * @return
     */
    public static <T> T JsonToObject(String json, TypeReference<T> javaType) {
        try {
            return OM.readValue(json, javaType);
        } catch (JsonParseException e) {
            log.error("转换JSON字符串为对象失败",e);
        } catch (JsonMappingException e) {
            log.error("转换JSON字符串为对象失败",e);
        } catch (IOException e) {
            log.error("转换JSON字符串为对象失败",e);
        }
        return null;
    }


  


    /**
     * 转换JSON字符串为对象，失败返回null。
     * 
     * @author 徐明龙 XuMingLong
     * @createDate 2018-04-03
     * @param json
     * @param javaType
     * @return
     */
    public static <T> T JsonToObject(InputStream json, TypeReference<T> javaType) {
        try {
            return OM.readValue(json, javaType);
        } catch (JsonParseException e) {
            log.error("转换JSON字符串为对象失败",e);
        } catch (JsonMappingException e) {
            log.error("转换JSON字符串为对象失败",e);
        } catch (IOException e) {
            log.error("转换JSON字符串为对象失败",e);
        }
        return null;
    }


  

}
