package aunyamane.mynavigator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class VehicleActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles);

        final int[] resId = { R.drawable.p1,
                        R.drawable.p2,
                        R.drawable.p8,
                        R.drawable.p3,
                        R.drawable.p4,
                        R.drawable.car1
        };

        String[] list = {"Phothong","Long tail boat","Taximeter","Motorcycle for rent","TukTuk","Car for rent"};

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), list, resId);

        ListView listView = (ListView)findViewById(R.id.listview3);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    final Dialog dialog = new Dialog(VehicleActivity.this);
                    dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.schedule_bt);
                    dialog.show();

                    //ปุ่มตารางการเดินรถสายที่2
                    final Button bt1 = (Button) dialog.findViewById(R.id.button4);
                    bt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent button4 = new Intent(VehicleActivity.this, SchedulePhoThong2.class);
                            startActivity(button4);
                        }

                       /* {

                            Intent button3 = new Intent(VehicleActivity.this, ScheduleActivity.class);
                            startActivity(button3);
                        }*/

                    });

                    //ปุ่มตารางการเดินรถสายที่3
                    final Button bt2 = (Button) dialog.findViewById(R.id.button3);
                    bt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent button3 = new Intent(VehicleActivity.this, SchedulePhoThong3.class);
                            startActivity(button3);


                        }
            /* {

                            Intent button3 = new Intent(VehicleActivity.this, ScheduleActivity.class);
                            startActivity(button3);
                        }*/
                    });

                    //ปุ่มตารางการเดินรถสายที่1
                    final Button bt3 = (Button) dialog.findViewById(R.id.button6);
                    bt3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent button6 = new Intent(VehicleActivity.this, SchedulePhoThong1.class);
                            startActivity(button6);

                        }

                       /* {

                            Intent button3 = new Intent(VehicleActivity.this, ScheduleActivity.class);
                            startActivity(button3);
                        }*/

                    });
                }
                if (i == 0) {
                    final Dialog dialog = new Dialog(VehicleActivity.this);
                    dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.boat_bt);
                    dialog.show();

                    //ปุ่มท่าเรือ1
                    final Button bt1 = (Button) dialog.findViewById(R.id.button1);
                    bt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent button1 = new Intent(VehicleActivity.this, SchedulePhoThong2.class);
                            startActivity(button1);
                        }

                       /* {

                            Intent button3 = new Intent(VehicleActivity.this, ScheduleActivity.class);
                            startActivity(button3);
                        }*/

                    });

                    //ปุ่มท่าเรือ2
                    final Button bt2 = (Button) dialog.findViewById(R.id.button2);
                    bt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent button2 = new Intent(VehicleActivity.this, SchedulePhoThong3.class);
                            startActivity(button2);


                        }
            /* {

                            Intent button3 = new Intent(VehicleActivity.this, ScheduleActivity.class);
                            startActivity(button3);
                        }*/
                    });

                    //ปุ่มท่าเรือ3
                    final Button bt3 = (Button) dialog.findViewById(R.id.button3);
                    bt3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent button3 = new Intent(VehicleActivity.this, SchedulePhoThong1.class);
                            startActivity(button3);


                        }

                       /* {

                            Intent button3 = new Intent(VehicleActivity.this, ScheduleActivity.class);
                            startActivity(button3);
                        }*/

                    });

                }


            }
    });
    }
}