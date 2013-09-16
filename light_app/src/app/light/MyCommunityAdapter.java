package app.light;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MyCommunityAdapter extends BaseAdapter {
	private ArrayList<CommunityObj> list;
	private Context context;
	private LayoutInflater inflater;
	private int layout;
	public LinearLayout ll_title;
	public LinearLayout ll_content;
	public LinearLayout ll_write;
	public LinearLayout ll_comment;
	public String tmp_type = "";
	
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
		title_info.setText(list.get(position).nickname + " / " + list.get(position).reg_date + " / 조회수: " + list.get(position).hits);

		
		TextView title_comment = (TextView)convertView.findViewById(R.id.community_title_comment);
		title_comment.setText(list.get(position).comment);
	
		
		title_content.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				v.requestFocus();
				EditText et = (EditText)((Activity)context).findViewById(R.id.community_search_val);
				InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(et.getWindowToken(),0); 
				
				LinearLayout ll_title = (LinearLayout)((Activity)context).findViewById(R.id.community_title_layout);
				LinearLayout ll_content = (LinearLayout)((Activity)context).findViewById(R.id.community_content_layout);
				ll_title.setVisibility(View.GONE);
				ll_content.setVisibility(View.VISIBLE);
					
				TextView title_type = (TextView)((Activity)context).findViewById(R.id.community_content_title_type);
				
				String tmp_type = list.get(pos).type;
				title_type.setText(tmp_type);
				
				if(tmp_type.equals("공지")){
					title_type.setBackgroundResource(R.drawable.community_type_notice_background);
				}
				else{
					title_type.setBackgroundResource(R.drawable.community_type_etc_background);
				}
				
				TextView title_content = (TextView)((Activity)context).findViewById(R.id.community_content_title_content);
				title_content.setText(list.get(pos).title_content);
				
				TextView title_info = (TextView)((Activity)context).findViewById(R.id.community_content_title_info);
				title_info.setText(list.get(pos).nickname + " / " + list.get(pos).reg_date + " / 조회수: " + list.get(pos).hits);
				
				TextView content = (TextView)((Activity)context).findViewById(R.id.community_content_text);
				content.setText(list.get(pos).content);	
			}
		});
		
		Button content_title_btn = (Button)((Activity)context).findViewById(R.id.community_content_title_btn);
		Button write_title_btn = (Button)((Activity)context).findViewById(R.id.community_write_title_btn);
		Button write_complete_btn = (Button)((Activity)context).findViewById(R.id.community_write_complete_btn);
		Button content_comment_btn = (Button)((Activity)context).findViewById(R.id.community_content_comment_btn);
		Button community_comment_title_btn = (Button)((Activity)context).findViewById(R.id.community_comment_title_btn);
		ImageButton search_btn = (ImageButton)((Activity)context).findViewById(R.id.community_search_btn);
		ImageButton write_btn = (ImageButton)((Activity)context).findViewById(R.id.community_write_btn);
		ImageButton sort_btn = (ImageButton)((Activity)context).findViewById(R.id.community_sort_btn);
		ll_title = (LinearLayout)((Activity)context).findViewById(R.id.community_title_layout);
		ll_content = (LinearLayout)((Activity)context).findViewById(R.id.community_content_layout);
		ll_write = (LinearLayout)((Activity)context).findViewById(R.id.community_write_layout);
		ll_comment = (LinearLayout)((Activity)context).findViewById(R.id.community_comment_layout);
		
		final Spinner ws = (Spinner)((Activity)context).findViewById(R.id.write_spinner); 
		final EditText et_title = (EditText)((Activity)context).findViewById(R.id.community_write_title_text);
		final EditText et_content = (EditText)((Activity)context).findViewById(R.id.community_write_text);
	
		et_title.setOnFocusChangeListener(new OnFocusChangeListener() { 
			public void onFocusChange(View v, boolean hasFocus) {
			    if(hasFocus == false) { 
			    	InputMethodManager imm = (InputMethodManager)context.getSystemService(
						      Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(et_title.getWindowToken(), 0);
			    } 
			} 
	    });
		
		et_content.setOnFocusChangeListener(new OnFocusChangeListener() { 
			public void onFocusChange(View v, boolean hasFocus) {
			    if(hasFocus == false) { 
			    	InputMethodManager imm = (InputMethodManager)context.getSystemService(
						      Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(et_content.getWindowToken(), 0);
			    } 
			} 
	    });
		
		content_title_btn.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {			
				ll_title.setVisibility(View.VISIBLE);
				ll_content.setVisibility(View.GONE);
				ll_write.setVisibility(View.GONE);
				ll_comment.setVisibility(View.GONE);
			}
		});
		
		content_comment_btn.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {		
				ll_comment.setVisibility(View.VISIBLE);
				ll_title.setVisibility(View.GONE);
				ll_content.setVisibility(View.GONE);
				ll_write.setVisibility(View.GONE);	
			}
		});
		
		community_comment_title_btn.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {				
				ll_title.setVisibility(View.VISIBLE);
				ll_content.setVisibility(View.GONE);
				ll_write.setVisibility(View.GONE);	
				ll_comment.setVisibility(View.GONE);
			}
		});
		
		write_title_btn.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {			
				EditText et = (EditText)((Activity)context).findViewById(R.id.community_write_title_text);
				InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(et.getWindowToken(),0); 
				
				ll_title.setVisibility(View.VISIBLE);
				ll_content.setVisibility(View.GONE);
				ll_write.setVisibility(View.GONE);
				ll_comment.setVisibility(View.GONE);
			}
		});	
		
		write_complete_btn.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				EditText et = (EditText)((Activity)context).findViewById(R.id.community_write_text);
				InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(et.getWindowToken(),0); 
			
				JSONObject json_param = new JSONObject();
				
				try {	
					String tmp_type = (String)ws.getSelectedItem();
					//json_param.put("type", tmp_type);
					json_param.put("type", tmp_type);
					final EditText title_text = (EditText)((Activity)context).findViewById(R.id.community_write_title_text);
					String title_val = title_text.getText().toString();
					json_param.put("title", title_val);
					final EditText content_text = (EditText)((Activity)context).findViewById(R.id.community_write_text);
					String content_val = content_text.getText().toString();
					json_param.put("content", content_val);
					
					//postData 함수로 데이터 전송
					CommonHttp ch = new CommonHttp();	
					String result_json = ch.postData("http://211.110.61.51:3000/community_write", json_param);		
									
					if(result_json.equals("error")){
						Toast.makeText(context, "글 올리기 실패!", Toast.LENGTH_SHORT).show();
					}
					else{
						Toast.makeText(context, "글 작성 완료!", Toast.LENGTH_SHORT).show();
						CommunityFrag cf = new CommunityFrag();
						cf.updateCommunityList();
					}
				}
				catch(Exception e){
					
				}
				ll_title.setVisibility(View.VISIBLE);
				ll_content.setVisibility(View.GONE);
				ll_write.setVisibility(View.GONE);
				ll_comment.setVisibility(View.GONE);
			}
		});
		
		write_btn.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				ll_write.setVisibility(View.VISIBLE);
				ll_content.setVisibility(View.GONE);
				ll_title.setVisibility(View.GONE);
				ll_comment.setVisibility(View.GONE);
			}
		});
		
		search_btn.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(context, "준비 중입니다..", Toast.LENGTH_SHORT).show();
			}
		});
		
		sort_btn.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(context, "준비 중입니다..", Toast.LENGTH_SHORT).show();
			}
		});
			
		final String[] write_type = context.getResources().getStringArray(R.array.write_type);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( 
				                  context, R.array.write_type, android.R.layout.simple_spinner_item); 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		ws.setAdapter(adapter);
		ws.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv_tmp = (TextView)view;
			}

			public void onNothingSelected(AdapterView<?> parent) {
				System.out.println("Spinner1: unselected");
			}
		});
		
		return convertView;
	}

}
