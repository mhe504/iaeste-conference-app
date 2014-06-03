/******************************************
 * AUTHOR: Martyn Ellison
 * LAST UPDATE: 16/12/2011
 ******************************************/
package org.iaeste.social;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import org.iaeste.R;

public class SocialTabControl extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.social);

	    TabHost tabHost = getTabHost();
	    TabHost.TabSpec spec;
	    Intent intent;

	    // FB Tab
	    intent = new Intent().setClass(this, FacebookActivity.class);
	    spec = tabHost.newTabSpec("fb").setIndicator("Facebook")
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    // Twitter Tab
	    intent = new Intent().setClass(this, TwitterActivity.class);
	    spec = tabHost.newTabSpec("twitter").setIndicator("Twitter")
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 60;
	    tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = 60;
	    tabHost.getTabWidget().getChildAt(0).setPadding(0, 0, 0, 15);
	    tabHost.getTabWidget().getChildAt(1).setPadding(0, 0, 0, 15);

	}
}
