package com.example.hotelgo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HotelService {

    @GET("hotels")
    Call<List<HotelResponse>>  getAllHotels(
            @Query("city") String city
    );

    @GET("hotelDetails")
    Call<List<HotelDetailResponse>> getHotelDetail(
            @Query("hotelId") int hotelId
    );

    @GET("login")
    Call<String> getLoginDetail(
        @Query("userEmail") String userEmail,
        @Query("userPassword") String userPassword
    );

    @POST("signup")
    Call<String> saveSignUpDetail(@Body SignUpRequest signUpRequest);

    @POST("user")
    Call<String> saveUser(@Body UserRequest userRequest);

    @GET("user")
    Call<List<UserResponse>> getUserDetail(
            @Query("userEmail") String userEmail
    );

    @GET("room")
    Call<List<RoomResponse>> getRooms(
            @Query("checkIn") long checkIn,
            @Query("checkOut") long checkOut,
            @Query("hotelId") int hotelId,
            @Query("noOfGuest") int noOfGuest
    );

    @POST("booking")
    Call<String> bookRoom(@Body BookingRequest bookingRequest);

    @GET("booked")
    Call<List<BookedResponse>> getBookedHotel(
            @Query("userId") int userId
    );

    @FormUrlEncoded
    @POST("cancelHotel")
    Call<String> cancelBooked(
            @Field("bookId") int bookId
    );


}
