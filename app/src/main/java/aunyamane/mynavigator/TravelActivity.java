package aunyamane.mynavigator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


public class TravelActivity extends AppCompatActivity {

    GridView gridview;

    String[] values = {
            "\"ไทยเที่ยวไทยถ้าไทยไม่เที่ยวไทยแล้วใครจะเที่ยวไทย\" จุดชมวิวแหลมกระทิงเป็นจุดชมพระอาทิตย์ตกได้สวยอีกจุดหนึ่งของภูเก็ต",
            "พระพุทธมิ่งมงคลเอกนาคคีรีหรือพระใหญ่ภูเก็ตประดิษฐานอยู่ที่เขานาคเกิดเลยจากวัดฉลองไปไม่ไกลเป็นสถานที่ท่องเที่ยวสำคัญของจังหวัดหวัดภูเก็ตในกลุ่มนักท่องเที่ยว",
            "ในบรรดาเกาะบริวารของภูเก็ต\"เกาะไม้ท่อน\" เป็นหนึ่งในเกาะที่ครบเครื่องที่สุดเลยก็ว่าได้เพราะมีทั้งหาดทรายสวยน้ำที่ใสไม่แพ้สิมิลัน ",
            "  \"UNSEEN PHUKET\" ครั้งหนึ่งในชีวิตต้องลองไปสัมผัสเครื่องบินแลนดิ้งระยะประชิดที่หาดไม้ขาว จังหวัดภูเก็ต",
            "อีกหนึ่งเสน่ห์ของภูเก็ตจังหวัดที่เที่ยวได้ทั้งปีไมได้มีดีแค่ทะเลกับความเก๋าในย่านเมืองเก่าพร้อมด้วยเรื่องราวที่น่าค้นหาทำให้ภูเก็ตเป็นเมืองสุดชิคอีกหนึ่งที่เมื่อเราได้มาเจอ\"ผนังมีชีวิต\" "
    };
    int[] pics = {
            R.drawable.krathing,
            R.drawable.bigbudha,
            R.drawable.maithon,
            R.drawable.maikhaw,
            R.drawable.yanmung
    };


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.travel_activity);

            gridview = (GridView) findViewById(R.id.grid);

            TravelAdapter travelAdapter = new TravelAdapter(this,values,pics);

            gridview.setAdapter(travelAdapter);
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i==0){
                        Intent linking = new Intent(Intent.ACTION_VIEW, Uri.parse("https://travel.kapook.com/view172861.html"));
                        startActivity(linking);
                        }
                    else if(i==1){
                        Intent linking = new Intent(Intent.ACTION_VIEW, Uri.parse("http://thaisign1.blogspot.com/2016/03/big-buddha-phuket.html"));
                        startActivity(linking);
                    }
                    else if(i==2){
                        Intent linking = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.9mot.com/2017/02/review-one-day-trip-maiton-island-with-maiton-resort/"));
                        startActivity(linking);
                    }
                    else {
                        Intent linking = new Intent(Intent.ACTION_VIEW, Uri.parse("https://travel.kapook.com/view140495.html"));
                        startActivity(linking);
                    }




                }
            });






            }
        }