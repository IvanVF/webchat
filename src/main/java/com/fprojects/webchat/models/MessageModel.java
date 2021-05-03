package com.fprojects.webchat.models;

import lombok.Data;

@Data
public class MessageModel {

    public enum MessageType { CHAT, JOIN, LEAVE }

    private MessageType messageType;

    private String content;

    private String sender;
}
