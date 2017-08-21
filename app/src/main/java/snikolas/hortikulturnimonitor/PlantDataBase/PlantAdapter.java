package snikolas.hortikulturnimonitor.PlantDataBase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import snikolas.hortikulturnimonitor.R;

/**
 * Created by Sara on 31.5.2017..
 */

public class PlantAdapter extends BaseAdapter{

    List<Plant> plants = new ArrayList<Plant>();
    Context context;
    LayoutInflater layoutInflater;

    public PlantAdapter(Context context, List<Plant> plantsList){
        this.context = context;
        this.plants = plantsList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return plants.size();
    }

    @Override
    public Object getItem(int position) {
        return plants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.plant_row, null);
            viewHolder = new ViewHolder();
            viewHolder.plantTitle = (TextView)convertView.findViewById(R.id.plant_name);
            viewHolder.plantImage = (ImageView)convertView.findViewById(R.id.plant_img);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.plantTitle.setText(plants.get(position).getTitle());
        byte[] img = plants.get(position).getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
        viewHolder.plantImage.setImageBitmap(bmp);

        viewHolder.plantImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, plants.get(position).getTitle() + "", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    static class ViewHolder{
        ImageView plantImage;
        TextView plantTitle;


    }
}
