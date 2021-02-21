package com.example.hotelgo;


import java.io.Serializable;

public class HotelResponse implements Serializable {

    private int hotel_id;
    private String hotel_name;
    private float hotel_ratings;
    private int hotel_no_rating;
    private int loc_id;
    private int hotel_price;
    private String hotel_img_url;
    private String street;
    private String city;
    private String state;

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public float getHotel_ratings() {
        return hotel_ratings;
    }

    public void setHotel_ratings(float hotel_ratings) {
        this.hotel_ratings = hotel_ratings;
    }

    public int getHotel_no_rating() {
        return hotel_no_rating;
    }

    public void setHotel_no_rating(int hotel_no_rating) {
        this.hotel_no_rating = hotel_no_rating;
    }

    public int getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(int loc_id) {
        this.loc_id = loc_id;
    }

    public int getHotel_price() {
        return hotel_price;
    }

    public void setHotel_price(int hotel_price) {
        this.hotel_price = hotel_price;
    }

    public String getHotel_img_url() {
        return hotel_img_url;
    }

    public void setHotel_img_url(String hotel_img_url) {
        this.hotel_img_url = hotel_img_url;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
