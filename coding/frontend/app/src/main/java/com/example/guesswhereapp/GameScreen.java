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

    public static String gameid;
    public static String challenge_url = "";
    public static String usertype = "";

    public static String message = "";

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
                Button button_challengeUser = (Button) findViewById(R.id.button_challengeUser);

                button_singleplayer.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        whichscreen = 1;
                        startAnotherGameActivity();
                    }
                });

                button_challengeUser.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        whichscreen = 2;
                        startAnotherGameActivity();
                    }
                });
                break;
            case 1://SinglePlayer
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_game_screen);
                imageView=findViewById(R.id.imageView);
                String Url = null;
                if (challenge_url == "") {
                    try {
                        Url = Database_test.getNewImage(MainScreen.user.getAccessToken());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (Url != null) {
                        Picasso.get().load(Url).into(imageView);
                    }
                } else {
                    Picasso.get().load(challenge_url).into(imageView);
                }
                Button button_guess = (Button) findViewById(R.id.button_guess);

                button_guess.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {startLocationPickerActivity(); }
                });
                break;
            case 2://Challenge User
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_game_select_user_screen);
                TextView textedit_challengedUser = (TextView) findViewById(R.id.textedit_challengedname);
                Button button_confirmChallenge = (Button) findViewById(R.id.button_confirmChallenge);
                Button button_checkChallenge = (Button) findViewById(R.id.button_checkChallenge);
                Button button_checkResults = (Button) findViewById(R.id.button_checkResults);

                button_confirmChallenge.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //database request
                        String challenged_user_name = textedit_challengedUser.getText().toString();
                        try {
                            challenge_url = Database_test.requestChallenge(MainScreen.user.getAccessToken(), challenged_user_name);
                            System.out.println(challenge_url);
                            if ((challenge_url != "False")) {
                                GameScreen.usertype = "sender";
                                whichscreen = 1;
                                startAnotherGameActivity();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                button_checkChallenge.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view){
                        try {
                            if(Database_test.checkForChallenge(MainScreen.user.getAccessToken())){
                                challenge_url = Database_test.playChallengedGame(MainScreen.user.getAccessToken());
                                if (challenge_url != "False") {
                                    GameScreen.usertype = "reciever";
                                    whichscreen = 1;
                                    startAnotherGameActivity();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                button_checkResults.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view){
                        String answer = null;
                        try {
                            answer = Database_test.challengeResult(MainScreen.user.getAccessToken());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (answer != null) {
                            answer = answer.replace("\"", "");
                            answer = answer.replace("{", "");
                            answer = answer.replace("}", "");
                            boolean status = false;
                            String username_sender = "";
                            float distance_sender = 0;
                            String username_reciever = "";
                            float distance_reciever = 0;
                            boolean result = true;

                            System.out.println(answer);

                            String[] array = answer.split(",");
                            for(String i: array){
                                if (i.startsWith("status:")){
                                    status = Boolean.parseBoolean(i.substring(7));
                                }
                                if (i.startsWith("username_sender:")){
                                    username_sender = i.substring(16);
                                }
                                if (i.startsWith("distance_sender:")){
                                    distance_sender = Float.parseFloat(i.substring(16));
                                }
                                if (i.startsWith("username_reciever:")){
                                    username_reciever = i.substring(18);
                                }
                                if (i.startsWith("distance_reciever:")){
                                    distance_reciever = Float.parseFloat(i.substring(18));
                                }
                                if (i.startsWith("result:")){
                                    result = Boolean.parseBoolean(i.substring(7));
                                }
                            }
                            if (result == true){
                                if (distance_sender < distance_reciever){
                                    message = username_sender + " was " + (distance_reciever - distance_sender) + " closer than " + username_reciever + ".";
                                } else {
                                    message = username_reciever + " was " + (distance_sender - distance_reciever) + " closer than " + username_sender + ".";
                                }
                                openDialog();
                            }
                        }
                    }
                });
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

                TextView x_coord_result = (TextView) findViewById(R.id.x_coord_result);
                x_coord_result.setText("Dein Tipp war " + round(d) + " Kilometer entfernt!");

                Button button_play_again = (Button) findViewById(R.id.button_play_again);
                Button button_main_menu = (Button) findViewById(R.id.button_main_menu);
                Button button_see_on_map = (Button) findViewById(R.id.button_see_on_map);

                button_play_again.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {playAnotherGame(); }
                });
                button_main_menu.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {toMainMenu(); }
                });
                button_see_on_map.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {toMapResult(); }
                });
                if (challenge_url == "") {
                    try {
                        Database_test.saveGame(GameScreen.gameid, guessed_coordinate_2, guessed_coordinate_1, d);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Database_test.saveChallenge(MainScreen.user.getAccessToken(), GameScreen.usertype, GameScreen.gameid, guessed_coordinate_2, guessed_coordinate_1, d);
                        challenge_url = "";
                        usertype = "";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

    private void toMapResult(){
        Intent intent = new Intent(GameScreen.this, MapResult.class);
        startActivity(intent);
    }

    private void openDialog() {
        PopupChallenge popup = new PopupChallenge();
        popup.show(getSupportFragmentManager(), "label");
    }
}