package aunyamane.mynavigator;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

class NearbyActivity extends Activity {
    //String ApiKey = "@string/google_maps_key";
    double latitude = 7.894143;
    double longitude = 98.352522;
    int radius = 1000;
    String type = "Hospital";
    String language = "th";
    String keyword = "Mission";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        GooglePlaceSearch gp = new GooglePlaceSearch("AIzaSyC5BfF90liA0hv4uzXZab1paN0U9m6P2j0");
        ArrayList<ContentValues> arr_cv = gp.getNearBy(latitude,longitude,radius,type,language,keyword);
        ArrayList<String> arr_list = new ArrayList<String>();
        if(arr_cv != null){
            for(int i = 0 ; i<arr_cv.size() ; i++){
                String lat = arr_cv.get(i).getAsString(GooglePlaceSearch.LATITUDE);
                String lon = arr_cv.get(i).getAsString(GooglePlaceSearch.LONGITUDE);
                String name = arr_cv.get(i).getAsString(GooglePlaceSearch.NAME);

                arr_list.add("lat : " + lat + "\n" + "lon : " + lon + "\n" + "lat : " + name + "\n");
            }
        }

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("Data "+gp.getDataStatus());

        ArrayAdapter adapter = new ArrayAdapter(NearbyActivity.this
                , android.R.layout.simple_list_item_1, arr_list);
        ListView listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(adapter);

    }
}
