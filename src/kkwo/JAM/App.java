package kkwo.JAM;

import java.util.Scanner;

import kkwo.JAM.controller.ArticleController;
import kkwo.JAM.controller.Controller;
import kkwo.JAM.controller.MemberController;

public class App {
	String controllerName;
	String actionMethodName;
	
	public void start() {
		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);
		
		ArticleController articleController = new ArticleController(sc);
		MemberController memberController = new MemberController(sc);
		
		Controller controller;
		
//		articleController.makeTestData();
//		memberController.makeTestData();

		while (true) {
			System.out.print("명령어 > ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (command.equals("exit")) {
				break;
			}
			
			String[] commDiv = command.split(" ");
			controllerName = commDiv[0];
			
			if(commDiv.length == 1) {
				System.out.println("명령어를 확인해주세요");
				continue;
			}
			
			actionMethodName = commDiv[1];
			controller = null;
			
			if(controllerName.equals("article")) {
				controller = articleController;
			} else if (controllerName.equals("member")) {
				controller = memberController;
			} else {
				System.out.println("명령어를 확인해주세요");
				continue;
			}
			
			String forLoginCheck = controllerName + "/" + actionMethodName;
			switch (forLoginCheck) {
			case "article/write":
			case "article/modify":
			case "article/delete":
			case "member/logout":
				if(Controller.isLoginCheck() == false) {
					System.out.println("로그아웃 후 이용해주세요");
					continue;
				}
				break;
			}
			switch (forLoginCheck) {
			case "member/join":
			case "member/login":
				if(Controller.isLoginCheck()) {
					System.out.println("로그아웃 후 이용해주세요");
					continue;
				}
			}
		
			controller.action(actionMethodName, command);

		}
		System.out.println("== 프로그램 종료 ==");
		sc.close();
	}
}
