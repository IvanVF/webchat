package com.fprojects.webchat.listeners;

import com.fprojects.webchat.models.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    public static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messageSendingOperations;

    @EventListener
    public void handleSocketConnectListener(SessionConnectedEvent connectedEvent) {
        logger.info("Получено новое подключение");
    }

    @EventListener
    public void handleSocketDisconnectListener(SessionDisconnectEvent disconnectEvent) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(disconnectEvent.getMessage());

        String username = (String) stompHeaderAccessor.getSessionAttributes().get("username");

        if (username != null) {
            logger.info("Пользователь " + username + " покинул чат");

            MessageModel messageModel = new MessageModel();
            messageModel.setMessageType(MessageModel.MessageType.LEAVE);
            messageModel.setSender(username);

            messageSendingOperations.convertAndSend("/topic/public", messageModel);
        }
    }


}
