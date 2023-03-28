package kkwo.JAM.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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


	public MemberController(Scanner sc, Connection conn) {
		memberService = Container.memberService;
		members = Container.memberDao.members;
		this.sc = sc;
		this.conn = conn;
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
		
		PreparedStatement pstmt = null;

		try {
			String sql = "INSERT INTO `member`";
			sql += " SET loginId = '" + loginId + "'";
			sql += ", loginPw = '" + loginPw + "'";
			sql += ", `name` = '" + name + "'";
			sql += ", regDate = NOW()";
			sql += ", updateDate = NOW();";

			System.out.println(sql);

			pstmt = conn.prepareStatement(sql);

			int affectedRow = pstmt.executeUpdate();

			System.out.println("affectedRow : " + affectedRow);
		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

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
