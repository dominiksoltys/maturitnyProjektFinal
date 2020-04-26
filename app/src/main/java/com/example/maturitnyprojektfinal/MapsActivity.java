package com.example.maturitnyprojektfinal;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userLatLong;

    private GoogleMap mMap;
        LatLng Kaufland = new LatLng(49.001179, 21.228365);
        LatLng KauflandDva = new LatLng(48.987722, 21.263838);
        LatLng KauflandTri = new LatLng(49.095092, 21.110068);



        LatLng Tesco = new LatLng(48.981293, 21.252381);
        LatLng TescoDva = new LatLng(49.009015, 21.219536);



        LatLng Lidl = new LatLng(49.001658, 21.218847);
        LatLng LidlDva = new LatLng(48.987768, 21.263871);
        LatLng LidlTri = new LatLng(49.100910, 21.101269);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //userLatLong
                //userLatLong = new LatLng(48.990478, 21.247214);

                userLatLong = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.clear();//vyclearuje stary marker
                mMap.addMarker(new MarkerOptions().position(userLatLong).title("Tvoja poloha"));
                mMap.addMarker(new MarkerOptions().position(Kaufland).title("Kauf PO"));
                mMap.addMarker(new MarkerOptions().position(KauflandDva).title("Kaufland PO"));
                mMap.addMarker(new MarkerOptions().position(Tesco).title("Tesco Stanica"));
                mMap.addMarker(new MarkerOptions().position(TescoDva).title("Tesco Stanica"));
                mMap.addMarker(new MarkerOptions().position(Lidl).title("Lidl"));
                mMap.addMarker(new MarkerOptions().position(LidlDva).title("Lidl sekčov"));
                mMap.addMarker(new MarkerOptions().position(LidlTri).title("Lidl Sabinov"));
                mMap.addMarker(new MarkerOptions().position(KauflandTri).title("Kaufland Sabinov"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLong));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //ziadost o povolenie

        askLocationPermission();

    }

    private void askLocationPermission() {
        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                //posledna znama lokacia pre nastavenie defaultneho markera
                try{
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                userLatLong = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                mMap.clear();//vyclearuje stary marker
                mMap.addMarker(new MarkerOptions().position(userLatLong).title("Tvoja poloha"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLong));} catch (Exception e) {
                    Toast.makeText(MapsActivity.this, "Vase zariadenie ma chybne API,preto nedokazeme zobrazit vasu polohu,ci polohu hypermarketov", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();

    }
}
