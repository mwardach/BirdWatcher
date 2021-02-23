package edu.augustana.BirdCounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {

    ArrayList<Bird> birdList = new ArrayList<Bird>();

    public MyAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        birdList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.bird_item, null);
        TextView birdName = (TextView) v.findViewById(R.id.birdName);
        TextView birdCount = (TextView) v.findViewById(R.id.birdCount);
        Button increment = (Button) v.findViewById(R.id.increment);
        Button decrement = (Button) v.findViewById(R.id.decrement);
        Button reset = (Button) v.findViewById(R.id.reset);

        //This code is gotten from https://stackoverflow.com/questions/12734793/android-get-position-of-clicked-item-in-gridview
        //Used to know for which bird the button is pushed
        increment.setTag(Integer.valueOf(position));
        birdName.setText(birdList.get(position).getBirdName());
        birdCount.setText("Count: " + birdList.get(position).getBirdCount());

        return v;
    }
}
