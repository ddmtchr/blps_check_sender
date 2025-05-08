package com.ddmtchr.blps_check_sender.consumer;

import com.ddmtchr.blps_check_sender.dto.CheckDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CheckMessageConsumer implements CommandLineRunner {

    @Value("${rabbitmq.url}")
    private String url;

    @Value("${rabbitmq.queue}")
    private String queue;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void run(String... args) throws Exception {
        JmsConnectionFactory factory = new JmsConnectionFactory(username, password, url);
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queue);
        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(message -> {
            try {
                if (message instanceof TextMessage textMessage) {
                    String json = textMessage.getText();
                    CheckDto dto = objectMapper.readValue(json, CheckDto.class);
                    log.info("Received message: {}", dto);
                }
            } catch (JsonProcessingException e) {
                log.error("Error processing JSON: {}", e.getMessage());
            } catch (JMSException e) {
                log.error("JMS Error: {}", e.getMessage());
            }
        });

    }
}
