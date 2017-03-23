package mmm.istic.fr.quickdish.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.*;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mmm.istic.fr.quickdish.R;
import mmm.istic.fr.quickdish.activities.bo.Dish;
import mmm.istic.fr.quickdish.activities.bo.Order;

public class ScanActivity extends AppCompatActivity {


    //barcode scanner
    private static final String LOG_TAG = "Barcode Scanner API";
    private static final int PHOTO_REQUEST = 10;
    private TextView scanResults;
    private BarcodeDetector detector;
    private Uri imageUri;
    private static final int REQUEST_WRITE_PERMISSION = 20;
    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_RESULT = "result";

    //Menu list
    android.widget.ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> itemList;
    HashMap<String, List<String>> listDataChild;

    //Save menu list in JSONObject
    JSONObject jsonResult;

    //Database
    FirebaseDatabase database;

    //Order
    private Order order;
    List<Dish> dishs = new ArrayList<Dish>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);


/*        // Connect to the Firebase database
        database = FirebaseDatabase.getInstance();

        final Dish dish1 = new Dish("1", "salade", "description \n blablabla", "10 €", 0, "entrees");
        final Dish dish2 = new Dish("1", "Pizza", "description \n blablabla", "10 €", 0, "plats");
        final Dish dish3 = new Dish("1", "Pizza", "description \n blablabla", "10 €", 0, "desserts");

        // Get a reference to the todoItems child items it the database
        final DatabaseReference myRef = database.getReference("Dishs");

        final Button scanButton = (Button) findViewById(R.id.scanButton);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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


        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Create a new child with a auto-generated ID.
                DatabaseReference childRef1 = myRef.push();
                DatabaseReference childRef2 = myRef.push();
                DatabaseReference childRef3 = myRef.push();
                // Set the child's data to the value passed in from the text box.
                childRef1.setValue(dish1);
                childRef2.setValue(dish2);
                childRef3.setValue(dish3);

            }
        });*/

        //initialise jsonResult for test
        try {
            jsonResult = new JSONObject("{" +
                    "\"entrees\": [\"entree1\", \"entree2\", \"entree3\", \"entree4\", \"entree5\"]," +
                    "\"plats\": [\"plat1\", \"plat2\", \"plat3\"]," +
                    "\"desserts\": [\"dessert1\", \"dessert2\", \"dessert3\", \"dessert4\"]" +
                    "}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button button = (Button) findViewById(R.id.scanButton);
        scanResults = (TextView) findViewById(R.id.scan_results);
        if (savedInstanceState != null) {
            imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));
            scanResults.setText(savedInstanceState.getString(SAVED_INSTANCE_RESULT));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(ScanActivity.this, new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
            }
        });

        detector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                .build();
        if (!detector.isOperational()) {
            scanResults.setText("Could not set up the detector!");
            return;
        }

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        itemList = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listAdapter = new ExpandableListAdapter(this, itemList, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        final int[] totalDishs = {0};
        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                dishs.add(totalDishs[0], new Dish("1", listDataChild.get(
                        itemList.get(groupPosition)).get(
                        childPosition), "description \n blablabla", "10 €", 0, "entree"));

                totalDishs[0]++;
                order = new Order(dishs, totalDishs[0], "1", false);

                Toast.makeText(
                        getApplicationContext(),
                        itemList.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                itemList.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });



        // initialise the menu list display
        itemList.clear();
        itemList.add("Entrées");
        itemList.add("Plats");
        itemList.add("Desserts");

    }

    // validate the command by click on button validate
    public void validateCommand(View view){
        System.out.println(order.dishsToString());

        Intent myIntent = new Intent( ScanActivity.this, CommandResumeActivity.class);


        // passe the order to next activity
        myIntent.putExtra("order", order);
        setResult(10, myIntent);
        finish();

        startActivity(myIntent);


    }

    //parse result (json file) and add elements to menu list
    public void showMenuList (){
        // show the ingredients
        List<String> entrees = new ArrayList<String>();
        List<String> plats = new ArrayList<String>();
        List<String> desserts = new ArrayList<String>();



        //parse jsonResult to show information
        JSONArray jsonEntrees = new JSONArray();
        JSONArray jsonPlats = new JSONArray();
        JSONArray jsonDesserts = new JSONArray();
        try {
            jsonEntrees = jsonResult.getJSONArray("entrees");
            jsonPlats = jsonResult.getJSONArray("plats");
            jsonDesserts = jsonResult.getJSONArray("desserts");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonEntrees.length(); i++) {
            try {
                String element = (String) jsonEntrees.get(i);
                entrees.add(element);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < jsonPlats.length(); i++) {
            try {
                String element = (String) jsonPlats.get(i);
                plats.add(element);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < jsonDesserts.length(); i++) {
            try {
                String element = (String) jsonDesserts.get(i);
                desserts.add(element);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        listDataChild.put(itemList.get(0), entrees);
        listDataChild.put(itemList.get(1), plats);
        listDataChild.put(itemList.get(2), desserts);
    }

    // request the firebase to get menu list by barcode
    public void getMenuFromFireBase (String barcode){
        // TODO : request the firebase to get menu using barcode and save result in jsonResult
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                } else {
                    Toast.makeText(ScanActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //request for menuList from firebase
        getMenuFromFireBase (scanResults.getText().toString());

        //show the menu list
        showMenuList();

        if (requestCode == PHOTO_REQUEST && resultCode == RESULT_OK) {
            launchMediaScanIntent();
            try {
                Bitmap bitmap = decodeBitmapUri(this, imageUri);
                if (detector.isOperational() && bitmap != null) {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<Barcode> barcodes = detector.detect(frame);

                    Barcode code = barcodes.valueAt(0);

                    scanResults.setText(scanResults.getText() + code.displayValue + "\n");



                    if (barcodes.size() == 0) {
                        scanResults.setText("Scan Failed: Found nothing to scan");
                    }
                } else {
                    scanResults.setText("Could not set up the detector!");
                }
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT)
                        .show();
                Log.e(LOG_TAG, e.toString());
            }
        }

    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, PHOTO_REQUEST);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (imageUri != null) {
            outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
            outState.putString(SAVED_INSTANCE_RESULT, scanResults.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }

    private void launchMediaScanIntent() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }

}
