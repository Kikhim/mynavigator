package aunyamane.mynavigator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TravelAdapter extends BaseAdapter {

    Context context;
    private final String[] values;
    private final int[] pics;
    View view;
    LayoutInflater layoutInflater;

   

    public TravelAdapter(Context context, String[] values, int[] pics)
    {
        this.context = context;
        this.values = values;
        this.pics = pics;
    }
    @Override
    public int getCount()
    {

        return values.length;
    }

    public Object getItem(int position)
    {
        return null;
    }

    public long getItemId(int position)
    {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(convertView == null)
        {
            view = new View(context);
            view = layoutInflater.inflate(R.layout.travel_adapter,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.image);
            TextView textView = (TextView)view.findViewById(R.id.text1);
            imageView.setImageResource(Integer.parseInt(String.valueOf(pics[position])));
            textView.setText(values[position]);
        }
        return view;


    }





}