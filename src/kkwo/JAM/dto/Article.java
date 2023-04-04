package kkwo.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Article extends Dto{
	public int hit;
	public int memberId;
	public String title;
	public String body;
	
	public String extra__writer;

	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.hit = (int) articleMap.get("hit");
		this.memberId = (int) articleMap.get("memberId");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.updateDate = (LocalDateTime) articleMap.get("updateDate");
		
		if(articleMap.get("extra__writer") != null) {
			this.extra__writer = (String)articleMap.get("extra__writer");
		}
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", hit=" + hit
				+ ", memberId=" + memberId + ", title=" + title + ", body=" + body + "]";
	}
}
