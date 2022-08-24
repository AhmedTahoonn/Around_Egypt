package com.example.ahmeddd.Models;

public class DisplayModel
{
    String placeName;
    String description;
    String url;
    String uId;

    public DisplayModel() {

    }
    public DisplayModel(String placeName, String description, String url, String uId) {
        this.placeName = placeName;
        this.description = description;
        this.url = url;
        this.uId = uId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
