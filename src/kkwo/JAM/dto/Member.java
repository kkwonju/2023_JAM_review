package kkwo.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Member extends Dto{
	public int id;
	public String loginId;
	public String loginPw;
	public String name;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;

	public Member(int id, String loginId, String loginPw, String name,LocalDateTime regDate, LocalDateTime updateDate) {
		this.id = id;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
		this.regDate = regDate;
		this.updateDate = updateDate;
	}
	
	public Member(Map<String, Object> memberMap) {
		this.id = (int) memberMap.get("id");
		this.loginId = (String) memberMap.get("loginId");
		this.loginPw = (String) memberMap.get("loginPw");
		this.name = (String) memberMap.get("name");
		this.regDate = (LocalDateTime) memberMap.get("regDate");
		this.updateDate = (LocalDateTime) memberMap.get("updateDate");
	}
	
	@Override
	public String toString() {
		return "Member [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", loginId=" + loginId
				+ ", loginPw=" + loginPw + ", name=" + name + "]";
	}
}
