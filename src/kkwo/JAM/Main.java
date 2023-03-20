package kkwo.JAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		List<Article> articles = new ArrayList<>();
		int lastArticleId = 0;

		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("명령어 > ");
			String command = sc.nextLine();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (command.equals("exit")) {
				break;
			}

			if (command.equals("article write")) {
				int articleId = lastArticleId + 1;
				System.out.println("== 게시물 작성 ==");
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();
				String regDate = Util.getNowDateTimeStr();
				articles.add(new Article(articleId, title, body, regDate));
				System.out.println(articleId + "번 게시글이 작성되었습니다");
				lastArticleId++;
			} else if (command.equals("article list")) {
				Article forPrintArticle = null;
				if (articles.size() > 0) {
					System.out.println(" 번호 / 제목 ");
					for (int i = articles.size() - 1; i >= 0; i--) {
						forPrintArticle = articles.get(i);
						System.out.printf("  %d  /  %s  \n", forPrintArticle.id, forPrintArticle.title);
					}
				}
				if(forPrintArticle == null) {
					System.out.println("게시글이 없습니다");
					continue;
				}
			} else {
				System.out.println("명령어를 확인해주세요");
				continue;
			}
		}
		System.out.println("== 프로그램 종료 ==");
		sc.close();
	}
}
