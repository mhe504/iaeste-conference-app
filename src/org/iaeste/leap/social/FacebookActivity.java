package org.iaeste.leap.social;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import org.iaeste.leap.R;

public class FacebookActivity extends Activity {

	private final String FB_PAGE_URL = "http://m.facebook.com/iaesteLEAP";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.facebook_page);
		
		WebView webview = (WebView) findViewById(R.id.webView);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl(FB_PAGE_URL);

	}
}
