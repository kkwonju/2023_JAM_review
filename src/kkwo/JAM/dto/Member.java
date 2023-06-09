package kkwo.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Member extends Dto{
	public String loginId;
	public String loginPw;
	public String name;

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
