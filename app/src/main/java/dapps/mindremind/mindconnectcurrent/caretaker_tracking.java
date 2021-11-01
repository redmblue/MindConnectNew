package dapps.mindremind.mindconnectcurrent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.microsoft.azure.maps.mapcontrol.AzureMaps;
import com.microsoft.azure.maps.mapcontrol.MapControl;
import com.microsoft.maps.MapView;
import android.widget.FrameLayout;
import com.microsoft.maps.MapRenderMode;
import com.microsoft.maps.MapView;

public class caretaker_tracking extends AppCompatActivity {
    private MapView mMapView;
    static {
        AzureMaps.setSubscriptionKey("INSERT-API-KEY-HERE");
        //Set the language to be used by Azure Maps.
        AzureMaps.setLanguage("en-US");

        //Set the regional view to be used by Azure Maps.
        AzureMaps.setView("auto");

    }

    MapControl mapControl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker_tracking);
        if (mMapView != null) {
            mMapView.onSaveInstanceState(savedInstanceState);
        }
        //mMapView.onCreate(savedInstanceState);
        mMapView = new MapView(this, MapRenderMode.VECTOR);  // or use MapRenderMode.RASTER for 2D map
        //mMapView.setCredentialsKey(BuildConfig.CREDENTIALS_KEY);
        mMapView.setCredentialsKey("INSERT-API-KEY-HERE");
        ((FrameLayout)findViewById(R.id.map_view)).addView(mMapView);
        setContentView(R.layout.activity_caretaker_tracking);
        mapControl = findViewById(R.id.mapcontrol);

        mapControl.onCreate(savedInstanceState);
        mapControl.onReady(map -> {
            //Add your post map load code here.
            //Create a data source and add it to the map.
            //DataSource dataSource = new DataSource();
            //map.sources.add(dataSource);

            //Create a list of points.
            // List<Point> points = Arrays.asList(
            //       Point.fromLngLat(-73.972340, 40.743270),
            //     Point.fromLngLat(-74.004420, 40.756800));

            //Create a LineString feature and add it to the data source.
            //dataSource.add(Point.fromLngLat(-73,23));


            //Create a line layer and add it to the map.


        });

    }
    @Override
    public void onResume() {
        super.onResume();
        mapControl.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapControl.onStart();
        mMapView.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapControl.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapControl.onStop();
        mMapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapControl.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mapControl!=null) {
            mapControl.onSaveInstanceState(outState);
        }
        if(mMapView!=null) {
            mMapView.onSaveInstanceState(outState);
        }
    }
}
