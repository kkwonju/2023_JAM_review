package kkwo.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import kkwo.JAM.container.Container;
import kkwo.JAM.controller.ArticleController;
import kkwo.JAM.controller.Controller;
import kkwo.JAM.controller.MemberController;

public class App {
	String controllerName;
	String actionMethodName;
	Controller controller;
	
	public void start() {
		System.out.println("== 프로그램 시작 ==");
		Container.sc  = new Scanner(System.in);
		
		Container.init();

		while (true) {
			System.out.print("명령어 > ");
			String command = Container.sc.nextLine().trim();

			Connection conn = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			try {
				conn = DriverManager.getConnection(url, "root", "");
				Container.conn = conn;

				int actionResult = action(command);

				if (actionResult == -1) {
					System.out.println("프로그램 종료");
					break;
				}
			} catch (SQLException e) {
				System.out.println("에러 : " + e);
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private int action(String command) {

		if (command.length() == 0) {
			System.out.println("명령어를 입력해주세요");
			return 0;
		}

		if (command.equals("exit")) {
			return -1;
		}

		String[] commDiv = command.split(" ");
		controllerName = commDiv[0];

		if (commDiv.length == 1) {
			System.out.println("명령어를 확인해주세요");
			return 0;
		}

		actionMethodName = commDiv[1];
		controller = null;

		if (controllerName.equals("article")) {
			controller = Container.articleController;
		} else if (controllerName.equals("member")) {
			controller = Container.memberController;
		} else {
			System.out.println("명령어를 확인해주세요");
			return 0;
		}

		String forLoginCheck = controllerName + "/" + actionMethodName;
		switch (forLoginCheck) {
		case "article/write":
		case "article/modify":
		case "article/delete":
		case "member/logout":
		case "member/delete":
			if (!Controller.isLoginCheck()) {
				System.out.println("로그인 후 이용해주세요");
				return 0;
			}
			break;
		}
		switch (forLoginCheck) {
		case "member/join":
		case "member/login":
			if (Controller.isLoginCheck()) {
				System.out.println("로그아웃 후 이용해주세요");
				return 0;
			}
		}
		controller.action(actionMethodName, command);
		return 0;
	}
}
