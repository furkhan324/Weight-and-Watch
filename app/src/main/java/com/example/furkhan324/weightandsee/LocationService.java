package com.example.furkhan324.weightandsee;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by furkhan324 on 2/17/16.
 */

public class LocationService extends Service implements LocationListener{

    LocationManager mLocationManager;
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_TEXT_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
    private static final String API_KEY ="AIzaSyCmQDhb8hMYPBDu8XVXHBwMGE9pa5KS7D8";
    double lat;
    double lng;
    private static final double radius = 100; //100 METERS (TOO BIG?// )
    Boolean isInternetPresent = false;
    Handler handler;
    // Place Details
    PlaceDetails placeDetails;
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Google Places
    LocationService googlePlaces;

    // Places List
    PlacesList nearPlaces;

    //Authdata object for firebase
    AuthData ad;


    //firebase ref
    Firebase myFirebaseRef;

    String uid;
    // Progress dialog
    ProgressDialog pDialog;


    // ListItems data
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();


    // KEY Strings
    public static String KEY_REFERENCE = "reference"; // id of the place
    public static String KEY_NAME = "name"; // name of the place
    public static String KEY_VICINITY = "vicinity"; // Place area name



    public LocationService() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        Toast.makeText(this, "Just looking out to see if you're out eating!", Toast.LENGTH_LONG).show();

        //!!!!!ADD LOCATION AND INTERNET CHECK BEFORE USING THESE METHODS
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(mLocationManager!=null) {

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5 * 60 * 1000,
                    6000, this);

