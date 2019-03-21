package com.dba.berthamandatoryassignment;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class AddSensorData {
    private static SensorRawData rawData;
    private static SensorUserData sensorUserData;
    private static final String postUrl = "https://berthabackendrestprovider.azurewebsites.net/api/data";

    public void add(String data, String userId){
        Log.e("JDATA", "" + data);

        Gson gson = new Gson();

        rawData = gson.fromJson(data, SensorRawData.class);
        sensorUserData = SensorUserData.combineData(rawData, userId);

        String jsonString = gson.toJson(sensorUserData);

        PostSensorData task = new PostSensorData();
        task.execute(postUrl, jsonString);
    }


    private class PostSensorData extends AsyncTask<String, Void, CharSequence>{

        @Override
        protected CharSequence doInBackground(String... httpParams){
            String urlString = httpParams[0];
            String jsonString = httpParams[1];

            try {
                //Checks that urlString has valid URL syntax
                URL url = new URL(urlString);

                // Open new HTTP connection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Add headers to HTTP Request
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");

                // Send data
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(jsonString);
                osw.flush();
                osw.close();

                // TODO Update Error handling fra Anders
                int responseCode = connection.getResponseCode();
                if (responseCode / 100 != 2) {
                    String responseMessage = connection.getResponseMessage();
                    throw new IOException("HTTP response code: " + responseCode + " " + responseMessage);
                }
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line = reader.readLine();
                return line;
            } catch (MalformedURLException ex) {
                cancel(true);
                String message = ex.getMessage() + " " + urlString;
                Log.e("BOOK", message);
                return message;
            } catch (IOException ex) {
                cancel(true);
                Log.e("BOOK", ex.getMessage());
                return ex.getMessage();
            }

        }

    }


}
