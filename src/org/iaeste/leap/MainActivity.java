/******************************************
 * AUTHOR: Martyn Ellison
 * LAST UPDATE: 16/12/2011
 ******************************************/

package org.iaeste.leap;

import java.util.Calendar;

import org.iaeste.leap.activities.delegates.DelegatesCountryActivity;
import org.iaeste.leap.itinerary.ItineraryActivity;
import org.iaeste.leap.mapping.MapsActivity;
import org.iaeste.leap.social.FacebookActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private static String SITE_URL = "http://www.iaeste.eu/leap";
	private static String APP_PASSWORD = "Titanic13";
	
	protected static boolean pass = false;
	protected static boolean complete = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean hasActivitated = getPreferences(MODE_PRIVATE).getBoolean(
				"hasActivitated", true);
		if (!hasActivitated) {
			securityScreen();
		} else {
			homeScreen();
		}

	}

	private void homeScreen() {
		setContentView(R.layout.main);

		showLocationPhoto();

		final ImageView itineraryButton = (ImageView) findViewById(R.id.itineraryButton);
		itineraryButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {

				Intent myIntent = new Intent(MainActivity.this,
						ItineraryActivity.class);
				MainActivity.this.startActivity(myIntent);

				return false;
			}
		});

		final ImageView mapsButton = (ImageView) findViewById(R.id.mapButton);
		mapsButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {

				Intent myIntent = new Intent(MainActivity.this,
						MapsActivity.class);
				MainActivity.this.startActivity(myIntent);

				return false;
			}
		});

		final ImageView phoneNosButton = (ImageView) findViewById(R.id.phoneButton);
		phoneNosButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {

				Intent myIntent = new Intent(MainActivity.this,
						PhoneNumbersActivity.class);
				MainActivity.this.startActivity(myIntent);

				return false;
			}
		});

		final ImageView delegatesButton = (ImageView) findViewById(R.id.delegatesButton);
		delegatesButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {

				Intent myIntent = new Intent(MainActivity.this,
						DelegatesCountryActivity.class);
				MainActivity.this.startActivity(myIntent);

				return false;
			}
		});

		final ImageView challengeButton = (ImageView) findViewById(R.id.webButton);
		challengeButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				boolean hideNetWarning = true;
				
				if (hideNetWarning)
				{
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(SITE_URL));
					MainActivity.this.startActivity(i);
				} else {
					AlertDialog.Builder alertbox = new AlertDialog.Builder(v
							.getContext());

					alertbox.setMessage("Internet Access Required");
					alertbox.setPositiveButton("Continue",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {

									Intent i = new Intent(Intent.ACTION_VIEW);
									i.setData(Uri.parse(SITE_URL));
									MainActivity.this.startActivity(i);

							        SharedPreferences settings = getPreferences(Context.MODE_WORLD_READABLE);
							        SharedPreferences.Editor editor = settings.edit();
							        editor.putBoolean("hideNetWarning", true);
							        editor.commit();
								}
							});

		            alertbox.setNeutralButton("Cancel",  null);
		            
		            alertbox.show();
				}

			}
		});

		final ImageView fbButton = (ImageView) findViewById(R.id.socialButton);
		fbButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				boolean hideNetWarning = true;
				
				if (hideNetWarning)
				{
					Intent myIntent = new Intent(MainActivity.this,
							FacebookActivity.class);
					MainActivity.this.startActivity(myIntent);
				} else {
					AlertDialog.Builder alertbox = new AlertDialog.Builder(v
							.getContext());

					alertbox.setMessage("Internet Access Required");
					alertbox.setPositiveButton("Continue",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {

									Intent myIntent = new Intent(
											MainActivity.this,
											FacebookActivity.class);
									MainActivity.this.startActivity(myIntent);

							        SharedPreferences settings = getPreferences(Context.MODE_WORLD_READABLE);
							        SharedPreferences.Editor editor = settings.edit();
							        editor.putBoolean("hideNetWarning", true);
							        editor.commit();
								}
							});

		            alertbox.setNeutralButton("Cancel",  null);
		            
		            alertbox.show();
				}
				return false;
			}
		});
	}

	private void securityScreen() {
		setContentView(R.layout.security);

		final Button continueButton = (Button) findViewById(R.id.btnContinue);
		continueButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				EditText passwordField = (EditText) findViewById(R.id.txtPasword);
				if (passwordField.getText().toString().equals(APP_PASSWORD)) {
					SharedPreferences settings = getPreferences(Context.MODE_WORLD_READABLE);
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("hasActivitated", true);
					editor.commit();

					homeScreen();
				} else {
					passwordField.setText("");
					AlertDialog alertDialog = new AlertDialog.Builder(
							MainActivity.this).create();
					alertDialog.setTitle("Incorrect");
					alertDialog
							.setMessage("Incorrect Password: Get the password from your confirmation email.");
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					alertDialog.show();
				}
			}

		});

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		int imageNo = getPreferences(MODE_PRIVATE).getInt("imageNo", 0);

		if (imageNo > 0) {
			imageNo--;
		} else {
			imageNo = 5;
		}

		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("imageNo", imageNo);
		editor.commit();

		boolean hasActivitated = getPreferences(MODE_PRIVATE).getBoolean(
				"hasActivitated", false);
		if (!hasActivitated) {
			securityScreen();
		} else {
			homeScreen();
		}

	}

	private void showLocationPhoto() {
		final LinearLayout locationPhoto = (LinearLayout) findViewById(R.id.topLayout);

		int imageNo = getPreferences(MODE_PRIVATE).getInt("imageNo", 0);

		switch (imageNo) {
		case 0:
			locationPhoto.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.background1));
			break;
		case 1:
			locationPhoto.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.background2));
			break;
		case 2:
			locationPhoto.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.background3));
			break;
		case 3:
			locationPhoto.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.background4));
			break;
		case 4:
			locationPhoto.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.background5));
			break;
		}
		if (imageNo < 4) {
			imageNo++;
		} else {
			imageNo = 0;
		}

		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("imageNo", imageNo);
		editor.commit();

	}

	/* Source: Ryan Fernandes, 12/07/2007, http://tripoverit.blogspot.com */
	public static long daysBetween(final Calendar startDate,
			final Calendar endDate) {
		startDate.get(Calendar.YEAR);
		startDate.get(Calendar.MONTH);
		startDate.get(Calendar.DATE);

		endDate.get(Calendar.YEAR);
		endDate.get(Calendar.MONTH);
		endDate.get(Calendar.DATE);

		Calendar sDate = (Calendar) startDate.clone();
		long daysBetween = 0;

		int y1 = sDate.get(Calendar.YEAR);
		int y2 = endDate.get(Calendar.YEAR);
		int m1 = sDate.get(Calendar.MONTH);
		int m2 = endDate.get(Calendar.MONTH);

		// **year optimization**
		while (((y2 - y1) * 12 + (m2 - m1)) > 12) {

			// move to Jan 01
			if (sDate.get(Calendar.MONTH) == Calendar.JANUARY
					&& sDate.get(Calendar.DAY_OF_MONTH) == sDate
							.getActualMinimum(Calendar.DAY_OF_MONTH)) {

				daysBetween += sDate.getActualMaximum(Calendar.DAY_OF_YEAR);
				sDate.add(Calendar.YEAR, 1);
			} else {
				int diff = 1 + sDate.getActualMaximum(Calendar.DAY_OF_YEAR)
						- sDate.get(Calendar.DAY_OF_YEAR);
				sDate.add(Calendar.DAY_OF_YEAR, diff);
				daysBetween += diff;
			}
			y1 = sDate.get(Calendar.YEAR);
		}

		// ** optimize for month **
		// while the difference is more than a month, add a month to start month
		while ((m2 - m1) % 12 > 1) {
			daysBetween += sDate.getActualMaximum(Calendar.DAY_OF_MONTH);
			sDate.add(Calendar.MONTH, 1);
			m1 = sDate.get(Calendar.MONTH);
		}

		// process remainder date
		while (sDate.before(endDate)) {
			sDate.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}

		return daysBetween;
	}
	

}