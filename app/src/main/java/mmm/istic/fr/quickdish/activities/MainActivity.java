package mmm.istic.fr.quickdish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import mmm.istic.fr.quickdish.R;
import mmm.istic.fr.quickdish.bo.Order;
import mmm.istic.fr.quickdish.firebase.DataBase;

public class MainActivity extends AppCompatActivity {

    DataBase dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBase = new DataBase();

        final DatabaseReference databaseReference = dataBase.getDatabase().getInstance().getReference("100");
        Query lastQuery = databaseReference.child("order").orderByKey().limitToLast(1);
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot.getChildren().iterator().next();//.getValue(Order.class);
                Order order = dataSnapshot1.getValue(Order.class);
                //System.out.println(dataSnapshot.child("-Kg5UA8l6gztUkdEtyN1").getValue().toString());
                System.out.println("last dish id ----> "+order.getId());
                //String message = dataSnapshot.child("message").getValue(Order).toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

    });

    }

    public void orderMenuActivity(View view) {
        Intent myIntent = new Intent(MainActivity.this, ScanActivity.class);
        startActivity(myIntent);
    }

    public void createCardActivity(View view) {
        Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(myIntent);
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
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
