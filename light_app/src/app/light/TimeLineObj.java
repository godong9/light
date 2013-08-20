package app.light;

import android.widget.ImageView;
import android.graphics.Bitmap;

public class TimeLineObj {
	//전체 data type 
	public int type;
	public String nickname;
	public String pre_content;
	public String content;
	public String calorie;
	public String day_count;
	public int bar_status;
	public String date;
	public ImageView manager_img;
	public Bitmap picture_img;
	public int manager_type;
	public int manager_num;
	
	public static final int VIEW_TYPE_TIMEBAR = 0;
	public static final int VIEW_TYPE_MANAGER_WORD = 1;
	public static final int VIEW_TYPE_MANAGER_MISSION = 2;
	public static final int VIEW_TYPE_MY_WORD = 3;
	public static final int VIEW_TYPE_MY_FOOD = 4;
	public static final int VIEW_TYPE_MY_EXERCISE = 5;
	public static final int VIEW_TYPE_MY_PICTURE = 6;		
	public static final int VIEW_TYPE_OTHER_WORD = 7;
	public static final int VIEW_TYPE_OTHER_FOOD = 8;
	public static final int VIEW_TYPE_OTHER_EXERCISE =9;
	public static final int VIEW_TYPE_OTHER_PICTURE = 10;	
	
	
	//TIMEBAR 생성자
	public TimeLineObj(int type, int bar_status, String day_count, String date) {
		this.type = type;
		this.bar_status = bar_status;
		this.day_count = day_count;
		this.date = date;	
	}
	
	//MY_WORD, MY_PICTURE 생성자 (type으로 구분)
	public TimeLineObj(int type, String content, String date) {
		this.type = type;
		this.content = content;
		this.date = date;	
	}
	
	//MY_FOOD, MY_EXERCISE 생성자 (type으로 구분)
	public TimeLineObj(int type, String pre_content, String content, String calorie, String date) {
		this.type = type;
		this.pre_content = pre_content;
		this.content = content;
		this.calorie = calorie;
		this.date = date;	
	}
	
	//MY_PICTURE 생성자
	public TimeLineObj(int type, Bitmap img, String date) {
		this.type = type;
		this.picture_img = img;
		System.out.println("TimelineObj - "+picture_img);
		this.date = date;
	}

	//OTHER_WORD, OTHER_PICTURE 생성자
	public TimeLineObj(int type, String nickname, String content, String date) {
		this.type = type;
		this.nickname = nickname;
		this.content = content;
		this.date = date;	
	}
	
	//OTHER_FOOD, OTHER_EXERCISE 생성자 (type으로 구분)
	public TimeLineObj(int type, String nickname, String pre_content, String content, String calorie, String date) {
		this.type = type;			
		this.nickname = nickname;
		this.pre_content = pre_content;
		this.content = content;
		this.calorie = calorie;
		this.date = date;	
	}
	
	//MANAGER_WORD 생성자
	public TimeLineObj(int type, int manager_type, int manager_num, String content, String date) {
		this.type = type;
		this.manager_type = manager_type;
		this.manager_num = manager_num;
		this.content = content;
		this.date = date;	
	}
	
	//MANAGER_MISSION 생성자
	public TimeLineObj(int type, int manager_type, int manager_num, String pre_content, String content, String date) {
		this.type = type;
		this.manager_type = manager_type;
		this.manager_num = manager_num;
		this.pre_content = pre_content;
		this.content = content;
		this.date = date;	
	}

}
