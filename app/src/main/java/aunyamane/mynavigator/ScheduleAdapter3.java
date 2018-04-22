package aunyamane.mynavigator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.TextView;

public class ScheduleAdapter3 extends BaseAdapter {

    private Context mContext;
    private String []  round, time;

    public ScheduleAdapter3(Context context, String[] round, String[] time) {
        mContext = context;
        this.round = round;
        this.time = time;
    }

    public int getCount() {
        return round.length;
    }

    public Object getItem(int position) {

        return null;
    }

    public long getItemId(int position) {

        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.schedule_list, parent, false);

        TextView textView1 = (TextView) view.findViewById(R.id.textView2);
        textView1.setText(round[position]);

        TextView textView2 = (TextView) view.findViewById(R.id.textView3);
        textView2.setText(time[position]);

        return view;
    }
}





