package edu.augustana.BirdCounter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {

    DatabaseReference birdRef;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.bird_item, null);

        LinearLayout gridCell = v.findViewById(R.id.gridCell);
        birdRef = FirebaseDatabase.getInstance().getReference();
        TextView birdName = (TextView) v.findViewById(R.id.birdName);
        Button increment = (Button) v.findViewById(R.id.increment);
        final TextView birdCount = (TextView) v.findViewById(R.id.birdCount);
        final Button decrement = (Button) v.findViewById(R.id.decrement);
        Button reset = (Button) v.findViewById(R.id.reset);

        if (birdList.get(position).getBirdCount() == 0) {
            decrement.setClickable(false);
        }
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bird bird = birdList.get(position);
                bird.setBirdCount(bird.getBirdCount() + 1);
                birdCount.setText("Count: " + bird.getBirdCount());
                birdRef.child(bird.getBirdName()).child("Count").setValue(bird.getBirdCount());
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (birdList.get(position).getBirdCount() - 1 >= 0) {
                    Bird bird = birdList.get(position);
                    bird.setBirdCount(bird.getBirdCount() - 1);
                    birdCount.setText("Count: " + bird.getBirdCount());
                    birdRef.child(bird.getBirdName()).child("Count").setValue(bird.getBirdCount());
                }
                if (birdList.get(position).getBirdCount() == 0) {
                    decrement.setClickable(false);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bird bird = birdList.get(position);
                bird.setBirdCount(0);
                birdCount.setText("Count: 0");
                decrement.setClickable(false);
                birdRef.child(bird.getBirdName()).child("Count").setValue(0);
            }
        });

        birdName.setText(birdList.get(position).getBirdName());
        birdCount.setText("Count: " + birdList.get(position).getBirdCount());

        return v;
    }
}
