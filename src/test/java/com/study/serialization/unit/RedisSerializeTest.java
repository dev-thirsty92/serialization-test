package com.study.serialization.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class RedisSerializeTest {

    @Test
    void testStringRedisSerializer() {

        StringRedisSerializer serializer = new StringRedisSerializer();

        byte[] result = serializer.serialize("THIRSTY");
        assertThat(result).isNotEmpty();
        log.info("[StringRedisSerializer] Serialized(+Base64) result: {}", Base64.getEncoder().encodeToString(result));

        String deserialize = serializer.deserialize(result);
        assertThat(deserialize).isEqualTo("THIRSTY");
        log.info("[StringRedisSerializer] Deserialized result: {}", deserialize);

    }

    @Test
    void jdkSerializationRedisSerializerTest(){

        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
        UserInfo targetObject = new UserInfo("THIRSTY", 1);

        byte[] result = serializer.serialize(targetObject);
        assertThat(result).isNotEmpty();
        log.info("[JdkSerializationRedisSerializer] Serialized result: {}", Base64.getEncoder().encodeToString(result));

        UserInfo deserialize = (UserInfo) serializer.deserialize(result);
        assertThat(deserialize).isNotNull();
        assertThat(deserialize.getName()).isEqualTo("THIRSTY");
        assertThat(deserialize.getAge()).isEqualTo(1);
        log.info("[JdkSerializationRedisSerializer] Deserialized result: {}", deserialize);

    }

    @Test
    void genericJackson2JsonRedisSerializerTest(){

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        UserInfo targetObject = new UserInfo("THIRSTY", 1);

        byte[] result = serializer.serialize(targetObject);
        assertThat(result).isNotEmpty();
        log.info("[GenericJackson2JsonRedisSerializer] Serialized result: {}", new String(result));

        UserInfo deserialize = (UserInfo) serializer.deserialize(result);
        assertThat(deserialize).isNotNull();
        assertThat(deserialize.getName()).isEqualTo("THIRSTY");
        assertThat(deserialize.getAge()).isEqualTo(1);
        log.info("[GenericJackson2JsonRedisSerializer] Deserialized result: {}", deserialize);

    }

    @Test
    @DisplayName("ObjectMapper를 아래와 같이 설정하면 대상 객체의 class 정보가 생략되어 직렬화 된다.")
    void genericJackson2JsonRedisSerializerTest2() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.deactivateDefaultTyping();

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        UserInfo targetObject = new UserInfo("THIRSTY", 1);

        byte[] result = serializer.serialize(objectMapper.writeValueAsString(targetObject));
        assertThat(result).isNotEmpty();
        log.info("[GenericJackson2JsonRedisSerializer2] Serialized result: {}", new String(result));


        String jsonObject = (String) serializer.deserialize(result);
        UserInfo deserialize = objectMapper.readValue(jsonObject, UserInfo.class);
        assertThat(deserialize).isNotNull();
        assertThat(deserialize.getName()).isEqualTo("THIRSTY");
        assertThat(deserialize.getAge()).isEqualTo(1);
        log.info("[GenericJackson2JsonRedisSerializer2] Deserialized result: {}", deserialize);

    }


}
