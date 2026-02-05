package com.yunke.admin;

import cn.dev33.satoken.SaManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;


/**
 * @className YunkeAdminApplication
 * @description: YunkeAdmin系统启动入口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Slf4j
@Import({cn.hutool.extra.spring.SpringUtil.class})
@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class YunkeAdminApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(YunkeAdminApplication.class);
        // 记录进程id
        app.addListeners(new ApplicationPidFileWriter());
        Environment env = app.run(args).getEnvironment();
            String protocol = "http";
            if (env.getProperty("server.ssl.key-store") != null) {
                protocol = "https";
            }
            String hostAddress = "localhost";
            try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
                "\n----------------------------------------------------------\n\t"
                        + "Application '{}' is running! Access URLs:\n\t"
                        + "Local: \t\t{}://localhost:{}{}\n\t"
                        + "External: \t{}://{}:{}{}\n\t"
                        + "Profile(s): {}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol, env.getProperty("server.port"),env.getProperty("server.servlet.context-path"),
                protocol, hostAddress, env.getProperty("server.port"),env.getProperty("server.servlet.context-path"),
                env.getActiveProfiles());

        System.out.println("启动成功：Sa-Token配置如下：" + SaManager.getConfig());

    }


}
