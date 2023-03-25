package kkwo.JAM.service;

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
	
	
}
