package kkwo.JAM.controller;

import java.util.List;

import kkwo.JAM.container.Container;
import kkwo.JAM.dto.Article;
import kkwo.JAM.dto.Comment;
import kkwo.JAM.service.ArticleService;
import kkwo.JAM.service.CommentService;
import kkwo.JAM.service.MemberService;

public class ArticleController extends Controller {
	private String actionMethodName;
	private String command;
	private ArticleService articleService;
	private MemberService memberService;
	private CommentService commentService;

	public ArticleController() {
		articleService = Container.articleService;
		memberService = Container.memberService;
		commentService = Container.commentService;
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

		int memberId = loginedMember.id;

		System.out.print("제목 : ");
		String title = Container.sc.nextLine();
		System.out.print("내용 : ");
		String body = Container.sc.nextLine();

		int newArticleId = articleService.doWrite(memberId, title, body);
		System.out.println(newArticleId + "번 글이 작성되었습니다");
	}

	private void showList() {
		int page = 1;
		int maxArticlesPerPage = 5;
		String searchKeyword = null;

		String[] commDiv = command.split(" ");

		if (commDiv.length > 2) {
			page = Integer.parseInt(commDiv[2]);
		}
		if (commDiv.length > 3) {
			searchKeyword = commDiv[3];
		}

		List<Article> articleList = articleService.getArticleList(page, maxArticlesPerPage, searchKeyword);

		if (articleList.size() == 0) {
			System.out.println("게시물이 없습니다");
			return;
		}

		System.out.println("  번호  /  제목  / 작성자 /  조회  ");
		for (Article article : articleList) {
			String writerName = "(탈퇴)";
			if (article.extra__writer != null) {
				writerName = article.extra__writer;
			}
			System.out.printf("  %d  /   %s   /  %s  /  %d  \n", article.id, article.title, writerName, article.hit);
		}
	}

	private void showDetail() {
		String[] commDiv = command.split(" ");
		if (commDiv.length < 3) {
			System.out.println("글 번호를 입력해주세요");
			return;
		}
		int articleId = Integer.parseInt(commDiv[2]);

		Article article = articleService.getArticleById(articleId);

		if (article == null) {
			System.out.println("해당 게시글이 존재하지 않습니다");
			return;
		}

		System.out.println("번호  : " + article.id);
		if (article.extra__writer == null) {
			System.out.println("작성자 : (탈퇴한 회원입니다)");
		} else {
			System.out.println("작성자  : " + article.extra__writer);
		}
		System.out.println("조회  : " + article.hit);
		System.out.println("제목  : " + article.title);
		System.out.println("내용  : " + article.body);
		System.out.println("작성일  : " + article.regDate);
		System.out.println("수정일  : " + article.updateDate);

		System.out.println("==== 댓글 ====");
		List<Comment> comments = commentService.getCommentsByArticleId(articleId);
		if (comments.size() == 0) {
			System.out.println("댓글이 없습니다");
		} else {
			for (Comment comment : comments) {
				System.out.println("작성자 : " + comment.extra__writer);
				System.out.println("내용 : " + comment.body);
				if (comment.regDate.equals(comment.updateDate)) {
					System.out.println("작성일 : " + comment.regDate);
				} else {
					System.out.println("수정일 : " + comment.updateDate);
				}
				System.out.println("---- ---- ----");
			}
		}
		articleService.increaseViewCount(articleId);

		if (!isLoginCheck()) {
			return;
		}
		System.out.print("== 댓글 메뉴 ==\n1. 댓글달기\n2 뒤로가기\n입력 : ");
		int choice = Container.sc.nextInt();
		Container.sc.nextLine();
		switch (choice) {
		case 1:
			System.out.print("내용 : ");
			String body = Container.sc.nextLine().trim();
			
			if (body.length() == 0) {
				System.out.println("댓글을 입력해주세요");
				break;
			}
			
			commentService.doWrite(body, loginedMember.id, articleId);
			System.out.println("작성 완료");
			showDetail();
		case 2:
			break;
		default:
			break;
		}

	}

	private void doModify() {
		String[] commDiv = command.split(" ");
		if (commDiv.length < 3) {
			System.out.println("글 번호를 입력해주세요");
			return;
		}
		int articleId = Integer.parseInt(commDiv[2]);

		Article article = articleService.getArticleById(articleId);

		if (article == null) {
			System.out.println("해당 게시글이 존재하지 않습니다");
			return;
		}

		if (loginedMember.id != article.memberId) {
			System.out.println("수정 권한이 없습니다");
			return;
		}

		System.out.print("새 제목 : ");
		String newTitle = Container.sc.nextLine();
		System.out.print("새 내용 : ");
		String newBody = Container.sc.nextLine();

		articleService.doModify(articleId, newTitle, newBody);

		System.out.println(articleId + "번 글이 수정되었습니다");
	}

	private void doDelete() {
		String[] commDiv = command.split(" ");
		if (commDiv.length < 3) {
			System.out.println("글 번호를 입력해주세요");
			return;
		}
		int articleId = Integer.parseInt(commDiv[2]);

		Article article = articleService.getArticleById(articleId);

		if (article == null) {
			System.out.println("해당 게시글이 존재하지 않습니다");
			return;
		}

		if (loginedMember.id != article.memberId) {
			System.out.println("삭제 권한이 없습니다");
			return;
		}

		articleService.doDelete(articleId);

		System.out.println(articleId + "번 글이 삭제되었습니다");
	}
}
