package com.mtgo.exam.notificationservice.consumer;

import com.mtgo.exam.notificationservice.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {
    private final EmailService emailService;

    public MessageListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = MQConfig.ORDER_PLACED_QUEUE)
    public void listener(OrderPlacedMessage orderPlacedMessage) {
        log.info("New Order with Order Number: {} has been placed", orderPlacedMessage.getOrderNumber());


        String restaurantEmail = "markus@example.com";
        String subject = "New Order Placed";
        String body = "A new order has been placed. Order Number: " + orderPlacedMessage.getOrderNumber();

        emailService.sendEmail(restaurantEmail, subject, body);
    }
}
