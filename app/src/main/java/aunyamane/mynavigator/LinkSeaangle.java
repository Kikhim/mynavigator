package aunyamane.mynavigator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class LinkSeaangle extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seaangle);
    }

    public void web(View v)
    {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.phuketferry.com/th/"));
        startActivity(webIntent);
    }
    public void facebook(View v)
    {
        Intent fbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/seaangelthailand/"));
        startActivity(fbIntent);
    }


}



