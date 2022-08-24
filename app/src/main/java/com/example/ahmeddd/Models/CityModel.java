package com.example.ahmeddd.Models;// on your project name

public class CityModel
{

    private String uId;

    private String cityName;
    private  String url;

    public CityModel() {}

    public CityModel(String uId, String cityName, String url) {
        this.uId = uId;
        this.cityName = cityName;
        this.url = url;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
