package cc.chisato.springcloud.service.impl;

import cc.chisato.springcloud.service.PaymentService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService {
    @Override
    public String paymentInfo_Ok(Integer id) {
        return "PaymentFallbackService fall back:OK";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "PaymentFallbackService fall back:Timeout";
    }
}
