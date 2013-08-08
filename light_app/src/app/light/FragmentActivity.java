package app.light;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class FragmentActivity extends Activity {
	
	private AlertDialogWindow popup_dialog;
	
	private static final int ID_NOTIFY = 1;
	private static final int ID_HELP   = 2;
	private static final int ID_LOGOUT   = 3;
	private static final int ID_FOOD     = 7;
	private static final int ID_EXERCISE     = 8;
	private static final int ID_WEIGHT     = 9;
	private static final int ID_CAMERA     = 10;
	private static final int ID_ALBUM     = 11;
	private static final int ID_MISSION     = 12;
	
	private QuickAction settingPopup;
	private QuickAction writePopup;
	private QuickAction cameraPopup;
	
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
	    
	    RivalFrag rf = new RivalFrag();   
	    tr.add(R.id.detail_frag, rf);
	    tr.commit();
	    
	    findViewById(R.id.rival_btn).setOnClickListener(btnListener);
	    findViewById(R.id.timeline_btn).setOnClickListener(btnListener);
	    findViewById(R.id.community_btn).setOnClickListener(btnListener);
	
	    
	    //설정 버튼 팝업 관련 코드	
		
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
  					Toast.makeText(getApplicationContext(), "알림설정 변경", Toast.LENGTH_SHORT).show();
  				} else {
  					Toast.makeText(getApplicationContext(), actionItem.getTitle() + " selected", Toast.LENGTH_SHORT).show();
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
  		////////////////

        
        //기록 버튼 팝업 관련 코드
	    
	    ActionItem write_food 	= new ActionItem(ID_FOOD, "음식 기록");
		ActionItem write_exercise 	= new ActionItem(ID_EXERCISE, "운동 기록");
        ActionItem write_weight 	= new ActionItem(ID_WEIGHT, "체중 기록");
      
        write_food.setSticky(true);
        write_exercise.setSticky(true);
        write_weight.setSticky(true);
        
        writePopup = new QuickAction(this, QuickAction.HORIZONTAL);
        
        //add action items into QuickAction
        writePopup.addActionItem(write_food);
        writePopup.addActionItem(write_exercise);
        writePopup.addActionItem(write_weight);   

        //Set listener for action item clicked
        writePopup.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
  			@Override
  			public void onItemClick(QuickAction source, int pos, int actionId) {				
  				ActionItem actionItem = writePopup.getActionItem(pos);
                   
  				//here we can filter which action item was clicked with pos or actionId parameter
  				if (actionId == ID_FOOD) {
  					Toast.makeText(getApplicationContext(), "음식 기록", Toast.LENGTH_SHORT).show();
  				} else if (actionId == ID_EXERCISE) {
  					Toast.makeText(getApplicationContext(), "운동 기록", Toast.LENGTH_SHORT).show();
  				} else if (actionId == ID_WEIGHT) {
  					Toast.makeText(getApplicationContext(), "체중 기록", Toast.LENGTH_SHORT).show();
  				} 
  			}
  		});		
        
        //set listnener for on dismiss event, this listener will be called only if QuickAction dialog was dismissed
  		//by clicking the area outside the dialog.
        writePopup.setOnDismissListener(new QuickAction.OnDismissListener() {			
  			@Override
  			public void onDismiss() {
  				ImageButton wb = (ImageButton)findViewById(R.id.write_btn);
  				wb.setSelected(false);
  			}
  		});
        ////////////////
        
        
        //카메라 버튼 팝업 관련 코드
	    
	    ActionItem camera_camera 	= new ActionItem(ID_CAMERA, "사진 촬영");
		ActionItem camera_album 	= new ActionItem(ID_EXERCISE, "앨범");
        ActionItem camera_mission 	= new ActionItem(ID_MISSION, "인증샷");
      
        camera_camera.setSticky(true);
        camera_album.setSticky(true);
        camera_mission.setSticky(true);
        
        cameraPopup = new QuickAction(this, QuickAction.HORIZONTAL);
        
        //add action items into QuickAction
        cameraPopup.addActionItem(camera_camera);
        cameraPopup.addActionItem(camera_album);
        cameraPopup.addActionItem(camera_mission);   

        //Set listener for action item clicked
        cameraPopup.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
  			@Override
  			public void onItemClick(QuickAction source, int pos, int actionId) {				
  				ActionItem actionItem = writePopup.getActionItem(pos);
                   
  				//here we can filter which action item was clicked with pos or actionId parameter
  				if (actionId == ID_CAMERA) {
  					Toast.makeText(getApplicationContext(), "사진 촬영", Toast.LENGTH_SHORT).show();
  				} else if (actionId == ID_EXERCISE) {
  					Toast.makeText(getApplicationContext(), "앨범", Toast.LENGTH_SHORT).show();
  				} else if (actionId == ID_MISSION) {
  					Toast.makeText(getApplicationContext(), "미션샷", Toast.LENGTH_SHORT).show();
  				} 
  			}
  		});		
        
        //set listnener for on dismiss event, this listener will be called only if QuickAction dialog was dismissed
  		//by clicking the area outside the dialog.
        cameraPopup.setOnDismissListener(new QuickAction.OnDismissListener() {			
  			@Override
  			public void onDismiss() {
  				ImageButton cb = (ImageButton)findViewById(R.id.camera_btn);
  				cb.setSelected(false);
  			}
  		});
        ////////////////
        
	}
	
	//메뉴 버튼 관련 코드
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
				
				RivalFrag rf = new RivalFrag();	    
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
	
	
	
	public class AlertDialogWindow extends DialogFragment {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
			LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
			mBuilder.setView(mLayoutInflater.inflate(R.layout.dialog_popup, null));

			return mBuilder.create();
		}

		@Override
		public void onStop() {
			super.onStop();
		}
	}
	
	/* Alert 다이얼로그 관련 소스
	public void clickSettingBtn(View v) {
		//Alert Dialog 형태의 팝업
		popup_dialog = new AlertDialogWindow();	
		popup_dialog.show(getFragmentManager(), "setting_popup");	
	}
	 */
	
	public void clickSettingBtn(View v) {
		v.setSelected(true);
		settingPopup.show(v);
	}
	
	
	public void clickTimelineWriteBtn(View v) {
		v.setSelected(true);
		writePopup.show(v);
	}
	
	
	public void clickTimelineCameraBtn(View v) {
		v.setSelected(true);
		cameraPopup.show(v);	
	}
	
	public void clickTimelineSendBtn(View v) {
		final EditText chat_text = (EditText)findViewById(R.id.chat_val);
		String chat_val = chat_text.getText().toString();
		if( chat_val.equals("")){
			Toast.makeText(getApplicationContext(), "내용을 입력하세요!", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(getApplicationContext(), chat_val, Toast.LENGTH_SHORT).show();
		}		
	}
	
	public void clickRivalUserBtn(View v) {
		if(v.getId() == R.id.rival_user1_click){
			popup_dialog = new AlertDialogWindow();		
			popup_dialog.show(getFragmentManager(), "User1 Popup");	
			//popup_dialog.dismiss();
		}	
		
		else if(v.getId() == R.id.rival_user2_click){
			popup_dialog = new AlertDialogWindow();		
			popup_dialog.show(getFragmentManager(), "User2 Popup");	
			//popup_dialog.dismiss();
		}
	
		else if(v.getId() == R.id.rival_user3_click){
			popup_dialog = new AlertDialogWindow();		
			popup_dialog.show(getFragmentManager(), "User3 Popup");	
			//popup_dialog.dismiss();
		}	
		else if(v.getId() == R.id.rival_user4_click){
			popup_dialog = new AlertDialogWindow();		
			popup_dialog.show(getFragmentManager(), "User4 Popup");	
			//popup_dialog.dismiss();
		}	
	}
	
	public void rivalDialogClickBtn(View v) {
		if(v.getId() == R.id.rival_dialog_exit){
			
			popup_dialog.dismiss();
		}		
		
	}
	
	
}