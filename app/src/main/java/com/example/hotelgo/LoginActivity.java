package com.example.hotelgo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.$Gson$Preconditions;

import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String USER_EMAIL = "userEmail";
    private Button logInButton;
    private TextView signUpButton;
    private EditText mUserEmail;
    private EditText mUserPassword;
    String userEmail;
    String userPassword;

    public String mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserEmail = findViewById(R.id.logIn_userMail);
        mUserPassword = findViewById(R.id.logIn_userPassword);

        logInButton = findViewById(R.id.logIn_button);
        signUpButton = findViewById(R.id.logIn_signUp_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail=mUserEmail.getText().toString();
                userPassword=mUserPassword.getText().toString();
                if(userEmail.isEmpty()) {
                    mUserEmail.setError("Please Enter the email");
                    mUserEmail.requestFocus();
                }
                else if(userPassword.isEmpty()) {
                    mUserPassword.setError("Please Enter the Password");
                    mUserPassword.requestFocus();
                }
                else if(userPassword.isEmpty()&&userEmail.isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"fill all the fields",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    getLoginStatus();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        loadUserEmail();
     //   Toast.makeText(this, mCurrentUser, Toast.LENGTH_SHORT).show();

        if(mCurrentUser!=null)
        {
            sendUserToHome();
        }

    }

    private void getLoginStatus() {

        Call<String> loginDetail = ApiClient.getHotelService().getLoginDetail(userEmail,userPassword);

        loginDetail.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String loginDetails = response.body();
                Toast.makeText(LoginActivity.this,  " "+loginDetails, Toast.LENGTH_SHORT).show();
                Log.d(TAG,"onSuccess "+loginDetails);

                saveUserEmail();

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }


            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: api not working "+t);
            }
        });

    }

    public void saveUserEmail(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_EMAIL,userEmail);
        editor.apply();
    }

    public void loadUserEmail()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        mCurrentUser = sharedPreferences.getString(USER_EMAIL, null);
    }


    private void sendUserToHome() {
     //  Intent intent = new Intent(LoginActivity.this,MainActivity.class);

     //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

     //   startActivity(intent);
        finish();


    }
}