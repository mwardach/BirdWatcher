package edu.augustana.BirdCounter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    GridView birdGrid;
    ArrayList birdList = new ArrayList<Bird>();
    Button increment;
    Button decrement;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        birdGrid = (GridView) findViewById(R.id.birdGrid);

        myRef.child("Sparrow").setValue(0);
        myRef.child("Sandpiper").setValue(0);
        myRef.child("Saddleback").setValue(0);
        myRef.child("Shoveler").setValue(0);
        myRef.child("Starling").setValue(0);
        myRef.child("Surfbird").setValue(0);
        myRef.child("Swallow").setValue(0);

        birdList.add(new Bird("Sparrow", 0));
        birdList.add(new Bird("Sparrow", 1));
        birdList.add(new Bird("Sparrow", 2));
        birdList.add(new Bird("Sparrow", 3));
        birdList.add(new Bird("Sparrow", 4));
        birdList.add(new Bird("Sparrow", 5));
        birdList.add(new Bird("Sparrow", 6));
        birdList.add(new Bird("Sparrow", 7));
        birdList.add(new Bird("Sparrow", 8));

        MyAdapter myAdapter=new MyAdapter(this,R.layout.bird_item, birdList);
        birdGrid.setAdapter(myAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);
        reset = findViewById(R.id.reset);

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.setValue("A birb");
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
