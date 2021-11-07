package com.cnsmash.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhb
 */
@Configuration
@Order
public class HttpMessageConverterConfiguration {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){

        MappingJackson2HttpMessageConverter mjmc = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        //解析null不报错
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        //忽略不存在get、set的属性
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(Boolean.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Boolean.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        mjmc.setObjectMapper(objectMapper);


        // 设置中文编码格式
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        MediaType media = new MediaType(MediaType.TEXT_HTML, StandardCharsets.UTF_8);
        MediaType media2 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        list.add(media);
        list.add(media2);
        mjmc.setSupportedMediaTypes(list);
        return mjmc;
    }
}
