package app.light;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RivalFrag extends CommonFragment {
	
	private Context context;
	private RivalDialogWindow popup_dialog;
	
	public static JSONObject group_info = new JSONObject();
	public static JSONObject my_info = new JSONObject();	//내 정보
	public static JSONArray rival_info = new JSONArray();
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.frag_rival, container, false);
		context = getActivity();
			
		
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
				popup_dialog = new RivalDialogWindow(0);	
				popup_dialog.show(getFragmentManager(), "User1 Popup");	
			}
		});
		
		//User2 캐릭터 클릭시
		user2_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{	
				popup_dialog = new RivalDialogWindow(1);		
				popup_dialog.show(getFragmentManager(), "User2 Popup");	
			}
		});
		
		//User3 캐릭터 클릭시
		user3_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{	
				popup_dialog = new RivalDialogWindow(1);	
				popup_dialog.show(getFragmentManager(), "User3 Popup");	
			}
		});
		
		//User4 캐릭터 클릭시
		user4_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{	
				popup_dialog = new RivalDialogWindow(1);		
				popup_dialog.show(getFragmentManager(), "User4 Popup");	
			}
		});
			
	
		return view;
	}
		
	@Override
	public void onStart() {
		super.onStart();
		setRivalView();	
	}
	
	public void setRivalView(){
		//DB에서 데이터 가져와서 변수에 저장
		
		JSONObject json_param = new JSONObject();
			
		try {		
			CommonHttp ch = new CommonHttp();	
			String result_json = ch.postData("http://211.110.61.51:3000/rival", json_param);		
			
			if(result_json.equals("error")){
				Toast.makeText(context, "데이터 수신 실패!", Toast.LENGTH_SHORT).show();
			}
			else{			
				System.out.println("성공");			
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
				
				System.out.println(rival_info.getJSONObject(0).getString("email"));
				System.out.println(rival_info.getJSONObject(1).getString("email"));
				System.out.println(rival_info.getJSONObject(2).getString("email"));
				
				
			//	System.out.println("GROUP->"+json_group_info);
				
			//	System.out.println(group_title);
			//	System.out.println("USER->"+json_user_info);
				
			}	
		
	
		} catch(Exception e) {
			System.out.println("에러 발생");
		}
		
	}
}
