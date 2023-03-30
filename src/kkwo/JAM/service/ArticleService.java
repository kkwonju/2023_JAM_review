package kkwo.JAM.service;

import java.util.List;

import kkwo.JAM.container.Container;
import kkwo.JAM.dao.ArticleDao;
import kkwo.JAM.dto.Article;

public class ArticleService {
	private ArticleDao articleDao;
	
	public ArticleService() {
		this.articleDao = Container.articleDao;
	}

	public int doWrite(int memberId, String title, String body) {
		return articleDao.doWrite(memberId, title, body);
	}

	public List<Article> getArticleList() {
		return articleDao.getArticleList();
	}

	public void doModify(int articleId, String newTitle, String newBody) {
		articleDao.doModify(articleId, newTitle, newBody);
	}

	public void doDelete(int articleId) {
		articleDao.doDelete(articleId);
	}
	
	public Article getArticleById(int articleId) {
		return articleDao.getArticleById(articleId);
	}

	public void increaseViewCount(int articleId) {
		articleDao.increaseViewCount(articleId);
	}
	
}
