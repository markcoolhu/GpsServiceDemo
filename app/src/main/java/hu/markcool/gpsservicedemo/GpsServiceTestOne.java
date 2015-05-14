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
        isInArea = false; //�O�_�b�d��
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            latitude = (double) intent.getFloatExtra("LATITUDE1", 22.6297370f); //���o�y��
            longitude = (double) intent.getFloatExtra("LONGITUDE1", 120.3278820f); //���o�y��
        } catch (NullPointerException e) {
            Log.i("GPSService","NullPointException");
        }
        Log.d("GPSService", "lat/long: " + latitude + ": " + longitude);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        manager.removeUpdates(this); //�����w��A�ȧ�s
    }

    @Override
    public void onLocationChanged(Location current) {


        Toast.makeText(this, "Service onLocationChanged:" + current, Toast.LENGTH_SHORT).show();

        // TODO Auto-generated method stub
        if (current == null)
            return;
        Location dest = new Location(current); //���o�{�b��m
        dest.setLatitude(latitude); //���o�{�b��m�y��
        dest.setLongitude(longitude); //���o�{�b��m�y��
        float distance = current.distanceTo(dest); //�p��ؼЦ�m�P�{�b��m�Z��
        if (distance < 1000.0) { //��ؼФp��1������
            if (isInArea == false) { //�b�ϰ줺
                Intent intent = new Intent("android.broadcast.LOCATION"); //�Ұʼs���A��
                sendBroadcast(intent); //�o�e�s��
                isInArea = true; //�O�_�b�ϰ줺:true
            }
        } else {
            isInArea = false; //�O�_�b�ϰ줺:false
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