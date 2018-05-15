package aunyamane.mynavigator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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
                        R.drawable.p4,
                        R.drawable.pai
        };

        String[] list = {"Phothong","Long tail boat","TukTuk","For Rent"};

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
                    if (i == 1)
                    {
                        final Dialog dialog2 = new Dialog(VehicleActivity.this);
                        dialog2.requestWindowFeature(dialog2.getWindow().FEATURE_NO_TITLE);
                        dialog2.setContentView(R.layout.boat_bt);
                        dialog2.show();

                        //ปุ่มท่าเรือ1
                        final Button bt1 = (Button) dialog2.findViewById(R.id.button1);
                        bt1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent button1 = new Intent(VehicleActivity.this, LinkChalong.class);
                                startActivity(button1);
                            }

                       /* {

                            Intent button3 = new Intent(VehicleActivity.this, ScheduleActivity.class);
                            startActivity(button3);
                        }*/

                        });

                        //ปุ่มท่าเรือ2
                        final Button bt2 = (Button) dialog2.findViewById(R.id.button2);
                        bt2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent button2 = new Intent(VehicleActivity.this, LinkSeaangle.class);
                                startActivity(button2);


                            }
            /* {

                            Intent button3 = new Intent(VehicleActivity.this, ScheduleActivity.class);
                            startActivity(button3);
                        }*/
                        });

                        //ปุ่มท่าเรือ3
                        final Button bt3 = (Button) dialog2.findViewById(R.id.button3);
                        bt3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent button3 = new Intent(VehicleActivity.this, LinkRassada.class);
                                startActivity(button3);


                            }

                       /* {

                            Intent button3 = new Intent(VehicleActivity.this, ScheduleActivity.class);
                            startActivity(button3);
                        }*/

                        });

                    }
                if (i == 2)
                {
                    final Dialog dialog2 = new Dialog(VehicleActivity.this);
                    dialog2.requestWindowFeature(dialog2.getWindow().FEATURE_NO_TITLE);
                    dialog2.setContentView(R.layout.boat_bt);
                    dialog2.show();

                    //ปุ่มตุ๊กๆ
                    final Button bt1 = (Button) dialog2.findViewById(R.id.button1);
                    bt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent button1 = new Intent(VehicleActivity.this, SchedulePhoThong2.class);
                            startActivity(button1);
                        }


                    });

                    //ปุ่มท่าเรือ2
                    final Button bt2 = (Button) dialog2.findViewById(R.id.button2);
                    bt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent button2 = new Intent(VehicleActivity.this, SchedulePhoThong3.class);
                            startActivity(button2);


                        }

                    });

                }
                if (i == 3)
                {
                    final Dialog dialog2 = new Dialog(VehicleActivity.this);
                    dialog2.requestWindowFeature(dialog2.getWindow().FEATURE_NO_TITLE);
                    dialog2.setContentView(R.layout.rent_bt);
                    dialog2.show();

                    //ปุ่มมอไซด์
                    final Button bt1 = (Button) dialog2.findViewById(R.id.button1);
                    bt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent rent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ต้นเช่ารถมอเตอร์ไซค์ภูเก็ต.com/"));
                            startActivity(rent1);
                        }

                    });

                    //ปุ่มรถเช่า
                    final Button bt2 = (Button) dialog2.findViewById(R.id.button2);
                    bt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent rent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/phuketacar/"));
                            startActivity(rent2);


                        }
                    });

                    //ปุ่มแท็กซี่
                    final Button bt3 = (Button) dialog2.findViewById(R.id.button3);
                    bt3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent rent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/NickyPhuketTaxi/?ref=br_rs"));
                            startActivity(rent3);


                        }

                    });

                }

            }
    });
    }
}