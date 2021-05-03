package com.fprojects.webchat.controllers;

import com.fprojects.webchat.models.MessageModel;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public MessageModel sendMessage(@Payload MessageModel messageModel) { return messageModel; }

    @MessageMapping("chat.addUser")
    @SendTo("/topic/public")
    public MessageModel addUser(@Payload MessageModel messageModel,
                                SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", messageModel.getSender());
        return messageModel;
    }

}
