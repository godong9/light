package app.light;


import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ProfileActivity extends CommonActivity {
	
	private int gender = 0;
	
	private static int GENDER_WOMAN = 1;
	private static int GENDER_MAN = 2;
	ImageButton woman_btn;
	ImageButton man_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_profile);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.basic_title);
		
		woman_btn = (ImageButton)findViewById(R.id.profile_woman_btn);
		man_btn = (ImageButton)findViewById(R.id.profile_man_btn);
	}
	
	public void onProfileWomanBtn(View v){
		gender = GENDER_WOMAN;
		woman_btn.setSelected(true);
		man_btn.setSelected(false);
	}
	
	public void onProfileManBtn(View v){
		gender = GENDER_MAN;
		woman_btn.setSelected(false);
		man_btn.setSelected(true);
	}
	
	
	public void onProfileCompleteBtn(View v){
		JSONObject json_param = new JSONObject();
		
		try {		
			//입력폼으로부터 데이터 가져와서 JSON 오브젝트에 저장
			json_param.put("gender", gender);
			final EditText profile_height_text = (EditText)findViewById(R.id.profile_height);
			String profile_height_val = profile_height_text.getText().toString();
			json_param.put("height", profile_height_val);
			final EditText profile_weight_text = (EditText)findViewById(R.id.profile_weight);
			String profile_weight_val = profile_weight_text.getText().toString();
			json_param.put("weight", profile_weight_val);
			final EditText profile_goal_text = (EditText)findViewById(R.id.profile_goal);
			String profile_goal_val = profile_goal_text.getText().toString();
			json_param.put("goal", profile_goal_val);	
			
			//postData 함수로 데이터 전송
			CommonHttp ch = new CommonHttp();	
			String result_json = ch.postData("http://211.110.61.51:3000/profile", json_param);		
							
			if(result_json.equals("error")){
				Toast toast = Toast.makeText(this, "프로필 데이터 전송 실패!", Toast.LENGTH_SHORT); 
				toast.show(); 
			}
			else{		
				JSONObject json_data = new JSONObject(result_json);
				String result_flag = json_data.getString("result");			
				// 회원가입 성공시
				if(result_flag.equals("success")){
					
		            //매칭 페이지로 이동
		            Intent intent = new Intent(ProfileActivity.this, MatchingActivity.class);
					startActivity(intent);            
			  	
				}
				else{
					Toast toast = Toast.makeText(this, json_data.getString("msg"), Toast.LENGTH_SHORT); 
					toast.show(); 
				}
			}
				
		}
		catch(JSONException e) {
			Log.e("Error", "putJSON", e);
		}
	}
}
