package mmm.istic.fr.quickdish.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mmm.istic.fr.quickdish.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public  void orderMenuActivity (View view){
        Intent myIntent = new Intent( MainActivity.this, scanActivity.class);
        startActivity(myIntent);
    }

    public void createCardActivity (View view){
        Intent myIntent = new Intent( MainActivity.this, registerActivity.class);
        startActivity(myIntent);
    }
   }
