package app.light;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyCommunityAdapter extends BaseAdapter {
	private ArrayList<CommunityObj> list;
	private Context context;
	private LayoutInflater inflater;
	private int layout;
	
	MyCommunityAdapter(Context context, ArrayList<CommunityObj> my_list) {
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
	public CommunityObj getItem(int position) {
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
			layout = R.layout.community_title;
			convertView = inflater.inflate(layout, parent, false);
		}
		
		TextView title_type = (TextView)convertView.findViewById(R.id.community_title_type);
		
		String tmp_type = list.get(position).type;
		title_type.setText(tmp_type);
		
		if(tmp_type.equals("공지")){
			title_type.setBackgroundResource(R.drawable.community_type_notice_background);
		}
		else{
			title_type.setBackgroundResource(R.drawable.community_type_etc_background);
		}
		
		TextView title_content = (TextView)convertView.findViewById(R.id.community_title_content);
		title_content.setText(list.get(position).title_content);
		
		TextView title_info = (TextView)convertView.findViewById(R.id.community_title_info);
		title_info.setText(list.get(position).nickname + " / " + list.get(position).reg_date + " / " + list.get(position).hits);

		
		TextView title_comment = (TextView)convertView.findViewById(R.id.community_title_comment);
		title_comment.setText(list.get(position).comment);
	
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
