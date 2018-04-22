package aunyamane.mynavigator;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    private Double latitude = 0.00;
    private Double longitude = 0.00;
    final List<Polyline> polylines = new ArrayList<Polyline>();

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

////////////////////////////ปุ่มลบเส้นทาง////////////////////////////////////////
        ////////////////////////////ปุ่มลบเส้นทาง////////////////////////////////////
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        double points1 = getIntent().getDoubleExtra("apoints1", 0);
        double points2 = getIntent().getDoubleExtra("apoints2", 0);
        mMap = map;
        mMap.setMyLocationEnabled(true);
        //////////////////////////////////////////// ปุ่ม UI //////////////////////////////////////////////
        mMap.getUiSettings().setMapToolbarEnabled(true);
        final GpsTracker gps;
        gps = new GpsTracker(MapsActivity.this);
        final double lats = gps.getLatitude();
        final double longs = gps.getLongitude();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lats, longs), 16);
        mMap.animateCamera(cameraUpdate);
        findDirections(lats, longs,points1,points2,"driving");
        ArrayList<HashMap<String, String>> location = null;
        String url1 = "http://sniperkla.lnw.mn/khim.php";
        try {
            JSONArray data = new JSONArray(getHttpGet(url1));

            location = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map1;
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                map1 = new HashMap<String, String>();
                map1.clear();
                map1.put("LocationID", c.getString("LocationID"));
                map1.put("location", c.getString("location"));
                map1.put("vehicle", c.getString("vehicle"));
                map1.put("latitude", c.getString("latitude"));
                map1.put("longitude", c.getString("longitude"));
                location.add(map1);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        final HashMap<String, String> markerMap = new HashMap<String, String>();
        final List<Marker> markersList = new ArrayList<Marker>();
        String[] id = new String[location.size()];
        final String[] a = new String[location.size()];

        for (int i = 0; i < location.size(); i++) {

            Marker a1 = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude = Double.parseDouble(location.get(i).get("latitude").toString()),longitude = Double.parseDouble(location.get(i).get("longitude").toString())))
                    .title(location.get(i).get("location"))
                    .snippet(location.get(i).get("vehicle"))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round)));
            markersList.add(a1); //ยัด al แทนที่ markersList
            id[i] = markersList.get(i).getId();
            markerMap.put(id[i], a[i]);
        }

        // Get Current Location


        // Add a circle in current
        Circle circle = map.addCircle(new CircleOptions()
                .center(new LatLng(latitude = lats,
                                   longitude = longs))
                .radius(500)
                .strokeColor(0x80ff0000)
                .fillColor(0x15ff0000));

        List<Double> x = new ArrayList<Double>();
        List<Double> y = new ArrayList<Double>();
        int i;
        for(i=0; i<location.size(); i++){
            x.add(Double.parseDouble(location.get(i).get("latitude").toString()));
            y.add(Double.parseDouble(location.get(i).get("longitude").toString()));

        }


    }
    public static String getHttpGet(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();

    }
    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
        PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);
        for (int i = 0; i < directionPoints.size(); i++) {
            rectLine.add(directionPoints.get(i));
        }
        //newPolyline = mMap.addPolyline(rectLine);
        polylines.add(this.mMap.addPolyline(rectLine));
    }
    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(AsyncTaskDrawDirection.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(AsyncTaskDrawDirection.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(AsyncTaskDrawDirection.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(AsyncTaskDrawDirection.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(AsyncTaskDrawDirection.DIRECTIONS_MODE, mode);
        AsyncTaskDrawDirection asyncTask = new AsyncTaskDrawDirection(this);
        asyncTask.execute(map);
    }
}

