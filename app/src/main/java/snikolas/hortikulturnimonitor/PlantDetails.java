package snikolas.hortikulturnimonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Sara on 26.6.2017..
 */

public class PlantDetails extends Activity {


    TextView details, detailHeader, sunTxt, soilTxt, tempTxt, timeTxt, env;
    String name, desc, time;
    Long id;
    int temp, sun,soil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_details);

        //details = (TextView)findViewById(R.id.detaild);
        detailHeader = (TextView)findViewById(R.id.details_text);
        sunTxt = (TextView)findViewById(R.id.sun);
        soilTxt = (TextView)findViewById(R.id.soil);
        tempTxt = (TextView)findViewById(R.id.temp);
        timeTxt = (TextView)findViewById(R.id.datum);
        env =(TextView)findViewById(R.id.env);

        Intent myIntent = getIntent();
        name = myIntent.getStringExtra("Name");
        id = myIntent.getLongExtra("Id",0);
        desc = myIntent.getStringExtra("description");
        time = myIntent.getStringExtra("time");
        temp = myIntent.getIntExtra("temp", 0);
        sun = myIntent.getIntExtra("sun",0);
        soil = myIntent.getIntExtra("soil",0);

        if (desc.equals("outdoor")){
            desc = "Sadnja biljke vani";
        }else if (desc.equals("indoor")){
            desc = "Sadnja biljke unutra";
        }

        detailHeader.setText(name);
        env.setText(" "+String.valueOf(desc));
        sunTxt.setText(" " +String.valueOf(sun) + "%");
        soilTxt.setText(" "+ String.valueOf(soil) + "%");
        tempTxt.setText(" "+String.valueOf(temp) + "°C");
        timeTxt.setText(" "+String.valueOf(time));


    }

}
