package app.light;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RivalFrag extends CommonFragment {
	
	private Context context;
	private RivalDialogWindow popup_dialog;
	private String packName = "app.light";
	private Resources res;
	private int matching_status = 0;
	
	public static JSONObject group_info = null;
	public static JSONObject my_info = null;	//내 정보
	public static JSONArray rival_info = null;

	public RivalFrag(int matching_status){
		this.matching_status = matching_status;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {
		View view = null;
		if(matching_status == 0){
			view = inflater.inflate(R.layout.frag_waiting, container, false);
		}
		else if(matching_status == 1){
			view = inflater.inflate(R.layout.frag_rival, container, false);	
			/*
	         * 버튼 클릭 관련 이벤트 처리하는 부분
	         * 
	         */
	        final ImageButton user1_btn = (ImageButton) view.findViewById(R.id.rival_user1_click);
			final ImageButton user2_btn = (ImageButton) view.findViewById(R.id.rival_user2_click);
			final ImageButton user3_btn = (ImageButton) view.findViewById(R.id.rival_user3_click);
			final ImageButton user4_btn = (ImageButton) view.findViewById(R.id.rival_user4_click);
		
			//User1 캐릭터 클릭시
			user1_btn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{	
					popup_dialog = new RivalDialogWindow(1, my_info);	
					popup_dialog.show(getFragmentManager(), "User1 Popup");	
				}
			});
			
			//User2 캐릭터 클릭시
			user2_btn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{	
					try {
						popup_dialog = new RivalDialogWindow(2, rival_info.getJSONObject(0));	
					}
					catch(Exception e){}	
					popup_dialog.show(getFragmentManager(), "User2 Popup");	
				}
			});
			
			//User3 캐릭터 클릭시
			user3_btn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{	
					try {
						popup_dialog = new RivalDialogWindow(3, rival_info.getJSONObject(1));	
					}
					catch(Exception e){}	
					popup_dialog.show(getFragmentManager(), "User3 Popup");	
				}
			});
			
			//User4 캐릭터 클릭시
			user4_btn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{	
					try {
						popup_dialog = new RivalDialogWindow(4, rival_info.getJSONObject(2));	
					}
					catch(Exception e){}		
					popup_dialog.show(getFragmentManager(), "User4 Popup");	
				}
			});
		}
		context = getActivity();
		
		return view;
	}
		
	@Override
	public void onStart() {
		super.onStart();
		setRivalView();	
	}
	
	public void setRivalView(){
		//group 정보가 없을 때 DB에서 데이터 가져와서 변수에 저장

		group_info = new JSONObject();
		my_info = new JSONObject();
		rival_info = new JSONArray();
		
		try {		
			if(group_info.length() == 0) {
				CommonHttp ch = new CommonHttp();	
				String result_json = ch.getData("http://211.110.61.51:3000/rival");		
				
				if(result_json.equals("error")){
					Toast.makeText(context, "데이터 수신 실패!", Toast.LENGTH_SHORT).show();
				}
				else{			
					//System.out.println("성공");			
					JSONObject json_data = new JSONObject(result_json);
					JSONArray json_group_info = json_data.getJSONArray("group_info");
					JSONArray json_user_info = json_data.getJSONArray("user_info");
					
					group_info = json_group_info.getJSONObject(0);
					String my_email = group_info.getString("email");
					
					for(int i=0; i<json_user_info.length(); i++){
						String tmp_email = json_user_info.getJSONObject(i).getString("email");
						if(my_email.equals(tmp_email)){
							my_info = json_user_info.getJSONObject(i);
						}
						else{
							rival_info.put(json_user_info.getJSONObject(i));
						}
					}
				}	
			}
		}
		catch(Exception e) {
			System.out.println("데이터 받아올 때 에러 발생");
		}
				
		try {
			//문자열로 날짜 변환 후에 다시 Date 형태로 변환
			String start_day = group_info.getString("start_date");
			String end_day = group_info.getString("end_date");
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			
			Calendar tmp_date_cal = Calendar.getInstance();
			Calendar start_date_cal = Calendar.getInstance();
			Calendar end_date_cal = Calendar.getInstance();
			
			start_day = start_day.replaceAll("T"," ");
			start_day = start_day.replaceAll("Z", "");
			start_date_cal.setTime(sdf.parse(start_day));
			start_date_cal.add(start_date_cal.HOUR, 9);
			
			end_day = end_day.replaceAll("T"," ");
			end_day = end_day.replaceAll("Z", "");
			end_date_cal.setTime(sdf.parse(end_day));
			end_date_cal.add(end_date_cal.HOUR, 9);
			
			System.out.println("start=> "+tmp_date_cal);
			System.out.println("end=> "+end_date_cal);
			
			//날짜 차이 계산
			long start_tmp_diff = tmp_date_cal.getTimeInMillis() - start_date_cal.getTimeInMillis();
			long tmp_end_diff = end_date_cal.getTimeInMillis()  - tmp_date_cal.getTimeInMillis();
			
			long start_tmp_diff_days = start_tmp_diff / (24*60*60*1000) + 1;
			long tmp_end_diff_days = tmp_end_diff / (24*60*60*1000) + 1;
	
			
			//그룹 목표, 제목 데이터
			String group_goal = group_info.getString("goal");
			String group_title = group_info.getString("title");
			
			//그룹 관련 타이틀 부분 데이터 적용
			TextView tvGroupCountDay = (TextView)((Activity)context).findViewById(R.id.rival_count_day);
			TextView tvGroupRemainDay = (TextView)((Activity)context).findViewById(R.id.rival_remain_day);    
			TextView tvGroupGoal = (TextView)((Activity)context).findViewById(R.id.rival_group_goal);
			
			tvGroupCountDay.setText(start_tmp_diff_days+"일째");
			tvGroupRemainDay.setText("D - "+tmp_end_diff_days);
			tvGroupGoal.setText(group_goal+"kg "+group_title);
			
			//내 데이터 적용
			TextView myNickName = (TextView)((Activity)context).findViewById(R.id.rival_user1_nickname);
			TextView myChat = (TextView)((Activity)context).findViewById(R.id.rival_user1_word);
			TextView myPreWeight = (TextView)((Activity)context).findViewById(R.id.rival_user1_pre_weight);
			TextView myWeight = (TextView)((Activity)context).findViewById(R.id.rival_user1_weight);
			TextView myScore = (TextView)((Activity)context).findViewById(R.id.rival_user1_score);
			ImageButton myCharacter = (ImageButton)((Activity)context).findViewById(R.id.rival_user1_click);
			ImageView myCalorie = (ImageView)((Activity)context).findViewById(R.id.rival_user1_status);
			
			myNickName.setText(my_info.getString("nickname"));
			myChat.setText(my_info.getString("chat_ballon"));
			myPreWeight.setText(my_info.getString("pre_weight")+"kg");
			myWeight.setText(my_info.getString("weight")+"kg");	
			
			String score_str = "";
			String pre_weight_str = my_info.getString("pre_weight");
			String tmp_weight_str = my_info.getString("weight");
	
			float pre_weight = Float.parseFloat(pre_weight_str);
			float tmp_weight = Float.parseFloat(tmp_weight_str);
			
			if(tmp_weight <= pre_weight){
				float tmp_score = pre_weight-tmp_weight;
				String tmp_score_str = String.format("%.1f", tmp_score);
				score_str = "-"+tmp_score_str+"kg";
			}
			else{
				float tmp_score = tmp_weight-pre_weight;
				String tmp_score_str = String.format("%.1f", tmp_score);
				score_str = "+"+tmp_score_str+"kg";
			}
			myScore.setText(score_str);
			
			res = getResources();
			
			// 칼로리 상태바 계산
			float day_calorie_num = Float.valueOf(my_info.getString("day_calorie"));
			float food_calorie_num = Float.valueOf(my_info.getString("food_calorie"));
			float exercise_calorie_num = Float.valueOf(my_info.getString("exercise_calorie"));
				
			float calorie_per_point = day_calorie_num/10.0f;
			float tmp_calorie_num = day_calorie_num-(food_calorie_num-exercise_calorie_num);
			float tmp_calorie_float = tmp_calorie_num / calorie_per_point;
			int tmp_calorie_int = Math.round(tmp_calorie_float);
			if(tmp_calorie_int > 10){
				tmp_calorie_int = 10;
			}
			else if(tmp_calorie_int < 0){
				tmp_calorie_int = 0;
			}
			
			String my_character = "@drawable/character_"+my_info.getString("character");
			String my_calorie = "@drawable/calorie_"+tmp_calorie_int;
			
			myCharacter.setBackgroundResource(res.getIdentifier(my_character, "drawable", packName));
			myCalorie.setBackgroundResource(res.getIdentifier(my_calorie, "drawable", packName));
		
			//라이벌 데이터 적용
			for(int i=0; i<rival_info.length(); i++){
				
				int user_num=2+i;
				String res_other = "@id/rival_user"+user_num+"_";
				System.out.println("res_other: "+res_other);
				
				res = getResources();
				
				TextView otherNickName = (TextView)((Activity)context).findViewById(res.getIdentifier(res_other+"nickname", "id", packName));
				TextView otherChat = (TextView)((Activity)context).findViewById(res.getIdentifier(res_other+"word", "id", packName));
				TextView otherPreWeight = (TextView)((Activity)context).findViewById(res.getIdentifier(res_other+"pre_weight", "id", packName));
				TextView otherWeight = (TextView)((Activity)context).findViewById(res.getIdentifier(res_other+"weight", "id", packName));
				TextView otherScore = (TextView)((Activity)context).findViewById(res.getIdentifier(res_other+"score", "id", packName));
				ImageButton otherCharacter = (ImageButton)((Activity)context).findViewById(res.getIdentifier(res_other+"click", "id", packName));
				ImageView otherCalorie = (ImageView)((Activity)context).findViewById(res.getIdentifier(res_other+"status", "id", packName));
				
				System.out.println(res_other+"status");
				
				otherNickName.setText(rival_info.getJSONObject(i).getString("nickname"));
				otherChat.setText(rival_info.getJSONObject(i).getString("chat_ballon"));
				otherPreWeight.setText(rival_info.getJSONObject(i).getString("pre_weight")+"kg");
				otherWeight.setText(rival_info.getJSONObject(i).getString("weight")+"kg");
				
				score_str = "";
				pre_weight_str = rival_info.getJSONObject(i).getString("pre_weight");
				tmp_weight_str = rival_info.getJSONObject(i).getString("weight");
		
				pre_weight = Float.parseFloat(pre_weight_str);
				tmp_weight = Float.parseFloat(tmp_weight_str);
				
				if(tmp_weight <= pre_weight){
					float tmp_score = pre_weight-tmp_weight;
					String tmp_score_str = String.format("%.1f", tmp_score);
					score_str = "-"+tmp_score_str+"kg";
				}
				else{
					float tmp_score = tmp_weight-pre_weight;
					String tmp_score_str = String.format("%.1f", tmp_score);
					score_str = "+"+tmp_score_str+"kg";
				}
				otherScore.setText(score_str);
				
				// 칼로리 상태바 계산	
				day_calorie_num = Float.valueOf(rival_info.getJSONObject(i).getString("day_calorie"));
				food_calorie_num = Float.valueOf(rival_info.getJSONObject(i).getString("food_calorie"));
				exercise_calorie_num = Float.valueOf(rival_info.getJSONObject(i).getString("exercise_calorie"));
				
				calorie_per_point = day_calorie_num/10.0f;
				tmp_calorie_num = day_calorie_num-(food_calorie_num-exercise_calorie_num);
				tmp_calorie_float = tmp_calorie_num / calorie_per_point;
				tmp_calorie_int = Math.round(tmp_calorie_float);
				if(tmp_calorie_int > 10){
					tmp_calorie_int = 10;
				}
				else if(tmp_calorie_int < 0){
					tmp_calorie_int = 0;
				}
					
				String other_character = "@drawable/character_"+rival_info.getJSONObject(i).getString("character");
				String other_calorie = "@drawable/calorie_"+tmp_calorie_int;
	
				System.out.println("=>"+other_character);
				System.out.println("=>"+other_calorie);
				
				otherCharacter.setBackgroundResource(res.getIdentifier(other_character, "drawable", packName));
				otherCalorie.setBackgroundResource(res.getIdentifier(other_calorie, "drawable", packName));
			
			}
		}
		catch(Exception e){
			System.out.println("데이터 세팅 중 에러 발생");
		}	
		
	}
}
