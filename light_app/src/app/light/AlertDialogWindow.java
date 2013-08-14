package app.light;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

//커스텀 AlertDialog 구현
	public class AlertDialogWindow extends DialogFragment {
		private Context context;
		private int type;

		public AlertDialogWindow(int type) {
			this.type=type;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceStadte) {
			//Theme_Holo_Light_Panel 이용해서 테두리 없게 만들어줌
			AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Panel);
			LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
			
			View view; 
			
			//타입에 따라 자기 다이얼로그 or 다른 사람 다이얼로그
			if(type==0){
				view = mLayoutInflater.inflate(R.layout.popup_my_dialog, null);
				mBuilder.setView(view);	
			}
			else{
				view = mLayoutInflater.inflate(R.layout.popup_other_dialog, null);
				mBuilder.setView(view);
			}

			context = getActivity();	
		
			return mBuilder.create();
		}
		
		@Override 
		public void onStart() {
			//다이얼로그 외부 클릭시 다이얼로그 사라지도록 구현
			if (getDialog() != null) {
				//다이얼로그 생성시 배경 어두워지게 설정
				WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
				lp.dimAmount=0.8f;
				
				getDialog().setCanceledOnTouchOutside(true);
				getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				getDialog().getWindow().setAttributes(lp);
			}
			
			super.onStart();
			
			/*
			 * 다이얼로그 내부 버튼 클릭시 이벤트 처리
			 * 
			 */
			ImageButton dialog_exit_btn;
			if(type == 0){
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