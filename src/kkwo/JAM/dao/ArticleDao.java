package kkwo.JAM.dao;

import java.util.ArrayList;
import java.util.List;

import kkwo.JAM.dto.Article;

public class ArticleDao {
	public List<Article> articles;
	
	public ArticleDao() {
		articles = new ArrayList<>();
	}

	public void add(Article article) {
		articles.add(article);
	}
}
