package kkwo.JAM.controller;

import kkwo.JAM.container.Container;
import kkwo.JAM.dto.Member;
import kkwo.JAM.service.MemberService;

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
		case "detail":
			showDetail();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		default:
			System.out.println("존재하지 않는 기능입니다");
			break;
		}
	}

	private void doJoin() {
		String loginId = null;
		String loginPw = null;
		String name = null;

		boolean isValidInput = false;
		while (!isValidInput) {
			System.out.print("아이디 : ");
			loginId = Container.sc.nextLine().trim();

			if (loginId.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			if (memberService.isExistingLoginId(loginId)) {
				System.out.println("이미 존재하는 아이디입니다");
				continue;
			}
			isValidInput = true;
		}

		isValidInput = false;
		while (!isValidInput) {
			System.out.print("비밀번호 : ");
			loginPw = Container.sc.nextLine().trim();

			if (loginPw.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			System.out.print("비밀번호 확인 : ");
			String loginPwConfrim = Container.sc.nextLine().trim();

			if (!loginPw.equals(loginPwConfrim)) {
				System.out.println("비밀번호를 확인해주세요");
				continue;
			}
			isValidInput = true;
		}

		isValidInput = false;
		while (!isValidInput) {
			System.out.print("이름 : ");
			name = Container.sc.nextLine().trim();

			if (name.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}
			isValidInput = true;
		}

		int memberId = memberService.doJoin(loginId, loginPw, name);

		System.out.println(memberId + "님, 회원가입되셨습니다");
	}

	private void doLogin() {
		String loginId = null;
		String loginPw = null;
		Member member = null;

		boolean isValidInput = false;
		int loginAttempt = 0;
		int maxAttempt = 3;
		while (!isValidInput) {
			if (loginAttempt == maxAttempt) {
				System.out.println("아이디를 확인 후 다시 시도해주세요");
				return;
			}
			loginAttempt++;

			System.out.print("아이디 : ");
			loginId = Container.sc.nextLine().trim();

			if (loginId.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			member = memberService.getMemberByLoginId(loginId);

			if (member == null) {
				System.out.println("일치하는 회원이 없습니다");
				continue;
			}
			isValidInput = true;
		}

		isValidInput = false;
		loginAttempt = 0;
		while (!isValidInput) {
			if (loginAttempt == maxAttempt) {
				System.out.println("비밀번호를 확인 후 다시 시도해주세요");
				return;
			}
			loginAttempt++;

			System.out.print("비밀번호 : ");
			loginPw = Container.sc.nextLine().trim();

			if (loginPw.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			if (!member.loginPw.equals(loginPw)) {
				System.out.println("비밀번호가 틀렸습니다");
				continue;
			}
			isValidInput = true;
		}

		loginedMember = member;
		System.out.println("반갑습니다 " + loginedMember.loginId + "님!");

	}

	private void doLogout() {
		loginedMember = null;
		System.out.println("로그아웃되었습니다");
	}

	private void showDetail() {
		System.out.println("  회원 정보  ");
		System.out.println("회원번호 : " + loginedMember.id);
		System.out.println("아이디 : " + loginedMember.loginId);
		System.out.println("이름 : " + loginedMember.name);
		System.out.println("가입일 : " + loginedMember.regDate);
		System.out.println("수정일 : " + loginedMember.updateDate);
	}
	
	private void doModify() {
		String newLoginPw = null;
		String newName = null;
		boolean isValidInput;

		while (true) {
			System.out.print("수정할 정보를 선택해주세요\n1. 비밀번호\n2. 이름\n3. 결정\n입력 : ");
			int choice = Container.sc.nextInt();
			Container.sc.nextLine();

			if (choice == 3) {
				if(newLoginPw != null || newName != null) {
					memberService.doModify(loginedMember.id, newLoginPw, newName);
					System.out.println("수정되었습니다");
					break;
				} else {
					System.out.println("변경사항이 없습니다");
					break;
				}
			}

			if (choice == 1) {
				isValidInput = false;
				while (!isValidInput) {
					System.out.print("새 비밀번호 : ");
					newLoginPw = Container.sc.nextLine().trim();
					if(newLoginPw.length() == 0) {
						System.out.println("필수 입력란입니다");
						continue;
					}
					if (loginedMember.loginPw.equals(newLoginPw)) {
						System.out.println("사용불가");
						continue;
					}
					System.out.print("새 비밀번호 확인 : ");
					String newLoginPwConfirm = Container.sc.nextLine().trim();
					if (!newLoginPw.equals(newLoginPwConfirm)) {
						System.out.println("새 비밀번호가 일치하지 않습니다");
						continue;
					}
					isValidInput = true;
				}
				System.out.println("안전한 비밀번호입니다");
			}

			if (choice == 2) {
				isValidInput = false;
				while (!isValidInput) {
					System.out.print("새 이름 : ");
					newName = Container.sc.nextLine().trim();
					if(newName.length() == 0) {
						System.out.println("필수 입력란입니다");
						continue;
					}
					if (loginedMember.name.equals(newName)) {
						System.out.println("새 이름을 입력해주세요");
						continue;
					}
					isValidInput = true;
				}
			}
		}
	}

	private void doDelete() {
		memberService.doDelete(loginedMember.id);
		System.out.println(loginedMember.loginId + "님, 탈퇴되었습니다");
		loginedMember = null;
	}
}