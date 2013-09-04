package app.light;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import static app.light.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static app.light.CommonUtilities.EXTRA_MESSAGE;
import static app.light.CommonUtilities.SENDER_ID;
import static app.light.CommonUtilities.SERVER_URL;

import com.google.android.gcm.GCMRegistrar;

public class LoginActivity extends CommonActivity {
	
	AsyncTask<Void, Void, Void> mRegisterTask;
	private static final String PACKAGE_NAME="app.light";
	private String reg_id = "";
	private boolean is_registered = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_login);
		
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
                is_registered = true;
                reg_id = regId;
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                
            	final Context context = this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {
                	
                    @Override
                    protected Void doInBackground(Void... params) {
                        boolean registered =
                                ServerUtilities.register(context, regId, "");
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
        }
        
		Intent intent = getIntent();   // 값을 받기 위한 Intent 생성

		String intent_str = intent.getStringExtra("type");
		
		String intent_nickname = intent.getStringExtra("nickname");
		String intent_content = intent.getStringExtra("content");
		
		if(intent_nickname != null && intent_content != null){
			System.out.println("MSG => "+intent_nickname+" : "+intent_content);
		}
		
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
		
	    System.out.println("intent_str=>"+intent_str);
	    
		if(intent_str == null){	//처음 앱 접속시
			if(!checkDB(this)){
				dumpDB(this);
				System.out.println("DUMP DB");
			}
			
			startActivity(new Intent(this, SplashActivity.class));
			
			// 로그인 상태 유지가 체크되었을 때
			if(keep_login_boolean){    			
			    
			    Handler login_handler = new Handler () {
			    	@Override
			    	public void handleMessage(Message msg) {
			    		ImageButton login_btn = (ImageButton)findViewById(R.id.login_btn);
			 		    onLoginBtn(login_btn);
			    	}
		        };
			
		        login_handler.sendEmptyMessageDelayed(0, 2100);	       
		    }		
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
			
			CommonHttp ch = new CommonHttp();	
			
			String result_json = ch.postData("http://211.110.61.51:3000/login", json_param);		
			
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
					
					if(is_registered){
						json_param.put("reg_id", reg_id);
		                String update_result = ch.postData("http://211.110.61.51:3000/update_reg_id", json_param);  
					}
	                	
					Intent intent = new Intent(LoginActivity.this, BaseFragment.class);
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
	
	// 회원가입 클릭시 회원가입 화면으로 이동
	public void onJoinBtn(View v) {
		
		// 테스트용 임시로 레이아웃 교체
		Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
		startActivity(intent);
	}
	

    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        unregisterReceiver(mHandleMessageReceiver);
        GCMRegistrar.onDestroy(this);
        super.onDestroy();  	
    }

    private void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(
                    getString(R.string.error_config, name));
        }
    }
    
    
	public boolean checkDB(Context mContext) {
		String path = Environment.getExternalStorageDirectory().toString();
		String filePath = path+"/Light_Diet/" + PACKAGE_NAME + "/db/food.db";
		File file = new File(filePath);
		return file.exists();
	}

	// Dump DB
	public void dumpDB(Context mContext) {
		AssetManager manager = mContext.getAssets();
		String path = Environment.getExternalStorageDirectory().toString();
		String folderPath = path+"/Light_Diet/" + PACKAGE_NAME + "/db";
		String filePath = path+"/Light_Diet/" + PACKAGE_NAME + "/db/food.db";
		
		File folder = new File(folderPath);
		File file = new File(filePath);

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			InputStream is = manager.open("db/food_db.sqlite");
			BufferedInputStream bis = new BufferedInputStream(is);

			if (folder.exists()) {
			} else {
				folder.mkdirs();
			}

			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}
			
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			int read = -1;
			byte[] buffer = new byte[1024];
			while ((read = bis.read(buffer, 0, 1024)) != -1) {
				System.out.println("BUFFER"+buffer);
				bos.write(buffer, 0, read);
			}

			bos.flush();
			bos.close();
			fos.close();
			bis.close();
			is.close();

		} catch (IOException e) {
			Log.e("ErrorMessage : ", e.getMessage());
		}
	}
	
}
