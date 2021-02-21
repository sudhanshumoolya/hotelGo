package com.example.hotelgo;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String USER_EMAIL = "userEmail";

    private TextView logInButton;
    private Button signUpButton;
    private EditText userEmail;
    private EditText userPassword;
    private EditText userConPassword;

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userEmail = findViewById(R.id.signUp_userMail);
        userPassword = findViewById(R.id.signUp_userPassword);
        userConPassword = findViewById(R.id.signUp_userPasswordConfirmation);
        signUpButton = findViewById(R.id.signUp_button);
        logInButton = findViewById(R.id.signUp_logIn_button);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
              //  startActivity(intent);
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=userEmail.getText().toString();
                password=userPassword.getText().toString();
                if(email.isEmpty()) {
                    userEmail.setError("Please Enter the email");
                    userPassword.requestFocus();
                }
                else if(password.isEmpty()) {
                    userPassword.setError("Please Enter the Password");
                    userPassword.requestFocus();
                }
                else if(password.isEmpty()&&email.isEmpty())
                {
                    Toast.makeText(SignUpActivity.this,"fill the fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    postSignUpDetails();
                }

            }
        });

    }

    private void postSignUpDetails() {

        SignUpRequest signUpRequest = new SignUpRequest();

        signUpRequest.setUserEmail(email);
        signUpRequest.setUserPassword(password);

        Call<String> signUpRequestCall = ApiClient.getHotelService().saveSignUpDetail(signUpRequest);

        signUpRequestCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responses = response.body();
                if(response.isSuccessful())
                {
                    Toast.makeText(SignUpActivity.this,"Saved Successfully", Toast.LENGTH_SHORT).show();
                    saveUserEmail();

                    Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(SignUpActivity.this,"Failed to save", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(SignUpActivity.this,"Failed to save", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t);
            }
        });

    }


    public void saveUserEmail(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_EMAIL,email);
        editor.apply();
    }

  /*  public void loadUserEmail()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        mCurrentUser = sharedPreferences.getString(USER_EMAIL, null);
    }*/
}