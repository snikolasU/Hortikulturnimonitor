package snikolas.hortikulturnimonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import snikolas.hortikulturnimonitor.PlantDataBase.Plant;
import snikolas.hortikulturnimonitor.PlantDataBase.PlantAdapter;
import snikolas.hortikulturnimonitor.PlantDataBase.PlantDBOpenHelper;
import snikolas.hortikulturnimonitor.PlantDataBase.PlantsDataSource;

/**
 * Created by Sara on 30.5.2017..
 */

public class PLantDB extends Activity{

    public static final String LOGTAG="EXPLORE";
    PlantsDataSource dataSource;

    ListView plantList;
    PlantDBOpenHelper plantDBOpenHelper;
    SQLiteDatabase database;
    Context context;
    private SharedPreferences sharedPreferences;
    boolean fromNewPlant;
    ImageView homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_db);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        context = this;

        plantList = (ListView)findViewById(R.id.plants_list);
        dataSource = new PlantsDataSource(this);
        dataSource.open();

        Intent myIntent = getIntent();
        fromNewPlant = myIntent.getBooleanExtra("fromNewPlant", false);
        homeBtn = (ImageView)findViewById(R.id.home_btn);


        plantDBOpenHelper = new PlantDBOpenHelper(this);
        database = plantDBOpenHelper.getWritableDatabase();

        if (!sharedPreferences.getBoolean("dataCreated", false)) {
            createData();
        }

        final List<Plant> plants = dataSource.dajBiljke();

      plantList.setAdapter(new PlantAdapter(context, plants));
       // if (fromNewPlant){
            plantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(context, plants.get(position).getId() + "", Toast.LENGTH_SHORT).show();
                    //if (fromNewPlant.equals("newPlant")){
                    if (fromNewPlant){
                        Intent goBack = new Intent(PLantDB.this, NewPlant.class);
                        goBack.putExtra("plantID", plants.get(position).getId());
                        goBack.putExtra("image", plants.get(position).getImage());
                        goBack.putExtra("name", plants.get(position).getTitle());
                        startActivity(goBack);
                        finish();
                    }else {
                        Intent goCare = new Intent(PLantDB.this, PlantCare.class);
                        goCare.putExtra("name", plants.get(position).getTitle());
                        goCare.putExtra("opis", plants.get(position).getSeed());
                        goCare.putExtra("zalijevanje", plants.get(position).getWatering());
                        goCare.putExtra("suncanje", plants.get(position).getSunlight());
                        goCare.putExtra("temperatura", plants.get(position).getBestTemperature());
                        goCare.putExtra("image", plants.get(position).getImage());
                        startActivity(goCare);
                    }
                }
            });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PLantDB.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void createData() {
        Plant plant = new Plant();
        plant.setTitle("Rajčica");
        plant.setDescription("Solanum lycoperscium");
        Drawable d = getResources().getDrawable(R.drawable.rajcica);
        byte[] byteArray = createByte(d);
        plant.setImage(byteArray);
        plant.setSeed("Posijajte sjeme u zatvorenom prostoru 6-10 tjedana prije posljednjeg mraza, klija u 1-2 tjedna na 18-21 °C.");
        plant.setWatering("2/5");
        plant.setSunlight("4/5");
        plant.setBestTemperature("18-21 °C");
        //plant = dataSource.create(plant);
        dataSource.create(plant);

        Log.i(LOGTAG, "Tour created with id " + plant.getId());

        plant = new Plant();
        plant.setTitle("Maslina");
        plant.setDescription("Maslina");
        d = getResources().getDrawable(R.drawable.maslina);
        byteArray = createByte(d);
        plant.setImage(byteArray);
        plant.setSeed("Posijajte sjeme u zatvorenom prostoru 6-10 tjedana prije posljednjeg mraza, klija u 1-2 tjedna na 18-21 °C.");
        plant.setWatering("2/5");
        plant.setSunlight("4/5");
        plant.setBestTemperature("18-21 °C");
        dataSource.create(plant);

        plant = new Plant();
        plant.setTitle("Orhideja");
        plant.setDescription("Orhideja");
        d = getResources().getDrawable(R.drawable.orhideja);
        byteArray = createByte(d);
        plant.setImage(byteArray);
        plant.setSeed("Posijajte sjeme u zatvorenom prostoru 6-10 tjedana prije posljednjeg mraza, klija u 1-2 tjedna na 18-21 °C.");
        plant.setWatering("1/5");
        plant.setSunlight("1/5");
        plant.setBestTemperature("18-21 °C");
        dataSource.create(plant);

        plant = new Plant();
        plant.setTitle("Paprika");
        plant.setDescription("Paprika");
        d = getResources().getDrawable(R.drawable.paprika);
        byteArray = createByte(d);
        plant.setImage(byteArray);
        plant.setSeed("Posijajte sjeme u zatvorenom prostoru 6-10 tjedana prije posljednjeg mraza, klija u 1-2 tjedna na 18-21 °C.");
        plant.setWatering("2/5");
        plant.setSunlight("3/5");
        plant.setBestTemperature("18-21 °C");
        dataSource.create(plant);

        plant = new Plant();
        plant.setTitle("Ljubičica");
        plant.setDescription("Ljubicica");
        d = getResources().getDrawable(R.drawable.ljubicica);
        byteArray = createByte(d);
        plant.setImage(byteArray);
        plant.setSeed("Posijajte sjeme u zatvorenom prostoru 6-10 tjedana prije posljednjeg mraza, klija u 1-2 tjedna na 18-21 °C.");
        plant.setWatering("2/5");
        plant.setSunlight("4/5");
        plant.setBestTemperature("18-21 °C");
        dataSource.create(plant);

        plant = new Plant();
        plant.setTitle("Krastavac");
        plant.setDescription("Krastavac");
        d = getResources().getDrawable(R.drawable.krastavac);
        byteArray = createByte(d);
        plant.setImage(byteArray);
        plant.setSeed("Posijajte sjeme u zatvorenom prostoru 6-10 tjedana prije posljednjeg mraza, klija u 1-2 tjedna na 18-21 °C.");
        plant.setWatering("2/5");
        plant.setSunlight("3/5");
        plant.setBestTemperature("18-21 °C");
        dataSource.create(plant);

        plant = new Plant();
        plant.setTitle("Suncokret");
        plant.setDescription("Suncokret");
        d = getResources().getDrawable(R.drawable.suncokret);
        byteArray = createByte(d);
        plant.setSeed("Posijajte sjeme u zatvorenom prostoru 6-10 tjedana prije posljednjeg mraza, klija u 1-2 tjedna na 18-21 °C.");
        plant.setWatering("2/5");
        plant.setSunlight("4/5");
        plant.setBestTemperature("18-21 °C");
        plant.setImage(byteArray);

        dataSource.create(plant);

        sharedPreferences.edit().putBoolean("dataCreated", true).apply();

    }

    public byte[] createByte (Drawable drawable){
        Bitmap bmp = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}

