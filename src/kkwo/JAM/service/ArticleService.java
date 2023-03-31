package kkwo.JAM.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<Article> getArticleList(int page, int maxArticlesPerPage, String searchKeyword) {
		
		int articleOffset = (page - 1) * maxArticlesPerPage;
		
		Map<String, Object> args = new HashMap<>();
		args.put("articleOffset", articleOffset);
		args.put("maxArticlesPerPage", maxArticlesPerPage);
		args.put("searchKeyword", searchKeyword);
		
		return articleDao.getArticleList(args);
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

//	public List<Article> searchArticlesByTitle(String searchKeyword) {
//		return articleDao.searchArticlesByTitle(searchKeyword);
//	}
	
}
