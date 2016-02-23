package com.example.furkhan324.weightandsee;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

;


public class AnalyticsFragment extends Fragment implements OnChartValueSelectedListener{
    Context context;
    ListView lv;
    private PieChart mChart;
    private RadarChart rChart;
    private BarChart bChart ;
    View v;
    Firebase myFirebaseRef;
    Firebase myFirebaseRef2;
    public int[] mealCount ={2,3,4};
    private String[] meals = { "Breakfast", "Lunch", "Dinner"};
    ArrayList<PlaceModel> restaurantsVisited = new ArrayList<PlaceModel>();
    String[] restaurantList ={"taco bell", "chipotle", "starbucks","pizza hut" ,"dominos","little caesars", "mcdonald", "burger king", "jack in the box", "subway","wendy", "panda express", "quiznos", "carl's", "in-n-out"};

    HashMap<String, Integer> prefsList = new HashMap<String, Integer>();
    HashMap<String, Integer> dateList = new HashMap<String, Integer>();

    private float[] yData = { 0,0,0};
    private String[] xData = { "Breakfast","Lunch", "Dinner" };
    public AnalyticsFragment() {
    
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void dataFrequencyProcessing(){/*
        bChart = (BarChart) v.findViewById(R.id.bar_chart);
        bChart.setOnChartValueSelectedListener(this);

        bChart.setDrawBarShadow(false);
        bChart.setDrawValueAboveBar(true);

        bChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        bChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        bChart.setPinchZoom(false);

        bChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        ///mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = bChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
     //   xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = bChart.getAxisLeft();
       // leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = bChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        //rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        setBarData(12, 50); */

        BarChart chart = (BarChart) v.findViewById(R.id.bar_chart);

        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("Caloric Intake");
        chart.animateXY(4000, 4000);
        chart.invalidate();
    }

    private BarDataSet getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        float[] zzzArray=new float[7];

        int[] calorieCountArray = new int[7];
        for(int i = 0; i < calorieCountArray.length; i++){
            zzzArray[i] = (float) Math.random();
            //if(zzz*3 < 1.3){
                //do nothing
            //}
            if(zzzArray[i]*3 > 0 && zzzArray[i] < 2.0){
                calorieCountArray[i] += Math.random()*500 + 300; // drink
            }
            else if(zzzArray[i]*3 > 2.0 && zzzArray[i] < 2.6){
                calorieCountArray[i] += Math.random()*450 + 500;// fast food
            }
            else if(zzzArray[i]*3 > 2.6){
                calorieCountArray[i] += Math.random()*600 + 600; // restaurant
            }
        }


        BarEntry v1e1 = new BarEntry(calorieCountArray[0], 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(calorieCountArray[1], 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(calorieCountArray[2], 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(calorieCountArray[3], 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(calorieCountArray[4], 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(calorieCountArray[5], 5); // Jun
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(calorieCountArray[6], 6); // Jun
        valueSet1.add(v1e7);


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Calorie Count");
        barDataSet1.setColor(Color.rgb(244, 67, 54));

        return barDataSet1;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Monday");

        xAxis.add("Tuesday");
        xAxis.add("Wednesday");
        xAxis.add("Thursday");
        xAxis.add("Friday");
        xAxis.add("Saturday");
        xAxis.add("Sunday");
        return xAxis;
    }


    public void dataSpreadProcessing(){/*
        mChart = (PieChart) v.findViewById(R.id.pie_chart);
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        // tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");

        // mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        setPieData(3, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        // Inflate the layout for this fragment */
        mChart=(PieChart) v.findViewById(R.id.pie_chart);


        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("Meal Spread");

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;


            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data
        addData();

        // customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        mChart.animateXY(1000,1000);


    }
    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();


        yVals1.add(new Entry(mealCount[0],0));
        yVals1.add(new Entry(mealCount[1],1));
        yVals1.add(new Entry(mealCount[2],2));


        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Meal Spread");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(new int[]{R.color.violet, R.color.purple, R.color.pink}, getContext());

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }


    public void dataPrefsProcessing(){

        /*
        for(int i =0; i< restaurantsVisited.size(); i++){
            for(int y=0; y< restaurantList.length; y++){
                if(restaurantsVisited.get(i).name_of_restaurant.toLowerCase().contains(restaurantList[y])){

                    if (prefsList.get(restaurantList[y])!=null){
                        int c =prefsList.get(restaurantList[y])+1;
                        prefsList.put(restaurantList[y],c);
                    }
                    else{
                        prefsList.put(restaurantList[y], 1);
                    }
                }
            }
        }






        rChart = (RadarChart) v.findViewById(R.id.radar_chart);

        //tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        rChart.setDescription("");

        rChart.setWebLineWidth(1.5f);
        rChart.setWebLineWidthInner(0.75f);
        rChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        //MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        //mChart.setMarkerView(mv);

        setRadarData(0,0);
        rChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = rChart.getXAxis();
        //xAxis.setTypeface(tf);
        xAxis.setTextSize(9f);

        YAxis yAxis = rChart.getYAxis();
        //yAxis.setTypeface(tf);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinValue(0f);

        Legend l = rChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
       // l.setTypeface(tf);
        l.setXEntrySpace(7f);

        l.setYEntrySpace(5f); */

        rChart = (RadarChart) v.findViewById(R.id.radar_chart);

     //   tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        rChart.setDescription("");

        rChart.setWebLineWidth(1.5f);
        rChart.setWebLineWidthInner(0.75f);
        rChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        //MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        //rChart.setMarkerView(mv);

        setData();

        rChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = rChart.getXAxis();
        //xAxis.setTypeface(tf);
        xAxis.setTextSize(9f);

        YAxis yAxis = rChart.getYAxis();
        //yAxis.setTypeface(tf);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinValue(0f);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //l.setTypeface(tf);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);

    }
    private String[] mParties = new String[]{
            "Taco Bell", "Chipotle", "Starbucks", "McDonalds" , "Subway"
    };

    public void setData() {

        float mult = 6;
        int cnt = 5;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < cnt; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 2, i));
        }



        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < cnt; i++)
            xVals.add(mParties[i % mParties.length]);

        RadarDataSet set1 = new RadarDataSet(yVals1, "Set 1");
        set1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        set1.setFillColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        set1.setDrawFilled(true);
        set1.setLineWidth(2f);

        RadarDataSet set2 = new RadarDataSet(yVals2, "Set 2");
        set2.setColor(ColorTemplate.VORDIPLOM_COLORS[4]);
        set2.setFillColor(ColorTemplate.VORDIPLOM_COLORS[4]);
        set2.setDrawFilled(true);
        set2.setLineWidth(2f);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(xVals, sets);
       // data.setValueTypeface(tf);
        data.setValueTextSize(8f);
        data.setDrawValues(false);

        rChart.setData(data);

        rChart.invalidate();
    }

