package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author JQ
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tedu.seckill.mapper")
public class StartSaleKill {
    public static void main( String[] args ) {
        SpringApplication.run(StartSaleKill.class,args);
    }
    @Bean
    @LoadBalanced
    public RestTemplate initSecRestTemplate(){
        return new RestTemplate();
    }
}
