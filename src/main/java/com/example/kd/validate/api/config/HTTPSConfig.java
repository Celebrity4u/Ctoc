package com.example.kd.validate.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HTTPSConfig implements WebMvcConfigurer {


    private String keyAlias;

    private String keyStore;

    private String keyStoreType;

    private String keyStorePassword;

    public String getKeyAlias() {
        return keyAlias;
    }
    @Value("${server.ssl.key-alias}")
    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public String getKeyStore() {
        return keyStore;
    }
    @Value("${server.ssl.key-store}")
    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    public String getKeyStoreType() {
        return keyStoreType;
    }
    @Value("${server.ssl.keyStoreType}")
    public void setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }
    @Value("${server.ssl.key-store-password}")
    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }
}
