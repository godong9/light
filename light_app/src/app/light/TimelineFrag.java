package app.light;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class TimelineFrag extends CommonFragment {
	
	
	
	private ArrayList<String> my_list;
	private MyListAdapter my_adapter;
	private ListView my_listview;
	Context context;

	public TimelineFrag(Context context) {
		super();
		this.context = context;
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.timeline_frag, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		setListView();
	}
	
	public void setListView() {
		
		  my_list = new ArrayList<String>();
	        //lock_listview = true;
			my_list.add("Red");
			my_list.add("Green");
			my_list.add("Blue");
			    
			   // View vv = (View)findViewById(R.id.detail_frag);
			
			my_adapter = new MyListAdapter(context, R.layout.listview_item, my_list);
			
			//Log.i("[test1]", this.toString());
			
			// 리스트뷰에 어댑터 연결
		    my_listview = (ListView)((Activity)context).findViewById(R.id.timeline_scroll);
		    
		   
		   // Log.i("[test2]", my_listview.toString());
		    System.out.println("my adapter is " + my_adapter);
		    System.out.println("my_listview is " + my_listview);
		    
		    my_listview.setAdapter(my_adapter);
		    
	}

}

