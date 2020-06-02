package com.example.chatter.data;

import android.content.Context;

import java.util.ArrayList;

public class Chat {

    public static String STORAGE_KEY = "CHAT";

    private ArrayList<TextMessage> messages = new ArrayList<TextMessage>();

    public ArrayList<TextMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<TextMessage> messages) {
        this.messages = messages;
    }

    public static void addMessage(Context context, String textMessage, boolean sender)
    {
        TextMessage message = new TextMessage();
        message.setMessage(textMessage);
        message.setSender(sender);
        Storage storage = new StorageImplementation();
        Object storageAsObject = storage
                .getObject(context, Chat.STORAGE_KEY, Chat.class);

        Chat chatStorage;
        if (storageAsObject != null) {
            chatStorage = (Chat) storageAsObject;
        } else {
            chatStorage = new Chat();
        }

        chatStorage.getMessages().add(message);
        storage.add(context, Chat.STORAGE_KEY, chatStorage);
    }
}
