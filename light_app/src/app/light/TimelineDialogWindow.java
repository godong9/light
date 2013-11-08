package app.light;

import java.util.Calendar;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


	//커스텀 AlertDialog 구현
	public class TimelineDialogWindow extends DialogFragment implements SensorEventListener{
		
		private static final String PACKAGE_NAME="app.light";
		private static String[] food_list;
		private static JSONObject food_data;
		
		private Context context;
		private int type;
		private static String my_email;
		private static String my_weight;
		private int tmp_position = 0;
		private Float tmp_time = 0f;
		private Float tmp_power = 1f;
		private Float tmp_food_calorie = 0f;
		private Float tmp_food_num = 1f;
		
		private String tmp_food_pre_content_val = "";
		private String tmp_food_content_val = "";
		private String tmp_food_calorie_val = "";
		
		private String tmp_exercise_pre_content_val = "";
		private String tmp_exercise_content_val = "";
		private String tmp_exercise_calorie_val = "";
		
		private String tmp_weight_content_val = "";
		
		SensorManager sm;
		SensorEventListener oriL;
		Sensor oriSensor;
		private long lastTime;
		int count = 0;
		private float numX, numY, numZ;
		private float lastX, lastY, lastZ;
		private static final int DATA_X = SensorManager.DATA_X;
		private static final int DATA_Y = SensorManager.DATA_Y;
		private static final int DATA_Z = SensorManager.DATA_Z;
		private float x, y, z;
		
		TextView count_num;
			
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
			context = getActivity();
			sm =  (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
			oriSensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION); // ����
		
			// 음식 기록 페이지
			if(type==0){
				view = mLayoutInflater.inflate(R.layout.popup_food_dialog, null);
				mBuilder.setView(view);	
			}
			// 운동 기록 페이지
			else if(type==1){
				view = mLayoutInflater.inflate(R.layout.popup_exercise_dialog, null);
				mBuilder.setView(view);
			}
			// 체중 기록 페이지
			else if(type==2){
				view = mLayoutInflater.inflate(R.layout.popup_weight_dialog, null);
				mBuilder.setView(view);
			}
			// 운동하기 페이지
			else if(type==3){
				view = mLayoutInflater.inflate(R.layout.popup_do_exercise_dialog, null);
				mBuilder.setView(view);
			}

			context = getActivity();	
		
			return mBuilder.create();
		}
		
		 
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			sm.registerListener(this, oriSensor, SensorManager.SENSOR_DELAY_NORMAL); // ����
		
		}
			

		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			sm.unregisterListener(this);
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
			count_num = (TextView)getDialog().findViewById(R.id.write_count_num);	
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
			
			if(type==0){	//음식 기록 페이지
				final ImageButton ok_btn = (ImageButton)getDialog().findViewById(R.id.write_food_ok_btn);
				AutoCompleteTextView tv_search = (AutoCompleteTextView)getDialog().findViewById(R.id.write_food_search_val);
				
				final ImageButton minus_btn = (ImageButton)getDialog().findViewById(R.id.write_food_minus_btn);
				final ImageButton plus_btn = (ImageButton)getDialog().findViewById(R.id.write_food_plus_btn);	
				final ImageButton delete_btn = (ImageButton)getDialog().findViewById(R.id.write_food_delete_btn);	
				
				SQLiteDatabase db; 
				final int DB_MODE = Context.MODE_PRIVATE;      
				final String DB_NAME = "food.db"; // DB 생성시 이름 		
				
				String path = Environment.getExternalStorageDirectory().toString();
				String filePath = path+"/Light_Diet/" + PACKAGE_NAME + "/db/food.db";
				
				db = SQLiteDatabase.openOrCreateDatabase(filePath, null); // 있으면 열고, 없으면 생성                
			    System.out.println("database test=>"+db); 
				    	    
				String sql = "select * from " + "food_db";
				Cursor cursor = db.rawQuery(sql, null);
	
				if (cursor != null) {
					int count = cursor.getCount(); // 조회된 개수얻기
					//System.out.println("데이터를 조회했어요. 레코드 갯수: " + count + "\n");
					food_list = new String[count];
					food_data = new JSONObject();
					for (int i = 0; i < count; i++) {
						cursor.moveToNext();
							
						// String name=cursor.getString(0) + "/"
						// +cursor.getString(1) +"/"+ cursor.getInt(2);
						
						food_list[i] = cursor.getString(0);
						
						//food_calorie[i] = cursor.getString(1);
						try {
							food_data.put(cursor.getString(0), cursor.getString(1));
						}
						catch(Exception e){
							System.out.println("JSON put 에러");
						}			
					}
				}
			    
				tv_search.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, food_list));
				
				tv_search.setOnItemClickListener(new OnItemClickListener() { 

				    @Override
				    public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
				    	TextView tv_tmp = (TextView)view;
				    	TextView tv_select_food = (TextView)getDialog().findViewById(R.id.user_select_food);
				    	TextView tv_food_calorie = (TextView)getDialog().findViewById(R.id.write_food_calorie);
				    	AutoCompleteTextView tv_search = (AutoCompleteTextView)getDialog().findViewById(R.id.write_food_search_val);
				    	
				    	LinearLayout ll = (LinearLayout)getDialog().findViewById(R.id.write_food_select_layout);
				    	try{
				    		String tmp_food_name = tv_tmp.getText().toString();
				    		String tmp_calorie_str = food_data.getString(tmp_food_name);
				    		//System.out.println("칼로리 => "+tmp_calorie);
				    		
				    		float tmp_calorie = Float.valueOf(tmp_calorie_str);
				    		tmp_food_calorie = tmp_calorie;
								
							//float tmp_calorie = Float.parseFloat(exercise_calorie[tmp_position]) * tmp_time * tmp_power;
				    		tmp_calorie = tmp_food_num * tmp_food_calorie ;
							String calorie_str = String.format("%.0f", tmp_calorie);
				    		
							tmp_food_pre_content_val = String.format("%.1f", tmp_food_num);
							tmp_food_content_val = tmp_food_name;  //음식 이름 변수 저장
							tmp_food_calorie_val = calorie_str;	//칼로리 변수 저장
							
				    		tv_select_food.setText(tv_tmp.getText().toString());
				    		tv_food_calorie.setText("+"+calorie_str+"Kcal");
				    	    	
				    		tv_search.setText("");
				    		ll.setVisibility(View.VISIBLE);  		
				    	}
				    	catch(Exception e){
				    		
				    	}
				    	
				    }
				});
				
				minus_btn.setOnClickListener(new View.OnClickListener()
				{
			    	TextView tv_food_calorie = (TextView)getDialog().findViewById(R.id.write_food_calorie);
			    	TextView tv_food_num = (TextView)getDialog().findViewById(R.id.write_food_num_val);
			    	@Override
					public void onClick(View v)
					{	
						String tmp_food_str = (String)tv_food_calorie.getText().toString();
						//tmp_food_str = tmp_food_str.replaceAll("+", "");
						tmp_food_str = tmp_food_str.replaceAll("Kcal", "");		
						
						Float tmp_num = Float.valueOf(tv_food_num.getText().toString());
						
						if(tmp_num > 0.5) {
							tmp_food_num = tmp_num-0.5f;
							float tmp_calorie = tmp_food_num * tmp_food_calorie ;
							String calorie_str = String.format("%.0f", tmp_calorie);
							String num_str = String.format("%.1f", tmp_food_num);
							tv_food_calorie.setText("+"+calorie_str+"Kcal");
							tv_food_num.setText(num_str);
							
							tmp_food_pre_content_val = num_str;
							tmp_food_calorie_val = calorie_str;	//칼로리 변수 저장
						}		
					}	
				});
				
				plus_btn.setOnClickListener(new View.OnClickListener()
				{
					TextView tv_food_calorie = (TextView)getDialog().findViewById(R.id.write_food_calorie);
			    	TextView tv_food_num = (TextView)getDialog().findViewById(R.id.write_food_num_val);
					@Override
					public void onClick(View v)
					{	
						String tmp_food_str = (String)tv_food_calorie.getText().toString();
						//tmp_food_str = tmp_food_str.replaceAll("+", "");
						tmp_food_str = tmp_food_str.replaceAll("Kcal", "");
						
						Float tmp_num = Float.valueOf(tv_food_num.getText().toString());
						
						if(tmp_num < 5) {
							tmp_food_num = tmp_num+0.5f;
							float tmp_calorie = tmp_food_num * tmp_food_calorie ;
							String calorie_str = String.format("%.0f", tmp_calorie);
							String num_str = String.format("%.1f", tmp_food_num);
							tv_food_calorie.setText("+"+calorie_str+"Kcal");
							tv_food_num.setText(num_str);
							
							tmp_food_pre_content_val = num_str;
							tmp_food_calorie_val = calorie_str;	//칼로리 변수 저장
						}
					}	
				});
				
				delete_btn.setOnClickListener(new View.OnClickListener()
				{
					TextView tv_food_calorie = (TextView)getDialog().findViewById(R.id.write_food_calorie);
			    	TextView tv_food_num = (TextView)getDialog().findViewById(R.id.write_food_num_val);
			    	LinearLayout ll = (LinearLayout)getDialog().findViewById(R.id.write_food_select_layout);
			    	
			    	@Override
					public void onClick(View v)
					{	
						tmp_food_num = 1.0f;
						tmp_food_calorie = 0f;
						tv_food_num.setText(String.format("%.1f", tmp_food_num));
						tv_food_calorie.setText(String.format("+"+"%.0f", tmp_food_calorie)+"Kcal");
						ll.setVisibility(View.GONE);  
						
						tmp_food_content_val = "";
						tmp_food_calorie_val = String.format("%.1f", tmp_food_num);	//칼로리 변수 저장
					}	
				});
			
				ok_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						if(!tmp_food_content_val.equals("")){	//기록 내용이 있을 때
							JSONObject json_my_param = new JSONObject();
							try{
								json_my_param.put("type", "4");
								json_my_param.put("pre_content", tmp_food_pre_content_val);
								json_my_param.put("content", tmp_food_content_val);
								json_my_param.put("calorie", tmp_food_calorie_val);
							}
							catch(Exception e){
								System.out.println("JSON put 에러");
							}
							TimelineFrag.addMyData(json_my_param);
							onStop();
						}
						else{
							EditText et_food = (EditText)getDialog().findViewById(R.id.user_iuput_food);
							EditText et_calorie= (EditText)getDialog().findViewById(R.id.user_input_calorie);
							String user_input_food_str = et_food.getText().toString();
							String user_input_calorie_str = et_calorie.getText().toString();
			
							if(!user_input_calorie_str.equals("")){
								JSONObject json_my_param = new JSONObject();
								try{
									json_my_param.put("type", "4");
									json_my_param.put("pre_content", "1.0");
									json_my_param.put("content", user_input_food_str);
									json_my_param.put("calorie", user_input_calorie_str);
								}
								catch(Exception e){
									System.out.println("JSON put 에러");
								}
								TimelineFrag.addMyData(json_my_param);
								onStop();
							}
						}
					}	
				});	
			}
			else if(type==1){	//운동 기록 페이지
				
		        final ImageButton low_btn = (ImageButton)getDialog().findViewById(R.id.write_exercise_low_btn);
				final ImageButton middle_btn = (ImageButton)getDialog().findViewById(R.id.write_exercise_middle_btn);
				final ImageButton high_btn = (ImageButton)getDialog().findViewById(R.id.write_exercise_high_btn);
				final ImageButton ok_btn = (ImageButton)getDialog().findViewById(R.id.write_exercise_ok_btn);
				
				middle_btn.setSelected(true);
				
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
						TextView tv_tmp = (TextView)view;
						String tmp_exercise_name = tv_tmp.getText().toString();
						tmp_position = position;
						float tmp_calorie = Float.parseFloat(exercise_calorie[tmp_position]) * tmp_time * tmp_power;
						String calorie_str = String.format("%.0f", tmp_calorie);
						
						tmp_exercise_content_val = tmp_exercise_name;
						tmp_exercise_calorie_val = calorie_str;	
						tv_calorie.setText("-"+calorie_str+"Kcal");
								
						if(tmp_exercise_content_val.equals("Fitbit")){
							try {				
								CommonHttp ch = new CommonHttp();	
								String result_json = ch.getData("http://211.110.61.51:3000/get_fitbit");		
								if(result_json.equals("error")) {
									System.out.println("데이터 전송 실패!");	
								}	
								else {
									JSONObject json_result = new JSONObject(result_json);
									tmp_exercise_calorie_val = json_result.getString("activity_cal");
									//System.out.println("result: "+tmp_exercise_calorie_val);
									tv_calorie.setText("-"+tmp_exercise_calorie_val+"Kcal");
								}
							}
							catch(Exception e){
								System.out.println("데이터 전송 실패!");
							}					
						}
						
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
						String calorie_str = String.format("%.0f", tmp_calorie);
						
						tmp_exercise_pre_content_val = String.format("%d", progress);
						tmp_exercise_calorie_val = calorie_str;	
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
						low_btn.setSelected(true);
						middle_btn.setSelected(false);
						high_btn.setSelected(false);
						
						tmp_power = 0.8f;	
						float tmp_calorie = Float.parseFloat(exercise_calorie[tmp_position]) * tmp_time * tmp_power;
						String calorie_str = String.format("%.0f", tmp_calorie);
						
						tmp_exercise_calorie_val = calorie_str;	
						tv_calorie.setText("-"+calorie_str+"Kcal");	
					}
				});
				middle_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						low_btn.setSelected(false);
						middle_btn.setSelected(true);
						high_btn.setSelected(false);
						
						tmp_power = 1.0f;	
						float tmp_calorie = Float.parseFloat(exercise_calorie[tmp_position]) * tmp_time * tmp_power;
						String calorie_str = String.format("%.0f", tmp_calorie);
						
						tmp_exercise_calorie_val = calorie_str;
						tv_calorie.setText("-"+calorie_str+"Kcal");	
					}
				});
				high_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						low_btn.setSelected(false);
						middle_btn.setSelected(false);
						high_btn.setSelected(true);
						
						tmp_power = 1.2f;		
						float tmp_calorie = Float.parseFloat(exercise_calorie[tmp_position]) * tmp_time * tmp_power;
						String calorie_str = String.format("%.0f", tmp_calorie);
						
						tmp_exercise_calorie_val = calorie_str;
						tv_calorie.setText("-"+calorie_str+"Kcal");	
					}
				});
				ok_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						if(!tmp_exercise_content_val.equals("")){	//기록 내용이 있을 때
							JSONObject json_my_param = new JSONObject();
							try{	
								if(tmp_exercise_content_val.equals("Fitbit")){
									json_my_param.put("type", "5");
									json_my_param.put("pre_content", "Cal");
									json_my_param.put("content", "칼로리");
									json_my_param.put("calorie", tmp_exercise_calorie_val);
								}
								else{
									json_my_param.put("type", "5");
									json_my_param.put("pre_content", tmp_exercise_pre_content_val+"분");
									json_my_param.put("content", tmp_exercise_content_val);
									json_my_param.put("calorie", tmp_exercise_calorie_val);
								}
							}
							catch(Exception e){
								System.out.println("JSON put 에러");
							}
							TimelineFrag.addMyData(json_my_param);
							onStop();
						}
						onStop();
					}	
				});	
			}
			else if(type==2){	//체중 기록 페이지
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
						tmp_weight_content_val = String.format("%.1f", tmp_weight_num);
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
						tmp_weight_content_val = String.format("%.1f", tmp_weight_num);		
					}	
				});
				
				ok_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						JSONObject json_my_param = new JSONObject();
						try{
							System.out.println("WEIGHT=>"+tmp_weight_content_val);
							json_my_param.put("type", "7");
							json_my_param.put("pre_content", "");
							json_my_param.put("content", tmp_weight_content_val);
							json_my_param.put("calorie", "");
						}
						catch(Exception e){
							System.out.println("JSON put 에러");
						}
						TimelineFrag.addMyData(json_my_param);
						onStop();
					}	
				});	
			}
			else if(type==3){	//운동하기 페이지
				final ImageButton ok_btn = (ImageButton)getDialog().findViewById(R.id.write_do_ok_btn);
				final LinearLayout do_layout = (LinearLayout)getDialog().findViewById(R.id.do_layout);
				
				do_layout.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{			
						count++;	
						String tmpCount = String.valueOf(count);
						Log.e("Count: ", tmpCount);
							
						count_num.setText(tmpCount);
					}	
				});	
				
				ok_btn.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{	
						
						JSONObject json_my_param = new JSONObject();
						try{			
							json_my_param.put("type", "5");
							json_my_param.put("pre_content", count+"개");
							json_my_param.put("content", "운동");
							json_my_param.put("calorie", count*5);
						}
						catch(Exception e){
							System.out.println("JSON put 에러");
						}
						TimelineFrag.addMyData(json_my_param);
						onStop();
		
				
					}	
				});	
			}
								
		}
		
		@Override
		public void onStop() {
			super.onStop();
			
			ImageButton wb = (ImageButton)getActivity().findViewById(R.id.write_btn);
			wb.setSelected(false);	
		}
		
		
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub	
			synchronized (this) {
				if(type==3){
					
					
					switch (event.sensor.getType()) {
	
					case Sensor.TYPE_ORIENTATION:
						x = event.values[DATA_X];
						y = event.values[DATA_Y];
						z = event.values[DATA_Z];
						long currentTime = System.currentTimeMillis();
						long gabOfTime = (currentTime - lastTime);
						
						if (gabOfTime > 800) {
							lastTime = currentTime;
							
							x = event.values[SensorManager.DATA_X];
							y = event.values[SensorManager.DATA_Y];
							z = event.values[SensorManager.DATA_Z];
							
							numX = lastX - x;
							numY = lastY - y;
							numZ = z - lastZ;
							String tmpX = String.valueOf(numX);
							String tmpY = String.valueOf(numY);
							String tmpZ = String.valueOf(numZ);
							Log.e("NumX", tmpX);
							Log.e("NumY", tmpY);
							Log.e("NumZ", tmpZ);
							if ( numY > 80 || numY > 80 ){
								// numX > 60 || || numY > 60 || numZ > 60
								count++;	
								String tmpCount = String.valueOf(count);
								Log.e("Count: ", tmpCount);
									
								count_num.setText(tmpCount);
							}
				
							lastX = event.values[DATA_X];
							lastY = event.values[DATA_Y];
							lastZ = event.values[DATA_Z];
	
						}
						
						break;
					}
				}                        
				                          
			}
		}
	}