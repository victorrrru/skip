package com.muyuan.platform.common.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by victorrrr on 2017/8/1.
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class SkipDataSource {
    private String url;
    private String username;
    private String password;
}
