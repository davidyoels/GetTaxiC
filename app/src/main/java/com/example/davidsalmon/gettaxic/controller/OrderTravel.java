package com.example.davidsalmon.gettaxic.controller;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidsalmon.gettaxic.Manifest;
import com.example.davidsalmon.gettaxic.R;
import com.example.davidsalmon.gettaxic.model.entities.Travel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import static com.example.davidsalmon.gettaxic.R.id.android_pay;
import static com.example.davidsalmon.gettaxic.R.id.mapViewOrder;

public class OrderTravel extends FragmentActivity implements OnMapReadyCallback,View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "OrderTravel";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    static LocationListener locationListener;
    GoogleMap mGoogleMap;
    Fragment mapView;
    View mView;
    String latitude, longitude;
    ImageButton FindCurrentLocation,AddNewTravel,SearchDestinationLocation;
    AutoCompleteTextView AutoCompleteDestination;
    private static Location latLngForCurrentLocation;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private  GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168),
            new LatLng(71, 136));
    String stringCurrentAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isServiceOK()) {
            //Toast.makeText(this, "perfect", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_order_travel);
            initMap();
            getLocation();
            findViews();
            initAutoComplete();
        }

    }

    private void findViews() {
        FindCurrentLocation = findViewById(R.id.FindCurrentLocationOrder);
        AddNewTravel = findViewById(R.id.AddNewTravleOrderTemp);
        AutoCompleteDestination = findViewById(R.id.AddNewTravleOrder);
        SearchDestinationLocation = findViewById(R.id.SearchDestinationLocation);
        SearchDestinationLocation.setOnClickListener(this);

        FindCurrentLocation.setOnClickListener(this);
        AddNewTravel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == FindCurrentLocation) {
            locationListener.onLocationChanged(latLngForCurrentLocation);
        } else if (v == AddNewTravel) {
            Toast.makeText(this, "for now, this button is useless", Toast.LENGTH_SHORT).show();
        } else if (v == SearchDestinationLocation) {
            Geocoder geocoder = new Geocoder(getApplicationContext());
            Address address = new Address(new Locale("hebrew"));
            try {
                List<Address> addressList = geocoder.getFromLocationName(AutoCompleteDestination.getText().toString(), 1);
                address = addressList.get(0);


            }catch (Exception EX){}
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Location.distanceBetween(latLngForCurrentLocation.getLatitude(), latLngForCurrentLocation.getLongitude()
                    , address.getLatitude(), address.getLongitude(), new float[]{3});
            String date = simpleDateFormat.format(calendar.getTime());
   //public Travel(Travel.taxi.AVAILABLE, stringCurrentAddress, address.getLocality() + address.getCountryName(), date, Time endTravelTime, String customerName, String customerPhone, String customerEmail) {

            //Travel newTravel = new Travel(Travel.taxi.AVAILABLE, latLngForCurrentLocation, , , , , , )
            Toast.makeText(this, "damn", Toast.LENGTH_SHORT).show();
        }
    }

    private void initAutoComplete() {
         mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, LAT_LNG_BOUNDS, null);
        AutoCompleteDestination.setAdapter(mPlaceAutocompleteAdapter);
        AutoCompleteDestination.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER)
                    getLocation();
             return false;
            }
        });
    }
    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(mapViewOrder);
        mapFragment.getMapAsync(this);

    }


    public boolean isServiceOK() {
        Log.d(TAG, "isServiceOk");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(OrderTravel.this);
        if (available == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(OrderTravel.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latLngForCurrentLocation = location;
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                    stringCurrentAddress = addressList.get(0).getLocality() + ",";
                    stringCurrentAddress += addressList.get(0).getCountryName();
                    mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(stringCurrentAddress));
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,locationListener);
        } else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "damn", Toast.LENGTH_SHORT).show();
        return;
    }
}