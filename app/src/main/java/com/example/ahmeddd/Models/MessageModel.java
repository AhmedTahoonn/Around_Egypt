package com.example.ahmeddd.Models;

public class MessageModel {
    String senderId;
    String reciverId;
    String message;
    String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MessageModel() {

    }
    public MessageModel(String message, String senderId, String reciverId, String time) {
      this.senderId = senderId;
        this.message = message;
        this.reciverId=reciverId;
        this.time=time;
    }

    public String getReciverId() {
        return reciverId;
    }

    public void setReciverId(String reciverId) {
        this.reciverId = reciverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
