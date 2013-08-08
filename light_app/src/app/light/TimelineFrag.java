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
	
	public final static int INSERT_COUNT = 10;
	private ArrayList<TimeLineObj> my_list;
	private MyListAdapter my_adapter;
	private ListView my_listview;
	private boolean lock_listview;
	private Context context;
	private int m_list_count = 0;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.timeline_frag, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		context = getActivity();
		setListView();
	}
	
	public void setListView() {
		
		my_list = new ArrayList<TimeLineObj>();
        lock_listview = false;

        
        //item을 넣는 부분
        my_list.add(new TimeLineObj("1","11","asdfsdf"));
        my_list.add(new TimeLineObj("2","111","asdfsdf"));
        my_list.add(new TimeLineObj("3","111","asdfsdf"));
        my_list.add(new TimeLineObj("4","111","asdfsdf"));
        my_list.add(new TimeLineObj("5","11","asdfsdf"));
        my_list.add(new TimeLineObj("6","111","asdfsdf"));
        my_list.add(new TimeLineObj("7","111","asdfsdf"));
        my_list.add(new TimeLineObj("8","111","asdfsdf"));
        my_list.add(new TimeLineObj("9","11","asdfsdf"));
        my_list.add(new TimeLineObj("10","111","asdfsdf"));

        
        m_list_count += INSERT_COUNT;
		   // View vv = (View)findViewById(R.id.detail_frag);
		
		my_adapter = new MyListAdapter(context, R.layout.listview_item, my_list);
		
		//Log.i("[test1]", this.toString());
		
		// 리스트뷰에 어댑터 연결
	    my_listview = (ListView)((Activity)context).findViewById(R.id.timeline_scroll);
	    
	   
	    my_listview.setAdapter(my_adapter);
	    my_listview.setOnScrollListener(this);
	    my_listview.setSelection(my_adapter.getCount() - 1);
		    
	}
	
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
		/*
		int count = totalItemCount - visibleItemCount;

		if(firstVisibleItem == 0 && totalItemCount != 0 && lock_listview == false)
		{
			Log.i("[test]", "Loading next items");
			
		}
		*/	
	}

	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		 // 리스트뷰가 구성이 완료되어 보이는 경우
        if(view.isShown()){
            if(scrollState == SCROLL_STATE_IDLE) {
                // 리스트뷰의 0 번 인덱스 항목이 리스트뷰의 상단에 보이고 있는 경우
                if(view.getFirstVisiblePosition() == 0) {
                    // 항목을 추가한다.
                    String str;
                    for(int i = 0; i < INSERT_COUNT; i++) {   
                        my_list.add(0, new TimeLineObj("99"+i,"111","asdfsdf"));
                    }
                    m_list_count += INSERT_COUNT;
          
                    view.setSelection(INSERT_COUNT);
                }
            }
        }
	}

	/*
	private void addItems(final int size)
	{

		lock_listview = true;
		
		Runnable run = new Runnable()
		{
			@Override
			public void run()
			{
				for(int i = 0 ; i < size ; i++)
				{
					 my_list.add(0, new TimeLineObj("99"+i,"111","asdfsdf"));
				}
				
				System.out.println(my_list);
				my_adapter.notifyDataSetChanged();
				lock_listview = false;
			}
		};
		//my_list.setSelectionFromTop();
		
		Handler handler = new Handler();
		handler.postDelayed(run, 3000);
	}
	*/

}

