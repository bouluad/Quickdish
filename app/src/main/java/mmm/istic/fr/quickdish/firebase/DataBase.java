package mmm.istic.fr.quickdish.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import mmm.istic.fr.quickdish.bo.Client;
import mmm.istic.fr.quickdish.bo.Dish;
import mmm.istic.fr.quickdish.bo.Order;

/**
 * Created by bouluad on 23/03/17.
 */
public class DataBase {

    FirebaseDatabase database;

    public FirebaseDatabase getDatabase() {
        return database;
    }

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

    public String saveOrders(Order order) {

        final DatabaseReference orderRef = database.getReference(order.getTableNumber().substring(0, 3)).child("order");

        DatabaseReference databaseReference = orderRef.push();
        String key = databaseReference.getKey();
        databaseReference.setValue(order);
        return key;

    }

    public void saveClient(Client client, String qrCode) {

        final DatabaseReference orderRef = database.getReference(qrCode.substring(0, 3)).child("clients");

        DatabaseReference databaseReference = orderRef.push();
        databaseReference.setValue(client);

    }


    public void getLastIdOrder (final Command c){
        final DatabaseReference databaseReference = database.getReference("100");
        Query lastQuery = databaseReference.child("order").orderByKey().limitToLast(1);

        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot.getChildren().iterator().next();//.getValue(Order.class);
                //Order order = dataSnapshot1.getValue(Order.class);
                System.out.println("la clé de firebase lastquery "+ dataSnapshot1.getRef().getKey());

                //System.out.println(dataSnapshot.child("-Kg5UA8l6gztUkdEtyN1").getValue().toString());
                //System.out.println("last dish id ----> "+order.getId());

                c.exec(dataSnapshot1.getRef().getKey());
                //String message = dataSnapshot.child("message").getValue(Order).toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public interface Command {
        public void exec(Object o);
    }


}
