package hu.markcool.gpsservicedemo;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

//import android.util.Log;


public class GpsServiceTestOne extends Service implements LocationListener {
    private LocationManager manager;
    private boolean isInArea;
    private double latitude, longitude;

    @Override
    public void onCreate() {

        Toast.makeText(this, "Service onCreate", Toast.LENGTH_SHORT).show();

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1,
                this);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1,
                this);
        isInArea = false; //是否在範圍內
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            latitude = (double) intent.getFloatExtra("LATITUDE1", 22.6297370f); //取得座標
            longitude = (double) intent.getFloatExtra("LONGITUDE1", 120.3278820f); //取得座標
        } catch (NullPointerException e) {
            Log.i("GPSService","NullPointException");
        }
        Log.d("GPSService", "lat/long: " + latitude + ": " + longitude);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        manager.removeUpdates(this); //移除定位服務更新
    }

    @Override
    public void onLocationChanged(Location current) {


        Toast.makeText(this, "Service onLocationChanged:" + current, Toast.LENGTH_SHORT).show();

        // TODO Auto-generated method stub
        if (current == null)
            return;
        Location dest = new Location(current); //取得現在位置
        dest.setLatitude(latitude); //取得現在位置座標
        dest.setLongitude(longitude); //取得現在位置座標
        float distance = current.distanceTo(dest); //計算目標位置與現在位置距離
        if (distance < 1000.0) { //當目標小於1公里時
            if (isInArea == false) { //在區域內
                Intent intent = new Intent("android.broadcast.LOCATION"); //啟動廣播服務
                sendBroadcast(intent); //發送廣播
                isInArea = true; //是否在區域內:true
            }
        } else {
            isInArea = false; //是否在區域內:false
        }

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Service onStatusChanged:", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

        Toast.makeText(this, "Service onProviderEnabled:", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

        Toast.makeText(this, "Service onProviderDisabled:", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

}