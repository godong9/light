package app.light;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
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
	
	private Dialog dialog;
	private Resources res;
	private String packName = "app.light";
	
	MyClosetAdapter(Context context, ArrayList<ClosetObj> my_list) {
		this.context = context;
		this.list = my_list;

		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setDialog(Dialog dialog){
		this.dialog = dialog;
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
	
		item_clothes.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				String select_clothes = list.get(pos).clothes;
				
				JSONObject json_param = new JSONObject();
				try {
					json_param.put("clothes", select_clothes);
					//postData 함수로 데이터 전송
					CommonHttp ch = new CommonHttp();	
					String result_json = ch.postData("http://211.110.61.51:3000/change_clothes", json_param);		
					if(result_json.equals("error")){
						Toast.makeText(context, "서버 통신 실패!", Toast.LENGTH_SHORT).show();
					}
					else{
						System.out.println("rs:"+result_json);
						JSONObject json_data = new JSONObject(result_json);

						String character_str = json_data.getString("character");
	
						ImageButton ib_character = (ImageButton)dialog.findViewById(R.id.closet_dialog_character);
						ImageButton main_character = (ImageButton)((Activity)context).findViewById(R.id.rival_user1_click);
						String character_back = "@drawable/character_"+character_str;
						res = context.getResources();
						ib_character.setBackgroundResource(res.getIdentifier(character_back, "drawable", packName));
						main_character.setBackgroundResource(res.getIdentifier(character_back, "drawable", packName));
						RivalFrag rf = new RivalFrag(1);
						rf.my_info.put("character", character_str);
					}
				}
				catch(Exception e){
					System.out.println("JSON 에러");
				}
				
			}
		});

		return convertView;
	}

}
