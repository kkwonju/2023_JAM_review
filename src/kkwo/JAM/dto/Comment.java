package kkwo.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Comment extends Dto {
	public int memberId;
	public int articleId;
	public String body;
	public String extra__writer;
	
	public Comment(Map<String, Object> commentMap) {
		this.id = (int) commentMap.get("id");
		this.memberId = (int) commentMap.get("memberId");
		this.articleId = (int) commentMap.get("articleId");
		this.body = (String) commentMap.get("body");
		this.regDate = (LocalDateTime) commentMap.get("regDate");
		this.updateDate = (LocalDateTime) commentMap.get("updateDate");
		this.extra__writer = (String) commentMap.get("extra__writer"); 
	}
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", memberId=" + memberId + ", articleId=" + articleId + ", body=" + body
				+ ", regDate=" + regDate + ", updateDate=" + updateDate + ", extra__writer=" + extra__writer + "]";
	}
}