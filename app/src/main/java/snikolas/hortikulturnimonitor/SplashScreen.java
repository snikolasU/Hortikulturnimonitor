package snikolas.hortikulturnimonitor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

/**
 * Created by Sara on 24.6.2017..
 */

public class SplashScreen extends Activity{

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.splash_screen);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                if(sharedPreferences.contains("dataCreated")){
                }else{
                    sharedPreferences.edit().putBoolean("dataCreated", false).apply();
                }
                if(sharedPreferences.contains("gardenCreated")){
                }else{
                    sharedPreferences.edit().putBoolean("gardenCreated", false).apply();
                }
                if(sharedPreferences.contains("detailsSaved")){
                }else{
                    sharedPreferences.edit().putBoolean("detailsSaved", false).apply();
                }
                if(sharedPreferences.contains("fromNewPlant")){
                }else{
                    sharedPreferences.edit().putBoolean("fromNewPlant", false).apply();
                }
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(mainIntent);
                finish();

            }
        };
        int DISPLAY_TIME = 2000;
        new Handler().postDelayed(runnable, DISPLAY_TIME);
    }

}

