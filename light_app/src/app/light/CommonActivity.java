package app.light;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class CommonActivity extends Activity {
	public boolean postData(String url,JSONObject obj) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpParams myParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		HttpConnectionParams.setSoTimeout(myParams, 10000);

		String json_obj=obj.toString();

		try {
			
		    HttpPost httppost = new HttpPost(url.toString());
		    httppost.setHeader("Content-type", "application/json");

		    StringEntity se = new StringEntity(json_obj, "UTF-8"); 
		    se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		    httppost.setEntity(se); 

		    HttpResponse response = httpclient.execute(httppost);
		    String tmp_json = EntityUtils.toString(response.getEntity(), "UTF-8");
		    Log.i("[post_val]", tmp_json);
		   
		    try{
		    	JSONObject json_data = new JSONObject(tmp_json);
		    	//
		    	String result_string = json_data.getString("msg");
		    	Toast toast = Toast.makeText(this, result_string, Toast.LENGTH_SHORT); 
				toast.show(); 
		    	//
		    	return true;
		    } catch (Exception e){
		    	e.printStackTrace();
		    	return false;
		    }
		    
		} catch (ClientProtocolException e) {
			Log.i("[error]", "error");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			Log.i("[error]", "error");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getData(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		
		try {
			HttpGet httpget = new HttpGet(url.toString());
			httpget.setHeader("Content-type", "application/json");

			HttpResponse response = httpclient.execute(httpget);	
			String tmp_json = EntityUtils.toString(response.getEntity(), "UTF-8");
			Log.i("[get_val]", tmp_json);
			
			try{
		    	JSONObject json_data = new JSONObject(tmp_json);
		    	//
		    	String result_string = json_data.getString("result");
		    	Toast toast = Toast.makeText(this, result_string, Toast.LENGTH_SHORT); 
				toast.show(); 
		    	//
		    	return true;
		    	
		    } catch (Exception e){
		    	e.printStackTrace();
		    	return false;
		    }

		} catch (ClientProtocolException e) {
			Log.i("[error]", "error");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			Log.i("[error]", "error");
			e.printStackTrace();
			return false;
		}
		
	}
}
