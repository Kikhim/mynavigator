package aunyamane.mynavigator;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Map;

public class AsyncTaskDrawDirection extends AsyncTask<Map<String, String>, Object, ArrayList<LatLng>>
{
    public static final String USER_CURRENT_LAT = "user_current_lat";
    public static final String USER_CURRENT_LONG = "user_current_long";
    public static final String DESTINATION_LAT = "destination_lat";
    public static final String DESTINATION_LONG = "destination_long";
    public static final String DIRECTIONS_MODE = "directions_mode";
    //private MapsActivity activity;
    private MapsActivity activity;
    private Exception exception;
    private ProgressDialog progressDialog;

    // public AsyncTaskDrawDirection(MapsActivity activity) {
    // super();
    //  this.activity = activity;
    //}
    public AsyncTaskDrawDirection (MapsActivity activity) {
        super();
        this.activity = activity;
    }

    public void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("กำลังคำนวณเส้นทาง");
        progressDialog.show();
    }

    @Override
    public void onPostExecute(ArrayList result) {
        progressDialog.dismiss();
        if (exception == null) {
            activity.handleGetDirectionsResult(result);
        } else {
            processException();
        }
    }

    @Override
    protected ArrayList<LatLng> doInBackground(Map<String, String>... params) {
        Map<String, String> paramMap = params[0];
        try {
            LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)) , Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
            LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)) , Double.valueOf(paramMap.get(DESTINATION_LONG)));
            GoogleMapsDirection md = new GoogleMapsDirection();
            Document doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));
            ArrayList<LatLng> directionPoints = md.getDirection(doc);

            for (int i = 0; i < directionPoints.size(); i++) {
                Log.d("TBT", directionPoints.get(i).toString());
            }
            return directionPoints;
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    private void processException() {
        Toast.makeText(activity, "การรับข้อมูลผิดพลาด โปรดลองใหม่", Toast.LENGTH_LONG).show();
    }
}