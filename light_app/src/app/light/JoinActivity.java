package app.light;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends CommonActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_join);
    }
       
    public void onJoinCompleteBtn(View v) {
		
		JSONObject json_param = new JSONObject();
				
		try {		
			final EditText id_text = (EditText)findViewById(R.id.join_id);
			String email_val = id_text.getText().toString();
			json_param.put("email", email_val);	
			final EditText password_text = (EditText)findViewById(R.id.join_password);
			String password_val = password_text.getText().toString();
			json_param.put("password", password_val);
			final EditText re_password_text = (EditText)findViewById(R.id.join_re_password);
			String re_password_val = re_password_text.getText().toString();
			json_param.put("re_password", re_password_val);
			final EditText nickname_text = (EditText)findViewById(R.id.join_nickname);
			String nickname_val = nickname_text.getText().toString();
			json_param.put("nickname", nickname_val);
						
			String result_json = postData("http://211.110.61.51:3000/join", json_param);		
							
			if(result_json.equals("error")){
				Toast toast = Toast.makeText(this, "회원가입 실패!", Toast.LENGTH_SHORT); 
				toast.show(); 
			}
			else{		
				JSONObject json_data = new JSONObject(result_json);
				String result_flag = json_data.getString("result");
	
				if(result_flag.equals("success")){
					Intent intent = new Intent(JoinActivity.this, FragmentActivity.class);
					startActivity(intent);
				}
			}		
		}
		catch(JSONException e) {
			Log.e("Error", "putJSON", e);
		}
	}
}