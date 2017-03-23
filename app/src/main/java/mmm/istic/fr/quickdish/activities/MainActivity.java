package mmm.istic.fr.quickdish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mmm.istic.fr.quickdish.R;
import mmm.istic.fr.quickdish.bo.Dish;
import mmm.istic.fr.quickdish.bo.Order;
import mmm.istic.fr.quickdish.firebase.DataBase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Dish> dishs = new ArrayList<>();
        Dish tajin = new Dish("100", "Tajin", "C’est une préparation sucrée ou salée où l’ont utilisé toutes les viandes, tous les poissons, les légumes comme les fruits.", "15 €", 5, "plats");

        dishs.add(tajin);
        DataBase dataBase = new DataBase();
        dataBase.saveOrders(new Order(dishs, 2, "10002", false));
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
