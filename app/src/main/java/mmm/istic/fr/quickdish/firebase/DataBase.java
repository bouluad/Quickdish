package mmm.istic.fr.quickdish.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mmm.istic.fr.quickdish.bo.Dish;
import mmm.istic.fr.quickdish.bo.Order;

/**
 * Created by bouluad on 23/03/17.
 */
public class DataBase {

    FirebaseDatabase database;

    public DataBase() {

        // Connect to the Firebase database
        database = FirebaseDatabase.getInstance();

    }

    public void getDishsByRestoId(String id, final Command c) {

        // Get a reference to the todoItems child items it the database
        final DatabaseReference myRef = database.getReference(id).child("menu");


        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Dish dish = dataSnapshot.getValue(Dish.class);
                if (dish != null) {
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

    public void saveOrders(Order order) {

        final DatabaseReference orderRef = database.getReference(order.getTableNumber().substring(0, 3)).child("order");

        DatabaseReference databaseReference = orderRef.push();
        databaseReference.setValue(order);

    }

    public interface Command {
        public void exec(Object o);
    }
}
