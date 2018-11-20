package com.example.tpasus.foodyapp.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tpasus.foodyapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class SplashScreenActivity extends AppCompatActivity /*implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener*/ {

    TextView txtVersion;
    GoogleApiClient googleApiClient;
    //public static final int REQUESTCODE_PERMISSION_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationClient;

    private LocationManager locationManager;
    private LocationListener locationListener;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flashscreen);

        txtVersion = findViewById(R.id.txtVersion);
       // sharedPreferences = getSharedPreferences("save_location", MODE_PRIVATE);


        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
            txtVersion.setText(getString(R.string.version) + " " + packageInfo.versionName);    //lay version trong gradle

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent iLogin = new Intent(SplashScreenActivity.this,LoginActivity.class);
                    startActivity(iLogin);
                    finish();
                }
            },2000);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }





     /*  mFusedLocationClient = LocationServices.getFusedLocationProviderClient(SplashScreenActivity.this);


        buildgac();*/


//        Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent iLogin = new Intent(SplashScreenActivity.this,LoginActivity.class);
//                startActivity(iLogin);
//                finish();
//            }
//        },2000);

//        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//
//
//              //  Log.d("kiemtra",location.toString());
////                if(location!=null)
////                {
////                    SharedPreferences.Editor editor = sharedPreferences.edit();
////                    editor.putString("latitude", String.valueOf(location.getLatitude()));
////                    editor.putString("longitude", String.valueOf(location.getLongitude()));
////
////                    editor.commit();
////
////                }
//
////                try {
////                    PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
////                    txtVersion.setText(getString(R.string.version) + " " + packageInfo.versionName);    //lay version trong gradle
////
////                    //chuyen sang trang dang nhap
////
////
////                } catch (PackageManager.NameNotFoundException e) {
////                    e.printStackTrace();
////                }
//
//            }
//
//            @Override
//            public void onStatusChanged(String s, int i, Bundle bundle) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String s) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String s) {
//
//            }
//        };
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
//
//        }else{
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//        }
//
//      //  mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        /*mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            // Logic to handle location object
//                        }
//                    }
//                });*/
    }

    /*synchronized void buildgac(){
        if(googleApiClient == null){
            googleApiClient=new GoogleApiClient.Builder(SplashScreenActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            googleApiClient.connect();
        }
    }


    void checkP(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},100);
        }else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //  Toast.makeText(SplashScreenActivity.this, location.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("ABCTAG",location.toString());
                }
            });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  //              nếu như có quyền đc cấp và đc chấp nhận (user nhấn allow)
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//            }
                checkP();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }*/
}
