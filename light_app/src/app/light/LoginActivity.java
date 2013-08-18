package app.light;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends CommonActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_login);
		
		Intent intent = getIntent();   // 값을 받기 위한 Intent 생성

		String intent_str = intent.getStringExtra("type");
		
		//System.out.println("intent: "+intent_str);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	        
	    EditText id_edit = (EditText)findViewById(R.id.login_id);
	    EditText password_edit = (EditText)findViewById(R.id.login_password);
	    CheckBox keep_login_check = (CheckBox)findViewById(R.id.login_keep_login);
	    
	    // 로컬 파일에서 로그인 데이터와 로그인 유지여부 데이터 가져옴
	    SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);   
		Boolean keep_login_boolean = prefs.getBoolean("keep_login", false);
		
		String id_text = prefs.getString("email", "");
	    String password_text = prefs.getString("password", "");
		
	    // 해당 값 넣어주기
	    id_edit.setText(id_text);
	    password_edit.setText(password_text);
	    keep_login_check.setChecked(keep_login_boolean);
		
		if(intent_str == null){	//처음 앱 접속시
			startActivity(new Intent(this, SplashActivity.class));
			
			// 로그인 상태 유지가 체크되었을 때
			if(keep_login_boolean){    			
			    
			    Handler login_handler = new Handler () {
			    	@Override
			    	public void handleMessage(Message msg) {
			    		Button login_btn = (Button)findViewById(R.id.login_btn);
			 		    onLoginBtn(login_btn);
			    	}
		        };
			
		        login_handler.sendEmptyMessageDelayed(0, 2100);	       
		    }		
		}
	}
	
	//로그인 버튼 클릭시
	public void onLoginBtn(View v) {

		JSONObject json_param = new JSONObject();
		
		try {		
			final EditText id_text = (EditText)findViewById(R.id.login_id);
			String email_val = id_text.getText().toString();
			json_param.put("email", email_val);	
			final EditText password_text = (EditText)findViewById(R.id.login_password);
			String password_val = password_text.getText().toString();
			json_param.put("password", password_val);
			
			final CheckBox keep_login = (CheckBox)findViewById(R.id.login_keep_login);
			
			String result_json = postData("http://211.110.61.51:3000/login", json_param);		
			
			if(result_json.equals("error")){
				Toast toast = Toast.makeText(this, "로그인 실패!", Toast.LENGTH_SHORT); 
				toast.show(); 
			}
			else{		
				JSONObject json_data = new JSONObject(result_json);
				String result_flag = json_data.getString("result");

				if(result_flag.equals("success")){
					// 로그인 성공시 로컬 파일에 로그인 관련 정보 저장
					SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
					SharedPreferences.Editor editor = prefs.edit();
					
					editor.putString("email", email_val);
					editor.putString("password", password_val);
					editor.putBoolean("keep_login", keep_login.isChecked());
					editor.commit();
					
					Intent intent = new Intent(LoginActivity.this, BaseFragment.class);
					startActivity(intent);
				}
			}	
		}
		catch(JSONException e) {
			Log.e("Error", "putJSON", e);
		}	
	}
	
	// 회원가입 클릭시 회원가입 화면으로 이동
	public void onJoinBtn(View v) {
		Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
		startActivity(intent);
	}
	
}
