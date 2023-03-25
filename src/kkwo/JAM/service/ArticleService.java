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

	public void add(Article article) {
		articleDao.add(article);
	}

	public int setNewId() {
		return articleDao.setNewId();
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public void remove(Article article) {
		articleDao.remove(article);
	}

	public void doModify(Article article, String newTitle, String newBody, String updateDate) {
		articleDao.doModify(article, newTitle, newBody, updateDate);
	}
	
	
}
