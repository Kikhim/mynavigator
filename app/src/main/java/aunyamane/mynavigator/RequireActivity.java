package aunyamane.mynavigator;

import android.app.Dialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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


public class RequireActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_require);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.test);
        String[] location = getResources().getStringArray(R.array.location);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,location);
        textView.setAdapter(adapter);
       // EditText name5 = (EditText) findViewById(R.id.editText);
        String[] str = {"TukTuk", "Phothong", "Long tail boat", "Taximeter", "Car for rent", "Motorcycle for rent"};
        //findViewById(R.id.editText);
      //  final EditText mEdit = (EditText) findViewById(R.id.editText);
        final Button buttonOK = (Button) findViewById(R.id.button);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                int i;
                List<Double> x = new ArrayList<Double>();
                List<Double> y = new ArrayList<Double>();
                GpsTracker gps2 = new GpsTracker(RequireActivity.this);
                final double latx = gps2.getLatitude();
                final double longx = gps2.getLongitude();
                List<String> name = new ArrayList<String>();
                for (i=0;i<location.size();i++)
                {
                    x.add(Double.parseDouble(location.get(i).get("latitude").toString()));
                    y.add(Double.parseDouble(location.get(i).get("longitude").toString()));
                    name.add(location.get(i).get("location").toString());
                }
                for(i=0;i<location.size();i++) {
                    if (textView.getText().toString().equals(name.get(i))) {
                        double apoints1 = x.get(i);
                        double apoints2 = y.get(i);
                        Intent intent = new Intent(RequireActivity.this, MapsActivity.class);
                        intent.putExtra("apoints1", apoints1);
                        intent.putExtra("apoints2", apoints2);
                        startActivity(intent);
                    }

                }

            }

        });
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
}