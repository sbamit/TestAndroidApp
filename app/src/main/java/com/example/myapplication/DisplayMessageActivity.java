package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class DisplayMessageActivity extends FragmentActivity {

    private LinearLayout mContainerView; //Replace this LinearLayout with a ListView
    ArrayList<CustomTextView> customTextViewList = new ArrayList<CustomTextView>();
    HiddenTextViewClickListener hiddenTextViewClickListener = new HiddenTextViewClickListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        //Get the Intent from main Activity
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //populate ArrayList<CustomTextView> in this method
        createCustomViewsFromString(message);

        //now lets show the custom views
        mContainerView = (LinearLayout) findViewById(R.id.container_view);
        showCustomTextViews();

        //get the textview and set text
        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setText(message);
    }


    private void createCustomViewsFromString(String completeText){
        //find how many words in the text
        String []words = completeText.split("\\s+");

        //add one customeview for each word
        for(String word : words){
            customTextViewList.add(new CustomTextView(DisplayMessageActivity.this, word));
        }
    }

    private void showCustomTextViews() {
        Display display = getWindowManager().getDefaultDisplay();
        mContainerView.removeAllViewsInLayout();
        mContainerView.removeAllViews();
        int maxWidth = display.getWidth() - 20;

        LinearLayout.LayoutParams params;
        LinearLayout newLL = new LinearLayout(DisplayMessageActivity.this);
        newLL.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        newLL.setGravity(Gravity.LEFT);
        newLL.setOrientation(LinearLayout.HORIZONTAL);

        int widthSoFar = 0;

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
                newLL.setOnTouchListener(hiddenTextViewClickListener);
                mContainerView.addView(newLL);
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
            }
        }
        newLL.setOnTouchListener(hiddenTextViewClickListener);
        mContainerView.addView(newLL);
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
