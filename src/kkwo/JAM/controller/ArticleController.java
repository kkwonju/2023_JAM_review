package kkwo.JAM.controller;

import java.util.List;
import java.util.Scanner;

import kkwo.JAM.container.Container;
import kkwo.JAM.dto.Article;
import kkwo.JAM.dto.Member;
import kkwo.JAM.service.ArticleService;
import kkwo.JAM.service.MemberService;
import kkwo.JAM.util.Util;

public class ArticleController extends Controller {
	private String actionMethodName;
	private String command;
	private Scanner sc;
	private ArticleService articleService;
	private MemberService memberService;
	private List<Article> articles;
	private List<Member> members;

	public ArticleController(Scanner sc) {
		articleService = Container.articleService;
		memberService = Container.memberService;
		articles = articleService.getArticles();
		members = memberService.getMembers();
		this.sc = sc;
	}

	@Override
	public void makeTestData() {
		System.out.println("article 테스트 테이터 생성");
		articleService.add(new Article(1, 3, "제목1", "내용1", "2023-12-12 12:12:12", "2023-12-12 12:12:12"));
		articleService.add(new Article(2, 2, "제목2", "내용2", "2023-12-12 12:12:12", "2023-12-12 12:12:12"));
		articleService.add(new Article(3, 2, "제목3", "내용3", "2023-12-12 12:12:12", "2023-12-12 12:12:12"));
	}

	@Override
	public void action(String actionMethodName, String command) {
		this.actionMethodName = actionMethodName;
		this.command = command;

		switch (actionMethodName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
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

	private void doWrite() {
		System.out.println("== 게시물 작성 ==");

		int articleId = articleService.setNewId();
		int memberId = loginedMember.id;

		System.out.print("제목 : ");
		String title = sc.nextLine();
		System.out.print("내용 : ");
		String body = sc.nextLine();
		String regDate = Util.getNowDateTimeStr();

		articleService.add(new Article(articleId, memberId, title, body, regDate, regDate));
		System.out.println(articleId + "번 게시글이 작성되었습니다");
	}

	private void showList() {
		if (articles.size() == 0) {
			System.out.println("게시물이 없습니다");
			return;
		}

		System.out.println(" 번호 / 제목 /  작성자  / 조회 ");

		for (int i = articles.size() - 1; i >= 0; i--) {
			Article article = articles.get(i);
			String writerName = null;

			for (Member member : members) {
				if (member.id == article.memberId) {
					writerName = member.name;
				}
			}
			System.out.printf("  %d  /  %s  /  %s  /  %d  \n", article.id, article.title, writerName, article.hit);
		}
	}

	private void showDetail() {
		String[] commDiv = command.split(" ");
		if (commDiv.length < 3) {
			System.out.println("글 번호를 입력해주세요");
			return;
		}
		int id = Integer.parseInt(commDiv[2]);
		Article article = articleService.getArticleById(id);

		if (article == null) {
			System.out.println("해당 게시글이 존재하지 않습니다");
			return;
		}

		String writerName = null;
		
		for (Member member : members) {
			if (member.id == article.memberId) {
				writerName = member.name;
			}
		}
		System.out.println("번호  : " + article.id);
		System.out.println("작성자  : " + writerName);
		System.out.println("조회  : " + article.hit);
		System.out.println("제목  : " + article.title);
		System.out.println("내용  : " + article.body);
		System.out.println("작성일  : " + article.regDate);
		System.out.println("수정일  : " + article.updateDate);

		article.hit++;
	}



	private void doModify() {
		String[] commDiv = command.split(" ");
		if (commDiv.length < 3) {
			System.out.println("글 번호를 입력해주세요");
			return;
		}
		int id = Integer.parseInt(commDiv[2]);
		Article article = articleService.getArticleById(id);

		if (article == null) {
			System.out.println("해당 게시글이 존재하지 않습니다");
			return;
		}
		
		if(loginedMember.id != article.memberId) {
			System.out.println("수정 권한이 없습니다");
			return;
		}

		System.out.print("새 제목 : ");
		String newTitle = sc.nextLine();
		System.out.print("새 내용 : ");
		String newBody = sc.nextLine();
		String updateDate = Util.getNowDateTimeStr();

		articleService.doModify(article, newTitle, newBody, updateDate);

		System.out.println(id + "번 글이 수정되었습니다");
	}

	private void doDelete() {
		String[] commDiv = command.split(" ");
		if (commDiv.length < 3) {
			System.out.println("글 번호를 입력해주세요");
			return;
		}
		int id = Integer.parseInt(commDiv[2]);
		Article article = articleService.getArticleById(id);

		if (article == null) {
			System.out.println("해당 게시글이 존재하지 않습니다");
			return;
		}
		
		if(loginedMember.id != article.memberId) {
			System.out.println("삭제 권한이 없습니다");
			return;
		}

		articleService.remove(article);
		System.out.println(id + "번 글이 삭제되었습니다");
	}

}
