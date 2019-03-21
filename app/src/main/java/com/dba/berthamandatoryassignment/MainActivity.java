package com.dba.berthamandatoryassignment;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.AsyncTask;
import android.service.autofill.UserData;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private static final String JSON_LOG = "JSONLOG";
    private static final String BaseUrlString = "https://berthabackendrestprovider.azurewebsites.net/api/data/";
    private static final String WristbandUrlString = "https://berthawristbandrestprovider.azurewebsites.net/api/wristbanddata";
    private static boolean isWristbandDataRequest;
    private static String jsonWristbandData;

    //Firebase user data
    private static String usernameUrl;
    private static String userEmail;

    final Gson gson = new GsonBuilder().create();

    // Listview items
    private SensorUserData[] userData;
//    private ArrayAdapter<SensorUserData> adapter;
//    private ListView listView;
    SwipeRefreshLayout pullToRefresh;

    // RecyclerView Items
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;



    private static final AddSensorData httpPostHandler = new AddSensorData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        pullToRefresh =  findViewById(R.id.pullToRefresh);
        recyclerView = findViewById(R.id.my_recycler_view);


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateList();
            }
        });

        // Add app bar to activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        usernameUrl = intent.getStringExtra("UID");
        userEmail = intent.getStringExtra("email");

        // Restore userData array
        if (savedInstanceState != null && savedInstanceState.getSerializable("userdata") != null){
            userData = (SensorUserData[]) savedInstanceState.getSerializable("userdata");
            initRecyclerView(userData);
        }

    }

    // Restore state when orientation changes
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (userData != null){
            outState.putSerializable("userdata", userData);
        }

    }

    // Adds any items from the menu resource file to the app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // Adds action to item clicks for items on app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.create_sensor_data:
                addData();
                return true;
            case R.id.update_data_list:
                updateList();
                return true;
            case R.id.maps:
                if (userData != null){
                    Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                    intent.putExtra("userData",userData);
                    startActivity(intent);
                    return true;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addData(){
        isWristbandDataRequest = true;
        ReadSensorData task = new ReadSensorData();
        task.execute(WristbandUrlString);
    }

    public void updateList(){
        ReadSensorData task = new ReadSensorData();
        task.execute(BaseUrlString + usernameUrl);

        Toast toast = Toast.makeText(getApplicationContext(), "Getting user data", Toast.LENGTH_SHORT);
        toast.show();
    }

    // Old method for creating listview
//    private void makeListView() {
//
//        adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, userData);
//
//        for (SensorUserData user : userData) {
//            user.setUserEmail(userEmail);
//        }
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getBaseContext(), SensorDataDetailedView.class);
//                intent.putExtra("object", userData[position]);
//
//                startActivity(intent);
//            }
//        });
//
//        listView.setAdapter(adapter);
//    }

    // Setup RecyclerView
    private void initRecyclerView(SensorUserData[] userData){

        for (SensorUserData user : userData) {
            user.setUserEmail(userEmail);
        }

        recyclerView = findViewById(R.id.my_recycler_view);
        mAdapter = new RecyclerViewAdapter(this, userData);


        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    // AsyncTask params: <Params, Progress, Result>
    private class ReadSensorData extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls){

            try {
                return readJson(urls[0]);
            } catch (IOException e) {
                cancel(true);
                Log.e(JSON_LOG, e.toString());
                return e.toString();
            }

        }


        @Override
        protected void onPostExecute(String jsonString){

                if (isWristbandDataRequest){
                    isWristbandDataRequest = false;

                    jsonWristbandData = jsonString;
                    httpPostHandler.add(jsonWristbandData, usernameUrl);

                }
                else{
                    Log.e(JSON_LOG, jsonString);

                    userData = gson.fromJson(jsonString, SensorUserData[].class);

                    initRecyclerView(userData);

                    Toast successToast = Toast.makeText(getApplicationContext(), "Data received", Toast.LENGTH_SHORT);
                    successToast.show();
                }

            pullToRefresh.setRefreshing(false);
        }

        @Override
        protected void onCancelled(String json) {
            super.onCancelled(json);
            Toast errorToast = Toast.makeText(getApplicationContext(), "Failed to read data", Toast.LENGTH_SHORT);
            errorToast.show();
        }

        private String readJson(String urlString) throws IOException{
            StringBuilder stringBuilder = new StringBuilder();
            final InputStream content = openHttpConnection(urlString);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            while (true) {
                final String line = reader.readLine();
                if (line == null)
                    break;
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }

        private InputStream openHttpConnection(final String urlString) throws IOException{
            final URL url = new URL(urlString);
            final URLConnection conn = url.openConnection();

            if (!(conn instanceof HttpURLConnection))
                throw new IOException("Not an HTTP connection");

            final HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);

            // No user interaction like dialog boxes, etc.
            httpConn.setInstanceFollowRedirects(true);

            // follow redirects, response code 3xx
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            final int response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                return httpConn.getInputStream();
            } else {
                throw new IOException("HTTP response not OK");
            }
        }

    }

}
