package app.light;

import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

//커스텀 AlertDialog 구현
	public class RivalDialogWindow extends DialogFragment {
		private Context context;
		private int type;
		private JSONObject user_info = new JSONObject();
		private Resources res;
		private String packName = "app.light";
		private LinearLayout home_layout;
		private LinearLayout history_layout;
		
		public RivalDialogWindow(int type, JSONObject user_info) {
			this.type = type;
			this.user_info = user_info;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceStadte) {
			//Theme_Holo_Light_Panel 이용해서 테두리 없게 만들어줌
			AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Panel);
			LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
			
			View view; 
			
			//타입에 따라 자기 다이얼로그 or 다른 사람 다이얼로그
			if(type==1){
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
			
			// 다이얼로그 Home 페이지 내 데이터 적용
			TextView myNickName = (TextView)getDialog().findViewById(R.id.rival_dialog_nickname);
			TextView myChat = (TextView)getDialog().findViewById(R.id.rival_dialog_word);
			TextView myHeight = (TextView)getDialog().findViewById(R.id.rival_dialog_height);
			TextView myWeight = (TextView)getDialog().findViewById(R.id.rival_dialog_weight);
			TextView myGoal = (TextView)getDialog().findViewById(R.id.rival_dialog_goal);
			ImageButton myCharacter = (ImageButton)getDialog().findViewById(R.id.rival_dialog_character);
			TextView myLimitCalorie = (TextView)getDialog().findViewById(R.id.rival_dialog_limit_calorie);
			TextView myEatCalorie = (TextView)getDialog().findViewById(R.id.rival_dialog_eat_calorie);
			TextView myExerciseCalorie = (TextView)getDialog().findViewById(R.id.rival_dialog_exercise_calorie);
			
			
			try {
				myNickName.setText(user_info.getString("nickname"));
				myChat.setText(user_info.getString("chat_ballon"));
				myHeight.setText("키       "+user_info.getString("height")+"cm");
				myWeight.setText("체중     "+user_info.getString("weight")+"kg");
				myGoal.setText(user_info.getString("goal_weight")+"kg");
				myLimitCalorie.setText(user_info.getString("day_calorie")+" Kcal");
				myEatCalorie.setText(user_info.getString("food_calorie")+" Kcal");
				myExerciseCalorie.setText(user_info.getString("exercise_calorie")+" Kcal");
				
				res = getResources();
				
				String my_character = "@drawable/character_"+user_info.getString("character");
			
				myCharacter.setBackgroundResource(res.getIdentifier(my_character, "drawable", packName));
	
			}
			catch(Exception e){
				System.out.println("다이얼로그 에러 발생");
			}
			
			
			/*
			 * 다이얼로그 내부 버튼 클릭시 이벤트 처리
			 * 
			 */
			ImageButton dialog_exit_btn;
	
			
			if(type == 1){
				dialog_exit_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_exit);
				
				final ImageButton dialog_page_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_page_btn);
				final ImageButton dialog_closet_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_closet_btn);
				final ImageButton dialog_shop_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_shop_btn);
				final ImageButton dialog_history_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_history_btn);
							
				home_layout = (LinearLayout) getDialog().findViewById(R.id.rival_my_dialog_layout);
				history_layout = (LinearLayout) getDialog().findViewById(R.id.rival_history_layout);
						
				dialog_page_btn.setSelected(true);
				
				dialog_page_btn.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v)
					{	
						dialog_page_btn.setSelected(true);
						dialog_closet_btn.setSelected(false);
						dialog_shop_btn.setSelected(false);
						dialog_history_btn.setSelected(false);	
						
						history_layout.setVisibility(View.GONE);
						home_layout.setVisibility(View.VISIBLE);			
					}
				});
				
				dialog_closet_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{				
						dialog_page_btn.setSelected(false);
						dialog_closet_btn.setSelected(true);
						dialog_shop_btn.setSelected(false);
						dialog_history_btn.setSelected(false);
					}	
				});
				
				dialog_shop_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{	
						dialog_page_btn.setSelected(false);
						dialog_closet_btn.setSelected(false);
						dialog_shop_btn.setSelected(true);
						dialog_history_btn.setSelected(false);
					}
				});
				
				dialog_history_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{	
						dialog_page_btn.setSelected(false);
						dialog_closet_btn.setSelected(false);
						dialog_shop_btn.setSelected(false);
						dialog_history_btn.setSelected(true);
			
						home_layout.setVisibility(View.GONE);
						history_layout.setVisibility(View.VISIBLE);
					}
				});
			
			}
			else{
				
				home_layout = (LinearLayout) getDialog().findViewById(R.id.rival_other_dialog_layout);
				history_layout = (LinearLayout) getDialog().findViewById(R.id.rival_history_layout);
				
				dialog_exit_btn = (ImageButton) getDialog().findViewById(R.id.rival_other_dialog_exit);
				
				final ImageButton dialog_page_btn = (ImageButton) getDialog().findViewById(R.id.rival_other_dialog_page_btn);
				final ImageButton dialog_history_btn = (ImageButton) getDialog().findViewById(R.id.rival_other_dialog_history_btn);
				
				dialog_page_btn.setSelected(true);
				
				dialog_page_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{	
						dialog_page_btn.setSelected(true);
						dialog_history_btn.setSelected(false);	
						
						history_layout.setVisibility(View.GONE);
						home_layout.setVisibility(View.VISIBLE);				
					}
				});
				
				dialog_history_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{				
						dialog_page_btn.setSelected(false);
						dialog_history_btn.setSelected(true);
						
						home_layout.setVisibility(View.GONE);
						history_layout.setVisibility(View.VISIBLE);
					}	
				});
				
			}
			
			//공통 다이얼로그 종료 버튼 이벤트 리스너
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