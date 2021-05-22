package space.mosk.checkbrain.Models;

import java.util.Date;

public class Message {
    public String userName;
    public String userEmail;
    public String textMessage;
    private long messageTime;


    public Message() {}

    public Message(String userName, String textMessage, String userEmail) {
        this.userName = userName;
        this.textMessage = textMessage;
        this.userEmail = userEmail;
        this.messageTime = new Date().getTime();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
