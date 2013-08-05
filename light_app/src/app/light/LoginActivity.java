package app.light;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
			final EditText id_text = (EditText)findViewById(R.id.login_id);
			String email_val = id_text.getText().toString();
			json_param.put("email", email_val);	
			final EditText password_text = (EditText)findViewById(R.id.login_password);
			String password_val = password_text.getText().toString();
			json_param.put("password", password_val);
			
			String result_json = postData("http://211.110.61.51:3000/login", json_param);		
			
			if(result_json.equals("error")){
				Toast toast = Toast.makeText(this, "로그인 실패!", Toast.LENGTH_SHORT); 
				toast.show(); 
			}
			else{		
				JSONObject json_data = new JSONObject(result_json);
				String result_flag = json_data.getString("result");
				String result_msg = json_data.getString("msg");
				
				Toast toast = Toast.makeText(this, result_msg, Toast.LENGTH_SHORT); 
				toast.show(); 

				if(result_flag.equals("success")){
					Intent intent = new Intent(LoginActivity.this, FragmentActivity.class);
					startActivity(intent);
				}
			}	
		}
		catch(JSONException e) {
			Log.e("Error", "putJSON", e);
		}	
	}
	
	public void onJoinBtn(View v) {
		Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
		startActivity(intent);
	}
	
}
