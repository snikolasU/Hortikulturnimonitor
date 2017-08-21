package snikolas.hortikulturnimonitor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import snikolas.hortikulturnimonitor.PlantDataBase.Plant;
import snikolas.hortikulturnimonitor.PlantDataBase.PlantsDataSource;

/**
 * Created by Sara on 24.6.2017..
 */

public class NewPlant extends Activity {

    TextView header;
    LinearLayout selectPlantDB;
    EditText plantName;
    ImageView indoor, outdoor;
    ImageView plantImg;
    TextView plantDBname;
    Button createPlant;
    private boolean flag = false;
    private String environment;
    Bitmap bitmap;
    PlantsDataSource dataSource;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_plant);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        dataSource = new PlantsDataSource(this);
        dataSource.open();

        environment = "";
        header = (TextView) findViewById(R.id.new_plant_txt);
        //na klik ide u bazu za odabrati biljku
        selectPlantDB = (LinearLayout) findViewById(R.id.select_plant_row);
        plantName = (EditText)findViewById(R.id.plant_name_box);
        indoor = (ImageView)findViewById(R.id.indoor_img);
        outdoor = (ImageView)findViewById(R.id.outdoor_img);
        plantImg = (ImageView) findViewById(R.id.new_plant_img);
        plantDBname = (TextView)findViewById(R.id.plant_db_name);
        createPlant = (Button)findViewById(R.id.create_plant);

        indoor.setClickable(true);
        outdoor.setClickable(true);

        if (getIntent().hasExtra("image")){
            flag = true;
            bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("image"),0,getIntent().getByteArrayExtra("image").length);
            plantImg.setImageBitmap(bitmap);
            String plantNameDB = getIntent().getStringExtra("name");
            plantDBname.setText(plantNameDB);

        }

        selectPlantDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewPlant.this, PLantDB.class);
                //intent.putExtra("fromNewPlant", "newPlant");
                intent.putExtra("fromNewPlant", true);
                startActivity(intent);
                finish();
            }
        });
        indoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                environment = "indoor";
            }
        });
        outdoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                environment = "outdoor";
            }
        });

        createPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plant plant = new Plant();
                plant.setTitle(plantName.getText().toString());
                plant.setDescription(environment);
                Log.e("desc env: ", environment);
                Drawable d = plantImg.getDrawable();
                Bitmap bmp = ((BitmapDrawable)d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                plant.setImage(byteArray);

                dataSource.createGarden(plant);
                Intent intent = new Intent(NewPlant.this, MyGarden.class);
                startActivity(intent);
                sharedPreferences.edit().putBoolean("gardenCreated", true).apply();
                dataSource.close();
                finish();
            }
        });

    }


}
