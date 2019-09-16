package com.cj.core.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
//@ConfigurationProperties设置前缀 database
@ConfigurationProperties(prefix = "datapram")
public class Datapram {

    private String tooldir;

    private String dbname;

    private String ip;

    private String username;

    private String password;

    private String dir;
}
