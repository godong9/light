package app.light;

public class CommunityObj {
	public String type;
	public String title;
	public String comment;
	public String nickname;
	public String reg_date;
	public String hits;
	
	CommunityObj(String type, String title, String comment, String nickname, String reg_date, String hits) {
		this.type = type;
		this.title = title;
		this.comment = comment;
		this.nickname = nickname;
		this.reg_date = reg_date;
		this.hits = hits;
	}
}
