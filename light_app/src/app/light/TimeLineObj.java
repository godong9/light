package app.light;

import android.widget.ImageView;

public class TimeLineObj {
	//전체 data type 
	public int type;
	public String nickname;
	public String food_type;
	public int exercise_time;
	public String content;
	public int food_calorie;
	public int exercise_calorie;
	public int day_count;
	public String time;
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
	public TimeLineObj(int type, int day_count, String time) {
		this.type = type;
		this.day_count = day_count;
		this.time = time;	
	}
	
	//MY_WORD 생성자
	public TimeLineObj(int type, String content, String time) {
		this.type = type;
		this.content = content;
		this.time = time;	
	}
	
	//MY_FOOD 생성자
	public TimeLineObj(int type, String food_type, String content, int food_calorie, String time) {
		this.type = type;
		this.content = content;
		this.time = time;	
		this.food_type = food_type;
		this.food_calorie = food_calorie;
	}
	
	//MY_EXERCISE 생성자
	public TimeLineObj(int type, int exercise_time, String content, int exercise_calorie, String time) {
		this.type = type;
		this.content = content;
		this.time = time;
		this.exercise_time = exercise_time;
		this.exercise_calorie = exercise_calorie;
	}
	
	//MY_PICTURE 생성자
	public TimeLineObj(int type, ImageView img, String time) {
		this.type = type;
		this.img = img;
		this.time = time;
	}

	//OTHER_WORD 생성자
	public TimeLineObj(int type, String nickname, String content, String time) {
		this.type = type;
		this.nickname = nickname;
		this.content = content;
		this.time = time;	
	}
	
	//OTHER_FOOD 생성자
	public TimeLineObj(int type, String nickname, String food_type, String content, int food_calorie, String time) {
		this.type = type;
		this.content = content;
		this.time = time;	
		this.food_type = food_type;
		this.food_calorie = food_calorie;
	}
	
	//OTHER_EXERCISE 생성자
	public TimeLineObj(int type, String nickname, int exercise_time, String content, int exercise_calorie, String time) {
		this.type = type;
		this.content = content;
		this.time = time;
		this.exercise_time = exercise_time;
		this.exercise_calorie = exercise_calorie;
	}
	
	//OTHER_PICTURE 생성자
	public TimeLineObj(int type, String nickname, ImageView img, String time) {
		this.type = type;
		this.img = img;
		this.time = time;
	}
	
	//MANAGER_WORD 생성자
	public TimeLineObj(int type, ImageView manager_img, String content, String time) {
		this.type = type;
		this.manager_img = manager_img;
		this.content = content;
		this.time = time;	
	}
	
	//MANAGER_MISSION 생성자
	public TimeLineObj(int type, ImageView manager_img, String mission_type, String content, String time) {
		this.type = type;
		this.manager_img = manager_img;
		this.content = content;
		this.time = time;	
	}

}
