package cc.chisato.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_Ok(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_Ok,id:" + id + "\t" + "O(∩_∩)O";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public String paymentInfo_Timeout(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_Timeout,id:" + id + "\t" + "/(ㄒoㄒ)/~~";
    }

    public String paymentInfo_TimeoutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_TimeoutHandler,id:" + id + "\t" + "(T_T)";
    }

    //======服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerHandler",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//请求次数
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),//时间窗口期
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")//失败率达到后跳闸
            })
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("*****id不能为负数");
        }
        String str = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "调用成功，流水号：" + str;

    }

    public String paymentCircuitBreakerHandler(Integer id) {
        return "id不能为负数";
    }
}
