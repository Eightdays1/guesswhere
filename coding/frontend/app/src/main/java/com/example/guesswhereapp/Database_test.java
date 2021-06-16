package com.example.guesswhereapp;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Database_test extends AppCompatActivity {

    public static void database_check() throws IOException {
        /*
        URL url = new URL("167.99.136.249/");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
         */
        //String urlParameters  = "param1=a&param2=b&param3=c";
        String request        = "http://api.guesswhere.net/api.php?type=dbstatus";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "";
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post Request
        conn.setDoOutput(true);
        //conn.setInstanceFollowRedirects( false );
        //conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty( "charset", "utf-8");
        //conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
        //conn.setUseCaches( false );

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        //Log.d("Output","\nSending 'POST' request to URL : " + url);
        //Log.d("Output","Post parameters : " + urlParameters);
        //Log.d("Output","Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        //StringBuffer response = new StringBuffer();
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //Log.d("Output", response.toString());



    }

    public static boolean create_user(String Username, String Password) throws IOException {
        String request        = "http://api.guesswhere.net/api.php?type=createuser";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "username=" + Username + "&password=" + Password;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post-Request
        conn.setDoOutput(true);
        conn.setRequestProperty( "charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        /*
        int responseCode = conn.getResponseCode();
        Log.d("Output","\nSending 'POST' request to URL : " + url);
        Log.d("Output","Post parameters : " + urlParameters);
        Log.d("Output","Response Code : " + responseCode);
        */
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        //StringBuffer response = new StringBuffer();
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //Log.d("Output", response.toString());

        return response.toString().equals("{\"status\":\"user_created\"}");
    }

    public static String request_access_token(String Username, String Password) throws IOException{
        String request        = "http://api.guesswhere.net/api.php?type=requestaccesstoken";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "username=" + Username + "&password=" + Password;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post-Request
        conn.setDoOutput(true);
        conn.setRequestProperty( "charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        /*
        int responseCode = conn.getResponseCode();
        Log.d("Output","\nSending 'POST' request to URL : " + url);
        Log.d("Output","Post parameters : " + urlParameters);
        Log.d("Output","Response Code : " + responseCode);
        */
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        //StringBuffer response = new StringBuffer();
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //Log.d("Output", response.toString());

        //parse data
        String answer = response.toString();
        answer = answer.replace("\"", "");
        answer = answer.replace("{", "");
        answer = answer.replace("}", "");
        String status = "false";
        String accesstoken = "";

        //Log.d("answer", answer);

        String[] array = answer.split(",");
        for(String i: array){
            if (i.startsWith("status:")){
                status= i.substring(7);
                //Log.d("status", status);
            }
            if(i.startsWith("accesstoken:") && (status.equals("true"))){
                accesstoken = i.substring(12);
                //Log.d("accesstoken", accesstoken);
            }
        }
        return accesstoken;
    }

    public static String getNewImage(String accesstoken) throws IOException {
        String request        = "http://api.guesswhere.net/api.php?type=requestnewgame";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "accesstoken=" + accesstoken;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post-Request
        conn.setDoOutput(true);
        conn.setRequestProperty( "charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        /*
        int responseCode = conn.getResponseCode();
        Log.d("Output","\nSending 'POST' request to URL : " + url);
        Log.d("Output","Post parameters : " + urlParameters);
        Log.d("Output","Response Code : " + responseCode);
        */
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        //StringBuffer response = new StringBuffer();
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //parse data
        String answer = response.toString();
        answer = answer.replace("\"", "");
        answer = answer.replace("{", "");
        answer = answer.replace("}", "");
        String status = "false";
        String image_url = "";

        Log.d("answer", answer);

        //status:true,imagekey:ByOF5FN54KT82mYfa1HBdA,coordinate1:48.0803522,coordinate2:16.2948672

        String[] array = answer.split(",");
        for(String i: array){
            if (i.startsWith("status:")){
                status = i.substring(7);
            }
            if (i.startsWith("imagekey:")){
                image_url = i.substring(9);
            }
            if (i.startsWith("coordinate1:")){
                GameScreen.coordinate_2 = Float.parseFloat(i.substring(12));
            }
            if (i.startsWith("coordinate2:")){
                GameScreen.coordinate_1 = Float.parseFloat(i.substring(12));
            }
            if (i.startsWith("gameid:")){
                GameScreen.gameid = i.replace("gameid:", "");
            }
        }

        return "https://images.mapillary.com/" + image_url + "/thumb-2048.jpg";
    }

    public static boolean changePassword(String username,String old_password, String new_password) throws IOException {
        String request        = "http://api.guesswhere.net/api.php?type=changepassword";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "username=" + username + "&oldpassword=" + old_password + "&newpassword=" + new_password;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post-Request
        conn.setDoOutput(true);
        conn.setRequestProperty( "charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String answer = response.toString();
        answer = answer.replace("\"", "");
        answer = answer.replace("{", "");
        answer = answer.replace("}", "");
        boolean status = false;

        String[] array = answer.split(",");
        for(String i: array){
            if (i.startsWith("status:")){
                status = Boolean.parseBoolean(i.substring(7));
            }
        }

        return status;
    }

    public static boolean saveGame(String gameid, float guessedcoor1, float guessedcoor2, double distance) throws IOException{
        String request        = "http://api.guesswhere.net/api.php?type=savegame";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "gameid=" + gameid + "&guessed_coor1=" + guessedcoor1 + "&guessed_coor2=" + guessedcoor2 + "&distance=" + distance;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post-Request
        conn.setDoOutput(true);
        conn.setRequestProperty( "charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String answer = response.toString();
        answer = answer.replace("\"", "");
        answer = answer.replace("{", "");
        answer = answer.replace("}", "");
        boolean status = false;

        String[] array = answer.split(",");
        for(String i: array){
            if (i.startsWith("status:")){
                status = Boolean.parseBoolean(i.substring(7));
            }
        }

        return status;
    }

    public static String requestStatistic(String accesstoken) throws IOException {
        String request        = "http://api.guesswhere.net/api.php?type=getstats";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "accesstoken=" + accesstoken;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post-Request
        conn.setDoOutput(true);
        conn.setRequestProperty( "charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static String deleteStatistic(String accesstoken) throws IOException {
        String request        = "http://api.guesswhere.net/api.php?type=deletestats";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "accesstoken=" + accesstoken;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post-Request
        conn.setDoOutput(true);
        conn.setRequestProperty( "charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static void deleteUser(String accesstoken) throws IOException {
        String request        = "http://api.guesswhere.net/api.php?type=deleteuser";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "accesstoken=" + accesstoken;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post-Request
        conn.setDoOutput(true);
        conn.setRequestProperty( "charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
    }

    public static String requestChallenge(String accesstoken, String username_reciever) throws IOException {
        String request        = "http://api.guesswhere.net/api.php?type=requestchallenge";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "accesstoken=" + accesstoken + "&username_reciever=" + username_reciever;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post-Request
        conn.setDoOutput(true);
        conn.setRequestProperty( "charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String answer = response.toString();
        answer = answer.replace("\"", "");
        answer = answer.replace("{", "");
        answer = answer.replace("}", "");
        String status = "false";
        String image_url = "";

        System.out.println(answer);

        String[] array = answer.split(",");
        for(String i: array){
            if (i.startsWith("status:")){
                status = i.substring(7);
            }
            if (i.startsWith("imagekey:")){
                image_url = i.substring(9);
            }
            if (i.startsWith("coordinate1:")){
                GameScreen.coordinate_2 = Float.parseFloat(i.substring(12));
            }
            if (i.startsWith("coordinate2:")){
                GameScreen.coordinate_1 = Float.parseFloat(i.substring(12));
            }
            if (i.startsWith("gameid:")){
                GameScreen.gameid = i.replace("gameid:", "");
            }
        }

        if (image_url != "") {
            return "https://images.mapillary.com/" + image_url + "/thumb-2048.jpg";
        } else {
            return "False";
        }
    }

    public static boolean saveChallenge(String accesstoken, String usertype, String gameid, float guessedcoor1, float guessedcoor2, double distance) throws IOException {
        String request        = "http://api.guesswhere.net/api.php?type=savechallengegame";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "accesstoken=" + accesstoken + "&gameid=" + gameid + "&usertype=" + usertype + "&guessed_coor1=" + guessedcoor1 + "&guessed_coor2=" + guessedcoor2 + "&distance=" + distance;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post-Request
        conn.setDoOutput(true);
        conn.setRequestProperty( "charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String answer = response.toString();
        answer = answer.replace("\"", "");
        answer = answer.replace("{", "");
        answer = answer.replace("}", "");
        boolean status = false;

        System.out.println(answer);

        String[] array = answer.split(",");
        for(String i: array){
            if (i.startsWith("status:")){
                status = Boolean.parseBoolean(i.substring(7));
            }
        }

        return status;
    }

    public static boolean checkForChallenge(String accesstoken) throws IOException {
        String request        = "http://api.guesswhere.net/api.php?type=checkforchallenge";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "accesstoken=" + accesstoken;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post-Request
        conn.setDoOutput(true);
        conn.setRequestProperty( "charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String answer = response.toString();
        answer = answer.replace("\"", "");
        answer = answer.replace("{", "");
        answer = answer.replace("}", "");
        boolean status = false;
        boolean challenged = false;

        System.out.println(answer);

        String[] array = answer.split(",");
        for(String i: array){
            if (i.startsWith("status:")){
                status = Boolean.parseBoolean(i.substring(7));
            }
            if (i.startsWith("challenged:")){
                challenged = Boolean.parseBoolean(i.substring(11));
            }
        }

        return challenged;
    }

    public static String playChallengedGame(String accesstoken) throws IOException {
        String request        = "http://api.guesswhere.net/api.php?type=playchallengedgame";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "accesstoken=" + accesstoken;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post-Request
        conn.setDoOutput(true);
        conn.setRequestProperty( "charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String answer = response.toString();
        answer = answer.replace("\"", "");
        answer = answer.replace("{", "");
        answer = answer.replace("}", "");
        String status = "false";
        String image_url = "";

        System.out.println(answer);

        String[] array = answer.split(",");
        for(String i: array){
            if (i.startsWith("imagekey:")){
                image_url = i.substring(9);
            }
            if (i.startsWith("coordinate1:")){
                GameScreen.coordinate_2 = Float.parseFloat(i.substring(12));
            }
            if (i.startsWith("coordinate2:")){
                GameScreen.coordinate_1 = Float.parseFloat(i.substring(12));
            }
            if (i.startsWith("gameid:")){
                GameScreen.gameid = i.replace("gameid:", "");
            }
        }

        if (image_url.equals("")) {
            return "False";
        } else {
            return "https://images.mapillary.com/" + image_url + "/thumb-2048.jpg";
        }
    }

    public static String challengeResult(String accesstoken) throws IOException {
        String request        = "http://api.guesswhere.net/api.php?type=challengeresult";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();

        //Request-Header
        String urlParameters  = "accesstoken=" + accesstoken;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        conn.setRequestMethod( "POST" );

        //Send Post-Request
        conn.setDoOutput(true);
        conn.setRequestProperty( "charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}

