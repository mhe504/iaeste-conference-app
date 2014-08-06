/******************************************
 * AUTHOR: Martyn Ellison
 * LAST UPDATE: 16/12/2011
 ******************************************/
package org.iaeste.mapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.iaeste.R;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.util.constants.MapViewConstants;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class MapsActivity extends Activity implements MapViewConstants {
   
    MyItemizedOverlay myItemizedOverlay = null;
    MyItemizedOverlay myItemizedOverlay2 = null;
	private GeoPoint currentPosition;
	private GeoPoint confLocation = new GeoPoint(50.780194, 15.173639);
	MapView mapView;
	private IMapController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        copyTilesToSd();
        mapView = (MapView) findViewById(R.id.mapview);
        controller = mapView.getController();
        mapView.setBuiltInZoomControls(true);
        
        Drawable marker=getResources().getDrawable(R.drawable.marker);
        Drawable locationMarker=getResources().getDrawable(android.R.drawable.ic_menu_mylocation);
        int markerWidth = marker.getIntrinsicWidth();
        int markerHeight = marker.getIntrinsicHeight();
        marker.setBounds(0, markerHeight, markerWidth, 0);
        locationMarker.setBounds(0, markerHeight, markerWidth, 0);
         
        ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
         
        myItemizedOverlay = new MyItemizedOverlay(marker, resourceProxy);
        myItemizedOverlay2 = new MyItemizedOverlay(locationMarker, resourceProxy);
        mapView.getOverlays().add(myItemizedOverlay);
        mapView.getOverlays().add(myItemizedOverlay2);
        new Handler().postDelayed(new Thread () {
            @Override
            public void run() {
            	controller.animateTo(confLocation);
            }
        }, 600);
        controller.setZoom(14);
        //controller.animateTo(myPoint1);
        mapView.invalidate();
        
        myItemizedOverlay.addItem(confLocation, "Conference Location", "Conference Location");
        setupGPS();
         
    } 

    
	
	private void setupGPS() {
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
		setLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, new LocationListener() {
			
        	public void onLocationChanged(Location arg0) {
        		setLocation(arg0);
        	}
        	
        	//unimplemented methods
        	public void onProviderDisabled(String arg0) {}
        	public void onProviderEnabled(String arg0) {}
        	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
		});
	}
	
	private void setLocation(Location location) {
		
		if(location != null)
		{
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			
			currentPosition = new GeoPoint(latitude, longitude);     
			
			TextView locationTextView = (TextView) findViewById(R.id.locationTextView);
			locationTextView.setText("Location Found");
			locationTextView.setWidth(90);
			myItemizedOverlay.clear();
			myItemizedOverlay2.clear();
			myItemizedOverlay.addItem(confLocation, "Conference Location", "myPoint1");
	        myItemizedOverlay2.addItem(currentPosition, "Your Location", "myPoint2");
	        
	        controller.setCenter(currentPosition);
	        mapView.invalidate();
			
		}
	}
	
	private void copyTilesToSd() {

		try {
			
			InputStream myInput = getAssets().open("packaged_map_tiles.zip");
		    File directory = new File("/sdcard/osmdroid");
		    if (!directory.exists()) 
		    {
		        directory.mkdirs();
		    } 

		    OutputStream myOutput = new FileOutputStream(directory.getPath()+ "/packaged_map_tiles.zip");

		    // Transfer bytes from the input file to the output file
		    byte[] buffer = new byte[1024];
		    int length;
		    while ((length = myInput.read(buffer))>0)
		    {
		        myOutput.write(buffer, 0, length);
		    }

		    myOutput.flush();
 
		    myOutput.close();
 
		    myInput.close();
 
		} catch (FileNotFoundException e) {
			displayLongToast("Can not load cached map. FileNotFoundException.");
		} catch (IOException e) {
			displayLongToast("Can not load cached map. IOException.");
		}
	}
	
	private void displayLongToast(String message) {
		final Toast errorMessage = Toast.makeText(getBaseContext(), 
												  message,
												  Toast.LENGTH_SHORT);

		new CountDownTimer(6000, 1000)
		{
		    public void onTick(long millisUntilFinished) {errorMessage.show();}
		    public void onFinish() {}
		}.start();
	}

    
}