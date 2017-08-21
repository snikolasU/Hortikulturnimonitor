package snikolas.hortikulturnimonitor;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import snikolas.hortikulturnimonitor.PlantDataBase.Plant;
import snikolas.hortikulturnimonitor.PlantDataBase.PlantAdapter;
import snikolas.hortikulturnimonitor.PlantDataBase.PlantDBOpenHelper;
import snikolas.hortikulturnimonitor.PlantDataBase.PlantsDataSource;
import snikolas.hortikulturnimonitor.Utility.FlowerPowerConstants;

/**
 * Created by Sara on 24.6.2017..
 */

public class MyGarden extends Activity {

    ListView plantList;
    PlantsDataSource dataSource;
    PlantDBOpenHelper plantDBOpenHelper;
    SQLiteDatabase database;
    Context context;
    ImageView addNewPlant;
    ImageView homeBtn;

    private boolean mScanning;
    private BluetoothAdapter mBluetoothAdapter;
    private static final long SCAN_PERIOD = 10000;
    private static final String DEVICE_NAME = "flower power";
    private static final int REQUEST_ENABLE_BT_START = 1;
    private static final int REQUEST_ENABLE_BT_ABOUT = 2;
    String selectedPlantName;
    private long plant_id;
    private String environment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_garden);

        context = this;

        dataSource = new PlantsDataSource(this);
        dataSource.open();
        plantDBOpenHelper = new PlantDBOpenHelper(this);
        database = plantDBOpenHelper.getWritableDatabase();

        plantList = (ListView)findViewById(R.id.plants_list);
        addNewPlant = (ImageView)findViewById(R.id.add_new_plant);
        homeBtn = (ImageView)findViewById(R.id.home_btn);

        final List<Plant> plants = dataSource.getGarden();
        plantList.setAdapter(new PlantAdapter(context, plants));

        plantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPlantName = plants.get(position).getTitle();
                plant_id = plants.get(position).getId();
                environment = plants.get(position).getDescription();
                Toast.makeText(context, plants.get(position).getId() + "", Toast.LENGTH_SHORT).show();
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT_START);

                } else {
                    scanLeDevice(true);
                }
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyGarden.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
        {
            Toast.makeText(getApplicationContext(), R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (null == mBluetoothAdapter) {
            Toast.makeText(getApplicationContext(), R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public void addNewPlant(View v){
        Intent intent = new Intent(MyGarden.this, NewPlant.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_ENABLE_BT_START) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                scanLeDevice(true);
            } else {
                finish();
            }
        }

    }
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }
    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (device.getName().toLowerCase().contains(DEVICE_NAME)) {
                            Intent intent = new Intent(MyGarden.this, MyPlantLive.class);
                            FlowerPowerConstants.DEVICE_ADDRESS = device.getAddress();
                            intent.putExtra(FlowerPowerConstants.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                            intent.putExtra("name", selectedPlantName);
                            intent.putExtra("plant_id", plant_id);
                            intent.putExtra("environment", environment);
                            if (mScanning) {
                                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                                mScanning = false;
                            }
                            startActivity(intent);
                        }
                    }catch (Exception e){
                        Log.e("Exception", e.toString());
                    }

                }
            });
        }
    };

}
