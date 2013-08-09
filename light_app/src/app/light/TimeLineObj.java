package app.light;

public class TimeLineObj {
	//data type 총추가
	String ID;
	String content;
	String time;
	
	int type;
	
	public static final int VIEW_TYPE_MY_MSG = 0;
	public static final int VIEW_TYPE_OTHER_MSG = 1;
//	public static final int VIEW_TYPE_OTHER_MSG = 1;
	
	public TimeLineObj(String ID, String content, String time, int type) {
		
		this.ID = ID;
		this.content = content;
		this.time = time;
		this.type = type;
	}

}
