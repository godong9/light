package app.light;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

//커스텀 AlertDialog 구현
	public class AlertDialogWindow extends DialogFragment {
		private Context context;
		private int type;

		public AlertDialogWindow(int type) {
			this.type=type;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
			LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
			
			if(type==0){
				mBuilder.setView(mLayoutInflater.inflate(R.layout.popup_my_dialog, null));
			}
			else{
				mBuilder.setView(mLayoutInflater.inflate(R.layout.popup_other_dialog, null));
			}
			context = getActivity();
				
			return mBuilder.create();
		}
		
		@Override 
		public void onStart() {
			//다이얼로그 외부 클릭시 다이얼로그 사라지도록 구현
			if (getDialog() != null)
				getDialog().setCanceledOnTouchOutside(true);
			
			super.onStart();
			
			/*
			 * 다이얼로그 내부 버튼 클릭시 이벤트 처리
			 * 
			 */
			ImageButton dialog_exit_btn;
			if(type==0){
				dialog_exit_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_exit);
			}
			else{
				dialog_exit_btn = (ImageButton) getDialog().findViewById(R.id.rival_other_dialog_exit);
			}
			
			dialog_exit_btn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{	
					dismiss();
				}
			});
		}
		
		@Override
		public void onStop() {
			super.onStop();
		}
	}