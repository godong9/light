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
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"1","11","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"2","111","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"3","111","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"4","111","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"5","111","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"6","111","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"7","111","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"8","111","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"9","111","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"10","111","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"11","111","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"12","111","asdfsdf"));
        my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_OTHER_WORD,"13","111","asdfsdf"));
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

