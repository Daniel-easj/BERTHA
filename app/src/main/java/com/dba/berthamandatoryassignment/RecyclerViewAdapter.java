package com.dba.berthamandatoryassignment;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<SensorUserData> mUserData;
    private Context context;
    private Geocoder geocoder;

    public RecyclerViewAdapter( Context context, ArrayList<SensorUserData> mUserData) {
        this.mUserData = mUserData;
        this.context = context;

        geocoder = new Geocoder(context, Locale.getDefault());
    }


    // Responsible for inflating the View
    // implementation is very standard except for "R.layout.recyclerview_row" which can be different
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);

        return new ViewHolder(view);
    }

    // This method is called every time a new item is put in the recycler view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.userText.setText(String.format("User: %s", mUserData.get(position).getUserEmail()));
        holder.timeText.setText(String.format("Time: %s", mUserData.get(position).getDateFormatted()));
        holder.locationText.setText(String.format("Location: %s", mUserData.get(position).getLatitude(), " ", mUserData.get(position).getLongitude()));
        holder.co2Text.setText(String.format("CO2 Level: %s", mUserData.get(position).getCo2()));
        holder.tempText.setText(String.format("Temperature: %s", mUserData.get(position).getTemperature()));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SensorDataDetailedView.class);
                intent.putExtra("object", mUserData.get(position));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserData.size();
    }

    public void addItem(int position, SensorUserData user, String userId){
        user.setUserEmail(userId);
        mUserData.add(position,user);
        this.notifyItemChanged(position);
    }

  //  private String geoLocation(double latitude, double longitude, int length){
  //      try {
  //          return geocoder.getFromLocation(latitude,longitude, length).get(0).getLocality();
  //      } catch (IOException e) {
  //          return String.format("Location: %s", latitude, " ", longitude);
  //      }
  //  }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userText;
        TextView timeText;
        TextView locationText;
        TextView tempText;
        TextView co2Text;

        LinearLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userText = itemView.findViewById(R.id.userText);
            timeText = itemView.findViewById(R.id.timeText);
            locationText = itemView.findViewById(R.id.locationText);
            tempText = itemView.findViewById(R.id.tempText);
            co2Text = itemView.findViewById(R.id.co2Text);


            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }




}
