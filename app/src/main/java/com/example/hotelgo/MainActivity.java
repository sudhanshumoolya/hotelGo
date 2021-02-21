package com.example.hotelgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    public String mCurrentUser;
    public int userId;

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String USER_EMAIL = "userEmail";
    private static final String TAG = MainActivity.class.getSimpleName();
    String name;
    String age;
    String address;
    String mobileNo;
    Boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadUserEmail();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getUserDetails();



        /*Fragment selectedFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("userId",userId);
        selectedFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUserEmail();

        if(mCurrentUser==null)
        {
            sendUserToLogIn();
        }

    }

    private void sendUserToLogIn() {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch(item.getItemId())
                    {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            Bundle homeBundle = new Bundle();
                            homeBundle.putInt("userId",userId);
                            selectedFragment.setArguments(homeBundle);
                            break;
                        case R.id.nav_booked:
                            selectedFragment = new BookedFragment();
                            Bundle bookedBundle = new Bundle();
                            bookedBundle.putInt("userId",userId);
                            selectedFragment.setArguments(bookedBundle);
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    if(item.getItemId()==R.id.nav_profile)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("name",name);
                        bundle.putString("age",age);
                        bundle.putString("address",address);
                        bundle.putString("mobileNo",mobileNo);
                        selectedFragment.setArguments(bundle);
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

                    return true;
                }
            };

    public void loadUserEmail()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        mCurrentUser = sharedPreferences.getString(USER_EMAIL, null);
    }


    private void getUserDetails() {
        String userEmail = mCurrentUser;

        Call<List<UserResponse>> userResponseCall = ApiClient.getHotelService().getUserDetail(userEmail);

        userResponseCall.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {

                if(response.isSuccessful()) {
                    List<UserResponse> userResponses = response.body();

                    userId = userResponses.get(0).getUser_id();
                    name = userResponses.get(0).getUser_name();
                    age = String.valueOf(userResponses.get(0).getUser_age());
                    address = userResponses.get(0).getUser_address();
                    mobileNo = String.valueOf(userResponses.get(0).getMobile_number());
                    //Toast.makeText(MainActivity.this, String.valueOf(userId), Toast.LENGTH_SHORT).show();

                    Fragment selectedFragment = new HomeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("userId",userId);
                    selectedFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

                  }

            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Enter all the details", Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.toString());
                flag = false;
            }
        });


    }

}