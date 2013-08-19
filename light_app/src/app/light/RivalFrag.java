package app.light;

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
	
	private JSONObject rival1_info;	//내 정보
	private JSONObject rival2_info;
	private JSONObject rival3_info;
	private JSONObject rival4_info;
	
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
				
			String result_json = postData("http://211.110.61.51:3000/rival", json_param);		
			
			if(result_json.equals("error")){
				Toast.makeText(context, "데이터 수신 실패!", Toast.LENGTH_SHORT).show();
			}
			else{	
				
				System.out.println(result_json);
				
				JSONObject json_data = new JSONObject(result_json);
				
			}	
		
	
		} catch(Exception e) {}
		
	}
}
