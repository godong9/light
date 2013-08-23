package app.light;

public class CommunityObj {
	public String type;
	public String title_content;	
	public String nickname;
	public String reg_date;
	public String hits;
	public String comment;

	CommunityObj(String type, String title_content, String nickname, String reg_date, String hits, String comment) {
		this.type = type;
		this.title_content = title_content;	
		this.nickname = nickname;
		this.reg_date = reg_date;
		this.hits = hits;
		this.comment = comment;
	}
}
