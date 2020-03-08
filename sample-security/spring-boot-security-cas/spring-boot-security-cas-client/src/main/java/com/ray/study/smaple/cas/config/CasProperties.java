package com.ray.study.smaple.cas.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * CAS的配置参数
 * @author ChengLi
 */
@Component
@Data
public class CasProperties {

    @Value("${cas.server.url}")
    private String casServerUrl;

    @Value("${cas.server.login_url}")
    private String casServerLoginUrl;

    @Value("${cas.server.logout_url}")
    private String casServerLogoutUrl;

    @Value("${app.server.url}")
    private String appServerUrl;

    @Value("${app.server.login_url}")
    private String appLoginUrl;

    @Value("${app.server.logout_url}")
    private String appLogoutUrl;

}