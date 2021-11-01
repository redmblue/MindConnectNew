package dapps.mindremind.mindconnectcurrent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.microsoft.azure.maps.mapcontrol.AzureMaps;
import com.microsoft.azure.maps.mapcontrol.MapControl;
import com.microsoft.maps.MapRenderMode;
import com.microsoft.maps.MapView;
import android.widget.FrameLayout;
import android.os.Bundle;
import com.microsoft.azure.maps.mapcontrol.AzureMaps;
import com.microsoft.azure.maps.mapcontrol.MapControl;
import com.microsoft.azure.maps.mapcontrol.layer.SymbolLayer;
import com.microsoft.azure.maps.mapcontrol.options.MapStyle;
import com.microsoft.azure.maps.mapcontrol.source.DataSource;

import static dapps.mindremind.mindconnectcurrent.BuildConfig.CREDENTIALS_KEY;


public class TestMap extends AppCompatActivity {
    static {
        AzureMaps.setSubscriptionKey("<INSERT-API-KEY-HERE>");
        AzureMaps.setLanguage("en-US");
        AzureMaps.setView("auto");
    }

    MapControl mapControl;
    //private MapView mMapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Hai");
        setContentView(R.layout.activity_test_map);
        //mMapView = new MapView(this, MapRenderMode.VECTOR);  // or use MapRenderMode.RASTER for 2D map
        //mMapView.onCreate(savedInstanceState);
        //mMapView.setCredentialsKey(CREDENTIALS_KEY);
        //((FrameLayout)findViewById(R.id.map_viewsit)).addView(mMapView);
        mapControl = findViewById(R.id.myMap);

        mapControl.onCreate(savedInstanceState);

        //Wait until the map resources are ready.
        mapControl.onReady(map -> {
            //Add your post map load code here.
            System.out.println("Test");

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mapControl.onResume();
        //mMapView.onResume();
    }

    @Override
    protected void onStart(){
        super.onStart();
        mapControl.onStart();
        //mMapView.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapControl.onPause();
       // mMapView.onPause();
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
       // mMapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapControl.onDestroy();
        //mMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapControl.onSaveInstanceState(outState);
        //mMapView.onSaveInstanceState(outState);
    }
}
