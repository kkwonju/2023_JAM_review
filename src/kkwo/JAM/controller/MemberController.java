package kkwo.JAM.controller;

import java.util.Map;

import kkwo.JAM.container.Container;
import kkwo.JAM.dto.Member;
import kkwo.JAM.service.MemberService;
import kkwo.JAM.util.DBUtil;
import kkwo.JAM.util.SecSql;

public class MemberController extends Controller {
	private String actionMethodName;
	private String command;
	private MemberService memberService;

	public MemberController() {
		memberService = Container.memberService;
	}

	@Override
	public void action(String actionMethodName, String command) {
		this.actionMethodName = actionMethodName;
		this.command = command;

		switch (actionMethodName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		default:
			System.out.println("존재하지 않는 기능입니다");
			break;
		}
	}

	private void doJoin() {
		String loginId = null;
		String loginPw = null;
		String loginPwConfrim = null;
		String name = null;

		while (true) {
			System.out.print("아이디 : ");
			loginId = Container.sc.nextLine().trim();

			if (loginId.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			SecSql sql = new SecSql();

			sql.append("SELECT COUNT(*)");
			sql.append("FROM `member`");
			sql.append("WHERE loginId = ?", loginId);

			int countMember = DBUtil.selectRowIntValue(Container.conn, sql);

			if (countMember != 0) {
				System.out.println("이미 존재하는 아이디입니다");
				continue;
			}
			break;
		}

		while (true) {
			System.out.print("비밀번호 : ");
			loginPw = Container.sc.nextLine().trim();

			if (loginPw.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			System.out.print("비밀번호 확인 : ");
			loginPwConfrim = Container.sc.nextLine().trim();

			if (loginPw.equals(loginPwConfrim) == false) {
				System.out.println("비밀번호를 확인해주세요");
				continue;
			}
			break;
		}

		while (true) {
			System.out.print("이름 : ");
			name = Container.sc.nextLine().trim();

			if (name.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}
			break;
		}

		SecSql sql = new SecSql();

		sql.append("INSERT INTO `member`");
		sql.append("SET loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?", name);
		sql.append(", regDate = NOW()");
		sql.append(", updateDate = NOW()");

		int id = DBUtil.insert(Container.conn, sql);

		System.out.println(id + "님, 회원가입되셨습니다");
	}

	private void doLogin() {
		String loginId = null;
		String loginPw = null;
		Member member = null;
		
		int countAttempt = 0;
		int attemptLimit = 3;
		while (true) {
			if(countAttempt == attemptLimit) {
				System.out.println("아이디를 확인 후 다시 시도해주세요");
				return;
			}
			countAttempt++;
			
			System.out.print("아이디 : ");
			loginId = Container.sc.nextLine().trim();

			if (loginId.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			SecSql sql = new SecSql();

			sql.append("SELECT *");
			sql.append("FROM `member`");
			sql.append("WHERE loginId = ?", loginId);

			Map<String, Object> memberMap = DBUtil.selectRow(Container.conn, sql);

			if (memberMap.isEmpty()) {
				System.out.println("일치하는 회원이 없습니다");
				continue;
			}
			
			member = new Member(memberMap);
			break;
		}
		countAttempt = 0;
		while (true) {
			if(countAttempt == attemptLimit) {
				System.out.println("비밀번호를 확인 후 다시 시도해주세요");
				return;
			}
			countAttempt++;
			System.out.print("비밀번호 : ");
			loginPw = Container.sc.nextLine().trim();

			if (loginPw.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			if (member.loginPw.equals(loginPw) == false) {
				System.out.println("비밀번호가 틀렸습니다");
				continue;
			}
			break;
		}

		loginedMember = member;
		System.out.println("반갑습니다 " + loginedMember.loginId + "님!");

	}

	private void doLogout() {
		loginedMember = null;
		System.out.println("로그아웃되었습니다");
	}

}
