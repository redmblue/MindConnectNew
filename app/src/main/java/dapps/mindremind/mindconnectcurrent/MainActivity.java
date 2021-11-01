package dapps.mindremind.mindconnectcurrent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import android.widget.TextView;
import android.os.StrictMode;
import java.net.URLConnection;
import java.util.Scanner;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.File;
import java.io.FileReader;
import java.io.*;
import java.util.regex.*;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.gson.*;
import com.microsoft.azure.maps.mapcontrol.AzureMaps;
import com.microsoft.azure.maps.mapcontrol.MapControl;
import com.microsoft.azure.maps.mapcontrol.layer.SymbolLayer;
import com.microsoft.azure.maps.mapcontrol.options.MapStyle;
import com.microsoft.azure.maps.mapcontrol.source.DataSource;
import com.microsoft.azure.maps.mapcontrol.*;
import com.mapbox.geojson.Point;
import static com.microsoft.azure.maps.mapcontrol.options.LineLayerOptions.strokeColor;
import static com.microsoft.azure.maps.mapcontrol.options.LineLayerOptions.strokeWidth;
import com.microsoft.maps.MapRenderMode;
import com.microsoft.maps.MapView;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    static {
        AzureMaps.setSubscriptionKey("INSERT-API-KEY-HERE");
        //Set the language to be used by Azure Maps.
        AzureMaps.setLanguage("en-US");

        //Set the regional view to be used by Azure Maps.
        AzureMaps.setView("auto");

    }

    MapControl mapControl;

    Context context;
    FusedLocationProviderClient client;
    LocationCallback loccall;
    StrictMode.ThreadPolicy defpol = StrictMode.getThreadPolicy();
    private static final int PERMISSIONS_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_caretaker_tracking);
        mapControl = findViewById(R.id.mapcontrol);
        mapControl.onCreate(savedInstanceState);
        mapControl.onReady(map -> {
            //DataSource dataSource = new DataSource();
            //map.sources.add(dataSource);

            //Create a list of points.
            // List<Point> points = Arrays.asList(
            //       Point.fromLngLat(-73.972340, 40.743270),
            //     Point.fromLngLat(-74.004420, 40.756800));


            //dataSource.add(Point.fromLngLat(-73,23));




        });

        //mMapView = new MapView(this, MapRenderMode.VECTOR);  // or use MapRenderMode.RASTER for 2D map
        //mMapView.setCredentialsKey(BuildConfig.CREDENTIALS_KEY);
        //((FrameLayout)findViewById(R.id.map_view)).addView(mMapView);
        //mMapView.onCreate(savedInstanceState);
        SharedPreferences mPrefs46 = getSharedPreferences("label", 0);
        String mString67 = mPrefs46.getString("ID", null);
        if (mString67.equals(null)) {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("location");
            String id = ref.push().getKey();
            final String perm = id;
            SharedPreferences mPrefs = getSharedPreferences("label", 0);
            SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putString("ID", id).commit();
        }
        SharedPreferences mPrefs4 = getSharedPreferences("label", 0);
        String mString = mPrefs4.getString("TSelect", null);
        if (mString.equals(null) || mString.equals("Main")) {
            setContentView(R.layout.activity_main);
        } else if (mString.equals("Caretaker")) {
            setContentView(R.layout.activity_main__caretaker);
        } else if (mString.equals("Patient")) {
            setContentView(R.layout.activity_main_patient);
        }

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            finish();
        }
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            //startTrackerService();
            Toast.makeText(this, "GPS tracking permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }
        //setContentView(R.layout.activity_main);

    }

    public void gotoBack1(View view) {
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("TSelect", "Main").commit();
        setContentView(R.layout.activity_main);

    }

    public void onRequestPermissionsxResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {
            Toast.makeText(this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
        }
        startService(new Intent(this, TrackingService.class));

//Notify the user that tracking has been enabled//

        Toast.makeText(this, "GPS tracking enabled", Toast.LENGTH_SHORT).show();
    }

    private void startTrackerService() {
        startService(new Intent(this, TrackingService.class));
        Toast.makeText(this, "GPS tracking enabled", Toast.LENGTH_SHORT).show();
    }

    public void sendchCaretaker(View view) {
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("TSelect", "Caretaker").commit();
        //System.out.println(AddresstoCordinatePoint("233 S Wacker Dr, Chicago, IL 60606"));


        //System.out.println(new Networking().AddresstoCordinatePoint("233 S Wacker Dr, Chicago, IL 60606"));

        //System.out.println(AddresstoLatLong("233 S Wacker Dr, Chicago, IL 60606"));

        setContentView(R.layout.activity_main__caretaker);

        //startTrackerService();

        //sendLoc();

        //setContentView(R.layout.activity_caretaker_view);

    }

    protected void sendLoc() {
        LocationRequest request = new LocationRequest();
        request.setInterval(1000);
        //request.
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        client = LocationServices.getFusedLocationProviderClient(this);
        //loccall = new LocationCallback();
        //loccall = this;
        //client.removeLocationUpdates(this);

        client.requestLocationUpdates(request, loccall = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

//Get a reference to the database, so your app can perform read and write operations//

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("location");
                //FirebaseDatabase  base = FirebaseDatabase.getInstance();
                Location location = locationResult.getLastLocation();
                //location.reset();
                /*

                String id = ref.push().getKey();
                final String perm = id;
                SharedPreferences mPrefs = getSharedPreferences("label", 0);
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putString("ID", id).commit();
                */

                if (location != null) {

//Save the location data to the database//
                    //int id = 0;
                    SharedPreferences mPrefs1 = getSharedPreferences("label", 0);
                    String mString = mPrefs1.getString("ID", null);
                    ref.child(mString).setValue(location);
                    System.out.println("Hello.");
                    //Adding and updating values
                    /*
                    ref.child(mString).child("Data1").setValue("Lat,Long,Rem");
                     */

                    retrieveLoc(mString);
                }

            }
        }, null);
    }

    public void retrieveLoc(String ID) {
        //DatabaseReference reffits = Firebase
        //SharedPreferences mPrefs10 = getSharedPreferences("label", 0);
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("location");
        //String mString10 = mPrefs10.getString("ID", null);
        //DataSnapshot databh = reff.child(mString10).child("latitude");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                SharedPreferences mPrefs10 = getSharedPreferences("label", 0);
                DatabaseReference reff = FirebaseDatabase.getInstance().getReference("location");
                String mString10 = mPrefs10.getString("ID", null);
                String lat = dataSnapshot.child(mString10).child("latitude").getValue().toString();
                String longt = dataSnapshot.child(mString10).child("longitude").getValue().toString();
                //if(!dataSnapshot.child(mString10).child(mString10).child("Data1").getValue().toString().equals("Lat,Long,Rem")){
                //  reff.child(mString10).child("Data1").setValue("Lat,Long,Rem");
                //}
                System.out.println(longt);
                System.out.println(lat);
                TextView a = (TextView) findViewById(R.id.Latitude);
                a.setText("Lat: " + lat);
                TextView b = (TextView) findViewById(R.id.Longitude);
                b.setText("Long: " + longt);
                // ...

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        //mPostReference.addValueEventListener(postListener);
        //https://maps.googleapis.com/maps/api/geocode/xml?address=1600+Amphitheatre+Parkway,
        //+Mountain+View,+CA&key=YOUR_API_KEY
        reff.addValueEventListener(postListener);
    }

    public void onPauseLoc(View view) {
        //super.onPause();
        System.out.println("STOOPE");
        client.removeLocationUpdates(loccall);
        Toast.makeText(this, "GPS tracking disabled", Toast.LENGTH_SHORT).show();
        //client.removeLocationUpdates(this);
    }

    public void onResumeLoc(View view) {
        //super.onResume();
        //sendchCaretaker(view);
        sendLoc();
        //client = LocationServices.getFusedLocationProviderClient(this);
        Toast.makeText(this, "GPS tracking enabled", Toast.LENGTH_SHORT).show();
    }

    public String AddresstoLatLong(String address) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String inp = address.replace(" ", "-");
        String urli = "https://atlas.microsoft.com/search/fuzzy/json?subscription-key[INSERT-API-KEY-HERE]&api-version=1.0&query=" + inp;
        String sURL = "http://freegeoip.net/json/"; //just a string

        // Connect to the URL using java's native library
        String ans = "";
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(urli);
            InputStream is = url.openStream();
            int ptr = 0;
            //StringBuffer buffer = new StringBuffer();
            while ((ptr = is.read()) != -1) {
                buffer.append((char) ptr);
            }
        } catch (Exception e) {

        }
        String thing = buffer.toString();
        Pattern pattern = Pattern.compile("\"position\":(.+?)\"viewport", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(thing);
        matcher.find();
        String thingits = matcher.group(0);
        Pattern Late = Pattern.compile("\\{\"lat\"(.+?),");
        Pattern Lone = Pattern.compile("lon(.+?)\\}");
        Matcher matchlone = Lone.matcher(thing);
        matchlone.find();
        String lon = matchlone.group(0).substring(5, matchlone.group(0).length() - 1);

        Matcher matchlate = Late.matcher(thing);
        matchlate.find();
        String lat = matchlate.group(0).substring(7, matchlate.group(0).length() - 1);

        //System.out.println(matcher.group(0));
        StrictMode.setThreadPolicy(defpol);
        return lon + " " + lat;
        //return thingits;
    }

    public void sendchPatient(View view) {
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("TSelect", "Patient").commit();
        setContentView(R.layout.activity_main_patient);
        //setContentView(R.layout.activity_patient_view);
    }
    public void sendcaretakerTracking(View view){
        //setContentView(R.layout.activity_caretaker_tracking);
        startActivity(new Intent("com.dapps.mindremind.mindconnectcurrent.TestMap"));
       // setContentView(R.layout.activity_test_map);
        //startActivity(new Intent(Intent.ACTION_VIEW, TestMap));
        //startActivity


        //mapControl.onStart();
        //setContentView(R.layout.activity_caretaker_tracking);
        //AzureMaps.setSubscriptionKey("<INSERT-API-KEY-HERE>");
        //AzureMaps.setSubscriptionKey("INSERT-API-KEY-HERE");

        //mapControl = findViewById(R.id.mapcontrol);

        //mapControl = new Map;
        /*
        try{
            //mapControl.onCreate(savedInstanceState);
        }
        catch(Exception e){

        }

         */
        //mapControl.onCreate(savedInstanceState);

        //Wait until the map resources are ready.
        //super.onStart();
        //mapControl.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        mapControl.onResume();
        //mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapControl.onStart();
        //mMapView.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapControl.onPause();
        //mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapControl.onStop();
        //mMapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapControl.onLowMemory();
        //mMapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mMapView.onDestroy();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapControl.onSaveInstanceState(outState);
        //mMapView.onSaveInstanceState(outState);
    }


}
