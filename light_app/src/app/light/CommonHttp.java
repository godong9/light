package app.light;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class CommonHttp {
	private static HttpClient httpclient = new DefaultHttpClient();
	
	public String postData(String url,JSONObject obj) {
		// Create a new HttpClient and Post Header
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
		    return tmp_json;	    
		} catch (ClientProtocolException e) {
			Log.i("[error]", "error");
			e.printStackTrace();
			return "error";
		} catch (IOException e) {
			Log.i("[error]", "error");
			e.printStackTrace();
			return "error";
		}
	}
	
	public String getData(String url) {
		
		try {
			HttpGet httpget = new HttpGet(url.toString());
			httpget.setHeader("Content-type", "application/json");
			
			HttpResponse response = httpclient.execute(httpget);	
			String tmp_json = EntityUtils.toString(response.getEntity(), "UTF-8");
			Log.i("[get_val]", tmp_json);
			return tmp_json;
		} catch (ClientProtocolException e) {
			Log.i("[error]", "error");
			e.printStackTrace();
			return "error";
		} catch (IOException e) {
			Log.i("[error]", "error");
			e.printStackTrace();
			return "error";
		}		
	}
	
	public Bitmap getImg(String img_url) {
		URL url = null;
		Bitmap bitmap = null;
		try {
			url = new URL(img_url);
			HttpGet httpRequest = null;
			try {
				httpRequest = new HttpGet(url.toURI());
			} catch (Exception e) {
				e.printStackTrace();
			}
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = (HttpResponse) httpclient
					.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
			InputStream instream = bufHttpEntity.getContent();
			bitmap = BitmapFactory.decodeStream(instream);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}
}
