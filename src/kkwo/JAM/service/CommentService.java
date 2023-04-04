package kkwo.JAM.service;

import java.util.List;

import kkwo.JAM.container.Container;
import kkwo.JAM.dao.CommentDao;
import kkwo.JAM.dto.Comment;

public class CommentService {
	private CommentDao commentDao;
	
	public CommentService() {
		commentDao = Container.commentDao;
	}

	public List<Comment> getCommentsByArticleId(int articleId) {
		return commentDao.getCommentsByArticleId(articleId);
	}

	public void doWrite(String body, int memberId, int articleId) {
		commentDao.doWrite(body, memberId, articleId);
	}
}
