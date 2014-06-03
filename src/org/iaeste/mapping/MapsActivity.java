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
import java.util.LinkedList;
import java.util.List;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.iaeste.R;

public class MapsActivity extends Activity {
	protected static final String PROVIDER_NAME = LocationManager.GPS_PROVIDER;
    private MapController osmViewController;
    private MapView osmView;
    private double latitude = 54.584274484353536;
    private double longitude = -5.93660831451416;
    private GeoPoint currentPosition;
    private LocationMarker myLocationOverlay;
    
    private List<PoiMarker> markers;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
        	copyTilesToSd();
        }
        else{
            displayLongToast("Can not load cached map. SD Card could not be accessed.");
        }
  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);

        osmView = (MapView) findViewById(R.id.mapview);
        osmViewController = osmView.getController();

        osmView.setTileSource(TileSourceFactory.MAPNIK);
        osmView.setBuiltInZoomControls(true);
        osmViewController.setZoom(15);

        currentPosition = new GeoPoint(latitude,longitude);
        osmViewController.setCenter(currentPosition);
        
        myLocationOverlay = new LocationMarker(this);
        
        populateMarkers();
        
        setupSpinner();
        
        setupGPS();
        
        Button centreButton = (Button)findViewById(R.id.centreButton);
        centreButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
	            osmViewController.setCenter(currentPosition);
			}
		});
        
        Button hideButton = (Button)findViewById(R.id.hideButton);
        hideButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Button sender = (Button)v;
				if ("Hide Labels".equals(sender.getText()))
				{
					myLocationOverlay.setLabelVisibility(false);
					for (PoiMarker marker: markers)
					{
						marker.setLabelVisibility(false);
					}	
					sender.setText("Show Labels");
				}
				else
				{
					myLocationOverlay.setLabelVisibility(true);
					for (PoiMarker marker: markers)
					{
						marker.setLabelVisibility(true);
					}	
					sender.setText("Hide Labels");
				}
				osmView.invalidate();
			}
		});
        
    	Button dirButton = (Button) findViewById(R.id.directionsButton);
    	dirButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {

			}
		});
        
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
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			
            currentPosition = new GeoPoint(latitude, longitude);     
            
            myLocationOverlay.setmLocation(currentPosition);
            osmView.getOverlays().add(myLocationOverlay);
            
            TextView locationTextView = (TextView) findViewById(R.id.locationTextView);
            locationTextView.setText("Location Found");
            locationTextView.setWidth(90);
            
            Button centreButton = (Button)findViewById(R.id.centreButton);
            centreButton.setVisibility(Button.VISIBLE);
           
		}
	}

	private void setupSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.markerSpinner);
        ArrayAdapter <CharSequence> adapter = new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        for (PoiMarker marker : markers)
        {
        	adapter.add(marker.getLabel());
        }
        adapter.add("ALL");
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
		        
				for (PoiMarker marker : markers)
		        {
		        	if (marker.getLabel().equals(arg0.getSelectedItem().toString()))
		        	{
		                osmView.getOverlays().add(marker);
			            osmViewController.setCenter(marker.getMyLocation());
		        	}
		        	else if (arg0.getSelectedItem().toString().equals("ALL"))
		                osmView.getOverlays().add(marker);
		        	else
		        	{
		        		osmView.getOverlays().remove(marker);
		        	}
		        	
		        	Button dirButton = (Button) findViewById(R.id.directionsButton);
		        	Button hideButton = (Button) findViewById(R.id.hideButton);
		        	if (arg0.getSelectedItem().toString().equals("Paddy's  Palace "))
		        	{
		        		dirButton.setVisibility(Button.VISIBLE);
		        		hideButton.setVisibility(Button.GONE);
		        	}
		        	else
		        	{
		        		dirButton.setVisibility(Button.GONE);
		        		hideButton.setVisibility(Button.VISIBLE);	
		        	}
		        }
				osmView.invalidate();
			}

			public void onNothingSelected(AdapterView<?> arg0) {}
		});
	}

    private void populateMarkers()
    {
    	markers = new LinkedList<PoiMarker>();
    	
    	PoiMarker item = new PoiMarker(this, "Europa Bus Station", new GeoPoint(54.594339, -5.934554));
    	markers.add(item);

    	item = new PoiMarker(this, "Paddy's  Palace ", new GeoPoint(54.586146, -5.940170));
    	markers.add(item);

    	item = new PoiMarker(this, "Student's Union ", new GeoPoint(54.58452318010526, -5.936704874038696));
    	markers.add(item);
    	
    	item = new PoiMarker(this, "Fithly McNastys ", new GeoPoint(54.59144874693254, -5.9330034255981445));
    	markers.add(item);
    	
    	item = new PoiMarker(this, "Eglantine Inn  ", new GeoPoint(54.58034488999118, -5.938475131988525));
    	markers.add(item);
    	
    	item = new PoiMarker(this, "Laverys     ", new GeoPoint(54.58881294286831, -5.934569835662842));
    	markers.add(item);
    	
    	item = new PoiMarker(this, "Wetherspoons    ", new GeoPoint(54.593562240177754, -5.931565761566162));
    	markers.add(item);
    	
    	item = new PoiMarker(this, "El Divino    ", new GeoPoint(54.59532754459013, -5.915687084197998));
    	markers.add(item);
    	
    	
    }
    
}