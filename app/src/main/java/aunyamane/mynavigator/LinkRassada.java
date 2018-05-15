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


public class LinkRassada extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rassada);
    }

    public void web2(View v) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.phuketferry.com/th/pier-information.html?pier=rassada-pier"));
        startActivity(webIntent);
    }
    public void cont(View v) {
        Intent conIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/pages/%E0%B8%97%E0%B9%88%E0%B8%B2%E0%B9%80%E0%B8%A3%E0%B8%B7%E0%B8%AD%E0%B8%A3%E0%B8%B1%E0%B8%A9%E0%B8%8E%E0%B8%B2-%E0%B8%A0%E0%B8%B9%E0%B9%80%E0%B8%81%E0%B9%87%E0%B8%95/207955019222807"));
        startActivity(conIntent);
    }

}



