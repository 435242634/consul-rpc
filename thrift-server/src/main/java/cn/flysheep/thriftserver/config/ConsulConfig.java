package cn.flysheep.thriftserver.config;

import cn.flysheep.thriftserver.util.InetUtil;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ConsulConfig {

    @Value("${spring.application.name}")
    private String serverName;

    @Value("${server.port}")
    private int serverPort;

    @Value("${spring.cloud.consul.host}")
    private String consulHost;

    @Value("${spring.cloud.consul.port}")
    private int consulPort;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void init() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                registerService();
            }
        });
    }

    // 注册服务
    private void registerService() {
        InetAddress inetAddress = InetUtil.findFirstNonLoopbackAddress();
        String hostAddress = inetAddress.getHostAddress();

        ConsulClient consulClient = new ConsulClient(consulHost, consulPort);
        NewService newService = new NewService();
        newService.setId("1");
        newService.setAddress(hostAddress);
        newService.setName(serverName);
        newService.setPort(serverPort);
        newService.setTags(null);

        consulClient.agentServiceRegister(newService);
    }

    // 注销服务
    @PreDestroy
    public void deregisterService() {
        ConsulClient consulClient = new ConsulClient(consulHost, consulPort);
        consulClient.agentServiceDeregister("1");
    }
}
