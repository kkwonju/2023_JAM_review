package kkwo.JAM.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kkwo.JAM.container.Container;
import kkwo.JAM.dto.Comment;
import kkwo.JAM.util.DBUtil;
import kkwo.JAM.util.SecSql;

public class CommentDao {

	public List<Comment> getCommentsByArticleId(int articleId) {

		SecSql sql = new SecSql();

		sql.append("SELECT C.*, m.name AS extra__writer");
		sql.append("FROM `comment` C");
		sql.append("INNER JOIN `member` M");
		sql.append("ON C.memberId = M.id");
		sql.append("WHERE C.articleId = ?", articleId);

		List<Map<String, Object>> commentMaps = DBUtil.selectRows(Container.conn, sql);
		List<Comment> comments = new ArrayList<>();

		for (Map<String, Object> commentMap : commentMaps) {
			comments.add(new Comment(commentMap));
		}

		return comments;
	}

	public void doWrite(String body, int memberId, int articleId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO `comment`");
		sql.append("SET articleId = ?", articleId);
		sql.append(", memberId = ?", memberId);
		sql.append(", `body` = ?", body);
		sql.append(", regDate = NOW()");
		sql.append(", updateDate = NOW()");
		
		DBUtil.insert(Container.conn, sql);
	}
}
