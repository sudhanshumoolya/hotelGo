package com.example.hotelgo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiscoverFragment extends Fragment implements HotelAdapter.ClickedItem{


    private static final String TAG = DiscoverFragment.class.getSimpleName();
    RecyclerView recyclerView;
    HotelAdapter hotelAdapter;

    String city;
    public long checkIn;
    public long checkOut;
    public int noOfGuest;
    public int userId;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_discover, container, false);

        recyclerView = view.findViewById(R.id.hotel_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        city = getArguments().getString("city");
        checkIn = getArguments().getLong("checkIn");
        checkOut = getArguments().getLong("checkOut");
        noOfGuest = getArguments().getInt("noOfGuest");
        userId = getArguments().getInt("userId");

       // HotelAdapter hotelAdapter = new HotelAdapter(hotelResponses,getActivity(),this::ClickedHotel);
        Toast.makeText(getActivity(), city, Toast.LENGTH_SHORT).show();
        getAllHotelDetail();


        return view;
    }


    public void getAllHotelDetail() {

        Call<List<HotelResponse>> hotelList = ApiClient.getHotelService().getAllHotels(city);

        hotelList.enqueue(new Callback<List<HotelResponse>>() {
            @Override
            public void onResponse(Call<List<HotelResponse>> call, Response<List<HotelResponse>> response) {
                List<HotelResponse> hotelResponses = response.body();
                //Toast.makeText(getContext(),  " "+hotelResponses, Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(new HotelAdapter(hotelResponses,getActivity(),this::ClickedHotels));

            }

            private void ClickedHotels(HotelResponse hotelResponse) {

               ClickedHotel(hotelResponse);
            }


            @Override
            public void onFailure(Call<List<HotelResponse>> call, Throwable t) {
                Log.d(TAG, "onFailure: api not working "+t);
            }
        });

    }

    @Override
    public void ClickedHotel(HotelResponse hotelResponse) {
      //  Toast.makeText(getContext(), ""+hotelResponse.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(),HotelRoomActivity.class);
        intent.putExtra("data",hotelResponse);
        intent.putExtra("checkIn",checkIn);
        intent.putExtra("checkOut",checkOut);
        intent.putExtra("noOfGuest",noOfGuest);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }

  /*  @Override
    public void ClickedHotel(HotelResponse hotelResponse) {

    }*/
}