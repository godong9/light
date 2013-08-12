package app.light;

import android.widget.ImageView;

public class TimeLineObj {
	//전체 data type 
	public int type;
	public String write_type;
	public String nickname;
	public String pre_content;
	public String content;
	public String calorie;
	public String day_count;
	public int bar_status;
	public String date;
	public ImageView manager_img;
	public ImageView img;
	public String mission_type;
	
	public static final int VIEW_TYPE_TIMEBAR = 0;
	public static final int VIEW_TYPE_MY_WORD = 1;
	public static final int VIEW_TYPE_MY_FOOD = 2;
	public static final int VIEW_TYPE_MY_EXERCISE = 3;
	public static final int VIEW_TYPE_MY_PICTURE = 4;		
	public static final int VIEW_TYPE_OTHER_WORD = 5;
	public static final int VIEW_TYPE_OTHER_FOOD = 6;
	public static final int VIEW_TYPE_OTHER_EXERCISE =7;
	public static final int VIEW_TYPE_OTHER_PICTURE = 8;	
	public static final int VIEW_TYPE_MANAGER_WORD = 9;
	public static final int VIEW_TYPE_MANAGER_MISSION = 10;
	
	//TIMEBAR 생성자
	public TimeLineObj(int type, int bar_status, String day_count, String date) {
		this.type = type;
		this.day_count = day_count;
		this.date = date;	
	}
	
	//MY_WORD 생성자
	public TimeLineObj(int type, String content, String date) {
		this.type = type;
		this.content = content;
		this.date = date;	
	}
	
	//MY_FOOD, MY_EXERCISE 생성자 (type으로 구분)
	public TimeLineObj(int type, String pre_content, String content, String calorie, String date) {
		this.type = type;
		if(type == VIEW_TYPE_MY_FOOD){
			write_type="식단";
		}
		else if(type == VIEW_TYPE_MY_EXERCISE){
			write_type="운동";
		}
		this.pre_content = pre_content;
		this.content = content;
		this.calorie = calorie;
		this.date = date;	
	}
	
	//MY_PICTURE 생성자
	public TimeLineObj(int type, ImageView img, String date) {
		this.type = type;
		this.img = img;
		this.date = date;
	}

	//OTHER_WORD 생성자
	public TimeLineObj(int type, String nickname, String content, String date) {
		this.type = type;
		this.nickname = nickname;
		this.content = content;
		this.date = date;	
	}
	
	//OTHER_FOOD, OTHER_EXERCISE 생성자 (type으로 구분)
	public TimeLineObj(int type, String nickname, String pre_content, String content, String calorie, String date) {
		if(type == VIEW_TYPE_OTHER_FOOD){
			write_type="식단";
		}
		else if(type == VIEW_TYPE_OTHER_EXERCISE){
			write_type="운동";
		}
		this.type = type;
		this.pre_content = pre_content;
		this.content = content;
		this.calorie = calorie;
		this.date = date;	
	}
		
	//OTHER_PICTURE 생성자
	public TimeLineObj(int type, String nickname, ImageView img, String date) {
		this.type = type;
		this.img = img;
		this.date = date;
	}
	
	//MANAGER_WORD 생성자
	public TimeLineObj(int type, ImageView manager_img, String content, String date) {
		this.type = type;
		this.manager_img = manager_img;
		this.content = content;
		this.date = date;	
	}
	
	//MANAGER_MISSION 생성자
	public TimeLineObj(int type, ImageView manager_img, String mission_type, String content, String date) {
		this.type = type;
		this.manager_img = manager_img;
		this.content = content;
		this.date = date;	
	}

}
