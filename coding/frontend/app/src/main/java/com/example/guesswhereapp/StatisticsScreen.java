package com.example.guesswhereapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;

public class StatisticsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_screen);
        try {
            Database_test.requestStatistic(MainScreen.user.getAccessToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}