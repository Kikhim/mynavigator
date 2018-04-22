package aunyamane.mynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imgv = (ImageView) findViewById(R.id.imageView1);
        imgv.setImageResource(R.drawable.logo);
        imgv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent imgv = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(imgv);
            }
        });
    }
}

