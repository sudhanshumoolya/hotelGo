package com.example.hotelgo;

public class RoomResponse {

    private int room_id;
    private int hotel_id;
    private String room_name;
    private int room_size;
    private int no_of_guest;
    private int room_cost;
    private int total_rooms;

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public int getRoom_size() {
        return room_size;
    }

    public void setRoom_size(int room_size) {
        this.room_size = room_size;
    }

    public int getNo_of_guest() {
        return no_of_guest;
    }

    public void setNo_of_guest(int no_of_guest) {
        this.no_of_guest = no_of_guest;
    }

    public int getRoom_cost() {
        return room_cost;
    }

    public void setRoom_cost(int room_cost) {
        this.room_cost = room_cost;
    }

    public int getTotal_rooms() {
        return total_rooms;
    }

    public void setTotal_rooms(int total_rooms) {
        this.total_rooms = total_rooms;
    }
}
