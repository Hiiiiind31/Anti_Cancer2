package com.example.hindahmed.anti_cancer;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Welcome_Activity extends AppCompatActivity {
    Button sign_in_button ;
    Button sign_up_button;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_);


         sign_in_button = (Button)findViewById(R.id.sign_in_button);
         sign_up_button = (Button)findViewById(R.id.sign_up_button);

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
                Intent i = new Intent(Welcome_Activity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
                Intent i = new Intent(Welcome_Activity.this,Sign_up_Activity.class);
                startActivity(i);
            }
        });

        // Checking for first time launch
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            sign_up_button.setVisibility(View.GONE);
            sign_in_button.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(Welcome_Activity.this, Public_Activity.class);
                    startActivity(i);
                    Toast.makeText(Welcome_Activity.this,"kkkkkkk",Toast.LENGTH_LONG).show();
                    // close this activity
//                    Intent i = new Intent(Welcome_Activity.this,LoginActivity.class);
//                    startActivity(i);
                    finish();

                }
            }, 1000);
        }

    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);

    }

}
