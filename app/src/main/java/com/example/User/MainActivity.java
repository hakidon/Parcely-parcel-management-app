package com.example.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    //Variable Home Screen
    Animation topAnim;
    Animation bottomAnim;

    private static int SPLASH_SCREEN = 5000;

    TextView name;

    @Override
    public void onBackPressed() {
        openMain();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        button1 = (Button) findViewById(R.id.page1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open1();
            }
        });


        button3 = (Button) findViewById(R.id.page3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open3();
            }
        });



    }

        public void openMain() {
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
        }

        public void open1() {
            Intent intent = new Intent (this, FirstPage.class);
            startActivity(intent);
        }



        public void open3() {
            Intent intent = new Intent (this, ThirdPage.class);
            startActivity(intent);
        }




    }