package cc.chisato.springcloud.controller;

import cc.chisato.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/consumer")
@Slf4j
@DefaultProperties(defaultFallback = "fallback", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
})
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    //    @HystrixCommand
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_Ok(@PathVariable("id") Integer id) {
        return paymentService.paymentInfo_Ok(id);
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id") Integer id) {
        return paymentService.paymentInfo_Timeout(id);
    }

    public String paymentInfo_TimeoutHandler(Integer id) {
        return "id为：" + id + "的操作调用微服务失败，请稍后再试";
    }

    //全局fallback
    public String fallback() {
        return "网络繁忙，请稍后再试";
    }
}
