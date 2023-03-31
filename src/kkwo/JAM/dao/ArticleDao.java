package kkwo.JAM.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kkwo.JAM.container.Container;
import kkwo.JAM.dto.Article;
import kkwo.JAM.util.DBUtil;
import kkwo.JAM.util.SecSql;

public class ArticleDao extends Dao {

	/** article 데이터 생성 */
	public int doWrite(int memberId, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET hit = 0");
		sql.append(", memberId = ?", memberId);
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append(", regDate = NOW()");
		sql.append(", updateDate = NOW()");

		return DBUtil.insert(Container.conn, sql);
	}

	/** article 데이터 수정 */
	public void doModify(int articleId, String newTitle, String newBody) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET title = ?", newTitle);
		sql.append(", `body` = ?", newBody);
		sql.append(", updateDate = NOW()");
		sql.append("WHERE id = ?", articleId);

		DBUtil.update(Container.conn, sql);
	}

	/** article 데이터 삭제 */
	public void doDelete(int articleId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", articleId);

		DBUtil.delete(Container.conn, sql);
	}

	/** id 일치하는 데이터 불러오기 */
	public Article getArticleById(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", articleId);

		Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql);

		if (articleMap.isEmpty()) {
			return null;
		}
		Article article = new Article(articleMap);

		return article;
	}

	/** article 목록 불러오기 */
	public List<Article> getArticleList(Map<String, Object> args) {

		int articleOffset = (int) args.get("articleOffset");
		int maxArticlesPerPage = (int) args.get("maxArticlesPerPage");
		String searchKeyword = (String) args.get("searchKeyword");

		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		if (searchKeyword != null && searchKeyword.length() > 0) {
			sql.append("WHERE title LIKE CONCAT('%',?,'%')", searchKeyword);
		}
		sql.append("ORDER BY id ASC");
		sql.append("LIMIT ?, ?", articleOffset, maxArticlesPerPage);

		List<Article> articleList = new ArrayList<>();
		List<Map<String, Object>> articlesMaps = DBUtil.selectRows(Container.conn, sql);

		for (Map<String, Object> articleMap : articlesMaps) {
			articleList.add(new Article(articleMap));
		}

		return articleList;
	}

	/** 검색된 게시물 불러오기 */
//	public List<Article> searchArticlesByTitle(String searchKeyword) {
//
//		List<Article> articleList = getArticleList();
//		List<Article> articles = new ArrayList<>();
//		for (Article article : articleList) {
//			if (article.title.contains(searchKeyword)) {
//				articles.add(article);
//			}
//		}
//		return articles;
//	}

	/** 조회수 증가 */
	public void increaseViewCount(int articleId) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET hit = hit + 1");
		sql.append("WHERE id = ?", articleId);

		DBUtil.update(Container.conn, sql);
	}
}
