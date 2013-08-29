package app.light;

import java.util.Calendar;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//커스텀 AlertDialog 구현
	public class TimelineDialogWindow extends DialogFragment {
		private Context context;
		private int type;
		private String my_email;
		private String my_weight;
		private int tmp_position = 0;
		private Float tmp_time = 0f;
		private Float tmp_power = 1f;
		
		public TimelineDialogWindow(int type, String my_email, String my_weight) {
			this.type=type;
			this.my_email=my_email;
			this.my_weight=my_weight;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceStadte) {
			//Theme_Holo_Light_Panel 이용해서 테두리 없게 만들어줌
			AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Panel);
			LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
			
			View view; 
			
			//타입에 따라 음식 기록 or 운동 기록
			if(type==0){
				view = mLayoutInflater.inflate(R.layout.popup_food_dialog, null);
				mBuilder.setView(view);	
			}
			else if(type==1){
				view = mLayoutInflater.inflate(R.layout.popup_exercise_dialog, null);
				mBuilder.setView(view);
			}
			else if(type==2){
				view = mLayoutInflater.inflate(R.layout.popup_weight_dialog, null);
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
			 * 버튼 이벤트 처리해주는 부분
			 */
			
			
			final ImageButton exit_btn = (ImageButton)getDialog().findViewById(R.id.write_exit_btn);
			exit_btn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{	
					//데이터 전송해주는 부분 추가 필요
					onStop();
				}	
			});
			
			
			if(type==0){
				final ImageButton ok_btn = (ImageButton)getDialog().findViewById(R.id.write_food_ok_btn);
				//검색 및 자동완성 구현
				
				
				ok_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						//데이터 전송해주는 부분 추가 필요
						onStop();
					}	
				});	
			}
			else if(type==1){
				
		        final ImageButton low_btn = (ImageButton)getDialog().findViewById(R.id.write_exercise_low_btn);
				final ImageButton middle_btn = (ImageButton)getDialog().findViewById(R.id.write_exercise_middle_btn);
				final ImageButton high_btn = (ImageButton)getDialog().findViewById(R.id.write_exercise_high_btn);
				final ImageButton ok_btn = (ImageButton)getDialog().findViewById(R.id.write_exercise_ok_btn);
	
				Spinner es = (Spinner)getDialog().findViewById(R.id.exercise_spinner); 
				
				final String[] exercise_calorie = getResources().getStringArray(R.array.exercise_calorie);
				final TextView tv_calorie = (TextView)getDialog().findViewById(R.id.write_exercise_calorie); 
				
				
				ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( 
						                  context, R.array.exercise, android.R.layout.simple_spinner_item); 
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
				es.setAdapter(adapter);
				es.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent, View view,
							int position, long id) {
						tmp_position = position;
						float tmp_calorie = Float.parseFloat(exercise_calorie[tmp_position]) * tmp_time * tmp_power;
						String calorie_str = String.format("%.1f", tmp_calorie);
						tv_calorie.setText("-"+calorie_str+"Kcal");	
					}
		
					public void onNothingSelected(AdapterView<?> parent) {
						System.out.println("Spinner1: unselected");
					}
				});
				
				SeekBar seekbar = (SeekBar)getDialog().findViewById(R.id.write_exercies_time_bar);
				
				seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					TextView tv_val = (TextView)getDialog().findViewById(R.id.write_exercies_time_text);
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						tmp_time = Float.valueOf(progress);
						tv_val.setText(progress+"분");
						
						float tmp_calorie = Float.parseFloat(exercise_calorie[tmp_position]) * tmp_time * tmp_power;
						String calorie_str = String.format("%.1f", tmp_calorie);
						tv_calorie.setText("-"+calorie_str+"Kcal");			
					}
	
					public void onStartTrackingTouch(SeekBar seekBar) {
					}
	
					public void onStopTrackingTouch(SeekBar seekBar) {
					}
				});
				
				low_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						tmp_power = 0.8f;	
						float tmp_calorie = Float.parseFloat(exercise_calorie[tmp_position]) * tmp_time * tmp_power;
						String calorie_str = String.format("%.1f", tmp_calorie);
						tv_calorie.setText("-"+calorie_str+"Kcal");	
					}
				});
				middle_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						tmp_power = 1.0f;	
						float tmp_calorie = Float.parseFloat(exercise_calorie[tmp_position]) * tmp_time * tmp_power;
						String calorie_str = String.format("%.1f", tmp_calorie);
						tv_calorie.setText("-"+calorie_str+"Kcal");	
					}
				});
				high_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						tmp_power = 1.2f;		
						float tmp_calorie = Float.parseFloat(exercise_calorie[tmp_position]) * tmp_time * tmp_power;
						String calorie_str = String.format("%.1f", tmp_calorie);
						tv_calorie.setText("-"+calorie_str+"Kcal");	
					}
				});
				ok_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						//데이터 전송해주는 부분 추가 필요
						onStop();
					}	
				});	
			}
			else if(type==2){
				final ImageButton minus_btn = (ImageButton)getDialog().findViewById(R.id.write_weight_minus_btn);
				final ImageButton plus_btn = (ImageButton)getDialog().findViewById(R.id.write_weight_plus_btn);
				final TextView tv_date = (TextView)getDialog().findViewById(R.id.write_weight_date);
				final TextView tv_weight = (TextView)getDialog().findViewById(R.id.write_weight_val);
				final ImageButton ok_btn = (ImageButton)getDialog().findViewById(R.id.write_weight_ok_btn);
				
				Calendar cal = Calendar.getInstance();
            	
        		String tmp_date_string;
        		
        		tmp_date_string = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
        	
				tv_date.setText(tmp_date_string);
				tv_weight.setText(my_weight+"kg");
				
				minus_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						String tmp_weight_str = (String)tv_weight.getText();
						tmp_weight_str = tmp_weight_str.replaceAll("kg", "");
						Float tmp_weight_num = Float.valueOf(tmp_weight_str);
						tmp_weight_num = tmp_weight_num-0.1f;
						tv_weight.setText(String.format("%.1f", tmp_weight_num)+"kg");
					}	
				});
				
				plus_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						String tmp_weight_str = (String)tv_weight.getText();
						tmp_weight_str = tmp_weight_str.replaceAll("kg", "");
						Float tmp_weight_num = Float.valueOf(tmp_weight_str);
						tmp_weight_num = tmp_weight_num+0.1f;
						tv_weight.setText(String.format("%.1f", tmp_weight_num)+"kg");
					}	
				});
				
				ok_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						//데이터 전송해주는 부분 추가 필요
						onStop();
					}	
				});	
			}
						
			
			/*
			 * 다이얼로그 내부 버튼 클릭시 이벤트 처리
			 * 
			
			ImageButton dialog_exit_btn;
	
			
			if(type == 0){
				dialog_exit_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_exit);
				
				final ImageButton dialog_page_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_page_btn);
				final ImageButton dialog_closet_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_closet_btn);
				final ImageButton dialog_shop_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_shop_btn);
				final ImageButton dialog_history_btn = (ImageButton) getDialog().findViewById(R.id.rival_my_dialog_history_btn);
					
				dialog_page_btn.setSelected(true);
				
				dialog_page_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{	
						dialog_page_btn.setSelected(true);
						dialog_closet_btn.setSelected(false);
						dialog_shop_btn.setSelected(false);
						dialog_history_btn.setSelected(false);	
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
					}
				});
			
			}
			else{
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
					}
				});
				
				dialog_history_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{				
						dialog_page_btn.setSelected(false);
						dialog_history_btn.setSelected(true);
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
				 */		
		}
		
		@Override
		public void onStop() {
			super.onStop();
			
			ImageButton wb = (ImageButton)getActivity().findViewById(R.id.write_btn);
			wb.setSelected(false);	
		}
	}