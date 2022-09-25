package org.tanghao.hotpot;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@MapperScan("org.tanghao.hotpot.mapper")
@SpringBootApplication
public class HotpotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(HotpotApplication.class, args);
        applicationInfo(applicationContext);
    }

    /**
     * 控制台输出应用信息
     * @param applicationContext ConfigurableApplicationContext
     */
    private static void applicationInfo(ConfigurableApplicationContext applicationContext) {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String port = applicationContext.getEnvironment().getProperty("server.port");
        log.info("\n------------------------------------------------------------\n" +
                "Application Hotpot is running!\n" +
                "Local Url: \t\thttp://" + ip + ":" + port + "\n" +
                "Swagger-ui Url: http://" + ip + ":" + port + "/swagger-ui.html" +
                "\n------------------------------------------------------------\n");
    }

}
