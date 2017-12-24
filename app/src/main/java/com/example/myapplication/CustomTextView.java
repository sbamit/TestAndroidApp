package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Sajib on 12/24/2017.
 */

public class CustomTextView extends FrameLayout implements View.OnClickListener{
    private TextView mLabelTextView;

    public CustomTextView(Context context) {
        super(context);
        init(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_text_view, this);
        setOnClickListener(this);
        mLabelTextView = (TextView) findViewById(R.id.label);
        mLabelTextView.setText(Long.toString(System.currentTimeMillis()));
    }

    @Override
    public void onClick(View v) {
        mLabelTextView.setVisibility(View.VISIBLE);
    }
}
