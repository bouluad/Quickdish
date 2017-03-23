package mmm.istic.fr.quickdish.firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import mmm.istic.fr.quickdish.R;
import mmm.istic.fr.quickdish.bo.Dish;


public class TestFireBase extends AppCompatActivity {

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fire_base);
        // Get ListView object from xml
        final ListView listView = (ListView) findViewById(R.id.listView);

        // Create a new Adapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // Connect to the Firebase database
        database = FirebaseDatabase.getInstance();

        // Get a reference to the todoItems child items it the database
        final DatabaseReference menuRef = database.getReference("100").child("menu");
        final DatabaseReference commandRef = database.getReference("100").child("command");

        // Assign a listener to detect changes to the child items
        // of the database reference.
        menuRef.addChildEventListener(new ChildEventListener() {

            // This function is called once for each child that exists
            // when the listener is added. Then it is called
            // each time a new child is added.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                String value = dataSnapshot.getValue(String.class);
                adapter.add(value);
            }

            // This function is called each time a child item is removed.
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                adapter.remove(value);
            }

            // The following functions are also required in ChildEventListener implementations.
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG:", "Failed to read value.", error.toException());
            }
        });

        // Add items via the Button and EditText at the bottom of the window.
        final EditText text = (EditText) findViewById(R.id.todoText);
        final Button button = (Button) findViewById(R.id.addButton);

        final Dish bpoulet = new Dish("100", "Briouate au poulet", "Petite briouate spécial ramadan !", "5 €", 1, "entrees");
        final Dish sCarottes = new Dish("100", "Salade de carottes à l'orientale", "Salade de carottes à l'orientale, cumin, huile argan", "6 €", 0, "entrees");
        final Dish bsales = new Dish("100", "Beignets salés", "Beignets salés", "4 €", 0, "entrees");

        final Dish tajin = new Dish("100", "Tajin", "C’est une préparation sucrée ou salée où l’ont utilisé toutes les viandes, tous les poissons, les légumes comme les fruits.", "15 €", 5, "plats");
        final Dish couscous = new Dish("100", "Couscous", "Composé d’un mélange de viande, de légumes et de semoule de blé cuite à la vapeur", "12 €", 2, "plats");
        final Dish pastilla = new Dish("100", "Pastilla", "Il s’agit d’un feuilleté, fabriqué à partir de feuilles brick, farci de viande ou de poulet. Le tout est recouvert de sucre et de cannelle.", "13 €", 3, "plats");

        final Dish baklavas = new Dish("100", "Baklavas", "Baklavas aux amandes et pistaches", "3 €", 0, "desserts");
        final Dish corne = new Dish("100", "Corne", "Corne de gazelle", "4 €", 0, "desserts");
        final Dish msemen = new Dish("100", "Msemen", "Msemen (crêpes marocaines)", "4 €", 0, "desserts");

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               /* // Create a new child with a auto-generated ID.
                DatabaseReference childRef = myRef.push();

                // Set the child's data to the value passed in from the text box.
                childRef.setValue(text.getText().toString());*/
                // Create a new child with a auto-generated ID.
                DatabaseReference refbpoulet = menuRef.push();
                DatabaseReference refsCarottes = menuRef.push();
                DatabaseReference refbsales = menuRef.push();
                DatabaseReference reftajin = menuRef.push();
                DatabaseReference refcouscous = menuRef.push();
                DatabaseReference refpastilla = menuRef.push();
                DatabaseReference refbaklavas = menuRef.push();
                DatabaseReference refcorne = menuRef.push();
                DatabaseReference refmsemen = menuRef.push();

                // Set the child's data to the value passed in from the text box.
                refbpoulet.setValue(bpoulet);
                refsCarottes.setValue(sCarottes);
                refbsales.setValue(bsales);
                reftajin.setValue(tajin);
                refcouscous.setValue(couscous);
                refpastilla.setValue(pastilla);
                refbaklavas.setValue(baklavas);
                refcorne.setValue(corne);
                refmsemen.setValue(msemen);

            }
        });

        // Delete items when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Query myQuery = menuRef.orderByValue().equalTo((String)
                        listView.getItemAtPosition(position));

                myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                            firstChild.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                })
                ;
            }
        })
        ;
    }
}
