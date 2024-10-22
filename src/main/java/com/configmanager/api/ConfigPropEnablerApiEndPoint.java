package com.configmanager.api;

import com.configmanager.config.AppProperties;
import com.configmanager.config.DatabaseProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigPropEnablerApiEndPoint {

    private final AppProperties appProperties;
    private final DatabaseProperties databaseProperties;

    @Autowired
    public ConfigPropEnablerApiEndPoint(AppProperties appProperties, DatabaseProperties databaseProperties) {
        this.appProperties = appProperties;
        this.databaseProperties = databaseProperties;
    }

    @GetMapping("/info")
    public String getAppInfo() {
        return "App Name: " + appProperties.getName() + ", Version: " + appProperties.getVersion();
    }

    @GetMapping("/dbinfo")
    public String getDbInfo() {
        return "DB URL: " + databaseProperties.getUrl() + ", Username: " + databaseProperties.getUsername();
    }
}
