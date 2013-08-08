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
	private ArrayList<String>list;
	private Context ctx;
	private int itemLayout;
	
	MyListAdapter(Context ctx, int itemLayout, ArrayList<String> list) {
		this.ctx = ctx;
		this.itemLayout = itemLayout;
		this.list = list;
	}
		 
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public String getItem(int position) {
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
			
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(itemLayout, parent, false);
			/*
			TextView textView = (TextView) convertView.findViewById(R.id.text);
			textView.setText(list.get(pos));
			View view = convertView.findViewById(R.id.view);
			
			int color = 0;
			if (list.get(position).equals("Red"))
				color = Color.RED;
			else if (list.get(position).equals("Green"))
				color = Color.GREEN;
			else if (list.get(position).equals("Blue"))
				color = Color.BLUE;
			view.setBackgroundColor(color);
			*/
			Button btn = (Button) convertView.findViewById(R.id.btn);
			
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(ctx, list.get(pos), Toast.LENGTH_SHORT)
							.show();
				}
			});
		}
		return convertView;
	}
	
}
