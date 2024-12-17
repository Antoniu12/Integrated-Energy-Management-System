package com.example.chat_microservice.messages;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/privateMessage")
    public void sendPrivateMessage(@Payload Message message) {
        if (message.getReceiverId() == null || message.getReceiverId().isEmpty()) {
            System.err.println("Receiver ID is missing in the message payload.");
            return;
        }

        message.setId(UUID.randomUUID().toString());
        System.out.println("Sending private message to: " + message.getReceiverId());

        messagingTemplate.convertAndSendToUser(
                message.getReceiverId(), "/queue/messages", message
        );
    }

    @MessageMapping("/typing")
    public void typingNotification(@Payload Message message) {
        if (message.getReceiverId() == null || message.getReceiverId().isEmpty()) {
            System.err.println("Receiver ID is missing in the typing payload.");
            return;
        }

        System.out.println("Sending typing notification to: " + message.getReceiverId());
        messagingTemplate.convertAndSendToUser(
                message.getReceiverId(), "/queue/typing", message
        );
    }


}
