package app.light;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//커스텀 AlertDialog 구현
	public class RivalDialogWindow extends DialogFragment {
		private Context context;
		private int type;
		private JSONObject user_info = new JSONObject();
		private Resources res;
		private String packName = "app.light";
		private LinearLayout home_layout;
		private LinearLayout closet_layout;
		private LinearLayout shop_layout;
		private LinearLayout history_layout;
		private LinearLayout rival_background_layout;
		
		private ArrayList<HistoryObj> history_list;
		private MyHistoryAdapter history_adapter;
		private ListView history_listview;
		private int history_list_count = 0;
		
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
			
			EditText myChat = null;
			TextView otherChat = null;
			if( type==1 ) {
				myChat = (EditText)getDialog().findViewById(R.id.rival_dialog_word);
			}
			else {
				otherChat = (TextView)getDialog().findViewById(R.id.rival_dialog_word);
			}
			TextView myHeight = (TextView)getDialog().findViewById(R.id.rival_dialog_height);
			TextView myWeight = (TextView)getDialog().findViewById(R.id.rival_dialog_weight);
			TextView myGoal = (TextView)getDialog().findViewById(R.id.rival_dialog_goal);
			ImageButton myCharacter = (ImageButton)getDialog().findViewById(R.id.rival_dialog_character);
			TextView myLimitCalorie = (TextView)getDialog().findViewById(R.id.rival_dialog_limit_calorie);
			TextView myEatCalorie = (TextView)getDialog().findViewById(R.id.rival_dialog_eat_calorie);
			TextView myExerciseCalorie = (TextView)getDialog().findViewById(R.id.rival_dialog_exercise_calorie);
				
			try {
				myNickName.setText(user_info.getString("nickname"));
				
				if( type==1 ) {
					myChat.setText(user_info.getString("chat_ballon"));
				}
				else {
					otherChat.setText(user_info.getString("chat_ballon"));
				}	
				myHeight.setText("키       "+user_info.getString("height")+"cm");
				myWeight.setText("체중    "+user_info.getString("weight")+"kg");
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
	
			//내 다이얼로그 선택시
			if(type == 1){
				dialog_exit_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_exit);
				
				final ImageButton dialog_page_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_page_btn);
				final ImageButton dialog_closet_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_closet_btn);
				final ImageButton dialog_shop_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_shop_btn);
				final ImageButton dialog_history_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_history_btn);
				final ImageButton dialog_chat_modify_btn = (ImageButton) getDialog().findViewById(R.id.rival_dialog_word_btn);
				
				home_layout = (LinearLayout) getDialog().findViewById(R.id.rival_my_dialog_layout);
				closet_layout = (LinearLayout) getDialog().findViewById(R.id.rival_closet_layout);
				shop_layout = (LinearLayout) getDialog().findViewById(R.id.rival_shop_layout);
				history_layout = (LinearLayout) getDialog().findViewById(R.id.rival_history_layout);
				rival_background_layout = (LinearLayout) getDialog().findViewById(R.id.rival_background_layout);
						
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
						closet_layout.setVisibility(View.GONE);
						shop_layout.setVisibility(View.GONE);
						home_layout.setVisibility(View.VISIBLE);	
						
						rival_background_layout.setBackgroundResource(R.drawable.rival_dialog_background);
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
						
						history_layout.setVisibility(View.GONE);				
						shop_layout.setVisibility(View.GONE);
						home_layout.setVisibility(View.GONE);
						closet_layout.setVisibility(View.VISIBLE);
						
						rival_background_layout.setBackgroundResource(R.drawable.rival_etc_background);
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
						
						history_layout.setVisibility(View.GONE);								
						home_layout.setVisibility(View.GONE);
						closet_layout.setVisibility(View.GONE);
						shop_layout.setVisibility(View.VISIBLE);
						
						rival_background_layout.setBackgroundResource(R.drawable.rival_etc_background);
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
						closet_layout.setVisibility(View.GONE);
						shop_layout.setVisibility(View.GONE);
						history_layout.setVisibility(View.VISIBLE);
						
						rival_background_layout.setBackgroundResource(R.drawable.rival_history_background);
						
						setHistoryList();
					}
				});
				
				dialog_chat_modify_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{			
						final EditText et_my_chat = (EditText)getDialog().findViewById(R.id.rival_dialog_word);
						String chat_val = et_my_chat.getText().toString();
						final TextView tv_my_chat = (TextView)getActivity().findViewById(R.id.rival_user1_word);
						tv_my_chat.setText(chat_val);
						modifyChat(chat_val);
					}
				});
			
			}
			else{
				
				home_layout = (LinearLayout) getDialog().findViewById(R.id.rival_other_dialog_layout);
				history_layout = (LinearLayout) getDialog().findViewById(R.id.rival_history_layout);
				rival_background_layout = (LinearLayout) getDialog().findViewById(R.id.rival_background_layout);
				
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
						rival_background_layout.setBackgroundResource(R.drawable.rival_dialog_background);
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
						rival_background_layout.setBackgroundResource(R.drawable.rival_history_background);
					
						setHistoryList();
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
		
		public void modifyChat(String chat_val) {
			JSONObject json_param = new JSONObject();
					
			try {
				
				json_param.put("chat_val", chat_val);
				
				CommonHttp ch = new CommonHttp();	
				String result_json = ch.postData("http://211.110.61.51:3000/chat", json_param);		
				
				if(result_json.equals("error")){
					Toast.makeText(context, "chat 업데이트 실패!", Toast.LENGTH_SHORT).show();
				}
				else{			
					RivalFrag rf = new RivalFrag(1);
					rf.my_info.put("chat_ballon", chat_val);
				}
				
			}
			catch(Exception e){
				System.out.println("다이얼로그 히스토리 가져오기 에러");
			}
			
		}
		
		public void setHistoryList() {
			
			history_list = new ArrayList<HistoryObj>();
			JSONObject json_param = new JSONObject();
					
			try {
				json_param.put("email", user_info.getString("email"));
				
				CommonHttp ch = new CommonHttp();	
				String result_json = ch.postData("http://211.110.61.51:3000/rival_history", json_param);		
				
				if(result_json.equals("error")){
					Toast.makeText(context, "데이터 수신 실패!", Toast.LENGTH_SHORT).show();
				}
				else{			
					//System.out.println("성공");			
					JSONObject json_data = new JSONObject(result_json);
					JSONArray json_history_data = json_data.getJSONArray("history_data");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
					
					for(int i=0; i<json_history_data.length(); i++){
							
						String status = json_history_data.getJSONObject(i).getString("status");
						String food_calorie = json_history_data.getJSONObject(i).getString("food_calorie");
						String exercise_calorie = json_history_data.getJSONObject(i).getString("exercise_calorie");
						String tmp_date = json_history_data.getJSONObject(i).getString("reg_date");
				
						// 시간 +9 적용(GMT 때문에)
						Calendar tmp_date_cal = Calendar.getInstance();
						tmp_date = tmp_date.replaceAll("T"," ");
						tmp_date = tmp_date.replaceAll("Z", "");			
						tmp_date_cal.setTime(sdf.parse(tmp_date));
						tmp_date_cal.add(tmp_date_cal.HOUR, 9);
						
						String tmp_date_str = String.format(" %04d %02d/%02d", tmp_date_cal.get(Calendar.YEAR), tmp_date_cal.get(Calendar.MONTH), tmp_date_cal.get(Calendar.DAY_OF_MONTH));
										
						history_list.add(new HistoryObj(tmp_date_str, status, food_calorie, exercise_calorie));
					}

				}
				
			}
			catch(Exception e){
				System.out.println("다이얼로그 히스토리 가져오기 에러");
			}
			
			history_adapter = new MyHistoryAdapter(context, history_list);
			
			// 리스트뷰에 어댑터 연결
		    history_listview = (ListView)getDialog().findViewById(R.id.history_scroll);	  
		    history_listview.setAdapter(history_adapter);
		 	
			
		}
		
	}