package com.example.furkhan324.weightandsee;

/**
 * Created by furkhan324 on 2/18/16.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class LogsFragment extends Fragment{
    Context context;
    ListView lv;
    Firebase myFirebaseRef;
    Firebase myFirebaseRef2;
    ArrayList<PlaceModel> restaurantsVisited = new ArrayList<PlaceModel>();
    private userListAdapter listAdapter;

    public LogsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v =inflater.inflate(R.layout.logs_fragment,container,false);
        myFirebaseRef = new Firebase("https://weightandsee.firebaseio.com/");
        myFirebaseRef2 = new Firebase("https://weightandsee.firebaseio.com/users/"+"facebook:"+myFirebaseRef.getAuth().getUid()+"/logs");
        Log.e("FIREBASE", "https://weightandsee.firebaseio.com/users/" + "facebook:" + myFirebaseRef.getAuth().getUid() + "/logs");

        prepareData();
        ParseObject po = new ParseObject("Yo");
        po.add("hello", "blah");
        try {
            po.save();
        } catch (ParseException e) {
            Log.e("PARSE", Log.getStackTraceString(e));
        }
        Log.e("FIREBASE","is this even running");
        listAdapter =new userListAdapter(restaurantsVisited);
        lv=(ListView) v.findViewById(R.id.listView);
        lv.setAdapter(listAdapter);

        // Inflate the layout for this fragment
        return v;
    }

    private void prepareData(){


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Log");
        query.whereEqualTo("userid", myFirebaseRef.getAuth().getUid());
        query.findInBackground(new FindCallback<ParseObject>() {


            public void done(List<ParseObject> scoreList, ParseException e) {
                Log.e("FIREBASE","is this even runnin23g");

                if (e == null) {
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    for(int i=0; i<scoreList.size();i++)
                    {
                        PlaceModel pm= new PlaceModel();
                        pm.name_of_restaurant = scoreList.get(i).getString("name_of_restaurant");
                        pm.address_of_restaurant = scoreList.get(i).getString("address_of_restaurant");
                        pm.date = scoreList.get(i).getString("date");
                        pm.lat = scoreList.get(i).getString("lat");
                        pm.lng = scoreList.get(i).getString("lng");
                        pm.icon = scoreList.get(i).getString("icon");
                        pm.meal = scoreList.get(i).getString("meal");

                    restaurantsVisited.add(pm);


                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
        /*
        Log.e("FIREBASE","is this even running");

        myFirebaseRef2.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                Log.e("FIREBASE","is this even running2");

                if(snapshot!=null) {
                    PlaceModel log  = snapshot.getValue(PlaceModel.class);
                    Log.e("FIREBASE", log.name_of_restaurant);
                    restaurantsVisited.add(log);
                }else{
                    Log.e("FIREBASE", "FOR SOME SHITTY REASON SNAPSHOT IS NULL UGHGHGHGHGHGB");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
            //... ChildEventListener also defines onChildChanged, onChildRemoved,
            //    onChildMoved and onCanceled, covered in later sections.
        });*/

    }


    public class userListAdapter extends BaseAdapter {
        List<PlaceModel> entries2;

        public userListAdapter(List<PlaceModel> users){
            Log.e("FIREBASE","is this even runniwekjlgng");

            entries2=users;
            Log.e("FIREBASE","is this even runniwekjlgng"+ entries2.size());
        }
        public int getCount() {
            // TODO Auto-generated method stub
            return entries2.size();
        }

        @Override
        public PlaceModel getItem(int arg0) {
            // TODO Auto-generated method stub
            return entries2.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }
        /*
                @Override
                public View getView(int arg0, View arg1, ViewGroup arg2) {
                    if(arg1==null)
                    {
                        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        arg1 = inflater.inflate(R.layout.list_item, arg2,false);
                    }
                    Typeface bariol=Typeface.createFromAsset(getActivity().getAssets(), "fonts/bariol.otf");
                    TextView icon=(TextView) arg1.findViewById(R.id.textView7);
                    TextView chapterName = (TextView)arg1.findViewById(R.id.textView1);
                    TextView chapterDesc = (TextView)arg1.findViewById(R.id.textView2);
                    ImageView iconImage=(ImageView) arg1.findViewById(R.id.imageView1);
                    chapterName.setTypeface(bariol);
                    chapterDesc.setTypeface(bariol);
                    //icon.setTypeface(bariol);
                    ParseUser chapter = users2.get(arg0);
                    //icon.setText(chapter.getUsername().toString().substring(0, 1).toUpperCase());
                    chapterName.setText("Test Volunteering Opp");
                    chapterDesc.setText("Testing beta succesfull");
                    int x=arg0;
                    return arg1;
                }
        */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent){

            RelativeLayout layout = null;

            if (convertView == null) {
                // inflating the row
                LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layout = (RelativeLayout) mInflater.inflate(
                        R.layout.log_item, parent, false);


            }else{
                layout =(RelativeLayout) convertView;
            }
            Log.e("FIREBASE","is this even rqgir;j3l;iqoiunning");
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/bariol.ttf");

            TextView title=(TextView) layout.findViewById(R.id.name);
            TextView desc = (TextView)layout.findViewById(R.id.progress_comment);
            TextView joined = (TextView)layout.findViewById(R.id.date);
            ImageView im = (ImageView) layout.findViewById(R.id.icon);
            title.setText(entries2.get(position).name_of_restaurant);
            desc.setText(entries2.get(position).date);
            joined.setText(entries2.get(position).meal);
            Picasso.with(getContext()).load(entries2.get(position).icon).into(im);
            title.setTypeface(tf);
            desc.setTypeface(tf);
            joined.setTypeface(tf);



            //   ImageView profile=(ImageView) layout.findViewById(R.id.profile);
            // ImageView check=(ImageView) layout.findViewById(R.id.check);
            //have to get the bitmap of the profile user who posted/
            /*ParseObject entry = entries2.get(position);
            if (entry==null){
            Log.e("TAG","Entry"+ position+" is null");}
            else {
                if(entry.get("picture")==null)
                {
                    Log.e("TAG","picture"+ position+" is null");
                }else{
                    String encoded = entry.get("picture").toString();
                byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                profile.setImageBitmap(decodedByte);}
                // this sets the green check to the view
                check.setImageResource(R.drawable.ok);
                //to be adjusted later
                //set title of post
                if(entry.get("name")==null){
                    Log.e("TAG","title"+ position+" is null");
             }else{
                    title.setText(entry.get("name").toString());
                }
                //set description of the post
                if(entry.getString("poster")==null){
                    Log.e("TAG","poster"+ position+" is null");
                }else{
                String description = "By " + entry.getString("poster");
                desc.setText(description);}
                //sets city and state in listview
                if(entry.getString("city")==null||entry.getString("state")==null){
                    Log.e("TAG","city"+ position+" is null");
                }else {
                    city.setText(entry.getString("city") + ", " + entry.getString("state"));
                    //
                }
            }*/


            return layout;
        }
        public PlaceModel getPosition(int position)
        {
            return entries2.get(position);
        }

    }

}



