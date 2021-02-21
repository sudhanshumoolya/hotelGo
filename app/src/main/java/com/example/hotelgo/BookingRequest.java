package com.example.hotelgo;

public class BookingRequest {

    private int user_id;
    private int hotel_id;
    private int room_id;
    private long check_in;
    private long check_out;
    private int number_of_guest;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
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
}