    private void prepareData(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Log");
        query.whereEqualTo("userid", myFirebaseRef.getAuth().getUid());
        query.findInBackground(new FindCallback<ParseObject>() {


            public void done(List<ParseObject> scoreList, ParseException e) {
                Log.e("FIREBASE", "is this even runnin23g");

                if (e == null) {
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    for (int i = 0; i < scoreList.size(); i++) {
                        PlaceModel pm = new PlaceModel();
                        pm.name_of_restaurant = scoreList.get(i).getString("name_of_restaurant");
                        pm.address_of_restaurant = scoreList.get(i).getString("address_of_restaurant");
                        pm.date = scoreList.get(i).getString("date");
                        pm.lat = scoreList.get(i).getString("lat");
                        pm.lng = scoreList.get(i).getString("lng");
                        pm.icon = scoreList.get(i).getString("icon");
                        pm.meal = scoreList.get(i).getString("meal");
                        pm.foodType= scoreList.get(i).getString("foodType");

                        restaurantsVisited.add(pm);


                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.analytics_fragment,container,false);
        myFirebaseRef = new Firebase("https://weightandsee.firebaseio.com/");
        myFirebaseRef2 = new Firebase("https://weightandsee.firebaseio.com/users/"+myFirebaseRef.getAuth().getUid()+"/logs");
        prepareData();
        dataSpreadProcessing();
        dataPrefsProcessing();
dataFrequencyProcessing();
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/bariol.ttf");
        TextView freq = (TextView)v.findViewById(R.id.frequency_title);
        TextView spread = (TextView)v.findViewById(R.id.spread_title);
        TextView prefs = (TextView)v.findViewById(R.id.prefs_title);
        TextView weekly1 = (TextView)v.findViewById(R.id.weekly);
        TextView weekly2 = (TextView)v.findViewById(R.id.weekly2);
        TextView weekly3 = (TextView)v.findViewById(R.id.weekly3);
        freq.setTypeface(tf);
        spread.setTypeface(tf);
        prefs.setTypeface(tf);
        weekly1.setTypeface(tf);
        weekly2.setTypeface(tf);
        weekly3.setTypeface(tf);  
        /*rChart.saveToGallery("rChart.jpg", 95);
        mChart.saveToGallery("mChart.jpg", 95);
        bChart.saveToGallery("bChart.jpg", 95);*/
        return v;

    }

    private void setBarData(int x, int y){
        ArrayList<String> xVals = new ArrayList<String>();
        for(int i =0; i< restaurantsVisited.size(); i++){


                    if (dateList.get(restaurantsVisited.get(i).date)!=null){
                        int c = dateList.get(restaurantsVisited.get(i).date) +1;
                        dateList.put(restaurantsVisited.get(i).date
                                ,c);
                    }else{
                        dateList.put(restaurantsVisited.get(i).date
                                ,1);
                    }


        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for(int i =0; i< dateList.size(); i++){
            yVals1.add(new BarEntry((float)dateList.get(i), i));
        }

        Set<String> keys = dateList.keySet();
        String[] array = keys.toArray(new String[keys.size()]);
        for(int i =0; i< array.length; i ++){
            xVals.add(array[i]);
        }


        BarDataSet set1 = new BarDataSet(yVals1, "Frequency " +
                "");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, set1);
        data.setValueTextSize(10f);
       // data.setValueTypeface(mTf);

        bChart.setData(data);
    }
    private void setPieData(int count, float range) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for(int i =0; i< restaurantsVisited.size(); i++){
            if (restaurantsVisited.get(i).meal.equals("Breakfast")) {
                mealCount[0]++;
            }else if(restaurantsVisited.get(i).meal.equals("Lunch")){
                mealCount[1]++;
            }else{
                mealCount[2]++;
            }

        }


        float mult = range;
        for (int i = 0; i < mealCount.length; i++) {
            yVals1.add(new Entry(mealCount
                    [i],i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < meals.length; i++)
            xVals.add(meals[i]);

        PieDataSet dataSet = new PieDataSet(yVals1, "Spread");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
       // data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
    private void setRadarData(int count , float range){


            ArrayList<Entry> yVals1 = new ArrayList<Entry>();


            // IMPORTANT: In a PieChart, no values (Entry) should have the same
            // xIndex (even if from different DataSets), since no values can be
            // drawn above each other.
            for (int i = 0; i < prefsList.size(); i++) {
                yVals1.add(new Entry(prefsList.get(i), i));
            }


            ArrayList<String> xVals = new ArrayList<String>();


            Set<String> keys = prefsList.keySet();
            String[] array = keys.toArray(new String[keys.size()]);
            for(int i =0; i< array.length; i ++){
                xVals.add(array[i]);
            }





        RadarDataSet set1 = new RadarDataSet(yVals1, "Preferences");
            set1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            set1.setFillColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            set1.setDrawFilled(true);
            set1.setLineWidth(2f);


            ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
            sets.add(set1);


            RadarData data = new RadarData(xVals, sets);
            data.setValueTextSize(8f);
            data.setDrawValues(false);

            rChart.setData(data);

            rChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}

