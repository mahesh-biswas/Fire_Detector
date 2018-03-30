package com.projects.mahesh.fire_detector;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by mahesh on 11/9/2017.
 */

public class myAdapter extends ArrayAdapter<RoomData>{
    Activity activity;
    Context context;
    List<RoomData> list;
    int resource;

    public myAdapter(MainActivity bookList, @NonNull List<RoomData> books, int resource){
        super(bookList.getApplicationContext(),resource,books);
        this.context = bookList.getApplicationContext();
        this.list = books;
        this.resource = resource;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        context = parent.getContext();
        View mview;
        mview = LayoutInflater.from(parent.getContext()).inflate(resource,null,false);
        TextView room_no = (TextView) mview.findViewById(R.id.room_no);
        TextView temperature = (TextView) mview.findViewById(R.id.temperature);
        TextView room_type = (TextView) mview.findViewById(R.id.room_type);
//        TextView feed_time = mview.findViewById(R.id.feed_time);
        LinearLayout viewBack = mview.findViewById(R.id.viewBack);
//        icon.setImageResource(list.get(position).image);
//        Glide.with(context).load(Uri.parse(list.get(position).imageUri)).placeholder(R.drawable.loadingprimary).into(icon);
        room_no.setText(list.get(position).getRoom_no());
        temperature.setText(list.get(position).getTemperature());
//        Date curTime = Calendar.getInstance().getTime();
//        feed_time.setText(curTime.toString());
        switch(room_no.getText().toString().charAt(4)){
            case '1':   room_type.setText("Kitchen");   break;
            case '2':   room_type.setText("Hall");   break;
            case '3':   room_type.setText("Drawing");   break;
            case '4':   room_type.setText("BedRoom");   break;
            case '5':   room_type.setText("BedRoom_1");   break;
            default:    room_type.setText("misc");
        }
        float temp = Float.parseFloat(temperature.getText().toString().trim());
        if(temp<10){
            viewBack.setBackgroundColor(Color.parseColor("#33b5e5"));
        }else if(temp<30){
            viewBack.setBackgroundColor(Color.parseColor("#99cc00"));
        }else if(temp<50 && temp>30){
            viewBack.setBackgroundColor(Color.parseColor("#ff8800"));
        }else if(temp>50){
            viewBack.setBackgroundColor(Color.parseColor("#cc0000"));
        }

        return mview;
    }

}
