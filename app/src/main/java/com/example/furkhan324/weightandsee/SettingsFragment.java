package com.example.furkhan324.weightandsee;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;


public class SettingsFragment extends Fragment{

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.settings_fragment, container, false);
        TextView title = (TextView) v.findViewById(R.id.report_title);
        TextView subtitle = (TextView) v.findViewById(R.id.report_subtitle);
        final EditText et1 = (EditText) v.findViewById(R.id.email1);
        EditText et2 = (EditText) v.findViewById(R.id.email2);
        EditText et3 = (EditText) v.findViewById(R.id.email3);
        EditText et4 = (EditText) v.findViewById(R.id.email4);
        EditText et5 = (EditText) v.findViewById(R.id.email5);
        Button b = (Button) v.findViewById(R.id.button);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/bariol.ttf");

        title.setTypeface(tf);
        subtitle.setTypeface(tf);
        et1.setTypeface(tf);
        et2.setTypeface(tf);
        et3.setTypeface(tf);
        et4.setTypeface(tf);
        et5.setTypeface(tf);
        Firebase fb = new Firebase("https://weightandsee.firebaseio.com/");

        et1.setText(fb.getAuth().getProviderData().get("email").toString());


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new Email1().execute("");


            }
        });


        return v;
    }

}
