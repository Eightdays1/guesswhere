package com.example.guesswhereapp;

import androidx.appcompat.app.AppCompatActivity;

public class User extends AppCompatActivity {
    private final String Username;
    private final String AccessToken;

    private User(String Usernm, String AT){
        Username = Usernm;
        AccessToken = AT;
    }

    public String getUsername(){return Username;}

    public String getAccessToken(){return AccessToken;}

    public static User factory(String first, String second) {
        return new User(first, second);
    }
}
