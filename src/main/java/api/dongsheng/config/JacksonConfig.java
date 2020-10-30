package api.dongsheng.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @program: aioh_sw_payment
 * @description: 过滤返回值中为null的参数
 * @author: urbane
 * @create: 2019-12-07 16:50
 **/
@Configuration
public class JacksonConfig {
    // @Bean
    // @Primary
    // @ConditionalOnMissingBean(ObjectMapper.class)
    // public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
    //     return builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    //             .visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
    //             .serializationInclusion(JsonInclude.Include.NON_EMPTY)
    //             .build();
    // }

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        //通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化，属性为NULL 不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return objectMapper;
    }

}