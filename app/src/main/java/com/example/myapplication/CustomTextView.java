package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Sajib on 12/24/2017.
 */

public class CustomTextView extends FrameLayout implements View.OnClickListener, View.OnTouchListener{
    private TextView mLabelTextView;
    public String text;

    public CustomTextView(Context context) {
        super(context);
        init(context);
    }


    public CustomTextView(Context context, String text) {
        super(context);
        this.text = text;
        init(context, text);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){

    }

    private void init(Context context, String text) {
        LayoutInflater.from(context).inflate(R.layout.custom_text_view, this);
        //setOnClickListener(this);
        mLabelTextView = (TextView) findViewById(R.id.label);
        if(text!=null && text.length()>0)
            mLabelTextView.setText(text);
        else
            mLabelTextView.setText(Long.toString(System.currentTimeMillis()));
    }

    @Override
    public void onClick(View v) {
        LinearLayout wrapperBox =  (LinearLayout)mLabelTextView.getParent();
        mLabelTextView.setVisibility(View.VISIBLE);
        wrapperBox.setBackgroundColor(Color.WHITE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            //do something when pressed down
            LinearLayout wrapperBox =  (LinearLayout)mLabelTextView.getParent();
            mLabelTextView.setVisibility(View.VISIBLE);
            wrapperBox.setBackgroundColor(Color.WHITE);
            return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            //do something when let go
            LinearLayout wrapperBox =  (LinearLayout)mLabelTextView.getParent();
            mLabelTextView.setVisibility(View.INVISIBLE);
            wrapperBox.setBackgroundColor(Color.GRAY);
            return true;
        }
        return false;
    }
}
