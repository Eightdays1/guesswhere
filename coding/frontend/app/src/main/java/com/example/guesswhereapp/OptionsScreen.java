package com.example.guesswhereapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class OptionsScreen extends AppCompatActivity {
    public static int whichscreen = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        switch(whichscreen) {
            case 0: //display normal screen
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_options);

                Button button_changePassword = (Button) findViewById(R.id.button_changePassword);
                Button button_deleteUser = (Button) findViewById(R.id.button_deleteUser);

                button_changePassword.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        whichscreen = 2;
                        startAnotherOptionsActivity();
                    }
                });

                button_deleteUser.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        try {
                            Database_test.deleteUser(MainScreen.user.getAccessToken());
                            whichscreen = 0;
                            MainScreen.user = null;
                            toMainMenu();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 2: //display change password screen
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_options_change_password);

                TextView textedit_newPassword = (TextView) findViewById(R.id.textedit_newPassword);
                TextView textedit_oldPassword = (TextView) findViewById(R.id.textedit_oldPassword);
                Button button_confirmPswChange = (Button) findViewById(R.id.button_confirmPswChange);
                button_confirmPswChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //database request
                        String name = MainScreen.user.getUsername();
                        String oldpsw = textedit_oldPassword.getText().toString();
                        String newpsw = textedit_newPassword.getText().toString();
                        try {
                            if(Database_test.changePassword(name, oldpsw, newpsw)){
                                MainScreen.popupMessage = "Passwort erfolgreich geändert!";
                            } else {
                                MainScreen.popupMessage = "Fehler beim ändern des Passwortes. Versuchen Sie es nochmal!";
                            }
                            openDialog();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                break;
        }
    }

    private void startAnotherOptionsActivity() {
        Intent intent = new Intent(this, OptionsScreen.class);
        startActivity(intent);
    }

    private void openDialog() {
        Popup popup = new Popup();
        popup.show(getSupportFragmentManager(), "label");
    }

    private void toMainMenu(){
        Intent intent = new Intent(OptionsScreen.this, MainScreen.class);
        // set the new task and clear flags
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}