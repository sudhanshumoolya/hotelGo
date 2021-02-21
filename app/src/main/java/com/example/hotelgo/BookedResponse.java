package com.example.hotelgo;

public class BookedResponse {

    private int book_id;
    private long check_in;
    private long check_out;
    private int number_of_guest;
    private String hotel_name;
    private String hotel_img_url;

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public long getCheck_in() {
        return check_in;
    }

    public void setCheck_in(long check_in) {
        this.check_in = check_in;
    }

    public long getCheck_out() {
        return check_out;
    }

    public void setCheck_out(long check_out) {
        this.check_out = check_out;
    }

    public int getNumber_of_guest() {
        return number_of_guest;
    }

    public void setNumber_of_guest(int number_of_guest) {
        this.number_of_guest = number_of_guest;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_img_url() {
        return hotel_img_url;
    }

    public void setHotel_img_url(String hotel_img_url) {
        this.hotel_img_url = hotel_img_url;
    }
}
