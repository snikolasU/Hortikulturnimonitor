package snikolas.hortikulturnimonitor;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import snikolas.hortikulturnimonitor.PlantDataBase.Plant;
import snikolas.hortikulturnimonitor.PlantDataBase.PlantAdapter;
import snikolas.hortikulturnimonitor.PlantDataBase.PlantsDataSource;

/**
 * Created by Sara on 26.6.2017..
 */

public class History extends Activity {

    PlantsDataSource dataSource;
    ImageView homeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        dataSource = new PlantsDataSource(this);
        dataSource.open();

        ListView historyLv = (ListView) findViewById(R.id.lv_flowers);
        homeBtn = (ImageView)findViewById(R.id.home_btn);

        final List<Plant> plants = dataSource.getGarden();
        dataSource.close();
        List<String> plantList = new ArrayList<>();

        int index = 1;
        for (Plant plant : plants){
            plantList.add(index++ +". " + plant.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, plantList);
        historyLv.setAdapter(adapter);

        historyLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(History.this, PlantDetails.class);
                intent.putExtra("Name", plants.get(position).getTitle());
                intent.putExtra("Id", plants.get(position).getId());
                intent.putExtra("description", plants.get(position).getDescription());
                intent.putExtra("time", plants.get(position).getTime());
                intent.putExtra("temp", plants.get(position).getTemperature());
                intent.putExtra("sun", plants.get(position).getSun());
                intent.putExtra("soil", plants.get(position).getSoil());
                startActivity(intent);
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
