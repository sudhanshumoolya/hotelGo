package com.example.hotelgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelRoomActivity extends AppCompatActivity {

    private static final String TAG = HotelRoomActivity.class.getSimpleName();
    private TextView hotelName, hotelAddress, hotelRating;
    private ImageView hotelImage;

    private TextView roomName, roomSize, roomPrice, roomNoOfGuest;
    private Button bookRoom;

    private TextView hotelEmail, hotelMobileNo, hotelDesc, hotelWifi, hotelAC, hotelTV, hotelCafe;

    Dialog bookingDialog;
    private CardView bookingCardView;
  //  private Button bookingButton;

    private HotelResponse hotelResponse;

    public long checkIn, checkOut;
    public int hotelId, noOfGuest;

    public int roomId;
    public int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_room);

        hotelName = findViewById(R.id.hotelRoom_name);
        hotelAddress = findViewById(R.id.hotelRoom_address);
        hotelRating = findViewById(R.id.hotelRoom_rateing);
        hotelImage = findViewById(R.id.hotelRoom_imageView);

        hotelDesc = findViewById(R.id.hotelRoom_description);
        hotelEmail = findViewById(R.id.hotelRoom_email);
        hotelMobileNo = findViewById(R.id.hotelRoom_mobileNo);
        hotelWifi = findViewById(R.id.hotelRoom_wifi);
        hotelAC = findViewById(R.id.hotelRoom_ac);
        hotelTV = findViewById(R.id.hotelRoom_tv);
        hotelCafe = findViewById(R.id.hotelRoom_cafe);

        roomName = findViewById(R.id.room_name);
        roomPrice = findViewById(R.id.room_prize);
        roomSize = findViewById(R.id.room_size_textView);
        roomNoOfGuest = findViewById(R.id.no_of_guest);
        bookRoom = findViewById(R.id.book_button);

        Intent intent = getIntent();



        if(intent.getExtras()!=null)
        {
            hotelResponse = (HotelResponse) intent.getSerializableExtra("data");
            checkIn = intent.getLongExtra("checkIn",0);
            checkOut = intent.getLongExtra("checkOut",0);
            noOfGuest = intent.getIntExtra("noOfGuest",0);
            userId = intent.getIntExtra("userId",0);

            Toast.makeText(this, checkIn+" "+checkOut+" "+noOfGuest, Toast.LENGTH_SHORT).show();

            hotelId = hotelResponse.getHotel_id();
            String hotel_name = hotelResponse.getHotel_name();
            String hotel_address = hotelResponse.getStreet()+", "+hotelResponse.getCity()+", "+hotelResponse.getState();
            String hotel_rating = String.valueOf(hotelResponse.getHotel_ratings());
            String hotel_image_url = hotelResponse.getHotel_img_url();

            hotelName.setText(hotel_name);
            hotelAddress.setText(hotel_address);
            hotelRating.setText(hotel_rating);
            Picasso.with(this).load(hotel_image_url).into(hotelImage);
        }

        getRoomDetails();
        getHotelDetails();


        bookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postBookingDetails();
            }
        });



    }

    private void getHotelDetails() {
        Call<List<HotelDetailResponse>> hotelResponseCall = ApiClient.getHotelService().getHotelDetail(hotelId);

        hotelResponseCall.enqueue(new Callback<List<HotelDetailResponse>>() {
            @Override
            public void onResponse(Call<List<HotelDetailResponse>> call, Response<List<HotelDetailResponse>> response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(HotelRoomActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onSuccess: "+response.body());
                    List<HotelDetailResponse> hotelDetailResponse = response.body();
                    hotelDesc.setText(hotelDetailResponse.get(0).getHotel_desc());
                    hotelEmail.setText(hotelDetailResponse.get(0).getHotel_email());
                    hotelMobileNo.setText(hotelDetailResponse.get(0).getMobile_number());
                    if (hotelDetailResponse.get(0).getAC()==0) {
                        hotelAC.setText("");
                        hotelAC.setBackground(null);
                        hotelAC.setCompoundDrawables(null, null, null, null);
                    }
                    if (hotelDetailResponse.get(0).getWifi()==0) {
                        hotelWifi.setText("");
                        hotelWifi.setBackground(null);
                        hotelAC.setCompoundDrawables(null, null, null, null);
                    }
                    if (hotelDetailResponse.get(0).getTv()==0) {
                        hotelTV.setText("");
                        hotelTV.setBackground(null);
                        hotelAC.setCompoundDrawables(null, null, null, null);
                    }
                    if (hotelDetailResponse.get(0).getCafe()==0) {
                        hotelCafe.setText("");
                        hotelCafe.setBackground(null);
                        hotelAC.setCompoundDrawables(null, null, null, null);
                    }
                }
                else{
                    Toast.makeText(HotelRoomActivity.this, "response "+response.body(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onSuccess not "+response.body());
                }
            }

            @Override
            public void onFailure(Call<List<HotelDetailResponse>> call, Throwable t) {
                Toast.makeText(HotelRoomActivity.this, ""+t, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t);
            }
        });
    }

    private void postBookingDetails() {

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setHotel_id(hotelId);
        bookingRequest.setCheck_in(checkIn);
        bookingRequest.setCheck_out(checkOut);
        bookingRequest.setRoom_id(roomId);
        bookingRequest.setUser_id(userId);
        bookingRequest.setNumber_of_guest(noOfGuest);
        Toast.makeText(this, String.valueOf(userId), Toast.LENGTH_SHORT).show();

        Call<String> bookingResponseCall = ApiClient.getHotelService().bookRoom(bookingRequest);

        bookingResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    String responses = response.body();
                   // Toast.makeText(HotelRoomActivity.this, "You Have Successfully booked ur Room", Toast.LENGTH_SHORT).show();
                    bookingDialog = new Dialog(HotelRoomActivity.this);
                    showBookingDialog();

                }
                else
                {
                    Toast.makeText(HotelRoomActivity.this, "Failed to Book the Room", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });

    }

    private void showBookingDialog() {

        bookingDialog.setContentView(R.layout.dialog_booking);

        bookingCardView = bookingDialog.findViewById(R.id.dialog_cardView);
     //   bookingButton = bookingDialog.findViewById(R.id.go_home_button);

        bookingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingDialog.dismiss();
            }
        });

     //   Window window = bookingDialog.getWindow();
    //    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    //    window.setGravity(Gravity.CENTER);
        bookingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bookingDialog.show();

    }

    private void getRoomDetails() {

        Call<List<RoomResponse>> roomResponseCall = ApiClient.getHotelService().getRooms(checkIn,checkOut,hotelId,noOfGuest);

        roomResponseCall.enqueue(new Callback<List<RoomResponse>>() {
            @Override
            public void onResponse(Call<List<RoomResponse>> call, Response<List<RoomResponse>> response) {

                if(response.isSuccessful())
                {
                    List<RoomResponse> roomResponse = response.body();

                    Toast.makeText(HotelRoomActivity.this, roomResponse.get(0).getRoom_name(), Toast.LENGTH_SHORT).show();

                    String name =roomResponse.get(0).getRoom_name();
                    String price = String.valueOf(roomResponse.get(0).getRoom_cost());
                    String size =String.valueOf(roomResponse.get(0).getRoom_size());
                    String guests =" ("+String.valueOf(roomResponse.get(0).getNo_of_guest())+"X)";

                    roomId = roomResponse.get(0).getRoom_id();

                    roomName.setText(name);
                    roomPrice.setText(price);
                    roomSize.setText(size);
                    roomNoOfGuest.setText(guests);
                }
            }

            @Override
            public void onFailure(Call<List<RoomResponse>> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });
    }


}