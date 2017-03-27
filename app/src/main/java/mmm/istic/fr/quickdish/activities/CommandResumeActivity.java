package mmm.istic.fr.quickdish.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.pnikosis.materialishprogress.ProgressWheel;

import mmm.istic.fr.quickdish.R;
import mmm.istic.fr.quickdish.bo.Order;
import mmm.istic.fr.quickdish.firebase.DataBase;


public class CommandResumeActivity extends AppCompatActivity {

    DatabaseReference myRef;
    private ProgressWheel progressWheel;
    private TextView textView;
    private Order order;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_resume);

        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        progressWheel.setVisibility(View.INVISIBLE);

        textView = (TextView) findViewById(R.id.progressBarinsideText);
        textView.setVisibility(View.INVISIBLE);

        dataBase = new DataBase();


        order = (Order) getIntent().getSerializableExtra("order");
        String orderString = order.dishsToString();
        TextView resumeCommand = (TextView) findViewById(R.id.resumeCommand);
        resumeCommand.setText(orderString);

    }


    public void passOrder(View view) {
        final String key = dataBase.saveOrders(order);
        progressWheel.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        view.setVisibility(view.INVISIBLE);

        myRef = dataBase.getDatabase().getReference(order.getTableNumber().substring(0, 3)).child("order");

        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                System.out.println("key of order firebase ----> "+ key);
                String key1 = dataSnapshot.getRef().getKey();
                String valid = dataSnapshot.getRef().child("validation").toString();
                //if (key.equals(key1)) {
                //String valid = dataSnapshot.getRef().child("validation").toString();
                System.out.println("key of order changed in database "+ key1);
                if (key.equals(key1)){
                    progressWheel.setVisibility(View.INVISIBLE);
                    textView.setText("Your order are ready ...");
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
