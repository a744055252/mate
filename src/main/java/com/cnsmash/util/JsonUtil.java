package com.cnsmash.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * @author lhb
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper = null;

    static {
        mapper = new ObjectMapper();

        //解析null不报错
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        //忽略不存在get、set的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        DeserializationConfig dc = mapper.getDeserializationConfig();
        // 设置反序列化日期格式
        dc.with(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        mapper.registerModule(simpleModule);

        mapper.setConfig(dc);
    }

    /**
     * 将对象转化为json
     * @param obj 待转化的对象
     * @return 当转化发生异常时返回null
     */
    public static String toJson(Object obj){
        if(obj == null){
            return null;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error(String.format("obj=[%s]", obj.toString()), e);
        }
        return null;
    }

    /**
     * 将json转化为对象, use parseJson instead
     * @param json json对象
     * @param clazz 待转化的对象类型
     * @return 当转化发生异常时返回null
     */
    @Deprecated
    public static <T> T fromJson(String json,Class<T> clazz){
        if(json == null){
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error(String.format("json=[%s]", json), e);
        }
        return null;
    }

    /**
     * 将json转化为对象, use parseJson instead
     * @param json json对象
     * @param clazz 待转化的对象类型
     * @return 当转化发生异常时返回null
     */
    public static <T> T parseJson(String json,Class<T> clazz){
        if(json == null){
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error(String.format("json=[%s]", json), e);
        }
        return null;
    }

/**
 * 将json对象转化为集合类型, use parseJson instead
 * @param json json对象
 * @param collectionClazz 具体的集合类的class，如：ArrayList.class
 * @param clazz 集合内存放的对象的class
 * @return
 */
    @SuppressWarnings("rawtypes")
    @Deprecated
    public static <T> Collection<T> fromJson(String json, Class<? extends Collection> collectionClazz, Class<T> clazz){
        if(json == null){
            return null;
        }
        try {
            Collection<T> collection =  mapper.readValue(json, getCollectionType(collectionClazz,clazz));
            return collection;
        } catch (IOException e) {
            logger.error(String.format("json=[%s]", json), e);
        }
        return null;
    }

    /**
     * 将json对象转化为集合类型
     * @param json json对象
     * @param collectionClazz 具体的集合类的class，如：ArrayList.class
     * @param clazz 集合内存放的对象的class
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T> Collection<T> parseJson(String json, Class<? extends Collection> collectionClazz, Class<T> clazz){
        if(json == null){
            return null;
        }
        try {
            Collection<T> collection =  mapper.readValue(json, getCollectionType(collectionClazz,clazz));
            return collection;
        } catch (IOException e) {
            logger.error(String.format("json=[%s]", json), e);
        }
        return null;
    }

    public static <T> T parseJson(String json, TypeReference<T> typeReference){
        T t = null;
        try {
            t = mapper.readValue(json, typeReference);
        }catch (IOException e) {
            logger.error(String.format("json=[%s]", json), e);
        }
        return t;
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static void main(String[] args) {
//        HashMap<String,Long> l = new HashMap();
//        l.put("a",430823576333975552L);
//        l.put("b",430823576333975552L);
//
//        System.out.println(JsonUtil.toJson(l));
//
//        String b = "{\"data\":\"2018-04-08 21:33:14\",\"name\":\"反对法反对法\"}";
//        HashMap c = JsonUtil.fromJson(b,HashMap.class);
//        Date d = (Date)c.get("data");
//        System.out.println(d.getTime());
    }

}
