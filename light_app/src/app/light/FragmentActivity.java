package app.light;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class FragmentActivity extends Activity {
	
	//private AlertDialogWindow popup_dialog;
	
	private static final int ID_UP     = 1;
	private static final int ID_DOWN   = 2;
	private static final int ID_SEARCH = 3;
	private static final int ID_INFO   = 4;
	private static final int ID_ERASE  = 5;	
	private static final int ID_OK     = 6;
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
	    
	    ActionItem setting_my_info 	= new ActionItem(ID_DOWN, "내 정보");
		ActionItem prevItem 	= new ActionItem(ID_UP, "Prev");
        ActionItem searchItem 	= new ActionItem(ID_SEARCH, "Find");
        ActionItem infoItem 	= new ActionItem(ID_INFO, "Info");
        ActionItem eraseItem 	= new ActionItem(ID_ERASE, "Clear");
        ActionItem okItem 		= new ActionItem(ID_OK, "OK");
	    
        prevItem.setSticky(true);
        
        settingPopup = new QuickAction(this, QuickAction.VERTICAL);
        
        //add action items into QuickAction
        settingPopup.addActionItem(setting_my_info);
        settingPopup.addActionItem(prevItem);
        settingPopup.addActionItem(searchItem);
        settingPopup.addActionItem(infoItem);
        settingPopup.addActionItem(eraseItem);
        settingPopup.addActionItem(okItem);
            
        //Set listener for action item clicked
        settingPopup.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
  			@Override
  			public void onItemClick(QuickAction source, int pos, int actionId) {				
  				ActionItem actionItem = settingPopup.getActionItem(pos);
                   
  				//here we can filter which action item was clicked with pos or actionId parameter
  				if (actionId == ID_SEARCH) {
  					Toast.makeText(getApplicationContext(), "Let's do some search action", Toast.LENGTH_SHORT).show();
  				} else if (actionId == ID_INFO) {
  					Toast.makeText(getApplicationContext(), "I have no info this time", Toast.LENGTH_SHORT).show();
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
  				Toast.makeText(getApplicationContext(), "Dismissed", Toast.LENGTH_SHORT).show();
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
        ActionItem camera_mission 	= new ActionItem(ID_MISSION, "미션샷");
      
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
	
	/* Alert 다이얼로그 관련 소스
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
	 */
	
	public void clickSettingBtn(View v) {
		/*Alert Dialog 형태의 팝업
		popup_dialog = new AlertDialogWindow();	
		popup_dialog.show(getFragmentManager(), "setting_popup");
		*/
		
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
	
	
}