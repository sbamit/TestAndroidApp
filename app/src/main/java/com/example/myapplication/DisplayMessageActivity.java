package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayMessageActivity extends FragmentActivity {
    //new code for custom adapter

    public int count = 0;

    //for testText
    public int length;
    public int last_storage=0;

    //author
    ListView listView;
    CustomAdapter customAdapter;
    ArrayList<Model> temp; //to be passed to the custom adapter
    //author


    ArrayList<Integer> breakpoint;

    //have to allocate memory for the arraylists, either we gotta get exceptions :|

    //previous approach
    private LinearLayout mContainerView; //Replace this LinearLayout with a ListView
    HiddenTextViewClickListener hiddenTextViewClickListener = new HiddenTextViewClickListener();

    TextView testText;
    LinearLayout llTestHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //disable screen capture here
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_display_message);
        listView = (ListView) findViewById(R.id.list_view);
        llTestHolder = (LinearLayout) findViewById(R.id.test_holder);

        //allocate memory
        temp = new ArrayList<Model>();

        testText = (TextView) findViewById(R.id.test); //faakibazi.xmls

        ArrayList<String> storage = new ArrayList<>();; //save the strings
        breakpoint = new ArrayList<>();

        //Get the Intent from main Activity
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //populate ArrayList<CustomTextView> in this method


        //now lets show the custom views
        mContainerView = (LinearLayout) findViewById(R.id.container_view);
       //showCustomTextViews();

        //data creation
        storage = createWordsFromString(message); //storage filled with the words
        int display_width = getDisplayWidth(); //get the display width first

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

        ArrayList<Integer> bhai = new ArrayList<>();
        bhai.add(breakpoint.get(0));
        for(int i=1;i<breakpoint.size();i++){
            Log.e("breakpoints ",String.valueOf(breakpoint.get(i)));
            bhai.add(breakpoint.get(i) - breakpoint.get(i-1));
        }

        for(int i=0;i<bhai.size();i++){
            Log.e("llTestHolder ",String.valueOf(bhai.get(i)));
        }

        int iterator = 0;

        for(int i=0;i<bhai.size();i++){
            ArrayList<String> p = new ArrayList<>();
            for(int j=0;j<bhai.get(i);j++){
                p.add(storage.get(iterator+j));
            }
            iterator+=bhai.get(i);
            Model m = new Model();
            m.strings = p;
            temp.add(m);
        }

        ArrayList<String> p1 = new ArrayList<>();
        for(int k =iterator;k<storage.size();k++){
            p1.add(storage.get(k));
        }
        Model m1 = new Model();
        m1.strings = p1;
        temp.add(m1);

        llTestHolder.setVisibility(View.GONE);
        testText.setText("");




        //Log.e("size of the storage ",String.valueOf(storage.size())); //works fine

        Log.e("temp size ",String.valueOf(temp.size()));
        //Log.e("check ",String.valueOf(customAdapter.dataset.size()));


        customAdapter = new CustomAdapter(DisplayMessageActivity.this, temp, display_width);

        listView.setAdapter(customAdapter);

      //  listView.setAdapter(customAdapter);
       // listView.setVisibility(View.VISIBLE);
        //listView.setAdapter(customAdapter);

        Log.e("tag ",String.valueOf(customAdapter.getCount()));


    }


    private ArrayList<String> createWordsFromString(String completeText){
        //find how many words in the text
        String []words = completeText.split("\\s+");
        ArrayList<String> storage = new ArrayList<>();
        //add one customeview for each word
        for(String word : words){
            storage.add(word); //adding the words here //done
        }
        return storage;
    }

    private int getDisplayWidth(){ //test method
        Display display = getWindowManager().getDefaultDisplay();
        return display.getWidth() - 20;
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
