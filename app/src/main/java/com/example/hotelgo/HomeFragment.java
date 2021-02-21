package com.example.hotelgo;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private Button mSearchHotel;
    private EditText mLocationEditText;
    private EditText mRoomsEditText;
    private TextView mDatePicker;

    public String city;
    public long checkIn;
    public long checkOut;
    public int noOfGuest;
    public int userId;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mSearchHotel = view.findViewById(R.id.searchHotel_button);
        mLocationEditText = view.findViewById(R.id.destination_editText);
        mRoomsEditText = view.findViewById(R.id.rooms_editText);
        mDatePicker = view.findViewById(R.id.date_selector);

     //   Bundle bundle =getArguments();
        userId = getArguments().getInt("userId");

      //  Toast.makeText(getContext(), String.valueOf(userId), Toast.LENGTH_SHORT).show();

  //      city = mLocationEditText.getText().toString();
  //      Toast.makeText(getActivity(), city, Toast.LENGTH_SHORT).show();

        datePicker();

        mSearchHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = mLocationEditText.getText().toString();
                noOfGuest = Integer.parseInt(mRoomsEditText.getText().toString());

                DiscoverFragment discoverFragment = new DiscoverFragment();
                Bundle args = new Bundle();
                args.putString("city", city);
                args.putLong("checkIn", checkIn);
                args.putLong("checkOut", checkOut);
                args.putInt("noOfGuest",noOfGuest);
                args.putInt("userId",userId);
                discoverFragment.setArguments(args);


                getFragmentManager().beginTransaction().replace(R.id.fragment_container,discoverFragment).commit();

               // Intent intent = new Intent(getActivity(),HotelRoomActivity.class);
               // startActivity(intent);
            }
        });



        return view;
    }



    public void datePicker(){

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();

        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());

        builder.setCalendarConstraints(constraintBuilder.build());

        final MaterialDatePicker materialDatePicker = builder.build();

        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getFragmentManager(),"DatePicker");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long,Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                mDatePicker.setText(materialDatePicker.getHeaderText());
              //  Toast.makeText(getContext(),checkIn, Toast.LENGTH_SHORT).show();
                checkIn = selection.first;
                checkOut = selection.second;
                Toast.makeText(getContext(),String.valueOf(checkIn)+" "+checkOut, Toast.LENGTH_LONG).show();
            }
        });
    }
}