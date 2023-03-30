package kkwo.JAM.service;

import kkwo.JAM.container.Container;
import kkwo.JAM.dao.MemberDao;
import kkwo.JAM.dto.Member;

public class MemberService {
	private MemberDao memberDao;
	
	public MemberService() {
		memberDao = Container.memberDao;
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public boolean isExistingLoginId(String loginId) {
		return memberDao.isExistingLoginId(loginId);
	}

	public int doJoin(String loginId, String loginPw, String name) {
		return memberDao.doJoin(loginId, loginPw, name);
	}
}
