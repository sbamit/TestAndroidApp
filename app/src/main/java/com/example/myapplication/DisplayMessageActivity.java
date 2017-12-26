package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class DisplayMessageActivity extends FragmentActivity {

    private LinearLayout mContainerView;
    ArrayList<CustomTextView> customTextViewList = new ArrayList<CustomTextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        //populate ArrayList<CustomTextView> in this method
        createCustomViewsFromString("Once upon a time, there was a king and a man called Hercules.");

        //now lets show the custom views
        mContainerView = (LinearLayout) findViewById(R.id.container_view);
        showCustomTextViews();


        //Get the Intent from main Activity
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

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
                mContainerView.addView(newLL);

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
        mContainerView.addView(newLL);
    }
}
