package aunyamane.mynavigator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;


public class TravelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new TravelAdapter(this));


        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(TravelActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}