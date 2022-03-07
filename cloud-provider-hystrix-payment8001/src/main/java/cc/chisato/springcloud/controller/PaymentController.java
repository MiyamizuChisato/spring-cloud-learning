package cc.chisato.springcloud.controller;

import cc.chisato.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String port;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String Payment_ok(@PathVariable Integer id) {
        log.info("{}端口调用Payment_ok服务", this.port);
        return paymentService.paymentInfo_Ok(id);
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String Payment_timeout(@PathVariable Integer id) {
        log.info("{}端口调用Payment_timeout服务", this.port);
        return paymentService.paymentInfo_Timeout(id);
    }

    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        String str = paymentService.paymentCircuitBreaker(id);
        return str;
    }
}
