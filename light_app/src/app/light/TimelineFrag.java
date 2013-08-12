package app.light;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;


public class TimelineFrag extends CommonFragment implements OnScrollListener {
		
	public final static int INSERT_COUNT = 5;
	
	private Context context;
	private ArrayList<TimeLineObj> my_list;
	private MyListAdapter my_adapter;
	private ListView my_listview;
	private int my_list_count = 0;
		
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.frag_timeline, container, false);
	}
	
	@Override
	public void onStart() {  
		//Fragment가 완전히 생성되고 난 후에 호출되는 함수
		super.onStart();
		context = getActivity();
		setListView();
	}
	
	public void setListView() {
		
		my_list = new ArrayList<TimeLineObj>();
  
        //리스트에 아이템 넣는 부분
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"영아입니다","테스트","오후 11:20"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"윤하","테스트테스트테스트테스트테스트테스트테스트테스트","오전 12:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"3","테스트테","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MY_WORD,"테스트테스","오후 1:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MY_FOOD, "60","자전거","-550Kcal","오후 12:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_FOOD, "민옥입니다","점심","냉모밀","+750Kcal","오전 11:50"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_EXERCISE,"영아니다","30","웨이트트레이닝","-300Kcal","오후 1:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MY_EXERCISE,"60","걷기","-200Kcal","오전 10:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MANAGER_MISSION, 0, 0, "운동", "자전거 1시간 타기","오후 12:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MANAGER_WORD, 0, 0, "오늘 운동 많이했나요?","오후 13:00"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_TIMEBAR, 0, "10일째","2013. 8. 13"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MY_PICTURE,"test","오후 2:20"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_PICTURE,"영아","test","오후 3:10"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"14","111","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"15","111","asdfsdf"));
            
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
	
}

