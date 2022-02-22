package cc.chisato.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderFeignMain18080 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeignMain18080.class, args);
    }
}
