package com.ddmtchr.blps_check_sender.config;

import com.ddmtchr.blps_check_sender.connector.OneCConnection;
import com.ddmtchr.blps_check_sender.connector.OneCConnectionFactory;
import com.ddmtchr.blps_check_sender.connector.OneCManagedConnectionFactory;
import com.ddmtchr.blps_check_sender.connector.OneCResourceAdapter;
import jakarta.resource.ResourceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jca.support.ResourceAdapterFactoryBean;

@Configuration
public class JcaConfig {

    @Value("${onec.url}")
    private String url;

    @Bean
    public ResourceAdapterFactoryBean resourceAdapter() {
        ResourceAdapterFactoryBean bean = new ResourceAdapterFactoryBean();
        bean.setResourceAdapter(new OneCResourceAdapter());
        return bean;
    }

    @Bean
    public OneCManagedConnectionFactory oneCManagedConnectionFactory() {
        return new OneCManagedConnectionFactory(url);
    }

    @Bean
    public OneCConnectionFactory oneCConnectionFactory(OneCManagedConnectionFactory oneCManagedConnectionFactory) {
        return new OneCConnectionFactory(oneCManagedConnectionFactory);
    }

    @Bean
    public OneCConnection oneCConnection(OneCConnectionFactory oneCConnectionFactory) throws ResourceException {
        return (OneCConnection) oneCConnectionFactory.getConnection();
    }
}
