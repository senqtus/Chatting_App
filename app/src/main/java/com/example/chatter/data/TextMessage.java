package com.example.chatter.data;

public class TextMessage {

    private String message;
    private boolean sender; //if Alice True, else False

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSender() {
        return sender;
    }

    public void setSender(boolean sender) {
        this.sender = sender;
    }
}
