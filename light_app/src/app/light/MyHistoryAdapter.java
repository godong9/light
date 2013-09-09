package app.light;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyHistoryAdapter extends BaseAdapter {
	private ArrayList<HistoryObj> list;
	private Context context;
	private LayoutInflater inflater;
	private int layout;
	
	MyHistoryAdapter(Context context, ArrayList<HistoryObj> my_list) {
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
	public HistoryObj getItem(int position) {
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
			layout = R.layout.history_item;
			convertView = inflater.inflate(layout, parent, false);
		}

		TextView history_date = (TextView)convertView.findViewById(R.id.history_date);
		history_date.setText(list.get(position).date);
		
		String status_str = list.get(position).status;	
		ImageView status = (ImageView)convertView.findViewById(R.id.history_status);
		
		if(status_str.equals("good")){
			status.setBackgroundResource(R.drawable.history_calorie_good);
		}
		else{
			status.setBackgroundResource(R.drawable.history_calorie_bad);
		}
		
		TextView food_calorie = (TextView)convertView.findViewById(R.id.history_food_calorie);
		food_calorie.setText(list.get(position).food_calorie+"Kcal");
		
		TextView exercise_calorie = (TextView)convertView.findViewById(R.id.history_exercise_calorie);
		exercise_calorie.setText(list.get(position).exercise_calorie+"Kcal");
		
		int result_calorie_num = Integer.parseInt(list.get(position).food_calorie) - Integer.parseInt(list.get(position).exercise_calorie);
		
		TextView result_calorie = (TextView)convertView.findViewById(R.id.history_result_calorie);
		result_calorie.setText(result_calorie_num+"Kcal");

		return convertView;
	}

}
