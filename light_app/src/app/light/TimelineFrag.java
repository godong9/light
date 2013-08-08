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
	
	private ArrayList<String> my_list;
	private MyListAdapter my_adapter;
	private ListView my_listview;
	private boolean lock_listview;
	Context context;
	
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
		
		my_list = new ArrayList<String>();
        lock_listview = false;
        
		my_list.add("Ba1");
		my_list.add("Be2");
		my_list.add("B3");
		my_list.add("B4");
		my_list.add("B5");
		my_list.add("B6");
		my_list.add("B7");
		my_list.add("B8");
		
		my_list.add("B9");
		my_list.add("B10");
		my_list.add("B11");
		my_list.add("B12");
		my_list.add("B13");
		my_list.add("B14");
		my_list.add("B15");
		my_list.add("B16");
		my_list.add("B17");
		my_list.add("B18");
		my_list.add("B19");
		my_list.add("B20");
		my_list.add("B20");
		my_list.add("B20");
		my_list.add("B20");
		my_list.add("B20");
		my_list.add("B20");
		my_list.add("B20");
		my_list.add("B20");
		my_list.add("B30");
		
		my_list.add("B31");
		my_list.add("B20");
		my_list.add("B20");
		my_list.add("B40");

		
		System.out.println(my_list);
		   // View vv = (View)findViewById(R.id.detail_frag);
		
		my_adapter = new MyListAdapter(context, R.layout.listview_item, my_list);
		
		//Log.i("[test1]", this.toString());
		
		// 리스트뷰에 어댑터 연결
	    my_listview = (ListView)((Activity)context).findViewById(R.id.timeline_scroll);
	    
	   
	   // Log.i("[test2]", my_listview.toString());
	    System.out.println("my adapter is " + my_adapter);
	    System.out.println("my_listview is " + my_listview);
	    
	    my_listview.setOnScrollListener(this);
	    my_listview.setAdapter(my_adapter);
	    
	    my_listview.setSelection(my_adapter.getCount() - 1);
	   // addItems(10);
		    
	}
	
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
		
		int count = totalItemCount - visibleItemCount;

		if(firstVisibleItem == 0 && totalItemCount != 0 && lock_listview == false)
		{
			Log.i("[test]", "Loading next items");
			addItems(5);
		}	
	}

	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
	}

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
					my_list.add("Item " + i);
				}
				
				System.out.println(my_list);
				my_adapter.notifyDataSetChanged();
				lock_listview = false;
			}
		};
		
		// �ӵ��� �����̸� �����ϱ� ���� �ļ�
		Handler handler = new Handler();
		handler.postDelayed(run, 5000);
	}

}

