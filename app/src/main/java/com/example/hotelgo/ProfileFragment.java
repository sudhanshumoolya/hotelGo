package com.example.hotelgo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.PrivateKey;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private EditText userEmail;
    private EditText userName;
    private EditText userAge;
    private EditText userAddress;
    private EditText userMobileNo;
    private Button logOutButton;
    private Button saveUserButton;

    UserRequest userRequest;

    public String mCurrentUser;

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String USER_EMAIL = "userEmail";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logOutButton = view.findViewById(R.id.logOut_button);
        userEmail = view.findViewById(R.id.user_email);
        userName = view.findViewById(R.id.user_name);
        userAge = view.findViewById(R.id.user_age);
        userAddress = view.findViewById(R.id.user_address);
        userMobileNo = view.findViewById(R.id.user_mobileNo);
        saveUserButton = view.findViewById(R.id.save_button);

        loadUserEmail();

       Bundle bundle = this.getArguments();
       userName.setText(bundle.getString("name"));
       userAge.setText(bundle.getString("age"));
       userAddress.setText(bundle.getString("address"));
       userMobileNo.setText(bundle.getString("mobileNo"));

         //   disableProfile();
        if(bundle.getString("name")!=null)
        {
            disableProfile();
        }

        userEmail.setText(mCurrentUser);
        userEmail.setKeyListener(null);

       // getUserDetails();


        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserEmail();
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        saveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userRequest = new UserRequest();
                userRequest.setEmail(mCurrentUser);
                userRequest.setName(userName.getText().toString());
                userRequest.setAge(Integer.parseInt(userAge.getText().toString()));
                userRequest.setAddress(userAddress.getText().toString());
                userRequest.setMobileNo(userMobileNo.getText().toString());

                postUserDetail();
            }
        });


        return view;
    }

   /* private void getUserDetails() {
        String userEmail = mCurrentUser;

        Call<List<UserResponse>> userResponseCall = ApiClient.getHotelService().getUserDetail(userEmail);

        userResponseCall.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {

                List<UserResponse> userResponses = response.body();

                String name =userResponses.get(0).getUser_name();
                String age = String.valueOf(userResponses.get(0).getUser_age());
                String address = userResponses.get(0).getUser_address();
                String mobileNo = String.valueOf(userResponses.get(0).getMobile_number());

                userName.setText(name);
                userAge.setText(age);
                userAddress.setText(address);
                userMobileNo.setText(mobileNo);
                disableProfile();
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Enter all the details", Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.toString());

            }
        });
    }*/

    public void postUserDetail(){

        Call<String> userResponse = ApiClient.getHotelService().saveUser(userRequest);

        userResponse.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responses = response.body();
                if(response.isSuccessful())
                {
                    Toast.makeText(getContext(),"Saved Successfully", Toast.LENGTH_SHORT).show();
                    disableProfile();
                }
                else
                {
                    Toast.makeText(getContext(),"Failed to save", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(),"Failed to save", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void disableProfile(){
        userName.setKeyListener(null);
        userAge.setKeyListener(null);
        userAddress.setKeyListener(null);
        userMobileNo.setKeyListener(null);

        saveUserButton.setVisibility(View.INVISIBLE);

    }

    public void saveUserEmail(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_EMAIL,null);
        editor.apply();
    }

    public void loadUserEmail()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        mCurrentUser = sharedPreferences.getString(USER_EMAIL, null);
    }

}