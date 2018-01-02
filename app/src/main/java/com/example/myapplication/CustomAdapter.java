package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ashrafi on 12/31/2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Model> dataset= new ArrayList<Model>();
    LayoutInflater mInflater; //purpose ?
    int length;
    int display_width_calculated;
    boolean can_accomodate = true;

    public CustomAdapter(Context context) {
        this.context = context;
    }

    public CustomAdapter(Context context,ArrayList<Model> dataset,int display_width){
        this.context = context;
        this.dataset = dataset;
        this.display_width_calculated = display_width;
        mInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return dataset.size(); //how many lines? llTestHolder
    }

    @Override
    public Object getItem(int position) {
        return dataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.length = 0;
        //using view holder so that we gotta call findViewById() method only once, when the layout is first created
        Log.e("getview called ","llTestHolder");
        Model m = dataset.get(position);
        Log.e("line number ",String.valueOf(position));
        Log.e("line number size ",String.valueOf(m.strings.size())); //why do each m has exactly 15 words? :|
        ViewHolder viewholder;
        convertView = null;
        if (convertView == null) {
             mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.test, parent,false);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        }
        else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        for (int j = 0; j < m.strings.size(); j++) {
            String generator = "tv" + String.valueOf(j + 1);
            Log.e("String ", generator); //check
            //TextView tvName = (TextView) convertView.findViewById(R.id.generator);
            switch (generator) {
                case "tv1":
                    //TextView tvName1 = (TextView) convertView.findViewById(R.id.tv1);
                    viewholder.tv1.setText(m.strings.get(j));
                    viewholder.tv1.measure(0,0);
                    length+=viewholder.tv1.getMeasuredWidth();
                    break;
                    //tvName1.setText(m.strings.get(j));
                case "tv2":
                    //TextView tvName2 = (TextView) convertView.findViewById(R.id.tv2);
                    //tvName2.setText(m.strings.get(j));
                    viewholder.tv2.setText(m.strings.get(j));
                    viewholder.tv1.measure(0,0);
                    length+=viewholder.tv1.getMeasuredWidth();
                    break;
                case "tv3":
                    //TextView tvName3 = (TextView) convertView.findViewById(R.id.tv3);
                    //tvName3.setText(m.strings.get(j));
                    viewholder.tv3.setText(m.strings.get(j));
                    viewholder.tv1.measure(0,0);
                    length+=viewholder.tv1.getMeasuredWidth();
                    break;
                case "tv4":
                    //TextView tvName4 = (TextView) convertView.findViewById(R.id.tv4);
                    //tvName4.setText(m.strings.get(j));
                    viewholder.tv4.setText(m.strings.get(j));
                    viewholder.tv1.measure(0,0);
                    length+=viewholder.tv1.getMeasuredWidth();
                    break;
                case "tv5":
                    //TextView tvName5 = (TextView) convertView.findViewById(R.id.tv5);
                    //tvName5.setText(m.strings.get(j));
                    viewholder.tv5.setText(m.strings.get(j));
                    viewholder.tv1.measure(0,0);
                    length+=viewholder.tv1.getMeasuredWidth();
                    break;
                case "tv6":
                    //TextView tvName6 = (TextView) convertView.findViewById(R.id.tv6);
                    //tvName6.setText(m.strings.get(j));
                    viewholder.tv6.setText(m.strings.get(j));
                    viewholder.tv1.measure(0,0);
                    length+=viewholder.tv1.getMeasuredWidth();
                    break;
                case "tv7":
                    //TextView tvName7 = (TextView) convertView.findViewById(R.id.tv7);
                    //
                    viewholder.tv7.setText(m.strings.get(j));
                    viewholder.tv1.measure(0,0);
                    length+=viewholder.tv1.getMeasuredWidth();
                    break;
                case "tv8":
                    //TextView tvName8 = (TextView) convertView.findViewById(R.id.tv8);
                    //tvName8.setText(m.strings.get(j));
                    viewholder.tv8.setText(m.strings.get(j));
                    viewholder.tv1.measure(0,0);
                    length+=viewholder.tv1.getMeasuredWidth();
                    break;
                case "tv9":
                    //TextView tvName9 = (TextView) convertView.findViewById(R.id.tv9);
                    //tvName9.setText(m.strings.get(j));
                    viewholder.tv9.setText(m.strings.get(j));
                    viewholder.tv1.measure(0,0);
                    length+=viewholder.tv1.getMeasuredWidth();
                    break;
                case "tv10":
                    //TextView tvName10 = (TextView) convertView.findViewById(R.id.tv10);
                    //tvName10.setText(m.strings.get(j));
                    viewholder.tv10.setText(m.strings.get(j));
                    viewholder.tv1.measure(0,0);
                    length+=viewholder.tv1.getMeasuredWidth();
                    break;
                default:
                    break;
            }
        }

        //changing the list view item height //can very according to system font size
        ViewGroup.LayoutParams params = convertView.getLayoutParams();
        params.height = 120;
        convertView.setLayoutParams(params);

        Log.e("display ",String.valueOf(display_width_calculated));
        Log.e("place covered ",String.valueOf(length));
        return convertView;
        //return null; //no no no, don't return null :| careful buddy :|
    }



    private class ViewHolder{
        TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;

        public ViewHolder(View view){
            tv1 = (TextView) view.findViewById(R.id.tv1);
            tv2 = (TextView) view.findViewById(R.id.tv2);
            tv3 = (TextView) view.findViewById(R.id.tv3);
            tv4 = (TextView) view.findViewById(R.id.tv4);
            tv5 = (TextView) view.findViewById(R.id.tv5);
            tv6 = (TextView) view.findViewById(R.id.tv6);
            tv7 = (TextView) view.findViewById(R.id.tv7);
            tv8 = (TextView) view.findViewById(R.id.tv8);
            tv9 = (TextView) view.findViewById(R.id.tv9);
            tv10 = (TextView) view.findViewById(R.id.tv10);
        }
    }

}


