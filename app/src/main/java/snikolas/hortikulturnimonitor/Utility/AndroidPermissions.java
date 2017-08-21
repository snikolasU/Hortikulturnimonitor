package snikolas.hortikulturnimonitor.Utility;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Sara on 28.6.2017..
 */

public class AndroidPermissions extends Activity {

    Activity activity;
    final int BLUETOOTH = 12;
    final int BLUETOOTH_ADMIN = 13;

    public AndroidPermissions(Activity ac) {
        activity = ac;
    }

    public void bluetoothPermission() {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.BLUETOOTH)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.BLUETOOTH)) {
                //nothing to be inserted here
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.BLUETOOTH},
                        BLUETOOTH);
            }
        }
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.BLUETOOTH_ADMIN)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.BLUETOOTH_ADMIN)) {
                //nothing to be inserted here
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.BLUETOOTH_ADMIN},
                        BLUETOOTH_ADMIN);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case BLUETOOTH:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {}
                break;
            case BLUETOOTH_ADMIN:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {}
                break;
            }
    }

}
