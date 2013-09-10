package app.light;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MyClosetAdapter extends BaseAdapter {
	private ArrayList<ClosetObj> list;
	private Context context;
	private LayoutInflater inflater;
	private int layout;
	
	private Resources res;
	private String packName = "app.light";
	
	MyClosetAdapter(Context context, ArrayList<ClosetObj> my_list) {
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
	public ClosetObj getItem(int position) {
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
			layout = R.layout.closet_item;
			convertView = inflater.inflate(layout, parent, false);
		}

		TextView item_title = (TextView)convertView.findViewById(R.id.closet_item_title);
		item_title.setText(list.get(position).title);
		ImageButton item_clothes = (ImageButton)convertView.findViewById(R.id.closet_item_clothes);
		String clothes_str = list.get(position).clothes;
		
		res = context.getResources();
		
		String clothes_back = "@drawable/clothes_"+clothes_str;
	
		item_clothes.setBackgroundResource(res.getIdentifier(clothes_back, "drawable", packName));
		
		/*
		title_content.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				String str = list.get(pos).content;
				Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
			}
		});
		*/
		return convertView;
	}

}
