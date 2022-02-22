package cc.chisato.springcloud.lb.impl;

import cc.chisato.springcloud.lb.LoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLoadBalancer implements LoadBalancer {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrementIndex() {
        int current;
        int next;
        do {
            current = atomicInteger.get();
            next = current <= Integer.MAX_VALUE ? current + 1 : 0;
        } while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("******第" + next + "访问");
        return next;
    }

    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrementIndex() % serviceInstances.size();
        System.out.println("当前服务轮询索引" + index);
        ServiceInstance instance = serviceInstances.get(index);
        System.out.println("当前服务器:" + instance.getPort());
        return instance;
    }
}
