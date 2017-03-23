package mmm.istic.fr.quickdish.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mmm.istic.fr.quickdish.R;
import mmm.istic.fr.quickdish.bo.Dish;
import mmm.istic.fr.quickdish.firebase.DataBase;

import static mmm.istic.fr.quickdish.firebase.DataBase.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public  void orderMenuActivity (View view){
        Intent myIntent = new Intent( MainActivity.this, ScanActivity.class);
        startActivity(myIntent);
    }

    public void createCardActivity (View view){
        Intent myIntent = new Intent( MainActivity.this, RegisterActivity.class);
        startActivity(myIntent);
    }
   }
