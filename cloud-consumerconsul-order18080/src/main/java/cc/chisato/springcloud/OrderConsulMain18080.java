package cc.chisato.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderConsulMain18080 {
    public static void main(String[] args) {
        SpringApplication.run(OrderConsulMain18080.class, args);
    }
}