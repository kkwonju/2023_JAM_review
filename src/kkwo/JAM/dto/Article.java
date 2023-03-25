package kkwo.JAM.dto;

import java.time.LocalDateTime;

public class Article extends Dto{
	public int hit;
	public int memberId;
	public String title;
	public String body;

	public Article(int id, int memberId, String title, String body, String regDate, String updateDate) {
		this(id, 0, memberId, title, body, regDate, updateDate);
	}
	
	public Article(int id, int hit, int memberId, String title, String body, String regDate, String updateDate) {
		this.id = id;
		this.hit = hit;
		this.memberId = memberId;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
		this.updateDate = updateDate;
	}
}
