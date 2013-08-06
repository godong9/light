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
	private QuickAction quickAction;
	
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
	
	    
	    //퀵액션 3d 관련
	    
	    ActionItem nextItem 	= new ActionItem(ID_DOWN, "Next", getResources().getDrawable(R.drawable.menu_down_arrow));
		ActionItem prevItem 	= new ActionItem(ID_UP, "Prev", getResources().getDrawable(R.drawable.menu_up_arrow));
        ActionItem searchItem 	= new ActionItem(ID_SEARCH, "Find", getResources().getDrawable(R.drawable.menu_search));
        ActionItem infoItem 	= new ActionItem(ID_INFO, "Info", getResources().getDrawable(R.drawable.menu_info));
        ActionItem eraseItem 	= new ActionItem(ID_ERASE, "Clear", getResources().getDrawable(R.drawable.menu_eraser));
        ActionItem okItem 		= new ActionItem(ID_OK, "OK", getResources().getDrawable(R.drawable.menu_ok));
	    
        prevItem.setSticky(true);
        nextItem.setSticky(true);
        
        quickAction = new QuickAction(this, QuickAction.VERTICAL);
        
        //add action items into QuickAction
        quickAction.addActionItem(nextItem);
		quickAction.addActionItem(prevItem);
        quickAction.addActionItem(searchItem);
        quickAction.addActionItem(infoItem);
        quickAction.addActionItem(eraseItem);
        quickAction.addActionItem(okItem);
        
        
        //Set listener for action item clicked
  		quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
  			@Override
  			public void onItemClick(QuickAction source, int pos, int actionId) {				
  				ActionItem actionItem = quickAction.getActionItem(pos);
                   
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
  		quickAction.setOnDismissListener(new QuickAction.OnDismissListener() {			
  			@Override
  			public void onDismiss() {
  				Toast.makeText(getApplicationContext(), "Dismissed", Toast.LENGTH_SHORT).show();
  			}
  		});
        
        
  		//퀵액션 관련 끝
	
	}
	
	/* Alert 다이얼로그 관련 소스
	public class AlertDialogWindow extends DialogFragment {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
			LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
			mBuilder.setView(mLayoutInflater.inflate(R.layout.setting_popup, null));
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
		
		quickAction.show(v);
	}
	
	
	public void clickTimelineWriteBtn(View v) {

		Log.i("[TEST]", "test");
	
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