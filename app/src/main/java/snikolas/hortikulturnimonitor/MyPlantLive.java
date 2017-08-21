package snikolas.hortikulturnimonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import io.sule.gaugelibrary.GaugeView;
import snikolas.hortikulturnimonitor.PlantDataBase.Plant;
import snikolas.hortikulturnimonitor.PlantDataBase.PlantsDataSource;
import snikolas.hortikulturnimonitor.Utility.FlowerPowerConstants;
import snikolas.hortikulturnimonitor.model.FlowerPower;
import snikolas.hortikulturnimonitor.service.BluetoothDeviceModel;
import snikolas.hortikulturnimonitor.service.FlowerPowerServiceManager;
import snikolas.hortikulturnimonitor.service.IFlowerPowerDevice;
import snikolas.hortikulturnimonitor.service.IFlowerPowerServiceListener;

/**
 * Created by Sara on 24.6.2017..
 */

public class MyPlantLive extends Activity {

    private FlowerPowerServiceManager serviceManager;
    private GaugeView mTempGauge, mSoilGauge, mSunGauge, mBatteryGauge;
    private int temp, soil, sun;
    String nameFromIntent;
    private long plantId;
    private String environment;
    TextView nameLive;

    Button spremiBtn;
    PlantsDataSource dataSource;
    Context context;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_live);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        context = this;
        dataSource = new PlantsDataSource(this);
        dataSource.open();

        spremiBtn = (Button)findViewById(R.id.btn_spremi);
        nameLive = (TextView)findViewById(R.id.name_live);
        mTempGauge = (GaugeView) findViewById(R.id.temperature);
        mSoilGauge = (GaugeView) findViewById(R.id.soil);
        mSunGauge = (GaugeView) findViewById(R.id.sun);
        mBatteryGauge = (GaugeView) findViewById(R.id.battery);
        mTempGauge.setTargetValue(0);
        mSoilGauge.setTargetValue(0);
        mSunGauge.setTargetValue(0);
        mBatteryGauge.setTargetValue(0);

        Intent myIntent = getIntent();
        nameFromIntent = myIntent.getStringExtra("name");
        plantId = myIntent.getLongExtra("plant_id",0);
        environment = myIntent.getStringExtra("environment");
        nameLive.setText(nameFromIntent);

        String deviceAddress = getIntent().getStringExtra(FlowerPowerConstants.EXTRAS_DEVICE_ADDRESS);
        serviceManager = FlowerPowerServiceManager.getInstance(deviceAddress, getApplicationContext());
        IFlowerPowerServiceListener serviceListener = new IFlowerPowerServiceListener() {

            @Override
            public void serviceConnected() {
                Log.i(FlowerPowerConstants.TAG, "serviceListener: service connected");
            }

            @Override
            public void serviceDisconnected() {
                Log.i(FlowerPowerConstants.TAG, "serviceListener: service disconnected");
            }

            @Override
            public void serviceFailed(RuntimeException extra) {
                Log.i(FlowerPowerConstants.TAG, "serviceListener: service failed");
                finish();
            }

            @Override
            public void deviceDiscovered(BluetoothDeviceModel extra) {
                Log.i(FlowerPowerConstants.TAG, "serviceListener: device discovered " + extra.getName());
            }

            @Override
            public void deviceConnected() {
                Log.i(FlowerPowerConstants.TAG, "serviceListener: device connected");
            }

            @Override
            public void deviceDisconnected() {
                Log.i(FlowerPowerConstants.TAG, "serviceListener: device disconnected");
            }

            @Override
            public void deviceReady(IFlowerPowerDevice device) {
                // lovi temp, soil, ali ne sun
                device.notifyTemperature(true, 1000);   //2000
                device.notifySunlight(true, 2000);      //1000
                device.notifySoilMoisture(true, 1000);  //1000
                //device.notifyBatteryLevel(true, 1000);

                device.readTemperature();
                device.readSunlight();
                device.readSoilMoisture();
                device.readBatteryLevel();
            }

            @Override
            public void dataAvailable(FlowerPower fp) {
                temp = (int) fp.getTemperature();
                soil = (int) fp.getSoilMoisture();
                sun = (int) fp.getSunlight();
                mTempGauge.setTargetValue(temp);
                mSoilGauge.setTargetValue(soil);
                mSunGauge.setTargetValue(sun);
                mBatteryGauge.setTargetValue(fp.getBatteryLevel());
            }
        };

        serviceManager.addServiceListener(serviceListener);
        serviceManager.bind();
    }
    @Override
    protected void onStart() {
        super.onStart();
        serviceManager.connect();

        dataSource = new PlantsDataSource(this);
        dataSource.open();

        spremiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyPlantLive.this, MainActivity.class);
                Plant plant = new Plant();
                plant.setTime(getTimeStamp());
                plant.setTemperature(temp);
                plant.setSoil(soil);
                plant.setSun(sun);
                dataSource.updateGardenStatus(plant, plantId);
                dataSource.close();
                sharedPreferences.edit().putBoolean("detailsSaved", true).apply();
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        serviceManager.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serviceManager.disconnect();
        serviceManager.unbind();
    }

    private String getTimeStamp() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int hours = c.get(Calendar.HOUR);
        int mins = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        return "" + day + "." + month + "." + year + " " + hours + ":" + mins + ":" + seconds;
    }
}
