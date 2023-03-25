package kkwo.JAM.service;

import java.util.List;

import kkwo.JAM.container.Container;
import kkwo.JAM.dao.MemberDao;
import kkwo.JAM.dto.Member;

public class MemberService {
	private MemberDao memberDao;
	
	public MemberService() {
		memberDao = Container.memberDao;
	}

	public void add(Member member) {
		memberDao.add(member);
	}

	public List<Member> getMembers() {
		return memberDao.getMembers();
	}

	public int setNewId() {
		return memberDao.setNewId();
	}

	public boolean isJoinableLoginId(String loginId) {
		return memberDao.isJoinableLoginId(loginId);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}
}
