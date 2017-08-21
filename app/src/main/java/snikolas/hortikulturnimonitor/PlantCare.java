package snikolas.hortikulturnimonitor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import snikolas.hortikulturnimonitor.R;

/**
 * Created by Sara on 26.6.2017..
 */

public class PlantCare extends Activity {

    TextView sunTxt, soilTxt, tempTxt, header, descTxt;
    String name, opis, sunlight, watering, temperature;
    ImageView image;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_care);

        sunTxt = (TextView)findViewById(R.id.sun);
        soilTxt = (TextView)findViewById(R.id.soil);
        tempTxt = (TextView)findViewById(R.id.temp);
        descTxt = (TextView)findViewById(R.id.opis);
        header = (TextView)findViewById(R.id.header_care_text);
        image = (ImageView)findViewById(R.id.main_image);

        if (getIntent().hasExtra("image")){
            bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("image"),0,getIntent().getByteArrayExtra("image").length);
            name = getIntent().getStringExtra("name");
            opis = getIntent().getStringExtra("opis");
            watering = getIntent().getStringExtra("zalijevanje");
            sunlight = getIntent().getStringExtra("suncanje");
            temperature = getIntent().getStringExtra("temperatura");
        }

        image.setImageBitmap(bitmap);
        sunTxt.setText(sunlight);
        soilTxt.setText(watering);
        tempTxt.setText(temperature);
        descTxt.setText(opis);
        header.setText(header.getText() + name);
    }
}
