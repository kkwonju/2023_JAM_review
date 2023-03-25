package kkwo.JAM.dao;

import java.util.ArrayList;
import java.util.List;

import kkwo.JAM.dto.Member;

public class MemberDao extends Dao{
	public List<Member> members;
	
	public MemberDao() {
		members = new ArrayList<>();
	}

	public void add(Member member) {
		members.add(member);
		lastId++;
	}

	public List<Member> getMembers() {
		return members;
	}

	public int setNewId() {
		int id = lastId + 1;
		return id;
	}
	
	public boolean isJoinableLoginId(String loginId) {
		for (Member member : members) {
			/* 이미 아이디가 존재한다면 false 반환 */
			if (member.loginId.equals(loginId)) {
				return false;
			}
		}
		return true;
	}
	
	public Member getMemberByLoginId(String loginId) {
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return member;
			}
		}
		return null;
	}
}
