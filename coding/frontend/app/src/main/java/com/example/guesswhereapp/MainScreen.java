package com.example.guesswhereapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@SuppressWarnings("SpellCheckingInspection")
public class MainScreen extends AppCompatActivity {
    public static User user = null;
    public static int whichPopup = 0;
    public static String popupMessage = "";
    public static int play_another_game = 0;
    public MainScreen() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (user == null) { //Generic Screen [Not Logged in]
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            if(whichPopup != 0){
                switch(whichPopup){
                    case 4:
                        popupMessage = "You opened the Help Screen and Confirmed!";
                        break;
                    case 3:
                        popupMessage = "You sucessfully logged out!";
                        break;
                }
                openDialog();
                whichPopup = 0;
            }

            Button button_login = (Button) findViewById(R.id.button_login);
            Button button_help = (Button) findViewById(R.id.button_help);

            button_login.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    startLoginActivity();
                }
            });

            button_help.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) { startLocationPickerActivity(); }
            });

        } else { //Logged in Screen
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_loggedin);

            if(play_another_game == 1){
                play_another_game = 0;
                GameScreen.whichscreen = 1;
                startGameActivity();
            } else {
                if (whichPopup != 0) {
                    switch (whichPopup) {
                        case 1:
                            popupMessage = "You sucessfully registered!";
                            break;
                        case 2:
                            popupMessage = "You sucessfully logged in!";
                            break;
                    }
                    openDialog();
                    whichPopup = 0;
                }

                Button button_play = (Button) findViewById(R.id.button_play);
                Button button_options = (Button) findViewById(R.id.button_options);
                Button button_logout = (Button) findViewById(R.id.button_logout);
                Button button_statistics = (Button) findViewById(R.id.button_statistics);
                TextView textview_greetuser = (TextView) findViewById(R.id.textview_greetuser);

                textview_greetuser.setText("Hallo, " + user.getUsername() + "!");

                button_options.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        startOptionsActivity();
                    }

                });

            button_logout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    user = null;
                    finish(); //closes MainActivity
                    whichPopup = 3;
                    startActivity(getIntent()); //restarts Main
                }
            });

            button_play.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    GameScreen.whichscreen = 0;//reset to selection screen
                    startGameActivity();
                }
            });

            button_statistics.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) { startStatisticsActivity(); }
            });
            }
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }

    private void startOptionsActivity(){
        OptionsScreen.whichscreen = 0; //reset so it always displays the default version
        Intent intent = new Intent(this, OptionsScreen.class);
        startActivity(intent);
    }

    private void startHelpActivity(){
        Intent intent = new Intent(this, HelpScreen.class);
        startActivity(intent);
    }

    private void startLocationPickerActivity(){
        Intent intent = new Intent(this, LocationPickerActivity.class);
        startActivity(intent);
    }

    private void startStatisticsActivity(){
        Intent intent = new Intent(this, StatisticsScreen.class);
        startActivity(intent);
    }

    private void startGameActivity(){
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }

    private void openDialog() {
        Popup popup = new Popup();
        popup.show(getSupportFragmentManager(), "label");
    }
}
