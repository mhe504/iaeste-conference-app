/******************************************
 * AUTHOR: Martyn Ellison
 * LAST UPDATE: 16/12/2011
 ******************************************/
package org.iaeste.activities.delegates;

import android.app.Activity;
import android.os.Bundle;

import org.iaeste.R;

public class DelegatesCountryActivity extends Activity {
	
    public String lock = new String();

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delegates_list);


    
    }
	
}