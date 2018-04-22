package aunyamane.mynavigator;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class DetailActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String[] str = { "TukTuk", "Phothong", "Long tail boat","Taximeter","Car for rent","Motorcycle for rent" };

        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,str ));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                final Dialog dialog = new Dialog(DetailActivity.this);
                dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_data);
                /*Intent  intent;
                 switch (arg2){
                     case 0:
                         intent = new Intent(getApplicationContext(),MapsActivity.class);
                         startActivity(intent);
                         break;

                 }*/


                final Button buttonOK = (Button)dialog.findViewById(R.id.button4);

                buttonOK.setOnClickListener(new View.OnClickListener()

                        {
                            @Override
                            public void onClick(View v)
                            {
                                dialog.cancel();
                                Intent button4 = new Intent(DetailActivity.this, MapsActivity.class);
                                startActivity(button4);}
                            {
                                dialog.cancel();
                                Intent button3 = new Intent(DetailActivity.this, MapsActivity.class);
                                startActivity(button3);
                            }

                });

                dialog.show();



            }
        });

       /* Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent button2 = new Intent(DetailActivity.this, MapsActivity.class);
                startActivity(button2);
            }*/
        }



}
