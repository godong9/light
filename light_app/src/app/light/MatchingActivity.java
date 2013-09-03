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
import android.widget.TextView;
import android.widget.Toast;

public class MatchingActivity extends CommonActivity {
	
	private int term = 0;
	private int goal = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_matching);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.basic_title);
	

	}
	
	public void onMatchingMinusBtn(View v){
		final TextView goal_text = (TextView)findViewById(R.id.matching_weight);	
		String goal_str = goal_text.getText().toString();
		goal_str = goal_str.replaceAll("kg","");
		int tmp_goal = Integer.valueOf(goal_str);
		if(goal>0){
			goal = tmp_goal-1;
			goal_text.setText(String.valueOf(goal)+"kg");
		}
	}
	
	public void onMatchingPlusBtn(View v){
		final TextView goal_text = (TextView)findViewById(R.id.matching_weight);	
		String goal_str = goal_text.getText().toString();
		goal_str = goal_str.replaceAll("kg","");
		int tmp_goal = Integer.valueOf(goal_str);
		if(goal<30){
			goal = tmp_goal+1;
			goal_text.setText(String.valueOf(goal)+"kg");
		}
	}
	public void onMatching1weekBtn(View v){
		term = 1;
	}
	public void onMatching2weekBtn(View v){
		term = 2;
	}
	public void onMatching4weekBtn(View v){
		term = 4;
	}
	public void onMatching6weekBtn(View v){
		term = 6;
	}
	
	public void onMatchingCompleteBtn(View v){
		JSONObject json_param = new JSONObject();
		
		try {		
			//입력폼으로부터 데이터 가져와서 JSON 오브젝트에 저장
			json_param.put("goal", goal);
			json_param.put("term", term);	
			
			//postData 함수로 데이터 전송
			CommonHttp ch = new CommonHttp();	
			String result_json = ch.postData("http://211.110.61.51:3000/matching", json_param);		
							
			if(result_json.equals("error")){
				Toast toast = Toast.makeText(this, "매칭 데이터 전송 실패!", Toast.LENGTH_SHORT); 
				toast.show(); 
			}
			else{		
				JSONObject json_data = new JSONObject(result_json);
				String result_flag = json_data.getString("result");			
				// 회원가입 성공시
				if(result_flag.equals("success")){			
		            //매칭대기 페이지로 이동
		            Intent intent = new Intent(MatchingActivity.this, WaitingActivity.class);
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
