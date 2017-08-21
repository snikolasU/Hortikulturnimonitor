package snikolas.hortikulturnimonitor.PlantDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sara on 30.5.2017..
 */

public class PlantDBOpenHelper extends SQLiteOpenHelper{

    //tablica my garden
    public static final String TABLE_GARDEN = "MyGarden";
    public static final String TABLE_STATUS = "status";


    private static final String LOGTAG = "EXPLORE";

    private static final String DATABASE_NAME = "plant.dbb";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PLANTS = "plants";
    //public static final String COLUMN_ID = "plantId";
    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_IMAGE = "image";
    public static final String FLOWER_TIME = "vrijeme";
    public static final String FLOWER_TEMP = "temperatura";
    public static final String FLOWER_SOIL = "tlo";
    public static final String FLOWER_SUN = "sunce";

    //novo
    public static final String FLOWER_SEED = "seed";
    public static final String FLOWER_WATERING = "zalijevanje";
    public static final String FLOWER_SUNLIGHT = "osuncanost";
    public static final String FLOWER_TEMPERATURE = "pogodnaTemperatura";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PLANTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_DESC + " TEXT, " +
                    COLUMN_IMAGE + " BLOB, " +
                    FLOWER_SEED + " TEXT, " +
                    FLOWER_WATERING + " TEXT, " +
                    FLOWER_SUNLIGHT + " TEXT, " +
                    FLOWER_TEMPERATURE + " TEXT " +
                    ")";

    private static final String TABLE_CREATE_GARDEN =
            "CREATE TABLE " + TABLE_GARDEN + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_DESC + " TEXT, " +
                    COLUMN_IMAGE + " BLOB, " +
                    FLOWER_TIME + " TEXT, " +
                    FLOWER_TEMP + " INTEGER DEFAULT 0, " +
                    FLOWER_SOIL + " INTEGER DEFAULT 0, " +
                    FLOWER_SUN + " INTEGER DEFAULT 0 " +
                    ")";

  /*  private static final String CREATE_STATUS =
            "CREATE TABLE " + TABLE_STATUS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_DESC + " TEXT, " +
                    FLOWER_TIME + " TEXT, " +
                    FLOWER_TEMP + " INTEGER DEFAULT 0, " +
                    FLOWER_SOIL + " INTEGER DEFAULT 0, " +
                    FLOWER_SUN + " INTEGER DEFAULT 0 " +
                    ")";*/

    public PlantDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        Log.i(LOGTAG, "Table plants has been created");

        db.execSQL(TABLE_CREATE_GARDEN);
        Log.i(LOGTAG, "Table garden has been created");

      //  db.execSQL(CREATE_STATUS);
       // Log.i(LOGTAG, "Table status has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GARDEN);
       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);

        onCreate(db);
    }
}
