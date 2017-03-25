package mmm.istic.fr.quickdish.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mmm.istic.fr.quickdish.R;
import mmm.istic.fr.quickdish.bo.Dish;
import mmm.istic.fr.quickdish.bo.Order;
import mmm.istic.fr.quickdish.firebase.DataBase;

public class ScanActivity extends AppCompatActivity {


    //barcode scanner
    private static final String LOG_TAG = "Barcode Scanner API";
    private static final int PHOTO_REQUEST = 10;
    private static final int REQUEST_WRITE_PERMISSION = 20;
    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_RESULT = "result";
    //Menu list
    android.widget.ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> itemList;
    HashMap<String, List<Dish>> listDataChild;
    //Save menu list in JSONObject
    JSONObject jsonResult;
    //Database
    DataBase database;
    List<Dish> dishes = new ArrayList<Dish>();
    private TextView scanResults;
    private BarcodeDetector detector;
    private Uri imageUri;
    //save qrcode
    private String qrCode;
    //Order
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // initialize database
        database = new DataBase();

        scanResults = (TextView) findViewById(R.id.scan_results);
        if (savedInstanceState != null) {
            imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));
            scanResults.setText(savedInstanceState.getString(SAVED_INSTANCE_RESULT));
        }

        // Create listener for scanButton
        Button scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(ScanActivity.this, new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
            }
        });

        // Create detector to get qrCode
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
        listDataChild = new HashMap<String, List<Dish>>();

        listAdapter = new ExpandableListAdapter(this, itemList, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                dishes.add(listDataChild.get(itemList.get(groupPosition)).get(childPosition));

                order = new Order(dishes, 1, qrCode, false);

                Toast.makeText(
                        getApplicationContext(),
                        itemList.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                itemList.get(groupPosition)).get(
                                childPosition).getTitle(), Toast.LENGTH_SHORT).show();
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
    public void validateCommand(View view) {
        alertMessage();
    }

    //parse result (json file) and add elements to menu list
    public void showMenuList(String barcode) {
        // show the menu
        final List<Dish> entrees = new ArrayList<Dish>();
        final List<Dish> plats = new ArrayList<Dish>();
        final List<Dish> desserts = new ArrayList<Dish>();

        database.getDishsByRestoId(barcode, new DataBase.Command() {
            @Override
            public void exec(Object o) {
                Dish dish = ((Dish) o);
                String type = dish.getType();
                switch (type) {
                    case "entrees":
                        entrees.add(dish);
                        break;
                    case "plats":
                        plats.add(dish);
                        break;
                    case "desserts":
                        desserts.add(dish);
                        break;
                    default:
                        break;
                }
                System.out.println("ceci est un commentaire lol :" + ((Dish) o).getTitle());
            }
        });

        listDataChild.put(itemList.get(0), entrees);
        listDataChild.put(itemList.get(1), plats);
        listDataChild.put(itemList.get(2), desserts);

        ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
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

        qrCode = "10003";
        //show the menu list
        showMenuList("100");

        if (requestCode == PHOTO_REQUEST && resultCode == RESULT_OK) {
            launchMediaScanIntent();
            try {
                Bitmap bitmap = decodeBitmapUri(this, imageUri);
                if (detector.isOperational() && bitmap != null) {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<Barcode> barcodes = detector.detect(frame);

                    Barcode code = barcodes.valueAt(0);


                    //qrCode = code.displayValue;

                    //scanResults.setText(scanResults.getText() + code.displayValue + "\n");
                    System.err.println("ID RESTAURANT = " + code.displayValue.substring(0, 3));
                    //showMenuList(code.displayValue.substring(0, 3));


                    if (barcodes.size() == 0) {
                        Toast.makeText(getApplicationContext(), "Scan Failed: Found nothing to scan", Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Could not set up the detector!", Toast.LENGTH_SHORT);
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

    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked

                        Intent myIntent = new Intent(ScanActivity.this, CommandResumeActivity.class);

                        // passe the order to next activity
                        myIntent.putExtra("order", order);
                        setResult(10, myIntent);
                        finish();
                        startActivity(myIntent);

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked
                        // do nothing
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to place order?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

}
