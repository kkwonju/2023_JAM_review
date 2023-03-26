package kkwo.JAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import kkwo.JAM.container.Container;
import kkwo.JAM.dto.Member;
import kkwo.JAM.service.MemberService;
import kkwo.JAM.util.Util;

public class MemberController extends Controller {
	private String actionMethodName;
	private String command;
	private Scanner sc;
	private MemberService memberService;
	private List<Member> members;
	private Connection conn;

	private int lastMemberId = 3;

	public MemberController(Scanner sc, Connection conn) {
		memberService = Container.memberService;
		members = Container.memberDao.members;
		this.sc = sc;
		this.conn = conn;
	}

	@Override
	public void makeTestData() {
		System.out.println("member 테스트 테이터 생성");
		memberService.add(new Member(1, "test1", "pw", "송강호", "2023-12-12 12:12:12", "2023-12-12 12:12:12"));
		memberService.add(new Member(2, "test2", "pw", "최민식", "2023-12-12 12:12:12", "2023-12-12 12:12:12"));
		memberService.add(new Member(3, "test3", "pw", "백윤식", "2023-12-12 12:12:12", "2023-12-12 12:12:12"));

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
		int id = memberService.setNewId();
		String loginId = null;
		String loginPw = null;
		String loginPwConfrim = null;
		String name = null;

		while (true) {
			System.out.print("아이디 : ");
			loginId = sc.nextLine().trim();

			if (loginId.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			if (memberService.isJoinableLoginId(loginId) == false) {
				System.out.println("이미 존재하는 아이디입니다");
				continue;
			}
			break;
		}

		while (true) {
			System.out.print("비밀번호 : ");
			loginPw = sc.nextLine().trim();

			if (loginPw.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			System.out.print("비밀번호 확인 : ");
			loginPwConfrim = sc.nextLine().trim();

			if (loginPw.equals(loginPwConfrim) == false) {
				System.out.println("비밀번호를 확인해주세요");
				continue;
			}
			break;
		}

		while (true) {
			System.out.print("이름 : ");
			name = sc.nextLine().trim();

			if (name.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}
			break;
		}

		String regDate = Util.getNowDateTimeStr();
		memberService.add(new Member(id, loginId, loginPw, name, regDate, regDate));
		System.out.println(loginId + "님, 회원가입되셨습니다");
	}

	private void doLogin() {
		String loginId = null;
		String loginPw = null;
		Member member = null;

		while (true) {
			System.out.print("아이디 : ");
			loginId = sc.nextLine().trim();

			if (loginId.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			member = memberService.getMemberByLoginId(loginId);

			if (member == null) {
				System.out.println("일치하는 회원이 없습니다");
				continue;
			}
			break;
		}

		while (true) {
			System.out.print("비밀번호 : ");
			loginPw = sc.nextLine().trim();

			if (loginPw.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			if (member.loginPW.equals(loginPw) == false) {
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
