package com.example.guesswhereapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class GameScreen extends AppCompatActivity {

    public static float coordinate_1;
    public static float coordinate_2;

    public static float guessed_coordinate_1;
    public static float guessed_coordinate_2;

    public static int whichscreen = 0;

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //button_singleplayer
        switch(whichscreen){
            case 0://Selection Screen
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_game_select_screen);
                Button button_singleplayer = (Button) findViewById(R.id.button_singleplayer);

                button_singleplayer.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        whichscreen = 1;
                        startAnotherGameActivity();
                    }
                });
                break;
            case 1://SinglePlayer
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_game_screen);
                imageView=findViewById(R.id.imageView);

                String Url = null;

                try {
                    Url = Database_test.getNewImage(MainScreen.user.getAccessToken());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Url != null){
                    Picasso.get().load(Url).into(imageView);
                }
                Button button_guess = (Button) findViewById(R.id.button_guess);

                button_guess.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {startLocationPickerActivity(); }
                });
                break;
            case 2://Challenge User
                break;
            case 3://GameOver
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_game_result_screen);

                //calculate distance 'd' in kilometres
                final double radius = 6371000;
                final double lat1 = coordinate_2 * Math.PI/180;
                final double lat2 = guessed_coordinate_2 * Math.PI/180;
                final double diff_lat = (guessed_coordinate_2 - coordinate_2) * Math.PI/180;
                final double diff_lon = (guessed_coordinate_1 - coordinate_1) * Math.PI/180;

                final double a = Math.sin(diff_lat/2) * Math.sin(diff_lat/2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(diff_lon/2) * Math.sin(diff_lon/2);
                final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                final double d = radius * c / 1000; // in kilometres

                float difference_x = abs(coordinate_1 - guessed_coordinate_1);
                float difference_y = abs(coordinate_2 - guessed_coordinate_2);

                TextView x_coord_result = (TextView) findViewById(R.id.x_coord_result);
                x_coord_result.setText("Dein Tipp war " + round(d) + " Kilometer entfernt!");
                //x_coord_result.setText("Deine X-Koordinate war " + difference_x + " entfernt!");

                Button button_play_again = (Button) findViewById(R.id.button_play_again);
                Button button_main_menu = (Button) findViewById(R.id.button_main_menu);
                button_play_again.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {playAnotherGame(); }
                });
                button_main_menu.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {toMainMenu(); }
                });

                try {
                    Database_test.saveGame(MainScreen.user.getAccessToken(), guessed_coordinate_2, guessed_coordinate_1, d);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void startAnotherGameActivity() {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }

    private void startLocationPickerActivity(){
        Intent intent = new Intent(this, LocationPickerActivity.class);
        startActivity(intent);
    }

    private void playAnotherGame(){
        Intent intent = new Intent(GameScreen.this, MainScreen.class);
        // set the new task and clear flags
        MainScreen.play_another_game = 1;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void toMainMenu(){
        Intent intent = new Intent(GameScreen.this, MainScreen.class);
        // set the new task and clear flags
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}