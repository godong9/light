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
	private Context ctx;
	private int itemLayout;
	
	MyListAdapter(Context ctx, int itemLayout, ArrayList<TimeLineObj> my_list) {
		this.ctx = ctx;
		this.itemLayout = itemLayout;
		this.list = my_list;
	}
		 
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public String getItem(int position) {
		return list.get(position).ID;
//		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		if (convertView == null) {
			
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(itemLayout, parent, false);
					
			

		}
		
		TextView tvID = (TextView) convertView.findViewById(R.id.ID);
		tvID.setText(list.get(pos).ID);
		
		TextView tvContent = (TextView) convertView.findViewById(R.id.content);
		tvContent.setText(list.get(pos).content);

		TextView tvTime = (TextView) convertView.findViewById(R.id.time);
		tvTime.setText(list.get(pos).time);

		return convertView;
	}
	
}
