package aunyamane.mynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        final ImageView im = (ImageView) findViewById(R.id.imageView2);
        im.setImageResource(R.drawable.sun);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent img = new Intent(HomeActivity.this,TravelActivity.class);
                startActivity(img);
            }
        });

        final ImageView im2 = (ImageView) findViewById(R.id.imageView3);
        im2.setImageResource(R.drawable.car2);
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent img2 = new Intent(HomeActivity.this,VehicleActivity.class);
                startActivity(img2);
            }
        });

        final ImageView im3 = (ImageView) findViewById(R.id.imageView4);
        im3.setImageResource(R.drawable.search4);
        im3.setOnClickListener(new View .OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent img2 = new Intent(HomeActivity.this,RequireActivity.class);
                startActivity(img2);
            }
        });
    }



}
