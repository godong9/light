package app.light;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCommentAdapter extends BaseAdapter {
	private ArrayList<CommentObj> list;
	private Context context;
	private LayoutInflater inflater;
	private int layout;
	
	MyCommentAdapter(Context context, ArrayList<CommentObj> my_list) {
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
	public CommentObj getItem(int position) {
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
			layout = R.layout.comment_item;
			convertView = inflater.inflate(layout, parent, false);
		}

		TextView comment_content = (TextView)convertView.findViewById(R.id.community_comment_item_content);
		comment_content.setText(list.get(position).content);
		
		TextView comment_info = (TextView)convertView.findViewById(R.id.community_comment_item_info);
		comment_info.setText(list.get(position).nickname + " / " + list.get(position).reg_date);

		return convertView;
	}

}
