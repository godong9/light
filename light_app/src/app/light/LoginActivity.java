package app.light;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends CommonActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_login);
		startActivity(new Intent(this, SplashActivity.class));
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	}
	
	public void onLoginBtn(View v) {

		JSONObject json_param = new JSONObject();
		
		try {		
			final EditText id_text = (EditText)findViewById(R.id.id_val);
			String email_val = id_text.getText().toString();
			json_param.put("email", email_val);	
			final EditText password_text = (EditText)findViewById(R.id.password_val);
			String password_val = password_text.getText().toString();
			json_param.put("password", password_val);
		}
		catch(JSONException e) {
			Log.e("Error", "putJSON", e);
		}
		
		
		/*
		boolean login_status = getData("http://211.110.61.51:3000/users");		
		if(login_status){
			Intent intent = new Intent(LoginActivity.this, FragmentActivity.class);
			startActivity(intent);
		}
		else{
			
		}
		*/
		
		
		boolean login_status = postData("http://211.110.61.51:3000/login", json_param);		
		if(login_status){
			Intent intent = new Intent(LoginActivity.this, FragmentActivity.class);
			startActivity(intent);
		}
		else{
			
		}
		
	}
	
	public void onJoinBtn(View v) {
		Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
		startActivity(intent);
	}
	
}
