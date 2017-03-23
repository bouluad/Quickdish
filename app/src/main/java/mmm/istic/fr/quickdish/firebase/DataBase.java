package mmm.istic.fr.quickdish.firebase;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import mmm.istic.fr.quickdish.bo.Dish;

/**
 * Created by bouluad on 23/03/17.
 */
public class DataBase {

    FirebaseDatabase database;

    public DataBase() {

        // Connect to the Firebase database
        database = FirebaseDatabase.getInstance();

    }

    public interface Command {
        public void exec (Object o);
    }

    public void getDishsByRestoId (String id, final Command c){

        // Get a reference to the todoItems child items it the database
        final DatabaseReference myRef = database.getReference(id);


        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Dish dish = dataSnapshot.getValue(Dish.class);
                if (dish!=null){
                    System.out.println(dish.getTitle()+" "+dish.getDescription());
                    c.exec(dish);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
}
