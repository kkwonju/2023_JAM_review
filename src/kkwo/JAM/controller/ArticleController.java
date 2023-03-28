package kkwo.JAM.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import kkwo.JAM.container.Container;
import kkwo.JAM.dto.Article;
import kkwo.JAM.service.ArticleService;
import kkwo.JAM.service.MemberService;
import kkwo.JAM.util.Util;

public class ArticleController extends Controller {
	private String actionMethodName;
	private String command;
	private Scanner sc;
	private ArticleService articleService;
	private MemberService memberService;

	private Connection conn;

	public ArticleController(Scanner sc, Connection conn) {
		articleService = Container.articleService;
		memberService = Container.memberService;

		this.sc = sc;
		this.conn = conn;
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

		PreparedStatement pstmt = null;

		try {
			String sql = "INSERT INTO article";
			sql += " SET hit = 0,";
			sql += " memberId = " + memberId + ",";
			sql += " title = '" + title + "',";
			sql += " `body` = '" + body + "',";
			sql += "regDate = NOW(),";
			sql += "updateDate = NOW();";

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

		System.out.println(articleId + "번 게시글이 작성되었습니다");
	}

	private void showList() {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Article> forPrintArticles = new ArrayList<>();

		try {
			String sql = "SELECT *";
			sql += " FROM article";
			sql += " ORDER BY id DESC;";

			System.out.println(sql);

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int hit = rs.getInt("hit");
				int memberId = rs.getInt("memberId");
				String title = rs.getString("title");
				String body = rs.getString("body");
				String regDate = rs.getString("regDate");
				String updateDate = rs.getString("updateDate");

				forPrintArticles.add(new Article(id, hit, memberId, title, body, regDate, updateDate));
			}

		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (forPrintArticles.size() == 0) {
			System.out.println("게시물이 없습니다");
			return;
		}

		System.out.println("  번호  /     제목     /  작성자 id  /  조회  ");
		for (Article article : forPrintArticles) {
			System.out.printf("  %d  / %s /    %s    /  %d  \n", article.id, article.title, article.id, article.hit);
		}
	}

	private void showDetail() {
		String[] commDiv = command.split(" ");
		if (commDiv.length < 3) {
			System.out.println("글 번호를 입력해주세요");
			return;
		}
		int id = Integer.parseInt(commDiv[2]);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Article foundArticle = null;

		try {
			String sql = "SELECT *";
			sql += " FROM article";
			sql += " WHERE id = '" + id + "';";

			System.out.println(sql);

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int foundArticleId = rs.getInt("id");
				int foundArticleHit = rs.getInt("hit");
				int foundArticleMemberId = rs.getInt("memberId");
				String foundArticleTitle = rs.getString("title");
				String foundArticleBody = rs.getString("body");
				String foundArticleRegDate = rs.getString("regDate");
				String foundArticleUpdateDate = rs.getString("updateDate");

				foundArticle = new Article(foundArticleId, foundArticleHit, foundArticleMemberId,
						foundArticleTitle, foundArticleBody, foundArticleRegDate, foundArticleUpdateDate);
			}

		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (foundArticle == null) {
			System.out.println("해당 게시글이 존재하지 않습니다");
			return;
		}

		System.out.println("번호  : " + foundArticle.id);
		System.out.println("작성자 id  : " + foundArticle.memberId);
		System.out.println("조회  : " + foundArticle.hit);
		System.out.println("제목  : " + foundArticle.title);
		System.out.println("내용  : " + foundArticle.body);
		System.out.println("작성일  : " + foundArticle.regDate);
		System.out.println("수정일  : " + foundArticle.updateDate);
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

		if (loginedMember.id != article.memberId) {
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

		if (loginedMember.id != article.memberId) {
			System.out.println("삭제 권한이 없습니다");
			return;
		}

		articleService.remove(article);
		System.out.println(id + "번 글이 삭제되었습니다");
	}

}
