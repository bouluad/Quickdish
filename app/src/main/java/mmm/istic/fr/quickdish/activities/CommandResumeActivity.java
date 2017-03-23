package mmm.istic.fr.quickdish.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import mmm.istic.fr.quickdish.R;
import mmm.istic.fr.quickdish.bo.Order;
import mmm.istic.fr.quickdish.firebase.DataBase;


public class CommandResumeActivity extends AppCompatActivity {

    private Order order;

    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_resume);

        dataBase = new DataBase();

        order = (Order) getIntent().getSerializableExtra("order");
        String orderString = order.dishsToString();
        System.out.println("order -------> :" + orderString);
        TextView resumeCommand = (TextView) findViewById(R.id.resumeCommand);
        resumeCommand.setText(orderString);
    }


    public void passOrder (View view){
        dataBase.saveOrders(order);
    }
}
