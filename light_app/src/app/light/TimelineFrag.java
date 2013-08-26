package app.light;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.net.Uri;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class TimelineFrag extends CommonFragment implements OnScrollListener, OnItemClickListener {
	
	// 카메라 관련 상수
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;
	
	private static final int INSERT_COUNT = 5;
	
	// 타임라인 입력 관련 버튼 상수
	private static final int ID_FOOD     = 11;
	private static final int ID_EXERCISE     = 12;
	private static final int ID_CAMERA     = 14;
	private static final int ID_ALBUM     = 15;	
		
	private Uri mImageCaptureUri;
	private FileInputStream mFileInputStream;
	private TimelineDialogWindow popup_dialog;
	
	private Context context;
	private QuickAction writePopup;
	private QuickAction cameraPopup;
	
	private ArrayList<TimeLineObj> my_list;
	private MyTimelineAdapter my_adapter;
	private ListView my_listview;
	private int my_list_count = 0;
	private int pre_list_add = 0;
	private boolean firstStart = true;
	
	private EditText chat_text = null;
	
	private String my_email = null;
	
	private Calendar last_get_date = Calendar.getInstance();
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {	

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.permitDiskReads() 
		.permitDiskWrites()
		.permitNetwork().build());
		
		View view = inflater.inflate(R.layout.frag_timeline, container, false);
		context = getActivity();
			
		//기록 버튼 팝업 관련 코드    
	    ActionItem write_food 	= new ActionItem(ID_FOOD, "음식 기록");
		ActionItem write_exercise 	= new ActionItem(ID_EXERCISE, "운동 기록");
      
		//버튼 눌러도 안사라지게 고정
        //write_food.setSticky(true);
        //write_exercise.setSticky(true);
 
		writePopup = new QuickAction(context, QuickAction.HORIZONTAL);
        
        //add action items into QuickAction
        writePopup.addActionItem(write_food);
        writePopup.addActionItem(write_exercise);

        //Set listener for action item clicked
        writePopup.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
  			@Override
  			public void onItemClick(QuickAction source, int pos, int actionId) {				
  				ActionItem actionItem = writePopup.getActionItem(pos);
                   
  				//here we can filter which action item was clicked with pos or actionId parameter
  				if (actionId == ID_FOOD) {
  					//Toast.makeText(context, "음식 기록", Toast.LENGTH_SHORT).show();
  					popup_dialog = new TimelineDialogWindow(0);
  					popup_dialog.show(getFragmentManager(), "Food Popup");	
  					
  				} else if (actionId == ID_EXERCISE) {
  					//Toast.makeText(context, "운동 기록", Toast.LENGTH_SHORT).show();
  					popup_dialog = new TimelineDialogWindow(1);
  					popup_dialog.show(getFragmentManager(), "Exercise Popup");	
  				}
  			}
  		});		
        
        //set listnener for on dismiss event, this listener will be called only if QuickAction dialog was dismissed
  		//by clicking the area outside the dialog.
        writePopup.setOnDismissListener(new QuickAction.OnDismissListener() {			
  			@Override
  			public void onDismiss() {
  				ImageButton wb = (ImageButton)getActivity().findViewById(R.id.write_btn);
  				wb.setSelected(false);
  			}
  		});
              
        //카메라 버튼 팝업 관련 코드  
	    ActionItem camera_camera 	= new ActionItem(ID_CAMERA, "사진 촬영");
		ActionItem camera_album 	= new ActionItem(ID_EXERCISE, "앨범");
      
		cameraPopup = new QuickAction(context, QuickAction.HORIZONTAL);
        
        //add action items into QuickAction
        cameraPopup.addActionItem(camera_camera);
        cameraPopup.addActionItem(camera_album);

        //Set listener for action item clicked
        cameraPopup.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
  			@Override
  			public void onItemClick(QuickAction source, int pos, int actionId) {				
  				ActionItem actionItem = writePopup.getActionItem(pos);
                   
  				//here we can filter which action item was clicked with pos or actionId parameter
  				if (actionId == ID_CAMERA) {
  					//Toast.makeText(context, "사진 촬영", Toast.LENGTH_SHORT).show();
  					doTakePhotoAction();
  					
  				} else if (actionId == ID_EXERCISE) {
  					//Toast.makeText(context, "앨범", Toast.LENGTH_SHORT).show();
  					doTakeAlbumAction();
  				}
  			}
  		});		
        
        //set listnener for on dismiss event, this listener will be called only if QuickAction dialog was dismissed
  		//by clicking the area outside the dialog.
        cameraPopup.setOnDismissListener(new QuickAction.OnDismissListener() {			
  			@Override
  			public void onDismiss() {
  				ImageButton cb = (ImageButton)getActivity().findViewById(R.id.camera_btn);
  				cb.setSelected(false);
  			}
  		});
		
        
        /*
         * 버튼 클릭 관련 이벤트 처리하는 부분
         * 
         */
        final ImageButton send_btn = (ImageButton) view.findViewById(R.id.send_btn);
		final ImageButton write_btn = (ImageButton) view.findViewById(R.id.write_btn);
		final ImageButton camera_btn = (ImageButton) view.findViewById(R.id.camera_btn);
		final ListView list_view = (ListView) view.findViewById(R.id.timeline_scroll);
		
		chat_text = (EditText) view.findViewById(R.id.chat_val);
		
		//전송 버튼 클릭시
		send_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{	
				String chat_val = chat_text.getText().toString();
				chat_text.setText("");
				if( chat_val.equals("")){
					Toast.makeText(context, "메시지를 입력해주세요!", Toast.LENGTH_SHORT).show();
				}
				else{
					addWord(chat_val);                  
				}						
			}
		});
		
		// 기록하기 버튼 클릭시
		write_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				v.setSelected(true);
				writePopup.show(v);
			}
		});
		
		// 카메라 버튼 클릭시
		camera_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				v.setSelected(true);
				cameraPopup.show(v);	
			}
		});
						
		return view;
	}
	
	@Override
	public void onStart() {
		if(firstStart){	//처음 시작될 때
			firstStart=false;
			//Fragment가 완전히 생성되고 난 후에 호출되는 함수
			super.onStart();
			setListView();	
		}
		else{ //카메라 작동 후 돌아왔을 때
			super.onStart();
		}
	}	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode != -1)	//카메라 정상동작하지 않을때
		{
				return;
		}
	
		System.out.println("result:"+resultCode);

		switch(requestCode)
		{
			case CROP_FROM_CAMERA:
			{
				final Bundle extras = data.getExtras();
				Bitmap resize;
				
				if(extras != null)
				{
					Bitmap tmpPicture = extras.getParcelable("data");
					addPicture(tmpPicture);	//사진 타임라인에 추가
					//사진 크기 재조정
					resize = Bitmap.createScaledBitmap(tmpPicture, 200, 150, true);
					try{
						FileOutputStream fOut = null;
						String path = Environment.getExternalStorageDirectory().toString();
						String fileName = my_email+"_"+String.valueOf(System.currentTimeMillis())+".jpg";
						String filePath = path+"/"+fileName;
						
						fOut = new FileOutputStream(filePath);	//context.openFileOutput(filePath, Context.MODE_PRIVATE);
						resize.compress(CompressFormat.JPEG, 100, fOut);	//jpeg 형태로 이미지 압축
					
						System.out.println(filePath);
						
						fOut.flush();
						fOut.close();
									
						String urlString = "http://211.110.61.51:3000/upload";
						
						//파일 서버로 업로드
						DoFileUpload(urlString, filePath);
										
						//임시 이미지 파일 삭제
						File f = new File(mImageCaptureUri.getPath());	
						if(f.exists())
						{
							f.delete();		
						}
						
					}
					catch(Exception e)
					{
						System.out.println("ERROR");
					}
					// 사진 업로드 구현 -> bitmap jpg로 변환 후 업로드
					// 받는 측에서는 jpg->bitmap으로 변환		
				}
	
				break;
			}
	
			case PICK_FROM_ALBUM:
			{
				mImageCaptureUri = data.getData();
			}
			
			case PICK_FROM_CAMERA:
			{
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(mImageCaptureUri, "image/*");
				intent.putExtra("outputX", 640);
				intent.putExtra("outputY", 480);
				intent.putExtra("aspectX", 4);
				intent.putExtra("aspectY", 3);
				intent.putExtra("scale", true);
				intent.putExtra("return-data", true);
				startActivityForResult(intent, CROP_FROM_CAMERA);
	
				break;
			}
		}
	}
	
	public void setListView() {
		
		my_list = new ArrayList<TimeLineObj>();
		
		Calendar cal = Calendar.getInstance();
	
		String end_date_string, start_date_string;
		
		end_date_string = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		start_date_string = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH) - 5);
		
		// 현재까지 불러온 날짜 last_get_date 변수에 저장
		last_get_date.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH) - 6);
			
		getTimelineData(start_date_string, end_date_string);
		
		if (pre_list_add == 0){
    		end_date_string = String.format("%04d-%02d-%02d", last_get_date.get(Calendar.YEAR), last_get_date.get(Calendar.MONTH), last_get_date.get(Calendar.DAY_OF_MONTH));
    		start_date_string = String.format("%04d-%02d-%02d", last_get_date.get(Calendar.YEAR), last_get_date.get(Calendar.MONTH), last_get_date.get(Calendar.DAY_OF_MONTH) - 5);
    		
    		// 현재까지 불러온 날짜 last_get_date 변수에 저장
    		last_get_date.set(last_get_date.get(Calendar.YEAR), last_get_date.get(Calendar.MONTH), last_get_date.get(Calendar.DAY_OF_MONTH) - 6);
    			
    		getTimelineData(start_date_string, end_date_string);
		}
		
		my_adapter = new MyTimelineAdapter(context, my_list);
		
		// 리스트뷰에 어댑터 연결
	    my_listview = (ListView)((Activity)context).findViewById(R.id.timeline_scroll);
	    
	    my_listview.setAdapter(my_adapter);
	    my_listview.setOnScrollListener(this);
	    my_listview.setOnItemClickListener(this);
	    //my_listview.setSelection(my_adapter.getCount() - 1);
   	}
	
	public void getTimelineData(String db_start_date, String db_end_date)
	{
		JSONObject json_param = new JSONObject();
		
		try{
			json_param.put("start_date", db_start_date);
			json_param.put("end_date", db_end_date);
			
			System.out.println("tmp -> "+db_start_date);
			System.out.println("last -> "+db_end_date);
			
			CommonHttp ch = new CommonHttp();	
			String result_json = ch.postData("http://211.110.61.51:3000/timeline", json_param);		
			
			JSONObject json_data = new JSONObject(result_json);
			JSONArray json_timeline_data = json_data.getJSONArray("timeline_data");
			my_email = json_data.getJSONObject("my_email").getString("my_email");
			
			System.out.println("EMAIL: "+my_email);
			
			for(int i=0; i<json_timeline_data.length(); i++){
				// JSON 데이터 가져와서 리스트에 추가하는 부분
				String tmp_email = json_timeline_data.getJSONObject(i).getString("email");
				String tmp_type_string = json_timeline_data.getJSONObject(i).getString("view_type");
				String tmp_nickname = json_timeline_data.getJSONObject(i).getString("nickname");
				String tmp_pre_content = json_timeline_data.getJSONObject(i).getString("pre_content");
				String tmp_content = json_timeline_data.getJSONObject(i).getString("content");
				String tmp_calorie = json_timeline_data.getJSONObject(i).getString("calorie");
				String tmp_date = json_timeline_data.getJSONObject(i).getString("reg_date");
				
				int tmp_type = Integer.parseInt(tmp_type_string);
				
				// 시간 +9 적용(GMT 때문에)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				Calendar tmp_date_cal = Calendar.getInstance();
	
				tmp_date = tmp_date.replaceAll("T"," ");
				tmp_date = tmp_date.replaceAll("Z", "");			
			
				tmp_date_cal.setTime(sdf.parse(tmp_date));
				tmp_date_cal.add(tmp_date_cal.HOUR, 9);
				
				// 타임라인에 맞게 시간 적용
				String dateStatus;
				int dateNoon = tmp_date_cal.get(Calendar.AM_PM);
				int dateHour = tmp_date_cal.get(Calendar.HOUR_OF_DAY);
				int dateMinute = tmp_date_cal.get(Calendar.MINUTE);
			
				if(dateNoon == 0){
					dateStatus = "오전";
				}
				else{
					dateStatus = "오후";
					if(dateHour != 12){
						dateHour = dateHour-12;
					}
				}
			
				String timeString = dateStatus+" "+dateHour+":"+String.format("%02d",dateMinute);
				String dateString = String.format("%04d. %02d. %02d", tmp_date_cal.get(Calendar.YEAR), tmp_date_cal.get(Calendar.MONTH) + 1, tmp_date_cal.get(Calendar.DAY_OF_MONTH));
				
				String tmp_list_date;
	
				if(tmp_type==0)	// 타임바일 경우 날짜로 적용
					tmp_list_date = dateString;
				else
					tmp_list_date = timeString;
					
				//리스트에 값 추가
				//내 데이터이거나 관리자 데이터일 때
				if(tmp_type==6){ // 사진 데이터일 경우 사진 데이터 서버에서 가져옴
					Bitmap img_bm = getTimelineImg(tmp_content);
					if(tmp_email.equals(my_email))
						my_list.add(0, new TimeLineObj(tmp_type, tmp_nickname, img_bm, tmp_list_date));	
					else
						my_list.add(0, new TimeLineObj(tmp_type+4, tmp_nickname, img_bm, tmp_list_date));				
				}
				else if(tmp_email.equals(my_email) || tmp_nickname.equals("LIGHT"))
					my_list.add(0, new TimeLineObj(tmp_type, tmp_nickname, tmp_pre_content, tmp_content, tmp_calorie, tmp_list_date));						
				else	//다른 사람 데이터일 때
					my_list.add(0, new TimeLineObj(tmp_type+4, tmp_nickname, tmp_pre_content, tmp_content, tmp_calorie, tmp_list_date));		
			}
			pre_list_add = json_timeline_data.length();	//직전에 추가된 리스트 개수
			my_list_count += json_timeline_data.length();	//개수만큼 불러와서 추가 		
		}
		catch(Exception e){
			System.out.println("에러 발생");
		}	
	}
	
	public void onItemClick(AdapterView<?> parent, View v, int position, long id)
	{
		// 리스트뷰 클릭시 키보드 숨기기
		InputMethodManager imm = (InputMethodManager)context.getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(chat_text.getWindowToken(), 0);
	}
	
	
	// 카메라 촬영시
	private void doTakePhotoAction()
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
		mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
		
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PICK_FROM_CAMERA);
	}
	
	//앨범 선택시
	private void doTakeAlbumAction()
	{
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(intent, PICK_FROM_ALBUM);
	}
	
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
	}

	//스크롤 이동 이벤트 발생시
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		// 리스트뷰가 구성이 완료되어 보이는 경우
        if(view.isShown()){
            if(scrollState == SCROLL_STATE_IDLE) {
                // 리스트뷰의 0 번 인덱스 항목이 리스트뷰의 상단에 보이고 있는 경우	
                if(view.getFirstVisiblePosition() == 0) {
                    // 항목을 추가한다.
         
                	//Calendar cal = Calendar.getInstance();
                	
            		String end_date_string, start_date_string;
            		
            		end_date_string = String.format("%04d-%02d-%02d", last_get_date.get(Calendar.YEAR), last_get_date.get(Calendar.MONTH), last_get_date.get(Calendar.DAY_OF_MONTH));
            		start_date_string = String.format("%04d-%02d-%02d", last_get_date.get(Calendar.YEAR), last_get_date.get(Calendar.MONTH), last_get_date.get(Calendar.DAY_OF_MONTH) - 5);
            		
            		// 현재까지 불러온 날짜 last_get_date 변수에 저장
            		last_get_date.set(last_get_date.get(Calendar.YEAR), last_get_date.get(Calendar.MONTH), last_get_date.get(Calendar.DAY_OF_MONTH) - 6);
            			
            		getTimelineData(start_date_string, end_date_string);
                	
            		System.out.println("마지막 날짜: "+last_get_date);
                	
    				System.out.println("더해진 개수: "+pre_list_add);
    				my_adapter.notifyDataSetChanged();
  
                    view.setSelection(pre_list_add);	//뷰 위치 이동
                }
            }
        }
	}
	
	//내가 쓴 채팅 내용 추가
	public void addWord(String chat_val)
	{		
		JSONObject json_param = new JSONObject();
		try {
			json_param.put("type", 3);	
			json_param.put("pre_content", "");
			json_param.put("content", chat_val);
			json_param.put("calorie", "");	
			System.out.println("JSON 입력");
		}
		catch(Exception e){
			System.out.println("JSON 에러");
		}
		
		boolean data_status = sendTimelineData(json_param);
		
		if(data_status){
		
			Calendar cal = Calendar.getInstance();
	
			String dateStatus;
			int dateNoon = cal.get(Calendar.AM_PM);
			int dateHour = cal.get(Calendar.HOUR_OF_DAY);
			int dateMinute = cal.get(Calendar.MINUTE);
		
			if(dateNoon == 0){
				dateStatus = "오전";
			}
			else{
				dateStatus = "오후";
				if(dateHour != 12){
					dateHour = dateHour-12;
				}
			}
			
			String timeString = dateStatus+" "+dateHour+":"+String.format("%02d",dateMinute);
			
			my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_MY_WORD, "", "", chat_val, "", timeString));	
			my_list_count += 1;     
			
			my_adapter.notifyDataSetChanged();		
			my_listview.setSelection(my_list.size());
		}	
	}
	
	//내가 찍은 사진 업로드
	public void addPicture(Bitmap tmpPicture)
	{	
		System.out.println("addPicture - "+tmpPicture);
		
		Calendar cal = Calendar.getInstance();

		String dateToString , timeToString ;
		String dateStatus;
		int dateNoon = cal.get(Calendar.AM_PM);
		int dateHour = cal.get(Calendar.HOUR_OF_DAY);
		int dateMinute = cal.get(Calendar.MINUTE);
	
		if(dateNoon == 0){
			dateStatus = "오전";
		}
		else{
			dateStatus = "오후";
			if(dateHour != 12){
				dateHour = dateHour-12;
			}
		}
		//dateToString = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		//timeToString = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		
		String timeString = dateStatus+" "+dateHour+":"+String.format("%02d",dateMinute);
	
		//my_list.add(new TimeLineObj(TimeLineObj.VIEW_TYPE_UPLOAD_MY_PICTURE,tmpPicture,timeString));
		//my_list_count += 1;     
		
		//my_adapter.notifyDataSetChanged();
		//my_listview.setSelection(my_list.size());
	}
	
	public void DoFileUpload(String apiUrl, String absolutePath) {
		HttpFileUpload(apiUrl, "", absolutePath);
	}

	public void HttpFileUpload(String urlString, String params, String fileName) {
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		
		try {

			mFileInputStream = new FileInputStream(fileName);
			URL connectUrl = new URL(urlString);
			Log.d("Test", "mFileInputStream  is " + mFileInputStream);

			// open connection
			HttpURLConnection conn = (HttpURLConnection) connectUrl
					.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			// write data
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
					+ fileName + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			int bytesAvailable = mFileInputStream.available();
			int maxBufferSize = 1024;
			int bufferSize = Math.min(bytesAvailable, maxBufferSize);

			byte[] buffer = new byte[bufferSize];
			int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

			Log.d("Test", "image byte is " + bytesRead);

			// read image
			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = mFileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
			}

			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// close streams
			Log.e("Test", "File is written");
			mFileInputStream.close();
			dos.flush(); // finish upload...

			// get response
			int ch;
			InputStream is = conn.getInputStream();
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			String s = b.toString();
			Log.e("Test", "result = " + s);
		//	mEdityEntry.setText(s);
			dos.close();

		} catch (Exception e) {
			Log.d("Test", "exception " + e.getMessage());
			// TODO: handle exception
		}
	}
	
	/*
	 * 타임라인 DB에 전송할 때 타입 값
	 * 3 - CHAT
	 * 4 - FOOD
	 * 5 - EXERCISE
	 * 6 - PICTURE
	 */
	public boolean sendTimelineData(JSONObject json_param) {	
		try {
			CommonHttp ch = new CommonHttp();	
			String result_json = ch.postData("http://211.110.61.51:3000/send_push", json_param);		
			if(result_json.equals("error")) {
				System.out.println("데이터 전송 실패!");
				Toast.makeText(context, "데이터 전송 실패!", Toast.LENGTH_SHORT).show();
				return false;
			}	
			return true;
		}
		catch(Exception e){
			System.out.println("데이터 전송 실패!");
			return false;
		}	
	}
	
	/*
	 * 타임라인 DB에 전송할 때 타입 값
	 * 3 - CHAT
	 * 4 - FOOD
	 * 5 - EXERCISE
	 * 6 - PICTURE
	 */
	public Bitmap getTimelineImg(String img_str) {	
		
		CommonHttp ch = new CommonHttp();	
		Bitmap img_bm = ch.getImg("http://211.110.61.51:3000/img?img_str="+img_str);		
	
		return img_bm;
	}

}

