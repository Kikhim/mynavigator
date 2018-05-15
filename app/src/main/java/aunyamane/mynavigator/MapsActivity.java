package aunyamane.mynavigator;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

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

import java.net.HttpURLConnection;
import java.net.URL;

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
import org.w3c.dom.Document;

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
    ArrayList<LatLng> markerPoints;
    private Double latitude = 0.00;
    private Double longitude = 0.00;

    ArrayList<Integer> colorList;
    private Integer line_count = 0;

    final List<Polyline> polylines = new ArrayList<Polyline>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        markerPoints = new ArrayList<LatLng>();
        colorList = new ArrayList<Integer>();
        colorList.add(Color.BLUE);
        colorList.add(Color.GREEN);
        colorList.add(Color.MAGENTA);

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
        ArrayList<HashMap<String, String>> nearby_vehicle = null;

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

        /*
         * Nearby Vehicle
         */
        String url_vehicle = "http://sniperkla.lnw.mn/khim_vehicle.php?lat=" + lats + "&lng=" + longs;
        String url_route   = null;

        try {
            JSONArray data = new JSONArray(getHttpGet(url_vehicle));
            nearby_vehicle = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map1;
            TypedArray ta = getResources().obtainTypedArray(R.array.colors);
            TypedArray m_hue = getResources().obtainTypedArray(R.array.marker_hue);
            Integer marker_color;

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                url_route = "http://sniperkla.lnw.mn/khim_route.php?vid=" + c.getString("id");
                System.out.println("dept_log_route["+i+"]: "+url_route);
                marker_color = m_hue.getColor((i%m_hue.length()), 0);
                JSONArray route_data = new JSONArray(getHttpGet(url_route));
                if(route_data.length() > 0){
                    markerPoints.clear();
                    for (int j = 0; j < route_data.length(); j++) {
                        JSONObject route_obj = route_data.getJSONObject(j);
                        LatLng point = new LatLng(
                                latitude = Double.parseDouble(route_obj.getString("latitude")),
                                longitude = Double.parseDouble(route_obj.getString("longitude")));
                        markerPoints.add(point);

                        // Creating MarkerOptions
                        MarkerOptions options = new MarkerOptions();

                        // Setting the position of the marker
                        options.position(point);

                        /**
                         * For the start location, the color of marker is GREEN and
                         * for the end location, the color of marker is RED and
                         * for the rest of markers, the color is AZURE
                         */
                        if(markerPoints != null) {
                            options.title(route_obj.getString("name_th"));
                            options.icon(BitmapDescriptorFactory.defaultMarker(marker_color));
                            /*
                            if (markerPoints.size() == 1) {
                                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            } else if (markerPoints.size() == 2) {
                                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            } else {
                                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                            }
                            */
                        }


                        // Add new marker to the Google Map Android API V2
                        map.addMarker(options);
                    }

                    System.out.println("dept_log_markerPoints: " + markerPoints);

                    if(markerPoints != null) {
                        if (markerPoints.size() >= 2) {
                            LatLng origin = markerPoints.get(0);
                            LatLng dest = markerPoints.get(1);

                            // Getting URL to the Google Directions API
                            String url = getDirectionsUrl(origin, dest);

                            DownloadTask downloadTask = new DownloadTask();
                            System.out.println("dept_log_direction: " + url);
                            // Start downloading json data from Google Directions API
                            downloadTask.execute(url);
                        }
                        // line_count++;
                    }
                }

                map1 = new HashMap<String, String>();
                map1.clear();
                map1.put("id", c.getString("id"));
                map1.put("type", c.getString("type"));
                map1.put("name", c.getString("name"));
                map1.put("lat", c.getString("latitude"));
                map1.put("lng", c.getString("longitude"));
                map1.put("distance", c.getString("distance"));

                nearby_vehicle.add(map1);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final HashMap<String, String> markerVehicle = new HashMap<String, String>();
        final List<Marker> markersVehicleList = new ArrayList<Marker>();
        String[] vehicle_id = new String[location.size()];
        final String[] vehicle_a = new String[location.size()];

        for (int i = 0; i < nearby_vehicle.size(); i++) {

            Marker a1 = mMap.addMarker(new MarkerOptions().position(
                    new LatLng(
                            latitude = Double.parseDouble(nearby_vehicle.get(i).get("lat").toString()),
                            longitude = Double.parseDouble(nearby_vehicle.get(i).get("lng").toString()))
            )
                    .title(nearby_vehicle.get(i).get("name"))
                    .snippet(nearby_vehicle.get(i).get("type") + ", " + nearby_vehicle.get(i).get("distance") + "Km.")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            markersVehicleList.add(a1); //ยัด al แทนที่ markersList
            vehicle_id[i] = markersVehicleList.get(i).getId();
            markerVehicle.put(vehicle_id[i], vehicle_a[i]);
        }

        /*
         * /nearby vehicle
         */

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
        Circle circle_start = map.addCircle(new CircleOptions()
                .center(new LatLng(latitude = lats,
                                   longitude = longs))
                .radius(500)
                .strokeColor(0x80ff0000)
                .fillColor(0x15ff0000));

        // Add a circle in destination
        Circle circle_dest = map.addCircle(new CircleOptions()
                .center(new LatLng(latitude = points1,
                        longitude = points2))
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

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Waypoints
        String waypoints = "";
        for(int i=2;i<markerPoints.size();i++){
            LatLng point  = (LatLng) markerPoints.get(i);
            if(i==2)
                waypoints = "waypoints=";
            waypoints += point.latitude + "," + point.longitude + "|";
        }

        // Building the parameters to the web service
        String map_api_key = getResources().getString(R.string.google_maps_key);
        map_api_key = "AIzaSyD9TUPq6I1TkQg5daafolxL-8TBJxa7qV8";
        String parameters = "key="+map_api_key+"&"+str_origin+"&"+str_dest+"&"+sensor+"&"+waypoints;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("except downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service

            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            Resources res = getResources();
            TypedArray ta = res.obtainTypedArray(R.array.colors);
            int[] colors = new int[ta.length()];
/*
            for (int i = 0; i < ta.length(); i++) {
                colors[i] = ta.getColor(i, 0);
            }
            ta.recycle();
            */

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(ta.getColor((line_count%ta.length()), 0));

            }

            // Drawing polyline in the Google Map for the i-th route
            System.out.println("dept_log_line_count: " + line_count);
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
                line_count++;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}

