package egat.kampeepan.kampeepanservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by 21100 on 8/7/2016.
 */
public class ServiceAdapter extends BaseAdapter{    //extend BaseAdapter -> Alt+enter - > Implement Method

    //Explicit
    private Context context;
    private String[] iconStrings, titleStrings, statusStrings, detailStrings;

    //Alt+Insert -> Constructor
    public ServiceAdapter(Context context,
                          String[] iconStrings,
                          String[] titleStrings,
                          String[] statusStrings,
                          String[] detailStrings) {
        this.context = context;
        this.iconStrings = iconStrings;
        this.titleStrings = titleStrings;
        this.statusStrings = statusStrings;
        this.detailStrings = detailStrings;
    }

    @Override
    public int getCount() {
        return iconStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.service_listview, viewGroup, false);

        //Bind Widget

        ImageView iconImageView = (ImageView) view1.findViewById(R.id.imageView8);
        TextView titleTextView = (TextView) view1.findViewById(R.id.textView9);
        ImageView statusImageView = (ImageView) view1.findViewById(R.id.imageView9);
        TextView detailTextView = (TextView) view1.findViewById(R.id.textView10);

        //Show View ต้อง add lib picasso มาก่อน
        Picasso.with(context)
                .load(iconStrings[i])
                .resize(120,120)
                .into(iconImageView);
        titleTextView.setText(titleStrings[i]);
        detailTextView.setText(detailStrings[i]);

        switch (Integer.parseInt(statusStrings[i])) {
            case 0:
                statusImageView.setImageResource(R.drawable.myfalse);
                break;
            case 1:
                statusImageView.setImageResource(R.drawable.mytrue);
                break;
        }


        return view1;
    }
}   //Main Class
