package aunyamane.mynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class SchedulePhoThong3 extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule3);

        String[] round = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20",
                "21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40",
                "41","42","43","44","45","46","47","48"};
        String[] time = {"06.00 น.","06.10 น.","06.20 น.","06.30 น.","06.30 น.","06.40 น.","06.50 น.",
                "07.00 น.", "07.15 น.", "07.30 น.","07.45 น.","08.00 น.","08.20 น.","08.40 น.","09.00 น.","09.20 น.","09.40 น.",
                "10.00 น.","10.20 น.","10.40 น.","11.00 น.","11.20 น.","11.40 น.","12.00 น.","12.20 น.","12.40 น.","13.00 น.","13.20 น.","13.40 น.","14.00 น.","14.40 น.",
                "15.00 น.","15.10 น.","15.20 น.","15.30 น.","15.40 น.","15.50 น.","16.00 น.","16.15 น.","16.30 น.","16.45 น.","17.00 น.",
                "17.20 น.","17.40 น.","18.00 น.","18.20 น.","18.40 น.","19.00 น."};



        ArrayList<String> lists = new ArrayList<>();

        ScheduleAdapter3 adapter = new ScheduleAdapter3(getApplicationContext(),round,time);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                if( i == 0){
                    Intent intent = new Intent(SchedulePhoThong3.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}
        /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });*/





