package com.example.geocash;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.view.Menu;
import android.location.Location;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.util.Log;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

public class MainActivity extends Activity implements LocationListener	{

	GoogleMap googleMap;

    LatLng myPosition;
    
    // Constants
    public final static String BUDDY_SERVICE_URL          = "https://webservice.buddyplatform.com/Service/v1/BuddyService.ashx";
    public final static String BUDDY_APPLICATION_NAME     = "<#Buddy App Name#>";       // Get it from Buddy's site
    public final static String BUDDY_APPLICATION_PASSWORD = "<#Buddy App Password#>";   // Same as above

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    //LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		
		// Getting reference to the SupportMapFragment of activity_main.xml
        MapFragment fm = (MapFragment)getFragmentManager().findFragmentById(R.id.the_map);

        // Getting GoogleMap object from the fragment
        googleMap = fm.getMap();

        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);

        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if(location!=null){
	        // Getting latitude of the current location
	        double latitude = location.getLatitude();
	
	        // Getting longitude of the current location
	        double longitude = location.getLongitude();
	
	        // Creating a LatLng object for the current location
	        LatLng latLng = new LatLng(latitude, longitude);
	
	        myPosition = new LatLng(latitude, longitude);
	        
	        googleMap.addMarker(new MarkerOptions().position(myPosition).title("me"));
	        
	        CameraUpdate center =
	                CameraUpdateFactory.newLatLng(latLng);
	            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

	            googleMap.moveCamera(center);
	            googleMap.animateCamera(zoom);
        }
    }
        
	@Override
	public void onLocationChanged(Location location) {
	    // TODO Auto-generated method stub

	    double latitude = location.getLatitude();
	    double longitude = location.getLongitude();

	    Log.i("GeoCash", "Latitude: " + latitude + ", Longitude: " + longitude);
	    
	    LatLng myPosition = new LatLng(latitude, longitude);

        googleMap.addMarker(new MarkerOptions().position(myPosition).title("Start"));
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		Log.i("GeoCash", "onProviderDisabled called");
	}

	@Override
	public void onProviderEnabled(String provider) {
	    Log.i("GeoCash", "onProviderEnabled called");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	    Log.i("GeoCash", "onStatusChanged called");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
