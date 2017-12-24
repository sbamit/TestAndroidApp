package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class DisplayMessageActivity extends FragmentActivity {

    private ViewGroup mContainerView;
    ArrayList<CustomTextView> CustomTextViewList = new ArrayList<CustomTextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);


        //add some customeview here
        CustomTextViewList.add(new CustomTextView(DisplayMessageActivity.this));
        CustomTextViewList.add(new CustomTextView(DisplayMessageActivity.this));
        CustomTextViewList.add(new CustomTextView(DisplayMessageActivity.this));

        //now lets show the custom views
        mContainerView = (ViewGroup)findViewById(R.id.container_view);
        showCustomTextViews();


        //Get the Intent from main Activity
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //get the textview and set text
        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setText(message);
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

        for (CustomTextView customTextView : CustomTextViewList) {

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
