package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author JQ
 */
@SpringBootApplication
@EnableEurekaServer
public class EasymallEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasymallEurekaServerApplication.class, args);
    }

}
