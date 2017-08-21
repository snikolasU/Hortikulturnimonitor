package snikolas.hortikulturnimonitor.PlantDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sara on 30.5.2017..
 */

public class PlantsDataSource {

    public static final String LOGTAG="EXPLORE";

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;
    PlantDBOpenHelper plantDBOpenHelper;

    private static final String[] allColumns = {
            PlantDBOpenHelper.COLUMN_ID,
            PlantDBOpenHelper.COLUMN_TITLE,
            PlantDBOpenHelper.COLUMN_DESC,
            PlantDBOpenHelper.COLUMN_IMAGE};

    public PlantsDataSource(Context context) {
        dbhelper = new PlantDBOpenHelper(context);
    }

    public void open() {
        Log.i(LOGTAG, "Database opened");
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database closed");
        dbhelper.close();
    }

    /*public Plant create(Plant plant) {
        ContentValues values = new ContentValues();
        values.put(PlantDBOpenHelper.COLUMN_TITLE, plant.getTitle());
        values.put(PlantDBOpenHelper.COLUMN_DESC, plant.getDescription());
        values.put(PlantDBOpenHelper.COLUMN_IMAGE, plant.getImage());
        long insertid = database.insert(PlantDBOpenHelper.TABLE_PLANTS, null, values);
        plant.setId(insertid);
        return plant;
    }*/
    public Plant create(Plant plant) {
        ContentValues values = new ContentValues();
        values.put(PlantDBOpenHelper.COLUMN_TITLE, plant.getTitle());
        values.put(PlantDBOpenHelper.COLUMN_DESC, plant.getDescription());
        values.put(PlantDBOpenHelper.COLUMN_IMAGE, plant.getImage());
        values.put(PlantDBOpenHelper.FLOWER_SEED, plant.getSeed());
        values.put(PlantDBOpenHelper.FLOWER_WATERING, plant.getWatering());
        values.put(PlantDBOpenHelper.FLOWER_SUNLIGHT, plant.getSunlight());
        values.put(PlantDBOpenHelper.FLOWER_TEMPERATURE, plant.getBestTemperature());

        long insertid = database.insert(PlantDBOpenHelper.TABLE_PLANTS, null, values);
        plant.setId(insertid);
        return plant;
    }

    public Plant createGarden (Plant plant) {
        ContentValues values = new ContentValues();
        values.put(PlantDBOpenHelper.COLUMN_TITLE, plant.getTitle());
        values.put(PlantDBOpenHelper.COLUMN_DESC, plant.getDescription());
        values.put(PlantDBOpenHelper.COLUMN_IMAGE, plant.getImage());
        long insertid = database.insert(PlantDBOpenHelper.TABLE_GARDEN, null, values);
        plant.setId(insertid);
        return plant;
    }



    public Plant updateGardenStatus (Plant plant, long plantID) {
        ContentValues values = new ContentValues();
        values.put(PlantDBOpenHelper.FLOWER_TIME, plant.getTime());
        values.put(PlantDBOpenHelper.FLOWER_TEMP, plant.getTemperature());
        values.put(PlantDBOpenHelper.FLOWER_SOIL, plant.getSoil());
        values.put(PlantDBOpenHelper.FLOWER_SUN, plant.getSun());
        database.update(PlantDBOpenHelper.TABLE_GARDEN, values, "_id="+plantID, null);
        return plant;
    }

    public List<Plant> findAll() {
        List<Plant> plants = new ArrayList<Plant>();

        Cursor cursor = database.query(PlantDBOpenHelper.TABLE_PLANTS, allColumns,
                null, null, null, null, null);

        Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Plant plant = new Plant();
                plant.setId(cursor.getInt(cursor.getColumnIndex(PlantDBOpenHelper.COLUMN_ID)));
                plant.setTitle(cursor.getString(cursor.getColumnIndex(PlantDBOpenHelper.COLUMN_TITLE)));
                plant.setDescription(cursor.getString(cursor.getColumnIndex(PlantDBOpenHelper.COLUMN_DESC)));
                plant.setImage(cursor.getBlob(cursor.getColumnIndex(PlantDBOpenHelper.COLUMN_IMAGE)));
                plants.add(plant);
            }
        }
        return plants;
    }

    public Cursor queryName() {
        String[] cols = { plantDBOpenHelper.COLUMN_ID, plantDBOpenHelper.COLUMN_TITLE, plantDBOpenHelper.COLUMN_DESC,
        plantDBOpenHelper.COLUMN_IMAGE};
        open();
        Cursor c = database.query(plantDBOpenHelper.TABLE_PLANTS, cols, null,
                null, null, null, null);

        return c;

    }

    public List<Plant> dajBiljke(){
        List <Plant> plants = new ArrayList<Plant>();
        Cursor cursor = database.rawQuery("SELECT _id, image, title, seed, zalijevanje, osuncanost, pogodnaTemperatura from plants", null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    byte[] img = cursor.getBlob(cursor.getColumnIndex("image"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    long id = cursor.getInt(cursor.getColumnIndex("_id"));

                    String seed = cursor.getString(cursor.getColumnIndex("seed"));
                    String watering = cursor.getString(cursor.getColumnIndex("zalijevanje"));
                    String sunlight = cursor.getString(cursor.getColumnIndex("osuncanost"));
                    String temperature = cursor.getString(cursor.getColumnIndex("pogodnaTemperatura"));

                    Plant plant = new Plant();
                    plant.setId(id);
                    plant.setTitle(title);
                    plant.setImage(img);

                    //novo
                    plant.setSeed(seed);
                    plant.setWatering(watering);
                    plant.setSunlight(sunlight);
                    plant.setBestTemperature(temperature);

                    plants.add(plant);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }

        return plants;
    }

    public List<Plant> getGarden(){
        List <Plant> plants = new ArrayList<Plant>();
        //Cursor cursor = database.rawQuery("SELECT _id, image, title from MyGarden", null);
        Cursor cursor = database.rawQuery("SELECT _id, image, title, description, vrijeme, temperatura, tlo, sunce from MyGarden", null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    byte[] img = cursor.getBlob(cursor.getColumnIndex("image"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    long id = cursor.getInt(cursor.getColumnIndex("_id"));

                    //novo
                    String desc = cursor.getString(cursor.getColumnIndex("description"));
                    String time = cursor.getString(cursor.getColumnIndex("vrijeme"));
                    int sun = cursor.getInt(cursor.getColumnIndex("sunce"));
                    int soil = cursor.getInt(cursor.getColumnIndex("tlo"));
                    int temp = cursor.getInt(cursor.getColumnIndex("temperatura"));


                    Plant plant = new Plant();
                    plant.setId(id);
                    plant.setTitle(title);
                    plant.setImage(img);

                    //novo
                    plant.setDescription(desc);
                    plant.setTime(time);
                    plant.setSun(sun);
                    plant.setSoil(soil);
                    plant.setTemperature(temp);

                    plants.add(plant);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return plants;
    }

}
