package app.light;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyListAdapter extends BaseAdapter {
	private ArrayList<TimeLineObj> list;
	private Context context;
	private int itemLayout;
	
	//새로 추가한 부분
	LayoutInflater inflater;
	
	MyListAdapter(Context context, int itemLayout, ArrayList<TimeLineObj> my_list) {
		
		this.context = context;
		this.itemLayout = itemLayout;
		this.list = my_list;
		
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
		 
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public String getItem(int position) {
		return list.get(position).nickname;
//		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		final int pos = position;
//		if (convertView == null) {
//			
//			LayoutInflater inflater = (LayoutInflater) ctx
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			convertView = inflater.inflate(itemLayout, parent, false);
//					
//			
//
//		}
//		
//		TextView tvID = (TextView) convertView.findViewById(R.id.ID);
//		tvID.setText(list.get(pos).ID);
//		
//		TextView tvContent = (TextView) convertView.findViewById(R.id.content);
//		tvContent.setText(list.get(pos).content);
//
//		TextView tvTime = (TextView) convertView.findViewById(R.id.time);
//		tvTime.setText(list.get(pos).time);
//
//		return convertView;
//	}
	//새로 추가된 부분
	public int getItemViewType(int position) {
		return list.get(position).type;
	}
	
	
	public int getViewTypeCount() {
		return 11;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		if (convertView == null) {
			int res = 0;
			
			//해당 뷰가 처음 생겨날때 resource 세팅
			switch(list.get(position).type) {
				case TimeLineObj.VIEW_TYPE_MY_WORD:
					
					res = R.layout.timeline_my_word;
					break;
					
				case TimeLineObj.VIEW_TYPE_OTHER_WORD:
					
					res = R.layout.timeline_other_word;
					break;
			}
			
			convertView = inflater.inflate(itemLayout, parent, false);

		}
		
		TextView tvTime;
		TextView tvContent;
		TextView tvID;
		
		switch(list.get(position).type) {
		
		
		
		case TimeLineObj.VIEW_TYPE_MY_WORD:
			//해당 뷰 세팅
			 tvTime = (TextView) convertView.findViewById(R.id.time);
			 tvContent = (TextView) convertView.findViewById(R.id.content);
			
			tvTime.setText(list.get(position).time);
			tvContent.setText(list.get(position).content);
			
			break;
			
		case TimeLineObj.VIEW_TYPE_OTHER_WORD:
			
			tvTime = (TextView) convertView.findViewById(R.id.time);
			tvContent = (TextView) convertView.findViewById(R.id.content);
			tvID = (TextView) convertView.findViewById(R.id.ID);
			
			tvTime.setText(list.get(position).time);
			tvContent.setText(list.get(position).content);
			tvID.setText(list.get(position).nickname);
			
			break;
		}
		
		
		
		


		return convertView;
	}
	
	
}
