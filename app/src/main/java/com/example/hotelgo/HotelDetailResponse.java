package com.example.hotelgo;

public class HotelDetailResponse {

    private int hotel_id;
    private String hotel_desc;
    private String mobile_number;
    private String hotel_email;
    private int wifi, tv, AC, cafe;

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_desc() {
        return hotel_desc;
    }

    public void setHotel_desc(String hotel_desc) {
        this.hotel_desc = hotel_desc;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getHotel_email() {
        return hotel_email;
    }

    public void setHotel_email(String hotel_email) {
        this.hotel_email = hotel_email;
    }

    public int getWifi() {
        return wifi;
    }

    public void setWifi(int wifi) {
        this.wifi = wifi;
    }

    public int getTv() {
        return tv;
    }

    public void setTv(int tv) {
        this.tv = tv;
    }

    public int getAC() {
        return AC;
    }

    public void setAC(int AC) {
        this.AC = AC;
    }

    public int getCafe() {
        return cafe;
    }

    public void setCafe(int cafe) {
        this.cafe = cafe;
    }
}
