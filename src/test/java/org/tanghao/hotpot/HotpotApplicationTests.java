package org.tanghao.hotpot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.tanghao.hotpot.cache.IGlobalCache;

@Slf4j
@SpringBootTest
class HotpotApplicationTests {
    @Autowired
    private IGlobalCache globalCache;

    @Test
    void contextLoads() {
        log.info("Hello World");
    }

    @Test
    void redisTest() {
        globalCache.set("applicationInfo", "hotpot");
        log.info("applicationInfo: " + globalCache.get("applicationInfo"));
    }

    @Test
    void generatePasswordTest() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        log.info("BCrypt加密`123456`:" + bCryptPasswordEncoder.encode("123456"));
    }
}
