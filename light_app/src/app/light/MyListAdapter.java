package app.light;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyListAdapter extends BaseAdapter {
	private ArrayList<TimeLineObj> list;
	private Context context;
	private LayoutInflater inflater;

	MyListAdapter(Context context, ArrayList<TimeLineObj> my_list) {
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
		// 뷰 얻어오는 부분 -> 여기 수정 필요
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
					res = R.layout.timeline_my_food;
					break;
				case TimeLineObj.VIEW_TYPE_MY_EXERCISE:
					res = R.layout.timeline_my_exercise;
					break;
				case TimeLineObj.VIEW_TYPE_MY_PICTURE:
					res = R.layout.timeline_my_picture;
					break;
					
				case TimeLineObj.VIEW_TYPE_OTHER_WORD:
					res = R.layout.timeline_other_word;
					break;
				case TimeLineObj.VIEW_TYPE_OTHER_FOOD:
					res = R.layout.timeline_other_food;
					break;
				case TimeLineObj.VIEW_TYPE_OTHER_EXERCISE:
					res = R.layout.timeline_other_exercise;
					break;
				case TimeLineObj.VIEW_TYPE_OTHER_PICTURE:
					res = R.layout.timeline_other_picture;
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
		TextView tvFoodType;
		TextView tvFoodCal;
		TextView tvExerciseTime;
		TextView tvExerciseCal;
		TextView tvDay;
		TextView tvMission;
		ImageView ivManager;
		ImageView ivPicture;	
		
		//전체 data type 
		/*
		public int type;
		public String nickname;
		public String food_type;
		public int exercise_time;
		public String content;
		public int food_calorie;
		public int exercise_calorie;
		public int day_count;
		public String date;
		public ImageView manager_img;
		public ImageView img;
		public String mission_type;
		*/
		
		switch (list.get(position).type) {
			case TimeLineObj.VIEW_TYPE_TIMEBAR:
				//
			break;

			case TimeLineObj.VIEW_TYPE_MY_WORD:
				tvDate = (TextView) convertView.findViewById(R.id.timeline_my_word_date);
				tvContent = (TextView) convertView.findViewById(R.id.timeline_my_word_content);
	
				tvDate.setText(list.get(position).date);
				tvContent.setText(list.get(position).content);
	
				break;	
			case TimeLineObj.VIEW_TYPE_MY_FOOD:
				tvDate = (TextView) convertView.findViewById(R.id.timeline_my_food_date);
				tvFoodType = (TextView) convertView.findViewById(R.id.timeline_my_food_type);
				tvContent = (TextView) convertView.findViewById(R.id.timeline_my_food_content);
				tvFoodCal = (TextView) convertView.findViewById(R.id.timeline_my_food_calorie);
				
				tvDate.setText(list.get(position).date);
				tvFoodType.setText(list.get(position).food_type); 
				tvContent.setText(list.get(position).content);
				tvFoodCal.setText(list.get(position).food_calorie); 
				//
				break;
			case TimeLineObj.VIEW_TYPE_MY_EXERCISE:
				//
				break;
			case TimeLineObj.VIEW_TYPE_MY_PICTURE:
				//
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
				//
				break;
			case TimeLineObj.VIEW_TYPE_OTHER_EXERCISE:
				//
				break;
			case TimeLineObj.VIEW_TYPE_OTHER_PICTURE:
				//
				break;
				
			case TimeLineObj.VIEW_TYPE_MANAGER_WORD:
				//
				break;
			case TimeLineObj.VIEW_TYPE_MANAGER_MISSION:
				//
				break;
		}

		return convertView;
	}

	public int getItemViewType(int position) {
		return list.get(position).type;
	}

	public int getViewTypeCount() {
		return 11;
	}

}
