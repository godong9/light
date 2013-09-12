package app.light;

public class CommunityObj {
	public String post_idx;
	public String type;
	public String title_content;	
	public String content;	
	public String nickname;
	public String reg_date;
	public String hits;
	public String comment;

	CommunityObj(String type, String post_idx, String title_content, String content, String nickname, String reg_date, String hits, String comment) {
		this.type = type;
		this.post_idx = post_idx;
		this.title_content = title_content;	
		this.content = content;	
		this.nickname = nickname;
		this.reg_date = reg_date;
		this.hits = hits;
		this.comment = comment;
	}
}
