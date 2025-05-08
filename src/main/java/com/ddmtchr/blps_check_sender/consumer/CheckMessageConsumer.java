package com.ddmtchr.blps_check_sender.consumer;

import com.ddmtchr.blps_check_sender.dto.CheckDto;
import com.ddmtchr.blps_check_sender.service.OneCService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckMessageConsumer implements CommandLineRunner {

    private final OneCService oneCService;
    private final JmsConnectionFactory factory;

    @Value("${rabbitmq.queue}")
    private String queue;

    @Value("${rabbitmq.queue.dead}")
    private String deadQueue;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void run(String... args) throws Exception {
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue(queue);
        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(message -> {
            try {
                if (message instanceof TextMessage textMessage) {
                    String json = textMessage.getText();
                    CheckDto dto = objectMapper.readValue(json, CheckDto.class);
                    oneCService.processCheck(dto);

                    message.acknowledge();
                }
            } catch (JsonProcessingException e) {
                log.error("Error processing JSON: {}", e.getMessage());
            } catch (Exception e) {
                log.error("Message processing error: {}", e.getMessage());

                try {
                    sendToDlx(message);
                    message.acknowledge();
                } catch (Exception ex) {
                    log.error("DLX sending error: {}", ex.getMessage());
                }
            }
        });

    }

    private void sendToDlx(Message originalMessage) throws JMSException {
        try (Connection dlxConnection = factory.createConnection();
             Session dlxSession = dlxConnection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

            dlxConnection.start();

            Destination dlxDestination = dlxSession.createQueue(deadQueue);
            MessageProducer producer = dlxSession.createProducer(dlxDestination);

            String body = ((TextMessage) originalMessage).getText();
            TextMessage newMessage = dlxSession.createTextMessage(body);
            producer.send(newMessage);

            log.warn("Message sent to DLX manually");
        }
    }
}
