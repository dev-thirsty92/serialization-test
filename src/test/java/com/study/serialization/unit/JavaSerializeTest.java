package com.study.serialization.unit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JavaSerializeTest {

    private byte[] serializedMember;

    @BeforeEach
    void setUp() throws IOException {
        UserInfo userInfo = new UserInfo("THIRSTY", 1);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)
        ){
            oos.writeObject(userInfo);
            serializedMember = baos.toByteArray();
        }

    }

    @Test
    @DisplayName("직렬화된 바이트 배열이 정상적으로 생성되었는지 확인")
    @Order(1)
    void testSerializedDataNotEmpty() {

        // 바이트 배열로 생성된 직렬화 데이터를 base64로 변환
        String base64Serialized = Base64.getEncoder().encodeToString(serializedMember);
        log.info("Serialized userInfo (Base64) = {}", base64Serialized);

        assertThat(serializedMember).isNotNull();
        assertThat(serializedMember.length).isGreaterThan(0);
    }

    @Test
    @DisplayName("UserInfo 역직렬화 테스트")
    @Order(2)
    void deserializedTest() throws IOException, ClassNotFoundException {

        try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedMember);
             ObjectInputStream ois = new ObjectInputStream(bais)
        ){
            UserInfo userInfo = (UserInfo) ois.readObject();
            log.info("Deserialized userInfo = {}", userInfo);

            assertThat(userInfo).isNotNull();
            assertThat(userInfo.getName()).isEqualTo("THIRSTY");
            assertThat(userInfo.getAge()).isEqualTo(1);
        }
    }

}
