package com.example.hotelgo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class BookedFragment extends Fragment implements BookedAdapter.ClickedItem {

    private static final String TAG = BookedFragment.class.getSimpleName();
    RecyclerView recyclerView;
    private int mUserId;

    public BookedFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booked, container, false);

        mUserId = getArguments().getInt("userId");
        recyclerView = view.findViewById(R.id.booked_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        getBookedDetails();


        return view;
    }

    private void getBookedDetails() {

        Call<List<BookedResponse>> bookedResponseCall = ApiClient.getHotelService().getBookedHotel(mUserId);

        bookedResponseCall.enqueue(new Callback<List<BookedResponse>>() {
            @Override
            public void onResponse(Call<List<BookedResponse>> call, Response<List<BookedResponse>> response) {
                List<BookedResponse> bookedResponses = response.body();

                recyclerView.setAdapter(new BookedAdapter(bookedResponses,getActivity(),this::ClickedBookedIds));

            }
            private void ClickedBookedIds(int bookedId) {

                ClickedBookedId(bookedId);
            }

            @Override
            public void onFailure(Call<List<BookedResponse>> call, Throwable t) {
                Log.d(TAG, "onFailure: api not working "+t);
            }
        });

    }

    @Override
    public void ClickedBookedId(int bookedId) {

       cancelBookedRoom(bookedId);
    }

    private void cancelBookedRoom(int bookId) {

        Call<String> cancelBookedCall = ApiClient.getHotelService().cancelBooked(bookId);

        Toast.makeText(getContext(), "BookId "+bookId, Toast.LENGTH_SHORT).show();
        cancelBookedCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(getContext(), "Cancel the Room with bookID"+response.body(), Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getContext(), "Failed to Cancel the Room", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to Cancel the Room", Toast.LENGTH_SHORT).show();
            }
        });

    }


}