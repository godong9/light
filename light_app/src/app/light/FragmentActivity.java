package app.light;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FragmentActivity extends Activity {
	
	private PopupDialog popup_dialog;
	
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
	}
		
	public static class PopupDialog extends DialogFragment {

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

	public void clickSettingBtn(View v) {
		popup_dialog = new PopupDialog();	
		popup_dialog.show(getFragmentManager(), "setting_popup");
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
				
				
			}	
		}
	};
	
	
}