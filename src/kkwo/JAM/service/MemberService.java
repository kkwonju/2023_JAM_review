package kkwo.JAM.service;

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
}
