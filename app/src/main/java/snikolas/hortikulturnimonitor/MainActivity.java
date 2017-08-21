package snikolas.hortikulturnimonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import snikolas.hortikulturnimonitor.PlantDataBase.Plant;
import snikolas.hortikulturnimonitor.PlantDataBase.PlantsDataSource;
import snikolas.hortikulturnimonitor.Utility.AndroidPermissions;

public class MainActivity extends Activity {

    private Button startGarden, plantDB, about, povijest;
    private SharedPreferences sharedPreferences;
    Intent intent;

    private boolean flag;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        context=this;
        startGarden = (Button)findViewById(R.id.start_garden_btn);
        plantDB = (Button)findViewById(R.id.goto_plantDB_btn);
        about = (Button)findViewById(R.id.about_btn);
        povijest = (Button)findViewById(R.id.goto_povijest_btn);

        AndroidPermissions ap = new AndroidPermissions(MainActivity.this);
        ap.bluetoothPermission();

        if (sharedPreferences.getBoolean("gardenCreated", false)) {
            //micem gumb za ici na new plant i stavljam my garden, dodaj u my garden gumb za new plant
            startGarden.setText("Moj vrt");
            intent = new Intent(MainActivity.this, MyGarden.class);
            flag = true;
        }else {
            intent = new Intent(MainActivity.this, NewPlant.class);
        }


        startGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        plantDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PLantDB.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
            }
        });
        povijest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getBoolean("detailsSaved", false)) {
                    Intent intent = new Intent(MainActivity.this, History.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(context, getResources().getString(R.string.nema_detalja), Toast.LENGTH_LONG).show();

                }
            }
        });
    }


}
