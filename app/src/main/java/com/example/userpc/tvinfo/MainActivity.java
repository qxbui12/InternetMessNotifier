package com.example.userpc.tvinfo;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends Activity {

    private class DownloadText extends AsyncTask<String , Void, String> {

        final TextView textDisplay = (TextView) findViewById(R.id.textDisplay);
        StringBuffer inputLine;
        String output;
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpsURLConnection connection = null;
            try {
                url = new URL(strings[0]);
                connection = (HttpsURLConnection) url.openConnection();
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        connection.getInputStream()));
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UnicodeLittle"));

                inputLine = new StringBuffer();
                //System.out.println("****************"+in.readLine());
                String grabber;
                while ((grabber = in.readLine()) != null){
                    inputLine.append(grabber);
                    inputLine.append(String.format("%n", ""));
                }
                String temp = inputLine.toString();
                in.close();
            }catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            output = inputLine.toString();
            return output;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Typeface font= Typeface.createFromAsset(getAssets(), "LEELAWDB.TTF");
            textDisplay.setText(result);
            textDisplay.setTypeface(font);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        new DownloadText().execute("https://dl.dropbox.com/s/h2ge68usjrdz1al/test.txt");
    }

}
