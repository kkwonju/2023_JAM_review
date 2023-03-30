package kkwo.JAM.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kkwo.JAM.container.Container;
import kkwo.JAM.dto.Article;
import kkwo.JAM.util.DBUtil;
import kkwo.JAM.util.SecSql;

public class ArticleDao extends Dao {

	public int setNewId() {
		int id = lastId + 1;
		return id;
	}

	public void increaseViewCount(Article article) {
		article.hit++;
	}
	
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
	public List<Article> getArticleList() {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC");

		List<Article> articleList = new ArrayList<>();
		List<Map<String, Object>> articlesMaps = DBUtil.selectRows(Container.conn, sql);
		
		for (Map<String, Object> articleMap : articlesMaps) {
			articleList.add(new Article(articleMap));
		}

		return articleList;
	}

}
