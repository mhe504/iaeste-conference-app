package org.iaeste.social;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.iaeste.R;

public class TwitterActivity extends Activity{
private final String TWITTER_WIDGET_URL = "https://mobile.twitter.com/BCouncil_NI";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_page);
		
		final WebView webview = (WebView) findViewById(R.id.webView);
		
		webview.setWebViewClient(new MyWebViewClient());
		webview.setBackgroundColor(Color.WHITE);
		
		webview.loadData("<html><body><p style='text-align:center'><br><br><br>Loading...</p></body></html>", "text/html", "UTF-8");
		
		class RetreiveFeedTask extends AsyncTask<String, Void, String> {
			
			protected String doInBackground(String... urls) {
				try {
					

					StringBuilder wholePage = new StringBuilder();
					try 
					{
					
					    HttpGet mRequest = new HttpGet();
					    HttpClient mClient = new DefaultHttpClient();
					    mRequest.setURI(new URI(urls[0]));
					    // Read all the text returned by the server
					    HttpResponse response = mClient.execute(mRequest);
					    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					    String str;
					    while ((str = in.readLine()) != null) 
					    {
					    	wholePage.append(str);
					    }
					    in.close();
					} catch (MalformedURLException e) {
					} catch (IOException e) {
					}
					
					String s = wholePage.toString();
					
					String res =  s.substring(0, s.indexOf("<body class=\"")) + 
								  s.substring(s.indexOf("<div class=\"timeline\">"), s.indexOf("<div class=\"w-button-more\">")) +
								  "</div></body</html>";
					
					webview.loadData(res, "text/html", "UTF-8");
					
					return res;
					
				} catch (Exception e) {
					return null;
				}
			}
			
			protected void onPostExecute(String feed) {
			}
		}
		
		new RetreiveFeedTask().execute(TWITTER_WIDGET_URL);
		
	}
	
	public class MyWebViewClient extends WebViewClient {
	    @Override
	               public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                
	                return true;
	                   
	               }
	}
	
	        
}  