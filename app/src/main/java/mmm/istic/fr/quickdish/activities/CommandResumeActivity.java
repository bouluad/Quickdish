package mmm.istic.fr.quickdish.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pnikosis.materialishprogress.ProgressWheel;

import mmm.istic.fr.quickdish.R;
import mmm.istic.fr.quickdish.bo.Dish;
import mmm.istic.fr.quickdish.bo.Order;
import mmm.istic.fr.quickdish.firebase.DataBase;


public class CommandResumeActivity extends AppCompatActivity {

    private ProgressWheel progressWheel;
    private TextView textView;
    private Order order;
    private DataBase dataBase;

    DatabaseReference myRef;

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
        dataBase.saveOrders(order);
        progressWheel.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        view.setVisibility(view.INVISIBLE);

        myRef = dataBase.getDatabase().getReference(order.getTableNumber().substring(0, 3)).child("order");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressWheel.setVisibility(View.INVISIBLE);
                textView.setText("Your order is ready en chantant lalalala");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
