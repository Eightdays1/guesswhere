package com.example.guesswhereapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;

public class StatisticsScreen extends AppCompatActivity {

    private TableLayout mTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_screen);
        mTableLayout = (TableLayout) findViewById(R.id.table);
        mTableLayout.setStretchAllColumns(true);
        try {
            String data = Database_test.requestStatistic(MainScreen.user.getAccessToken());
            loadData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button button_play_again = (Button) findViewById(R.id.button_return);
        Button button_main_menu = (Button) findViewById(R.id.button_delete);
        button_play_again.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {finish(); }
        });
        button_main_menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    deleteStatistics();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadData(String data){
        System.out.println(data);
        String j = data.replace("[", "");
        String k = j.replace("]", "");
        String l = k.replace("\"", "");
        String[] array = l.split(",");
        TextView textSpacer = null;
        System.out.println(array.length);
        for(int i = -1; i < array.length / 3; i++){
            if(i <= -1){
                textSpacer = new TextView(this);
                textSpacer.setText("");
            }
            final TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.LEFT);
            tv.setPadding(5, 15, 0, 15);
            if (i == -1){
                tv.setText("ID");
            }
            else {
                tv.setText(Integer.toString(i + 1));
            }

            final TextView tv2 = new TextView(this);
            if (i == -1) {
                tv2.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
            }
            else {
                tv2.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT));
            }
            tv2.setGravity(Gravity.LEFT);
            tv2.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tv2.setText("Distance");
            }
            else {
                tv2.setText(String.valueOf(array[(i*3) + 1]) + " km");
            }

            final TextView tv3 = new TextView(this);
            if (i == -1) {
                tv3.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv3.setPadding(5, 5, 0, 5);
            }
            else {
                tv3.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv3.setPadding(5, 0, 0, 5);
            }
            tv3.setGravity(Gravity.LEFT);
            if (i == -1) {
                tv3.setText("Date");
            }
            else {
                tv3.setText(String.valueOf(array[(i*3) + 2]));
            }

            if (i > -1){
                final TableRow tr = new TableRow(this);
            }

            // add table row
            final TableRow tr = new TableRow(this);
            tr.setId(i + 1);
            TableLayout.LayoutParams trParams = new
                    TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            trParams.setMargins(0, 0, 0, 0);
            tr.setPadding(0,0,0,0);
            tr.setLayoutParams(trParams);
            tr.addView(tv);
            tr.addView(tv2);
            tr.addView(tv3);
            mTableLayout.addView(tr, trParams);
            if (i > -1) {
                // add separator row
                final TableRow trSep = new TableRow(this);
                TableLayout.LayoutParams trParamsSep = new
                        TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParams.setMargins(0, 0, 0, 0);
                trSep.setLayoutParams(trParamsSep);
                TextView tvSep = new TextView(this);
                TableRow.LayoutParams tvSepLay = new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                tvSepLay.span = 4;
                tvSep.setLayoutParams(tvSepLay);
                tvSep.setHeight(1);
                trSep.addView(tvSep);
                mTableLayout.addView(trSep, trParamsSep);
            }
        }
    }

    private void deleteStatistics() throws IOException {
        System.out.println(Database_test.deleteStatistic(MainScreen.user.getAccessToken()));
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}

