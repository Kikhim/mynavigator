package aunyamane.mynavigator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class TravelAdapter extends BaseAdapter {

    private Context mContext;

    public TravelAdapter(Context context) {
        mContext = context;
    }

    public int getCount() {
        return 4;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 700));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(30, 100, 30, 100);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

    if(position == 0 )
        imageView.setImageResource(R.drawable.temple);
    else if (position == 1 )
        imageView.setImageResource(R.drawable.pic);
    else if (position == 2)
        imageView.setImageResource(R.drawable.icon1);
    else
        imageView.setImageResource(R.drawable.pic3);
    return imageView;

    }


}