package mmm.istic.fr.quickdish.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import mmm.istic.fr.quickdish.R;
import mmm.istic.fr.quickdish.activities.bo.Order;

public class CommandResumeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_resume);

        Order order = getIntent().getParcelableExtra("order");
        String orderString = order.dishsToString();
        System.out.println("order -------> :"+ orderString);
        TextView resumeCommand = (TextView) findViewById(R.id.resumeCommand);
        resumeCommand.setText(orderString);
    }
}
