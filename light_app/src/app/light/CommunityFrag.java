package app.light;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CommunityFrag extends CommonFragment {
	
	private Context context;
	private ArrayList<CommunityObj> community_list;
	private MyCommunityAdapter community_adapter;
	private ListView community_listview;
	private int community_list_count = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_community, container, false);
		context = getActivity();
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		setCommunityList();
	}			      
	
	public void setCommunityList(){
		community_list = new ArrayList<CommunityObj>();
		community_list.add(new CommunityObj("공지", "세븐스프링스 이벤트 당첨자 확인하세요!", "LIGHT", "2013.08.22", "조회수 61", "10"));
		community_list.add(new CommunityObj("공지", "세븐스프링스 이벤트 당첨자 확인하세요!", "LIGHT", "2013.08.22", "조회수 61", "10"));
		community_list.add(new CommunityObj("공지", "세븐스프링스 이벤트 당첨자 확인하세요!dsgsdgsdgsdgsdgwegwegweg", "LIGHT", "2013.08.22", "조회수 61", "10"));
		community_list.add(new CommunityObj("공지", "세븐스프링스 이벤트 당첨자 확인하세요!dsgsdgsdgsdgsdgwegwegweg", "LIGHT", "2013.08.22", "조회수 61", "10"));
		community_list.add(new CommunityObj("공지", "세븐스프링스 이벤트 당첨자 확인하세요!dsgsdgsdgsdgsdgwegwegweg", "LIGHT", "2013.08.22", "조회수 61", "10"));
		community_list.add(new CommunityObj("공지", "세븐스프링스 이벤트 당첨자 확인하세요!dsgsdgsdgsdgsdgwegwegweg", "LIGHT", "2013.08.22", "조회수 61", "10"));
		community_list.add(new CommunityObj("공지", "세븐스프링스 이벤트 당첨자 확인하세요!dsgsdgsdgsdgsdgwegwegweg", "LIGHT", "2013.08.22", "조회수 61", "10"));
		community_list.add(new CommunityObj("공지", "세븐스프링스 이벤트 당첨자 확인하세요!dsgsdgsdgsdgsdgwegwegweg", "LIGHT", "2013.08.22", "조회수 61", "10"));
		community_list.add(new CommunityObj("공지", "세븐스프링스 이벤트 당첨자 확인하세요!dsgsdgsdgsdgsdgwegwegweg", "LIGHT", "2013.08.22", "조회수 61", "10"));
		community_list.add(new CommunityObj("공지", "세븐스프링스 이벤트 당첨자 확인하세요!dsgsdgsdgsdgsdgwegwegweg", "LIGHT", "2013.08.22", "조회수 61", "10"));
		community_list.add(new CommunityObj("공지", "세븐스프링스 이벤트 당첨자 확인하세요!dsgsdgsdgsdgsdgwegwegweg", "LIGHT", "2013.08.22", "조회수 61", "10"));
		community_list.add(new CommunityObj("공지", "세븐스프링스 이벤트 당첨자 확인하세요!dsgsdgsdgsdgsdgwegwegweg", "LIGHT", "2013.08.22", "조회수 61", "10"));
		
		community_adapter = new MyCommunityAdapter(context, community_list);
		
		// 리스트뷰에 어댑터 연결
	    community_listview = (ListView)((Activity)context).findViewById(R.id.community_scroll);	  
	    community_listview.setAdapter(community_adapter);
	 	
	}
}