package mmm.istic.fr.quickdish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mmm.istic.fr.quickdish.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void orderMenuActivity(View view) {
        Intent myIntent = new Intent(MainActivity.this, ScanActivity.class);
        startActivity(myIntent);
    }

    public void createCardActivity(View view) {
        Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(myIntent);
    }
}
