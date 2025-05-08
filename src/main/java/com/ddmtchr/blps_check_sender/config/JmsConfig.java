//package com.ddmtchr.blps_check_sender.config;
//
//import org.apache.qpid.jms.JmsConnectionFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JmsConfig {
//
//    @Value("${rabbitmq.url}")
//    private String url;
//
//    @Value("${rabbitmq.username}")
//    private String username;
//
//    @Value("${rabbitmq.password}")
//    private String password;
//
//    @Bean
//    public JmsConnectionFactory jmsConnectionFactory() {
//        return new JmsConnectionFactory(username, password, url);
//    }
//
//}
