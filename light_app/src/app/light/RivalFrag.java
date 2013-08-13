package app.light;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RivalFrag extends CommonFragment {
	
	private Context context;
	private AlertDialogWindow popup_my_dialog;
	private AlertDialogWindow popup_other_dialog;
	
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
				popup_my_dialog = new AlertDialogWindow(0);		
				popup_my_dialog.show(getFragmentManager(), "User1 Popup");	
			}
		});
		
		//User2 캐릭터 클릭시
		user2_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{	
				popup_my_dialog = new AlertDialogWindow(1);		
				popup_my_dialog.show(getFragmentManager(), "User2 Popup");	
			}
		});
		
		//User3 캐릭터 클릭시
		user3_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{	
				popup_my_dialog = new AlertDialogWindow(1);	
				popup_my_dialog.show(getFragmentManager(), "User3 Popup");	
			}
		});
		
		//User4 캐릭터 클릭시
		user4_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{	
				popup_my_dialog = new AlertDialogWindow(1);		
				popup_my_dialog.show(getFragmentManager(), "User4 Popup");	
			}
		});
			
	
		return view;
	}
		
	@Override
	public void onStart() {
		super.onStart();
		
	}
	
}
