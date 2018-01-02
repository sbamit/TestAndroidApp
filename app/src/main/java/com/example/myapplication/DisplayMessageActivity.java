package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayMessageActivity extends FragmentActivity {
    //new code for custom adapter

    public int count = 0;

    //for testText
    public int length;

    public int display_width;

    public int last_storage=0;

    //author
    ListView listView;
    CustomAdapter customAdapter;
    ArrayList<Model> temp; //to be passed to the custom adapter
    //author

    //save all the words in a single cell
    ArrayList<String> storage; //save the strings

    ArrayList<String> storage1; //save the strings
    ArrayList<String> storage2; //save the strings
    ArrayList<String> storage3; //save the strings

    ArrayList<Integer> breakpoint;

    //have to allocate memory for the arraylists, either we gotta get exceptions :|

    //previous approach
    private LinearLayout mContainerView; //Replace this LinearLayout with a ListView
    ArrayList<CustomTextView> customTextViewList = new ArrayList<CustomTextView>();
    HiddenTextViewClickListener hiddenTextViewClickListener = new HiddenTextViewClickListener();

    TextView testText;
    LinearLayout yo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //disable screen capture here
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_display_message);
        listView = (ListView) findViewById(R.id.list_view);
        yo = (LinearLayout) findViewById(R.id.yo);

        //allocate memory
        temp = new ArrayList<Model>();

        testText = (TextView) findViewById(R.id.test); //faakibazi.xmls

        storage = new ArrayList<String>();
        storage1 = new ArrayList<String>();
        storage2 = new ArrayList<String>();
        storage3 = new ArrayList<String>();
        breakpoint = new ArrayList<Integer>();

        //Get the Intent from main Activity
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //populate ArrayList<CustomTextView> in this method


        //now lets show the custom views
        mContainerView = (LinearLayout) findViewById(R.id.container_view);
       //showCustomTextViews();

        //data creation
        createCustomViewsFromString(message); //storage filled with the words
        getWidth(); //get the display width first

        int checking = 0;
        this.length = 0;
        Log.e("size of storage ",String.valueOf(storage.size()));
        ArrayList<String> populate = new ArrayList<String>();
        for(int i=0;i<storage.size();i++){
             String s = storage.get(i);
            //populate.add(s);
            testText.setText(s);
            testText.measure(0,0);
            this.length+=testText.getMeasuredWidth();
            if(length>=display_width-100){
                if(last_storage==0) {
                    breakpoint.add(i);
                }
                if(last_storage==1){
                    breakpoint.add(i-length);
                }
                /*Model tmp = new Model();
                tmp.strings = populate;
                Log.e("populate size ",String.valueOf(populate.size()));
                temp.add(tmp);
                populate.clear();*/
                this.length=breakpoint.get(checking);
                checking++;
            }
        }

        //
        Log.e("breakpoint size ",String.valueOf(breakpoint.size()));

        ArrayList<Integer> bhai = new ArrayList<Integer>();
        bhai.add(breakpoint.get(0));
        for(int i=1;i<breakpoint.size();i++){
            Log.e("breakpoints ",String.valueOf(breakpoint.get(i)));
            bhai.add(breakpoint.get(i) - breakpoint.get(i-1));
        }

        for(int i=0;i<bhai.size();i++){
            Log.e("yo ",String.valueOf(bhai.get(i)));
        }

        int iterator = 0;

        for(int i=0;i<bhai.size();i++){
            ArrayList<String> p = new ArrayList<String>();
            for(int j=0;j<bhai.get(i);j++){
                p.add(storage.get(iterator+j));
            }
            iterator+=bhai.get(i);
            Model m = new Model();
            m.strings = p;
            temp.add(m);
        }

        ArrayList<String> p1 = new ArrayList<String>();
        for(int k =iterator;k<storage.size();k++){
            p1.add(storage.get(k));
        }
        Model m1 = new Model();
        m1.strings = p1;
        temp.add(m1);

        yo.setVisibility(View.GONE);
        testText.setText("");



        //test raw data
        /*Model m1 = new Model();
        storage1.add("once");
        storage1.add("upon");
        storage1.add("a");
        storage1.add("time,");
        m1.strings = storage1;
        temp.add(m1);


        storage2.add("there");
        storage2.add("was");
        storage2.add("a");
        storage2.add("god");
        storage2.add("called");
        storage2.add("zeus");
        Model m2 = new Model();
        m2.strings = storage2;
        temp.add(m2);

        storage3.add("and");
        storage3.add("his");
        storage3.add("son");
        storage3.add("named");
        storage3.add("hercules");
        Model m3 = new Model();
        m3.strings = storage3;
        temp.add(m3);*/




        //Log.e("size of the storage ",String.valueOf(storage.size())); //works fine

        Log.e("temp size ",String.valueOf(temp.size()));
        //Log.e("check ",String.valueOf(customAdapter.dataset.size()));


        customAdapter = new CustomAdapter(DisplayMessageActivity.this,temp,display_width);

        listView.setAdapter(customAdapter);

      //  listView.setAdapter(customAdapter);
       // listView.setVisibility(View.VISIBLE);
        //listView.setAdapter(customAdapter);

        Log.e("tag ",String.valueOf(customAdapter.getCount()));


    }


    private void createCustomViewsFromString(String completeText){
        //find how many words in the text
        String []words = completeText.split("\\s+");

        //add one customeview for each word
        for(String word : words){
            customTextViewList.add(new CustomTextView(DisplayMessageActivity.this, word));
            storage.add(word); //adding the words here //done
        }
    }

    private void getWidth(){ //test method
        Display display = getWindowManager().getDefaultDisplay();
        display_width = display.getWidth() - 20;
    }

    private void showCustomTextViews() {
        Display display = getWindowManager().getDefaultDisplay();
        //mContainerView.removeAllViewsInLayout();
        //mContainerView.removeAllViews();
        int maxWidth = display.getWidth();

        LinearLayout.LayoutParams params;
        LinearLayout newLL = new LinearLayout(DisplayMessageActivity.this);
        newLL.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        newLL.setGravity(Gravity.LEFT);
        newLL.setOrientation(LinearLayout.HORIZONTAL);

        int widthSoFar = 0;

        Model m = new Model(); //

        for (CustomTextView customTextView : customTextViewList) {

            LinearLayout LL = new LinearLayout(DisplayMessageActivity.this);
            LL.setOrientation(LinearLayout.HORIZONTAL);
            LL.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            LL.setLayoutParams(new ListView.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            customTextView.measure(0, 0);
            params = new LinearLayout.LayoutParams(customTextView.getMeasuredWidth(), FrameLayout.LayoutParams.WRAP_CONTENT);
            LL.addView(customTextView, params);
            LL.measure(0, 0);
            widthSoFar += customTextView.getMeasuredWidth();
            if (widthSoFar >= maxWidth) {
                Log.e("size found ",String.valueOf(m.strings.size()));
                temp.add(m);
                Log.e("final data size ",String.valueOf(temp.size()));
                m.strings.clear();
                Log.e("erased? ",String.valueOf(m.strings.size()));
                newLL.setOnTouchListener(hiddenTextViewClickListener);
                //mContainerView.addView(newLL);
                newLL.setOnTouchListener(hiddenTextViewClickListener);
                newLL = new LinearLayout(DisplayMessageActivity.this);
                newLL.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
                newLL.setOrientation(LinearLayout.HORIZONTAL);
                newLL.setGravity(Gravity.LEFT);
                params = new LinearLayout.LayoutParams(LL.getMeasuredWidth(), LL.getMeasuredHeight());
                newLL.addView(LL, params);
                widthSoFar = LL.getMeasuredWidth();
            } else {
                newLL.addView(LL);
                m.strings.add(customTextView.text);
            }
        }
        Log.e("size found ",String.valueOf(m.strings.size()));
        newLL.setOnTouchListener(hiddenTextViewClickListener);
        //mContainerView.addView(newLL);
        temp.add(m);
        Log.e("final data size ",String.valueOf(temp.size()));
    }

    class HiddenTextViewClickListener implements View.OnClickListener, View.OnTouchListener{
        @Override
        public void onClick(View v) {
            //LinearLayout LL = (LinearLayout)((LinearLayout)v).getChildAt(0);
            int numOfTextViews = ((LinearLayout)v).getChildCount();
            for(int i=0;i<numOfTextViews;i++) {
                LinearLayout LL = (LinearLayout)((LinearLayout)v).getChildAt(i);
                CustomTextView customTextView = (CustomTextView)LL.getChildAt(0);
                customTextView.performClick();
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int numOfTextViews = ((LinearLayout)v).getChildCount();
            for(int i=0;i<numOfTextViews;i++) {
                LinearLayout LL = (LinearLayout)((LinearLayout)v).getChildAt(i);
                CustomTextView customTextView = (CustomTextView)LL.getChildAt(0);
                customTextView.onTouch(customTextView,event);
            }
            return true;
        }
    }
}
