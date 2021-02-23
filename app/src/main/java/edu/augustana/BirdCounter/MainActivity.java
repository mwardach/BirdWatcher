package edu.augustana.BirdCounter;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//Used as a guide for adding GridView: https://abhiandroid.com/ui/gridview

public class MainActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    GridView birdGrid;
    ArrayList<Bird> birdList = new ArrayList<Bird>();
    Button increment;
    Button decrement;
    Button reset;
    FloatingActionButton addBird;
    Button submit;
    Button cancel;
    PopupWindow window;
    TextView newBird;
    CoordinatorLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        birdGrid = (GridView) findViewById(R.id.birdGrid);
        myRef = FirebaseDatabase.getInstance().getReference();

        //Populates GridView
        final MyAdapter myAdapter=new MyAdapter(this,R.layout.bird_item, birdList);
        birdGrid.setAdapter(myAdapter);

        //Pulls bird information from Firebase Database and updates Gridview when something is changed
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot bird : snapshot.getChildren()) {
                    String name = bird.getKey();
                    //https://stackoverflow.com/questions/47423405/store-integer-value-into-a-variable-from-firebase
                    int count = Integer.valueOf("" + bird.child("Count").getValue());
                    birdList.add(new Bird(name, count));
                    Log.d("Test", birdList.toString());
                }
                //Received this code from https://stackoverflow.com/questions/52559068/load-data-from-firebase-real-database-into-gridview
                //Updates gridview when Firebase is updated
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*birdList.add(new Bird("Sparrow", 0));
        birdList.add(new Bird("Sparrow", 1));
        birdList.add(new Bird("Sparrow", 2));
        birdList.add(new Bird("Sparrow", 3));
        birdList.add(new Bird("Sparrow", 4));
        birdList.add(new Bird("Sparrow", 5));
        birdList.add(new Bird("Sparrow", 6));
        birdList.add(new Bird("Sparrow", 7));
        birdList.add(new Bird("Sparrow", 8));*/



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);
        reset = findViewById(R.id.reset);

        addBird = findViewById(R.id.addBird);
        main = findViewById(R.id.mainLayout);
        addBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.new_bird_popup,null);
                newBird = customView.findViewById(R.id.newBird);
                submit = (Button) customView.findViewById(R.id.submit);
                cancel = customView.findViewById(R.id.cancel);
                window = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                window.showAtLocation(main, Gravity.CENTER, 0, 0);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        window.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = newBird.getText().toString();
                        birdList.add(new Bird(name, 0));
                        myRef.child(name).child("Count").setValue(0);
                        window.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
