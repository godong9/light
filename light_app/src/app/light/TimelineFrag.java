package app.light;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;


public class TimelineFrag extends CommonFragment implements OnScrollListener {
		
	public final static int INSERT_COUNT = 5;
	
	// 타임라인 입력 관련 버튼 상수
	private static final int ID_FOOD     = 11;
	private static final int ID_EXERCISE     = 12;
	private static final int ID_CAMERA     = 14;
	private static final int ID_ALBUM     = 15;
	
	private Context context;
	private QuickAction writePopup;
	private QuickAction cameraPopup;
	
	private ArrayList<TimeLineObj> my_list;
	private MyListAdapter my_adapter;
	private ListView my_listview;
	private int my_list_count = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {	

		View view = inflater.inflate(R.layout.frag_timeline, container, false);
		context = getActivity();
			
		//기록 버튼 팝업 관련 코드    
	    ActionItem write_food 	= new ActionItem(ID_FOOD, "음식 기록");
		ActionItem write_exercise 	= new ActionItem(ID_EXERCISE, "운동 기록");
      
		//버튼 눌러도 안사라지게 고정
        //write_food.setSticky(true);
        //write_exercise.setSticky(true);
 
		writePopup = new QuickAction(context, QuickAction.HORIZONTAL);
        
        //add action items into QuickAction
        writePopup.addActionItem(write_food);
        writePopup.addActionItem(write_exercise);

        //Set listener for action item clicked
        writePopup.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
  			@Override
  			public void onItemClick(QuickAction source, int pos, int actionId) {				
  				ActionItem actionItem = writePopup.getActionItem(pos);
                   
  				//here we can filter which action item was clicked with pos or actionId parameter
  				if (actionId == ID_FOOD) {
  					Toast.makeText(context, "음식 기록", Toast.LENGTH_SHORT).show();
  				} else if (actionId == ID_EXERCISE) {
  					Toast.makeText(context, "운동 기록", Toast.LENGTH_SHORT).show();
  				}
  			}
  		});		
        
        //set listnener for on dismiss event, this listener will be called only if QuickAction dialog was dismissed
  		//by clicking the area outside the dialog.
        writePopup.setOnDismissListener(new QuickAction.OnDismissListener() {			
  			@Override
  			public void onDismiss() {
  				ImageButton wb = (ImageButton)getActivity().findViewById(R.id.write_btn);
  				wb.setSelected(false);
  			}
  		});
              
        //카메라 버튼 팝업 관련 코드  
	    ActionItem camera_camera 	= new ActionItem(ID_CAMERA, "사진 촬영");
		ActionItem camera_album 	= new ActionItem(ID_EXERCISE, "앨범");
      
		cameraPopup = new QuickAction(context, QuickAction.HORIZONTAL);
        
        //add action items into QuickAction
        cameraPopup.addActionItem(camera_camera);
        cameraPopup.addActionItem(camera_album);

        //Set listener for action item clicked
        cameraPopup.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
  			@Override
  			public void onItemClick(QuickAction source, int pos, int actionId) {				
  				ActionItem actionItem = writePopup.getActionItem(pos);
                   
  				//here we can filter which action item was clicked with pos or actionId parameter
  				if (actionId == ID_CAMERA) {
  					Toast.makeText(context, "사진 촬영", Toast.LENGTH_SHORT).show();
  				} else if (actionId == ID_EXERCISE) {
  					Toast.makeText(context, "앨범", Toast.LENGTH_SHORT).show();
  				}
  			}
  		});		
        
        //set listnener for on dismiss event, this listener will be called only if QuickAction dialog was dismissed
  		//by clicking the area outside the dialog.
        cameraPopup.setOnDismissListener(new QuickAction.OnDismissListener() {			
  			@Override
  			public void onDismiss() {
  				ImageButton cb = (ImageButton)getActivity().findViewById(R.id.camera_btn);
  				cb.setSelected(false);
  			}
  		});
		
        
        /*
         * 버튼 클릭 관련 이벤트 처리하는 부분
         * 
         */
        final ImageButton send_btn = (ImageButton) view.findViewById(R.id.send_btn);
		final ImageButton write_btn = (ImageButton) view.findViewById(R.id.write_btn);
		final ImageButton camera_btn = (ImageButton) view.findViewById(R.id.camera_btn);
		
		final EditText chat_text = (EditText) view.findViewById(R.id.chat_val);
		
		//전송 버튼 클릭시
		send_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{	
				String chat_val = chat_text.getText().toString();
				chat_text.setText("");
				if( chat_val.equals("")){
					Toast.makeText(context, "메시지를 입력해주세요!", Toast.LENGTH_SHORT).show();
				}
				else{
					addItem(chat_val);                  
				}						
			}
		});
		
		// 기록하기 버튼 클릭시
		write_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				v.setSelected(true);
				writePopup.show(v);
			}
		});
		
		// 카메라 버튼 클릭시
		camera_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				v.setSelected(true);
				cameraPopup.show(v);	
			}
		});
						
		return view;
	}
	
	@Override
	public void onStart() {  
		//Fragment가 완전히 생성되고 난 후에 호출되는 함수
		super.onStart();
		setListView();		
	}
	
	public void setListView() {
		
		my_list = new ArrayList<TimeLineObj>();
  
        //리스트에 아이템 넣는 부분
		my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_TIMEBAR, 0, "9일째","2013. 8. 12"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"영아입니다","테스트","오후 11:20"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"윤하","테스트테스트테스트테스트테스트테스트테스트테스트","오전 12:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"3","테스트테","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MY_WORD,"테스","오후 1:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MY_FOOD, "10개","샌드위치","550Kcal","오후 12:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_FOOD, "민옥입니다","10개","냉모밀","+1750Kcal","오전 11:50"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_EXERCISE,"영아니다","30분","웨이트트레","-300Kcal","오후 1:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MY_EXERCISE,"60분","자전거타기","-1200Kcal","오전 10:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MANAGER_MISSION, 0, 0, "운동", "자전거 1시간 타기","오후 12:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MANAGER_WORD, 0, 0, "오늘 운동 많이했나요?","오후 13:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_TIMEBAR, 0, "10일째","2013. 8. 13"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MY_PICTURE,"test","오후 2:20"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_PICTURE,"영아","test","오후 3:10"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"영","강","asdfsdf"));
            
        my_list_count += 15;	//5개 불러와서 추가 
		my_adapter = new MyListAdapter(context, my_list);
	
		// 리스트뷰에 어댑터 연결
	    my_listview = (ListView)((Activity)context).findViewById(R.id.timeline_scroll);
	    
	    my_listview.setAdapter(my_adapter);
	    my_listview.setOnScrollListener(this);
	    my_listview.setSelection(my_adapter.getCount() - 1);	    
	}
	
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
	}

	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		// 리스트뷰가 구성이 완료되어 보이는 경우
        if(view.isShown()){
            if(scrollState == SCROLL_STATE_IDLE) {
                // 리스트뷰의 0 번 인덱스 항목이 리스트뷰의 상단에 보이고 있는 경우	
                if(view.getFirstVisiblePosition() == 0) {
                    // 항목을 추가한다.
         
    				my_list.add(0, new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"16","111","asdfsdf"));
                   	my_list.add(0, new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"17","111","asdfsdf"));
                   	my_list.add(0, new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"18","111","asdfsdf"));
                   	my_list.add(0, new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"19","111","asdfsdf"));
                   	my_list.add(0, new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"20","111","asdfsdf"));
    				
    				System.out.println(my_list);
    				my_adapter.notifyDataSetChanged();
 
                    my_list_count += INSERT_COUNT;     
                    view.setSelection(INSERT_COUNT);	//뷰 위치 이동
                }
            }
        }
	}
	
	public void addItem(String chat_val)
	{		
		
		Calendar cal = Calendar.getInstance();

		String dateToString , timeToString ;
		String dateStatus;
		int dateNoon = cal.get(Calendar.AM_PM);
		int dateHour = cal.get(Calendar.HOUR_OF_DAY);
		int dateMinute = cal.get(Calendar.MINUTE);
	
		if(dateNoon == 0){
			dateStatus = "오전";
		}
		else{
			dateStatus = "오후";
			dateHour = dateHour-12;
		}
		//dateToString = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		//timeToString = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		
		String timeString = dateStatus+" "+dateHour+":"+String.format("%02d",dateMinute);
	
		my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MY_WORD,chat_val,timeString));
		my_list_count += 1;     
		
		my_adapter.notifyDataSetChanged();
		
		my_listview.setSelection(my_list.size());
	}
	

}

