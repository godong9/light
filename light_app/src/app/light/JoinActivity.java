package app.light;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.GCMRegistrar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import static app.light.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static app.light.CommonUtilities.EXTRA_MESSAGE;
import static app.light.CommonUtilities.SENDER_ID;
import static app.light.CommonUtilities.SERVER_URL;

import com.google.android.gcm.GCMRegistrar;

public class JoinActivity extends CommonActivity {
    /** Called when the activity is first created. */
	AsyncTask<Void, Void, Void> mRegisterTask;
	private static final String PACKAGE_NAME="app.light";
	private String user_email = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_join);
    }
    
    // 회원가입 완료 버튼 클릭시
    public void onJoinCompleteBtn(View v) {
    	
		JSONObject json_param = new JSONObject();
				
		try {		
			//입력폼으로부터 데이터 가져와서 JSON 오브젝트에 저장
			final EditText id_text = (EditText)findViewById(R.id.join_id);
			String email_val = id_text.getText().toString();
			user_email = email_val;
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
			
			json_param.put("group_id", "-1");
			
			//postData 함수로 데이터 전송
			CommonHttp ch = new CommonHttp();	
			String result_json = ch.postData("http://211.110.61.51:3000/join", json_param);		
							
			if(result_json.equals("error")){
				Toast toast = Toast.makeText(this, "회원가입 실패!", Toast.LENGTH_SHORT); 
				toast.show(); 
			}
			else{		
				JSONObject json_data = new JSONObject(result_json);
				String result_flag = json_data.getString("result");			
				// 회원가입 성공시
				if(result_flag.equals("success")){
			
					//푸시서버 GCM 관련 설정
					checkNotNull(SERVER_URL, "SERVER_URL");
			        checkNotNull(SENDER_ID, "SENDER_ID");
			        // Make sure the device has the proper dependencies.
			        GCMRegistrar.checkDevice(this);
			        // Make sure the manifest was properly set - comment out this line
			        // while developing the app, then uncomment it when it's ready.
			        GCMRegistrar.checkManifest(this);
			        registerReceiver(mHandleMessageReceiver,
			                new IntentFilter(DISPLAY_MESSAGE_ACTION));
			        final String regId = GCMRegistrar.getRegistrationId(this);
			        
			        if (regId.equals("")) {
			            // Automatically registers application on startup.
			            GCMRegistrar.register(this, SENDER_ID);
			            System.out.println("GCM Register");
			        } else {
			            // Device is already registered on GCM, check server.
			            if (GCMRegistrar.isRegisteredOnServer(this)) {
			                // Skips registration.         	
			                System.out.println(getString(R.string.already_registered));
			            } else {
			                // Try to register again, but not in the UI thread.
			                // It's also necessary to cancel the thread onDestroy(),
			                // hence the use of AsyncTask instead of a raw thread.
			                final Context context = this;
			                mRegisterTask = new AsyncTask<Void, Void, Void>() {

			                    @Override
			                    protected Void doInBackground(Void... params) {
			                        boolean registered =
			                                ServerUtilities.register(context, regId, user_email);
			                        // At this point all attempts to register with the app
			                        // server failed, so we need to unregister the device
			                        // from GCM - the app will try to register again when
			                        // it is restarted. Note that GCM will send an
			                        // unregistered callback upon completion, but
			                        // GCMIntentService.onUnregistered() will ignore it.
			                        if (!registered) {
			                            GCMRegistrar.unregister(context);
			                        }
			                        return null;
			                    }

			                    @Override
			                    protected void onPostExecute(Void result) {
			                        mRegisterTask = null;
			                    }

			                };
			                mRegisterTask.execute(null, null, null);
			            }
			            
			            //프로필 페이지로 이동
			            Intent intent = new Intent(JoinActivity.this, ProfileActivity.class);
						startActivity(intent);            
			        }
					
					
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
    
    private final BroadcastReceiver mHandleMessageReceiver =
            new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            
            System.out.println("Receive Content => "+intent.getStringExtra("content"));
        }
    };
    
    private void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(
                    getString(R.string.error_config, name));
        }
    }
}