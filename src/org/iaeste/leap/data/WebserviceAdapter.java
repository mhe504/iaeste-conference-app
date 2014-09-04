/******************************************
 * AUTHOR: Martyn Ellison
 * LAST UPDATE: 16/12/2011
 ******************************************/
package org.iaeste.leap.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class WebserviceAdapter {
	
	private Context context;
	private final String SERVER_ADDRESS = "http://scmweb.infj.ulst.ac.uk/~iaeste12/app_services/";
	
	public WebserviceAdapter (Context context)
	{
		this.context = context;
	}
	
	public void upatePhoneNumbers()
	{
		String result = "";
		InputStream is = null;
		//http post
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost(SERVER_ADDRESS + "all_phone_numbers.php");
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		//convert response to string
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		 
		//parse JSON data
		try	{
				DatabaseAdapter dba = new DatabaseAdapter(context);
				dba.open();
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
	                JSONObject json_data = jArray.getJSONObject(i);


	                dba.updatePhoneNumber(json_data.getString("id"),
	                					  json_data.getString("visible"),
	                					  json_data.getString("name"),
	                					  json_data.getString("number"));
	                
		        }
		        dba.close();
		}
		catch(JSONException e){
		        Log.e("log_tag", "Error parsing data " + e.toString());
		}

	}

	public void updateDelegates()
	{
		String result = "";
		InputStream is = null;
		//http post
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost(SERVER_ADDRESS + "get_all_delegates.php");
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		//convert response to string
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		 
		//parse JSON data
		try	{
				List<Delegate> delegates = new LinkedList<Delegate>();
		        JSONArray jArray = new JSONArray(result);
		        
		        for(int i=0;i<jArray.length();i++){
		        	
	                JSONObject json_data = jArray.getJSONObject(i);
	                
	                Delegate delegate = new Delegate();
	                delegate.setCountry(ECountry.getFromName(json_data.getString("cb_nationality")));
	                delegate.setLc(json_data.getString("cb_lc"));
	                delegate.setFullName(json_data.getString("CONCAT(firstname, ' ', lastname)"));
	                delegates.add(delegate);       
		        }
				DatabaseAdapter dba = new DatabaseAdapter(context);
				dba.open();
				dba.updateDelegates(delegates);
		        dba.close();
		}
		catch(JSONException e){
		        Log.e("log_tag", "Error parsing data " + e.toString());
		}

	}
	
}