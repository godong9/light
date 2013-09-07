package app.light;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class BaseFragment extends Activity {
	
	private QuickAction settingPopup;
	
	// 설정 관련 버튼 상수
	private static final int ID_NOTIFY = 1;
	private static final int ID_HELP   = 2;
	private static final int ID_LOGOUT   = 3;
	private static int rf_type = 0;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	    setContentView(R.layout.frag_layout);
	    
	    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
	    ImageButton diary_btn = (ImageButton)findViewById(R.id.rival_btn);
	    diary_btn.setSelected(true);
	    
	    FragmentManager fm = getFragmentManager();
	    FragmentTransaction tr = fm.beginTransaction();
	    
	    //매칭여부 확인
	    CommonHttp ch = new CommonHttp();	
		String result_json = ch.getData("http://211.110.61.51:3000/matching_status");		
		
		if(result_json.equals("error")){
			System.out.println("데이터 수신 실패!");
		}
		else{			
			//System.out.println("성공");		
			try{
				JSONObject json_data = new JSONObject(result_json);
				rf_type = Integer.valueOf(json_data.getString("matching_status"));
				//System.out.println("RF_TYPE=>"+rf_type);
			}	
			catch(Exception e){
				System.out.println("매칭 상태 에러");
			}
		}
	    
	    RivalFrag rf = new RivalFrag(rf_type);   
	    tr.add(R.id.detail_frag, rf);
	    tr.commit();
	    
	    // 각 버튼마다 버튼 리스너 등록
	    findViewById(R.id.rival_btn).setOnClickListener(btnListener);
	    findViewById(R.id.timeline_btn).setOnClickListener(btnListener);
	    findViewById(R.id.community_btn).setOnClickListener(btnListener);
	   
	    // 설정 버튼 팝업 관련 코드		
        ActionItem setting_notify 	= new ActionItem(ID_NOTIFY, "알림 설정");
        ActionItem setting_help 	= new ActionItem(ID_HELP, "도움말");
        ActionItem setting_logout 	= new ActionItem(ID_LOGOUT, "로그아웃");
  
        setting_notify.setSticky(true);
       
        settingPopup = new QuickAction(this, QuickAction.VERTICAL);
        
        //add action items into QuickAction
        settingPopup.addActionItem(setting_notify);
        settingPopup.addActionItem(setting_help);
        settingPopup.addActionItem(setting_logout);
            
        //Set listener for action item clicked
        settingPopup.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
  			@Override
  			public void onItemClick(QuickAction source, int pos, int actionId) {				
  				ActionItem actionItem = settingPopup.getActionItem(pos);
                   
  				//here we can filter which action item was clicked with pos or actionId parameter
  				if (actionId == ID_NOTIFY) {
  					Toast.makeText(getApplicationContext(), actionItem.getTitle() + " (준비중입니다..)", Toast.LENGTH_SHORT).show();
  				} else if (actionId == ID_HELP) {
  					Toast.makeText(getApplicationContext(), actionItem.getTitle() + " (준비중입니다..)", Toast.LENGTH_SHORT).show();
  				} else if (actionId == ID_LOGOUT) {
   					//Toast.makeText(getApplicationContext(), actionItem.getTitle() + " selected", Toast.LENGTH_SHORT).show();
  					Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
  					intent.putExtra("type", "logout");
  					startActivity(intent);
  				}
  			}
  		});
  		
  		//set listnener for on dismiss event, this listener will be called only if QuickAction dialog was dismissed
  		//by clicking the area outside the dialog.
        settingPopup.setOnDismissListener(new QuickAction.OnDismissListener() {			
  			@Override
  			public void onDismiss() {
  				ImageButton sb = (ImageButton)findViewById(R.id.setting_btn);
  				sb.setSelected(false);
  			}
  		});      
	}	

	//메뉴 버튼 관련 이벤트 리스너
	Button.OnClickListener btnListener = new View.OnClickListener() {
		public void onClick(View v) {
			FragmentManager fm = getFragmentManager();
		    FragmentTransaction tr = fm.beginTransaction();
			
		    ImageButton rival_btn = (ImageButton)findViewById(R.id.rival_btn);
		    ImageButton timeline_btn = (ImageButton)findViewById(R.id.timeline_btn);
		    ImageButton community_btn = (ImageButton)findViewById(R.id.community_btn);
	
			if(v.getId() == R.id.rival_btn){
				rival_btn.setSelected(true);
				timeline_btn.setSelected(false);
				community_btn.setSelected(false);	
				
				RivalFrag rf = new RivalFrag(rf_type);	    
			    tr.replace(R.id.detail_frag, rf);
			    tr.commit();
			}	
			else if(v.getId() == R.id.timeline_btn){
				rival_btn.setSelected(false);
				timeline_btn.setSelected(true);
				community_btn.setSelected(false);	
				
				TimelineFrag tf = new TimelineFrag();	    
			    tr.replace(R.id.detail_frag, tf);
			    tr.commit();
			}
			else if(v.getId() == R.id.community_btn){
				rival_btn.setSelected(false);
				timeline_btn.setSelected(false);
				community_btn.setSelected(true);	
				
				CommunityFrag cf = new CommunityFrag();	    
			    tr.replace(R.id.detail_frag, cf);
			    tr.commit();	
			}
		}
	};
	
	// 설정 버튼 클릭시
	public void clickSettingBtn(View v) {
		v.setSelected(true);
		settingPopup.show(v);
	}
	
}