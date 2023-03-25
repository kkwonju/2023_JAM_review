package kkwo.JAM.dao;

import java.util.ArrayList;
import java.util.List;

import kkwo.JAM.dto.Member;

public class MemberDao {
	public List<Member> members;
	
	public MemberDao() {
		members = new ArrayList<>();
	}

	public void add(Member member) {
		members.add(member);
	}
}
