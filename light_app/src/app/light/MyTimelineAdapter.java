package app.light;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Bitmap;

public class MyTimelineAdapter extends BaseAdapter {
	private ArrayList<TimeLineObj> list;
	private Context context;
	private LayoutInflater inflater;

	MyTimelineAdapter(Context context, ArrayList<TimeLineObj> my_list) {
		this.context = context;
		this.list = my_list;

		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public TimeLineObj getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		if (convertView == null) {
			int res = 0;
			// 해당 뷰가 처음 생겨날때 resource 세팅
			switch (list.get(position).type) {
				case TimeLineObj.VIEW_TYPE_TIMEBAR:
					res = R.layout.timeline_timebar;
				break;
				
				case TimeLineObj.VIEW_TYPE_MY_WORD:
					res = R.layout.timeline_my_word;
					break;
				case TimeLineObj.VIEW_TYPE_MY_FOOD:
					res = R.layout.timeline_my_write;
					break;
				case TimeLineObj.VIEW_TYPE_MY_EXERCISE:
					res = R.layout.timeline_my_write;
					break;
				case TimeLineObj.VIEW_TYPE_MY_PICTURE:
					res = R.layout.timeline_my_picture;
					break;
				case TimeLineObj.VIEW_TYPE_MY_WEIGHT:
					res = R.layout.timeline_my_weight;
					break;
					
				case TimeLineObj.VIEW_TYPE_OTHER_WORD:
					res = R.layout.timeline_other_word;
					break;
				case TimeLineObj.VIEW_TYPE_OTHER_FOOD:
					res = R.layout.timeline_other_write;
					break;
				case TimeLineObj.VIEW_TYPE_OTHER_EXERCISE:
					res = R.layout.timeline_other_write;
					break;
				case TimeLineObj.VIEW_TYPE_OTHER_PICTURE:
					res = R.layout.timeline_other_picture;
					break;
				case TimeLineObj.VIEW_TYPE_OTHER_WEIGHT:
					res = R.layout.timeline_other_weight;
					break;
					
				case TimeLineObj.VIEW_TYPE_MANAGER_WORD:
					res = R.layout.timeline_manager_word;
					break;
				case TimeLineObj.VIEW_TYPE_MANAGER_MISSION:
					res = R.layout.timeline_manager_mission;
					break;
			}

			convertView = inflater.inflate(res, parent, false);
		}
	
		TextView tvNickname;	
		TextView tvContent;
		TextView tvDate;
		TextView tvPreContent;
		TextView tvCalorie;
		TextView tvDayCount;
		LinearLayout llWriteType;
		ImageView ivPicture;
		/*
		public int type;
		public String write_type;
		public String nickname;
		public String pre_content;
		public String content;
		public String calorie;
		public String day_count;
		public int bar_status;
		public String date;
		public ImageView manager_img;
		public ImageView img;
		public String mission_type;
		*/
		
		switch (list.get(position).type) {
			case TimeLineObj.VIEW_TYPE_TIMEBAR:
				tvDayCount = (TextView) convertView.findViewById(R.id.timeline_timebar_day_count);
				tvDate = (TextView) convertView.findViewById(R.id.timeline_timebar_date);				
				
				tvDayCount.setText(list.get(position).day_count);
				tvDate.setText(list.get(position).date);
				break;	
				
			case TimeLineObj.VIEW_TYPE_MY_WORD:
				tvDate = (TextView) convertView.findViewById(R.id.timeline_my_word_date);
				tvContent = (TextView) convertView.findViewById(R.id.timeline_my_word_content);
				
				tvDate.setText(list.get(position).date);
				tvContent.setText(list.get(position).content);
				break;	
				
			case TimeLineObj.VIEW_TYPE_MY_FOOD:
				tvDate = (TextView) convertView.findViewById(R.id.timeline_my_write_date);
				tvPreContent = (TextView) convertView.findViewById(R.id.timeline_my_write_pre_content);
				tvContent = (TextView) convertView.findViewById(R.id.timeline_my_write_content);
				tvCalorie = (TextView) convertView.findViewById(R.id.timeline_my_write_calorie);
				llWriteType = (LinearLayout) convertView.findViewById(R.id.timeline_my_write_type);
				
				llWriteType.setBackgroundResource(R.drawable.timeline_my_food);
				tvDate.setText(list.get(position).date);
				tvPreContent.setText(list.get(position).pre_content); 
				tvContent.setText(list.get(position).content);
				tvCalorie.setText("+"+list.get(position).calorie+"Kcal"); 
				break;
				
			case TimeLineObj.VIEW_TYPE_MY_EXERCISE:
				tvDate = (TextView) convertView.findViewById(R.id.timeline_my_write_date);
				tvPreContent = (TextView) convertView.findViewById(R.id.timeline_my_write_pre_content);
				tvContent = (TextView) convertView.findViewById(R.id.timeline_my_write_content);
				tvCalorie = (TextView) convertView.findViewById(R.id.timeline_my_write_calorie);
				llWriteType = (LinearLayout) convertView.findViewById(R.id.timeline_my_write_type);
				
				llWriteType.setBackgroundResource(R.drawable.timeline_my_exercise);
				tvDate.setText(list.get(position).date);
				tvPreContent.setText(list.get(position).pre_content); 
				tvContent.setText(list.get(position).content);
				tvCalorie.setText("-"+list.get(position).calorie+"Kcal"); 
				break;

			case TimeLineObj.VIEW_TYPE_MY_PICTURE:
				tvDate = (TextView) convertView.findViewById(R.id.timeline_my_picture_date);
				ivPicture = (ImageView) convertView.findViewById(R.id.timeline_my_picture_content);
				
				ivPicture.setImageBitmap(list.get(position).picture_img);			
				tvDate.setText(list.get(position).date);
				break;
				
			case TimeLineObj.VIEW_TYPE_MY_WEIGHT:			
				tvDate = (TextView) convertView.findViewById(R.id.timeline_my_weight_date);
				tvContent = (TextView) convertView.findViewById(R.id.timeline_my_weight_content);

				tvDate.setText(list.get(position).date);
				tvContent.setText(list.get(position).content);
				break;	
							
			case TimeLineObj.VIEW_TYPE_OTHER_WORD:		
				tvNickname = (TextView) convertView.findViewById(R.id.timeline_other_word_nickname);
				tvContent = (TextView) convertView.findViewById(R.id.timeline_other_word_content);
				tvDate = (TextView) convertView.findViewById(R.id.timeline_other_word_date);
	
				tvNickname.setText(list.get(position).nickname);
				tvContent.setText(list.get(position).content);
				tvDate.setText(list.get(position).date);
				break;	
				
			case TimeLineObj.VIEW_TYPE_OTHER_FOOD:
				tvNickname = (TextView) convertView.findViewById(R.id.timeline_other_write_nickname);
				tvDate = (TextView) convertView.findViewById(R.id.timeline_other_write_date);
				tvPreContent = (TextView) convertView.findViewById(R.id.timeline_other_write_pre_content);
				tvContent = (TextView) convertView.findViewById(R.id.timeline_other_write_content);
				tvCalorie = (TextView) convertView.findViewById(R.id.timeline_other_write_calorie);
				llWriteType = (LinearLayout) convertView.findViewById(R.id.timeline_other_write_type);
				
				llWriteType.setBackgroundResource(R.drawable.timeline_other_food);
				tvNickname.setText(list.get(position).nickname);
				tvDate.setText(list.get(position).date);
				tvPreContent.setText(list.get(position).pre_content); 
				tvContent.setText(list.get(position).content);
				tvCalorie.setText("+"+list.get(position).calorie+"Kcal"); 
				break;

			case TimeLineObj.VIEW_TYPE_OTHER_EXERCISE:
				tvNickname = (TextView) convertView.findViewById(R.id.timeline_other_write_nickname);
				tvDate = (TextView) convertView.findViewById(R.id.timeline_other_write_date);
				tvPreContent = (TextView) convertView.findViewById(R.id.timeline_other_write_pre_content);
				tvContent = (TextView) convertView.findViewById(R.id.timeline_other_write_content);
				tvCalorie = (TextView) convertView.findViewById(R.id.timeline_other_write_calorie);
				llWriteType = (LinearLayout) convertView.findViewById(R.id.timeline_other_write_type);
				
				llWriteType.setBackgroundResource(R.drawable.timeline_other_exercise);
				tvNickname.setText(list.get(position).nickname);
				tvDate.setText(list.get(position).date);
				tvPreContent.setText(list.get(position).pre_content); 
				tvContent.setText(list.get(position).content);
				tvCalorie.setText("-"+list.get(position).calorie+"Kcal"); 
				break;
				
			case TimeLineObj.VIEW_TYPE_OTHER_PICTURE:
				tvNickname = (TextView) convertView.findViewById(R.id.timeline_other_picture_nickname);
				tvDate = (TextView) convertView.findViewById(R.id.timeline_other_picture_date);
				ivPicture = (ImageView) convertView.findViewById(R.id.timeline_other_picture_content);
				
				ivPicture.setImageBitmap(list.get(position).picture_img);	
				tvNickname.setText(list.get(position).nickname);
				tvDate.setText(list.get(position).date);
				break;		
				
			case TimeLineObj.VIEW_TYPE_OTHER_WEIGHT:
				tvNickname = (TextView) convertView.findViewById(R.id.timeline_other_weight_nickname);
				tvDate = (TextView) convertView.findViewById(R.id.timeline_other_weight_date);
				tvContent = (TextView) convertView.findViewById(R.id.timeline_other_weight_content);
	
				tvNickname.setText(list.get(position).nickname);
				tvDate.setText(list.get(position).date);
				tvContent.setText(list.get(position).content);
				break;	
				
			case TimeLineObj.VIEW_TYPE_MANAGER_WORD:
				tvContent = (TextView) convertView.findViewById(R.id.timeline_manager_word_content);
				tvDate = (TextView) convertView.findViewById(R.id.timeline_manager_word_date);
	
				tvContent.setText(list.get(position).content);
				tvDate.setText(list.get(position).date);
				break;
				
			case TimeLineObj.VIEW_TYPE_MANAGER_MISSION:
				tvDate = (TextView) convertView.findViewById(R.id.timeline_manager_mission_date);
				tvPreContent = (TextView) convertView.findViewById(R.id.timeline_manager_mission_pre_content);
				tvContent = (TextView) convertView.findViewById(R.id.timeline_manager_mission_content);
				
				tvDate.setText(list.get(position).date);
				tvPreContent.setText(list.get(position).pre_content); 
				tvContent.setText(list.get(position).content);
				break;
		}

		return convertView;
	}

	public int getItemViewType(int position) {
		return list.get(position).type;
	}

	public int getViewTypeCount() {
		return 13;
	}

}
