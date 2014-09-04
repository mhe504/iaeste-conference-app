/******************************************
 * AUTHOR: Martyn Ellison
 * LAST UPDATE: 16/12/2011
 ******************************************/
package org.iaeste.leap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PhoneNumbersActivity extends Activity {
	
    public String lock = new String();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_numbers);

		final Button cmCallButton = (Button) findViewById(R.id.cmCallButton);
		cmCallButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				TextView numberTextView = (TextView) findViewById(R.id.cmPhoneNo);
				startCall(numberTextView.getText().toString());
			}
		});


		final Button emergencyCallButton = (Button) findViewById(R.id.emergencyServicesCallButton);
		emergencyCallButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				AlertDialog.Builder alertbox = new AlertDialog.Builder(v
						.getContext());
				alertbox.setMessage("Emergency Services will be phoned immediately");
				alertbox.setPositiveButton("Continue", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						TextView numberTextView = (TextView) findViewById(R.id.emergencyServicesPhone);
						startCall(numberTextView.getText().toString());
					}
				});

				alertbox.setNeutralButton("Cancel", null);

				alertbox.show();

			}
		});
	}

	private void startCall(String phoneNumber) {
		try {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + phoneNumber));
			startActivity(callIntent);
		} catch (ActivityNotFoundException e) {
			Log.e("Connect2012 app", "Call failed", e);
		}
	}
	

}