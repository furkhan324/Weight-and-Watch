package com.example.furkhan324.weightandsee;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{

    Context context;
    ArrayList<PlaceModel> data;

    private static LayoutInflater inflater=null;
    public CustomAdapter(MainActivity mainActivity, ArrayList<PlaceModel> data) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.data = data;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.e("FIREBASE","is this evenwgjn contst view running");



    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView name;
        TextView date;

        TextView progress_comment;
        ImageView icon;
        ImageView flag;



    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Log.e("FIREBASE", "is this even runnrowviewing");

        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.log_item, null);
        holder.name=(TextView) rowView.findViewById(R.id.name);
        holder.icon=(ImageView) rowView.findViewById(R.id.icon);
        holder.progress_comment =(TextView) rowView.findViewById(R.id.progress_comment);
        holder.date =(TextView) rowView.findViewById(R.id.date);
        holder.flag = (ImageView) rowView.findViewById(R.id.flag);

        Picasso.with(context).load("icon").into(holder.icon);
        holder.name.setText(data.get(position).name_of_restaurant);
        holder.date.setText(data.get(position).date);
        holder.progress_comment.setText(data.get(position).meal);



        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+data.get(0).toString(), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}