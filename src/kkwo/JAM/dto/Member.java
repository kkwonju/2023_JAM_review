package kkwo.JAM.dto;

import java.time.LocalDateTime;

public class Member extends Dto{
	public String loginId;
	public String loginPW;
	public String name;

	public Member(int id, String loginId, String loginPW, String name,String regDate, String updateDate) {
		this.id = id;
		this.loginId = loginId;
		this.loginPW = loginPW;
		this.name = name;
		this.regDate = regDate;
		this.updateDate = updateDate;
	}
}
