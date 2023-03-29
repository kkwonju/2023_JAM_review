package kkwo.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Article extends Dto{
	public int hit;
	public int memberId;
	public String title;
	public String body;

	public Article(int id, int memberId, String title, String body, LocalDateTime regDate, LocalDateTime updateDate) {
		this(id, 0, memberId, title, body, regDate, updateDate);
	}
	
	public Article(int id, int hit, int memberId, String title, String body, LocalDateTime regDate, LocalDateTime updateDate) {
		this.id = id;
		this.hit = hit;
		this.memberId = memberId;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
		this.updateDate = updateDate;
	}

	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.memberId = (int) articleMap.get("memberId");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.updateDate = (LocalDateTime) articleMap.get("updateDate");
	}

	@Override
	public String toString() {
		return "Article [hit=" + hit + ", memberId=" + memberId + ", title=" + title + ", body=" + body + "]";
	}
}
