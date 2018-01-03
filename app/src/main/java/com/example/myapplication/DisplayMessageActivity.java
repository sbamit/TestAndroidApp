package com.example.myapplication;

import android.content.Intent;
import android.graphics.Rect;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayMessageActivity extends FragmentActivity {
    //new code for custom adapter

    public int count = 0;

    //for testText
    public int length;

    public int so_far_length;

    public int length_LL;

    public int display_width;

    public int last_storage=0;

    //author
    ListView listView;
    CustomAdapter customAdapter;
    ArrayList<Model> temp; //to be passed to the custom adapter
    //author

    //save all the words in a single cell
    ArrayList<String> storage; //save the strings

    ArrayList<Integer> breakpoint;

    //have to allocate memory for the arraylists, either we gotta get exceptions :|

    //previous approach
    private LinearLayout mContainerView; //Replace this LinearLayout with a ListView
    ArrayList<CustomTextView> customTextViewList = new ArrayList<CustomTextView>();
    HiddenTextViewClickListener hiddenTextViewClickListener = new HiddenTextViewClickListener();

    TextView testText;
    TextView test1;
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

        testText = (TextView) findViewById(R.id.test);
        test1 = (TextView) findViewById(R.id.test1);

        storage = new ArrayList<String>();
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
        //int flag = 0;
        this.length = 0;
        this.so_far_length = 0;
        this.length_LL = 0;
        Log.e("size of storage ",String.valueOf(storage.size()));
        //ArrayList<String> populate = new ArrayList<String>();

        //main work
        int flag = 0;
        int caution_length = 0;
        for(int i=0;i<storage.size();i++){
            String s = storage.get(i);

            testText.setText(s);
            testText.measure(0,0);
            llTestHolder.measure(0,0);
            this.length+=testText.getMeasuredWidth();
            //this.length+=10;
            //this.length_LL+=llTestHolder.getMeasuredWidth();
            if(i<storage.size()-1){
                test1.setText(storage.get(i+1)); //placing the next word for extra caution
                flag = 1;
            }
            else{
                test1.setText("");
                flag = 0;
            }
            test1.measure(0,0);
            caution_length = this.length + test1.getMeasuredWidth();
            Log.e("caution length", String.valueOf(caution_length));

            /*if(this.length>=display_width-200){
                if(last_storage==0) { //first list view item
                    breakpoint.add(i-1);
                }
                if(last_storage==1){
                    breakpoint.add(i-so_far_length-1);
                }
                breakpoint.add(i);

                this.so_far_length=breakpoint.get(checking);
                Log.e("got breakpoint ",String.valueOf(breakpoint.get(checking)));
                checking++;
                this.length = 0; //new line er jonno space measure korte hobe now
                last_storage = 1;
            }*/

            if(caution_length>display_width){
                breakpoint.add(i);

                this.length=0;
                caution_length = 0;
            }
        }

        //
        Log.e("breakpoint size ",String.valueOf(breakpoint.size()));

        //final work
        ArrayList<Integer> bhai = new ArrayList<Integer>();
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

        llTestHolder.setVisibility(View.GONE);
        testText.setText("");
        test1.setText("");


        //Log.e("size of the storage ",String.valueOf(storage.size())); //works fine

        Log.e("temp size ",String.valueOf(temp.size()));
        //Log.e("check ",String.valueOf(customAdapter.dataset.size()));


        customAdapter = new CustomAdapter(DisplayMessageActivity.this,temp,display_width);

        listView.setAdapter(customAdapter);
        listView.setOnTouchListener(touchListener);
        //  listView.setAdapter(customAdapter);
        // listView.setVisibility(View.VISIBLE);
        //listView.setAdapter(customAdapter);

        Log.e("tag ",String.valueOf(customAdapter.getCount()));


    }
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        private float mDownX;
        private boolean mSwiping;
        private VelocityTracker mVelocityTracker;
        private int mDownPosition;
        private View mDownView;
        //private boolean mPaused;
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getActionMasked();

            if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
                switchViewVisibility(0,View.VISIBLE);
            }
            else if (MotionEvent.ACTION_MOVE == action || MotionEvent.ACTION_DOWN == action ) {

                    /*if (mPaused) {
                        return false;
                    }*/

                    // TODO: ensure this is a finger, and set a flag

                    // Find the child view that was touched (perform a hit test)
                    Rect rect = new Rect();
                    int childCount = listView.getChildCount();
                    int[] listViewCoords = new int[2];
                    listView.getLocationOnScreen(listViewCoords);
                    int x = (int) motionEvent.getRawX() - listViewCoords[0];
                    int y = (int) motionEvent.getRawY() - listViewCoords[1];
                    View child;
                    for (int i = 0; i < childCount; i++) {
                        child = listView.getChildAt(i);
                        child.getHitRect(rect);
                        if (rect.contains(x, y)) {
                            mDownView = child; // This is your down view
                            break;
                        }
                    }

                    if (mDownView != null) {
                        mDownX = motionEvent.getRawX();

                        mDownPosition = listView.getPositionForView(mDownView);
                        switchViewVisibility(mDownPosition, View.INVISIBLE);
                        Log.d("Touch","on position"+ mDownPosition);
                        mVelocityTracker = VelocityTracker.obtain();
                        mVelocityTracker.addMovement(motionEvent);
                    }
                    view.onTouchEvent(motionEvent);
                    return true;

               
            }
            return false;
        }
    };


    private  void switchViewVisibility(int position, int visibility) {
        View listItem;
        //Take ListView And make text hide
        try {
            final int firstListItemPosition = listView.getFirstVisiblePosition();
            final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

            if (position < firstListItemPosition || position > lastListItemPosition) {
                //This may occure using Android Monkey, else will work otherwise
                listItem = listView.getAdapter().getView(position, null, listView);
            } else {
                final int childIndex = position - firstListItemPosition;
                listItem = listView.getChildAt(childIndex);
            }
            listItem.setVisibility(visibility); //this will switch based on motion event

            //make all other rows visible
            for(int index=firstListItemPosition; index<lastListItemPosition; index++) {
                if(index!=position)
                    listView.getChildAt(index).setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        display_width = display.getWidth();
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
