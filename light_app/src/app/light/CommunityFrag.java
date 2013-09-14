package app.light;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class CommunityFrag extends CommonFragment implements OnItemClickListener {
	
	private static Context context;
	private static ArrayList<CommunityObj> community_list;
	private static MyCommunityAdapter community_adapter;
	private static ListView community_listview;
	private static int community_list_count = 0;
	private static int notice_count = 0;
	private EditText search_text = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_community, container, false);
		context = getActivity();
		
		search_text = (EditText) view.findViewById(R.id.community_search_val);
	
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		setCommunityList();
	}		
	
	public void onItemClick(AdapterView<?> parent, View v, int position, long id)
	{
		// 리스트뷰 클릭시 키보드 숨기기
		InputMethodManager imm = (InputMethodManager)context.getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(search_text.getWindowToken(), 0);
	}
	
	public static void updateCommunityList(){
		community_list = new ArrayList<CommunityObj>();
		community_list_count = 0;
		notice_count = 0;
		try{		
			CommonHttp ch = new CommonHttp();	
			String result_json = ch.getData("http://211.110.61.51:3000/community");		
			
			JSONObject json_data = new JSONObject(result_json);
			JSONArray json_community_data = json_data.getJSONArray("community_data");
			
			for(int i=0; i<json_community_data.length(); i++){
				// JSON 데이터 가져와서 리스트에 추가하는 부분		
				String tmp_type = json_community_data.getJSONObject(i).getString("type");
				String tmp_post_idx = json_community_data.getJSONObject(i).getString("post_idx");
				String tmp_nickname = json_community_data.getJSONObject(i).getString("nickname");
				String tmp_title = json_community_data.getJSONObject(i).getString("title");
				String tmp_content = json_community_data.getJSONObject(i).getString("content");
				String tmp_hits = json_community_data.getJSONObject(i).getString("hits");
				String tmp_num_comment = json_community_data.getJSONObject(i).getString("num_comment");
				String tmp_date = json_community_data.getJSONObject(i).getString("reg_date");
					
				// 시간 +9 적용(GMT 때문에)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				Calendar tmp_date_cal = Calendar.getInstance();
	
				tmp_date = tmp_date.replaceAll("T"," ");
				tmp_date = tmp_date.replaceAll("Z", "");			
			
				tmp_date_cal.setTime(sdf.parse(tmp_date));
				tmp_date_cal.add(tmp_date_cal.HOUR, 9);		
			
				String dateString = String.format("%04d.%02d.%02d", tmp_date_cal.get(Calendar.YEAR), tmp_date_cal.get(Calendar.MONTH) + 1, tmp_date_cal.get(Calendar.DAY_OF_MONTH));
				
				//리스트에 값 추가
				if(tmp_type.equals("공지")){
					community_list.add(0, new CommunityObj(tmp_type, tmp_post_idx, tmp_title, tmp_content, tmp_nickname, dateString, tmp_hits, tmp_num_comment));		
					notice_count++;
				}
				else{
					community_list.add(new CommunityObj(tmp_type, tmp_post_idx, tmp_title, tmp_content, tmp_nickname, dateString, tmp_hits, tmp_num_comment));		
				}
		
			}
			community_list_count += json_community_data.length();	//개수만큼 불러와서 추가 
			community_adapter = new MyCommunityAdapter(context, community_list);
			community_listview = (ListView)((Activity)context).findViewById(R.id.community_scroll);	  
			community_listview.setAdapter(community_adapter);
		}
		catch(Exception e){
			System.out.println("에러 발생");
		}	
	}
	
	public void setCommunityList(){
		community_list = new ArrayList<CommunityObj>();
		community_list_count = 0;
		notice_count = 0;
		
		try{		
			CommonHttp ch = new CommonHttp();	
			String result_json = ch.getData("http://211.110.61.51:3000/community");		
			
			JSONObject json_data = new JSONObject(result_json);
			JSONArray json_community_data = json_data.getJSONArray("community_data");
			
			for(int i=0; i<json_community_data.length(); i++){
				// JSON 데이터 가져와서 리스트에 추가하는 부분		
				String tmp_type = json_community_data.getJSONObject(i).getString("type");
				String tmp_post_idx = json_community_data.getJSONObject(i).getString("post_idx");
				String tmp_nickname = json_community_data.getJSONObject(i).getString("nickname");
				String tmp_title = json_community_data.getJSONObject(i).getString("title");
				String tmp_content = json_community_data.getJSONObject(i).getString("content");
				String tmp_hits = json_community_data.getJSONObject(i).getString("hits");
				String tmp_num_comment = json_community_data.getJSONObject(i).getString("num_comment");
				String tmp_date = json_community_data.getJSONObject(i).getString("reg_date");
					
				// 시간 +9 적용(GMT 때문에)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				Calendar tmp_date_cal = Calendar.getInstance();
	
				tmp_date = tmp_date.replaceAll("T"," ");
				tmp_date = tmp_date.replaceAll("Z", "");			
			
				tmp_date_cal.setTime(sdf.parse(tmp_date));
				tmp_date_cal.add(tmp_date_cal.HOUR, 9);		
			
				String dateString = String.format("%04d.%02d.%02d", tmp_date_cal.get(Calendar.YEAR), tmp_date_cal.get(Calendar.MONTH) + 1, tmp_date_cal.get(Calendar.DAY_OF_MONTH));
				
				//리스트에 값 추가
				if(tmp_type.equals("공지")){
					community_list.add(0, new CommunityObj(tmp_type, tmp_post_idx, tmp_title, tmp_content, tmp_nickname, dateString, tmp_hits, tmp_num_comment));		
					notice_count++;
				}
				else{
					community_list.add(new CommunityObj(tmp_type, tmp_post_idx, tmp_title, tmp_content, tmp_nickname, dateString, tmp_hits, tmp_num_comment));		
				}
		
			}

			community_list_count += json_community_data.length();	//개수만큼 불러와서 추가 		
			community_adapter = new MyCommunityAdapter(context, community_list);
			community_listview = (ListView)((Activity)context).findViewById(R.id.community_scroll);	  
		    community_listview.setOnItemClickListener(this);
			community_listview.setAdapter(community_adapter);
		}
		catch(Exception e){
			System.out.println("에러 발생");
		}	
	 	
	}
}