            myFirebaseRef = new Firebase("https://weightandsee.firebaseio.com/");
            ad = myFirebaseRef.getAuth();
            uid = ad.getUid();

        }
        else
        {
            Log.e("LOCATION", "Location manager not returned!");
        }
        //request location either every 5 minutes or every time location has changed by 6000 meters(roughly a mile)


    }

    @Override
    public void onStart(Intent intent, int startId) {
        // For time consuming an long tasks you can launch a new thread here...
        Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLocationChanged(Location location) {

        //this code should run every 5 mins or every time the user moves by a mile

        //GETING coords from the cureent Location
        lat=location.getLatitude();
        lng = location.getLongitude();

        //fills the PlacesList with Places
        new LoadPlaces().execute();
        //fills PlaceDetails with info about closest place
        new LoadSinglePlaceDetails().execute(nearPlaces.results.get(0).reference);




    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    ///////////////////////////////API CLIENT SIDE METHODS ///////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////API CLIENT SIDE METHODS ///////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Searching places
     * @param latitude - latitude of place
     * @params longitude - longitude of place
     * @param radius - radius of searchable area
     * @param types - type of place to search
     * @return list of places
     * */
    public PlacesList search(double latitude, double longitude, double radius, String types)
            throws Exception {

        try {

            HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
            HttpRequest request = httpRequestFactory
                    .buildGetRequest(new GenericUrl(PLACES_SEARCH_URL));
            request.getUrl().put("key",API_KEY );
            request.getUrl().put("location", lat + "," + lng);
            request.getUrl().put("radius", radius); // in meters
            request.getUrl().put("sensor", "false");
            if(types != null)
                request.getUrl().put("types", types);

            PlacesList list = request.execute().parseAs(PlacesList.class);
            // Check log cat for places response status
            Log.d("Places Status", "" + list.status);
            return list;

        } catch (Exception e) {
            Log.e("Error:", e.getMessage());
            return null;
        }

    }

    public PlaceDetails getPlaceDetails(String reference) throws Exception {
        try {

            HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
            HttpRequest request = httpRequestFactory
                    .buildGetRequest(new GenericUrl(PLACES_DETAILS_URL));
            request.getUrl().put("key", API_KEY);
            request.getUrl().put("reference", reference);
            request.getUrl().put("sensor", "false");

            PlaceDetails place = request.execute().parseAs(PlaceDetails.class);

            return place;

        } catch (Exception e) {
            Log.e("Error in Perform Detils", e.getMessage());
            throw e;
        }
    }

    /**
     * Creating http request Factory
     * */
    public static HttpRequestFactory createRequestFactory(
            final HttpTransport transport) {
        return transport.createRequestFactory(new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
                GoogleHeaders headers = new GoogleHeaders();
                headers.setApplicationName("AndroidHive-Places-Test");
                request.setHeaders(headers);
                JsonObjectParser parser = new JsonObjectParser(new JacksonFactory());
                request.setParser(parser);
            }
        });
    }

    ///////////////////////////////API CLIENT SIDE METHODS ///////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////API CLIENT SIDE METHODS ///////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////ASYNC TASK TO FIND PLACE ///////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////ASYNC TASK OT FIND PLACE ///////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////

    class LoadPlaces extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * getting Places JSON
         * */
        protected String doInBackground(String... args) {
            // creating Places class object
            googlePlaces = new LocationService();

            try {
                // Separeate your place types by PIPE symbol "|"
                // If you want all types places make it as null
                // Check list of types supported by google
                //
                String types = "cafe|restaurant"; // Listing places only cafes, restaurants

                // Radius in meters - increase this value if you don't find any places
                double radius = 10; // 10 meters

                // get nearest places
                nearPlaces = googlePlaces.search(lat,
                        lng, radius, types);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * and show the data in UI
         * Always use runOnUiThread(new Runnable()) to update UI from background
         * thread, otherwise you will get error
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            // updating UI from Background Thread

                    /**
                     * Updating parsed Places into LISTVIEW
                     * */
                    // Get json response status
                    String status = nearPlaces.status;

                    // Check for all possible status
                    if(status.equals("OK")){
                        // Successfully got places details
                        if (nearPlaces.results != null) {
                            // loop through each place
                            for (Place p : nearPlaces.results) {
                                HashMap<String, String> map = new HashMap<String, String>();

                                // Place reference won't display in listview - it will be hidden
                                // Place reference is used to get "place full details"
                                map.put(KEY_REFERENCE, p.reference);

                                // Place name
                                map.put(KEY_NAME, p.name);


                                // adding HashMap to ArrayList
                                placesListItems.add(map);
                            }

                        }
                    }

                    else
                    {
                        Log.e("PLACE", status);
                    }


        }

    }
    ///////////////////////////////ASYNC TASK TO FIND PLACE ///////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////ASYNC TASK OT FIND PLACE ///////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////ASYNC TASK TO get PLACE////////////////////////////////////////////////
    // /////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////ASYNC TASK to get PLACE ///////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Background Async Task to Load Google places
     * */
    class LoadSinglePlaceDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * getting Profile JSON
         * */
        protected String doInBackground(String... args) {
            String reference = args[0];

            // creating Places class object
            googlePlaces = new LocationService();

            // Check if used is connected to Internet
            try {
                placeDetails = googlePlaces.getPlaceDetails(reference);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            // updating UI from Background Thread

                    /**
                     * Updating parsed Places into LISTVIEW
                     * */
                    if(placeDetails != null){
                        String status = placeDetails.status;

                        // check place deatils status
                        // Check for all possible status
                        if(status.equals("OK")){
                            if (placeDetails.result != null) {
                                String name = placeDetails.result.name;
                                String address = placeDetails.result.formatted_address;
                                String phone = placeDetails.result.formatted_phone_number;
                                String latitude = Double.toString(placeDetails.result.geometry.location.lat);
                                String longitude = Double.toString(placeDetails.result.geometry.location.lng);

                                Log.d("Place ", name + address + phone + latitude + longitude);


                                // Check for null data from google
                                // Sometimes place details might missing
                                name = name == null ? "Not present" : name; // if name is null display as "Not present"
                                address = address == null ? "Not present" : address;
                                phone = phone == null ? "Not present" : phone;
                                latitude = latitude == null ? "Not present" : latitude;
                                longitude = longitude == null ? "Not present" : longitude;

                                ///////////Ok do this if there is a place found from the latest query
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("name_of_restaurant", name);
                                map.put("address_of_restaurant", address);
                                map.put("lat", latitude);
                                map.put("lng", longitude);
                                map.put("icon", placeDetails.result.icon);
                                long yourmilliseconds = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

                                Date resultdate = new Date(yourmilliseconds);


                                int hour=resultdate.getHours();
                                if(hour< 12){
                                    map.put("meal", "breakfast");

                                }else if(hour>=12 && hour<=4){
                                    map.put("meal", "lunch");

                                }else{
                                    map.put("meal", "dinner");

                                }
                                map.put("date", resultdate.getDay() +"/" + resultdate.getMonth());                             ///////Using picasso to load in images into listview
                                myFirebaseRef.child("users").child(uid).child("logs").push().setValue(map);

                            }
                        }
                        else{
                            Log.e("PLACE", status);

                        }


                    }else{
                        Log.e("PLACE", "Place details was null");

                    }

        }

    }
    ///////////////////////////////ASYNC TASK TO get PLACE////////////////////////////////////////////////
    // /////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////ASYNC TASK to get PLACE ///////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////

}
