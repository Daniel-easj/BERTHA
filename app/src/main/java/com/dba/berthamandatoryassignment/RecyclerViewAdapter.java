package com.dba.berthamandatoryassignment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private SensorUserData[] mUserData;
    private Context context;

    public RecyclerViewAdapter( Context context, SensorUserData[] mUserData) {
        this.mUserData = mUserData;
        this.context = context;
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

        holder.textView.setText(mUserData[position].toString());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SensorDataDetailedView.class);
                intent.putExtra("object", mUserData[position]);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        LinearLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.my_text_view);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }

}
