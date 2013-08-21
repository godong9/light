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
	public String date;
	//public Bitmap picture_img;
	
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
	
	
	public TimeLineObj(int type, String nickname, String pre_content, String content, String calorie, String date) {
		if(type == VIEW_TYPE_TIMEBAR){
			this.type = type;
			this.day_count = content;
			this.date = date;	
		}
		else if(type == VIEW_TYPE_MANAGER_WORD){
			this.type = type;
			this.content = content;
			this.date = date;	
		}
		else if(type == VIEW_TYPE_MANAGER_MISSION){
			this.type = type;
			this.pre_content = pre_content;
			this.content = content;
			this.date = date;	
		}
		else if(type == VIEW_TYPE_MY_WORD || type == VIEW_TYPE_MY_PICTURE){
			this.type = type;
			this.content = content;
			this.date = date;		
		}
		else if(type == VIEW_TYPE_MY_FOOD || type == VIEW_TYPE_MY_EXERCISE){
			this.type = type;
			this.pre_content = pre_content;
			this.content = content;
			this.calorie = calorie;
			this.date = date;	
		}
	
		else if(type == VIEW_TYPE_OTHER_WORD || type == VIEW_TYPE_OTHER_PICTURE){
			this.type = type;
			this.nickname = nickname;
			this.content = content;
			this.date = date;	
		}
		else if(type == VIEW_TYPE_OTHER_FOOD || type == VIEW_TYPE_OTHER_EXERCISE){
			this.type = type;			
			this.nickname = nickname;
			this.pre_content = pre_content;
			this.content = content;
			this.calorie = calorie;
			this.date = date;	
		}
	}
	
	
	/*
	
	//MY_PICTURE 생성자
	public TimeLineObj(int type, Bitmap img, String date) {
		this.type = type;
		this.picture_img = img;
		System.out.println("TimelineObj - "+picture_img);
		this.date = date;
	}

	*/

}
