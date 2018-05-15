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


public class LinkChalong extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chalong);
    }

    public void web3(View v) {
        Intent chalongbayIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://phuket.holidaythai.com/2016/01/aow-chalong-phuket.html"));
        startActivity(chalongbayIntent);
    }
    public void cont2(View v) {
        Intent cont2Intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/pages/%E0%B8%AA%E0%B8%B0%E0%B8%9E%E0%B8%B2%E0%B8%99%E0%B8%AD%E0%B9%88%E0%B8%B2%E0%B8%A7%E0%B8%89%E0%B8%A5%E0%B8%AD%E0%B8%87/350848144958452?rf=448727581846192"));
        startActivity(cont2Intent);
    }
}



