package kkwo.JAM.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kkwo.JAM.dto.Article;

public class ArticleDao extends Dao{
	public List<Article> articles;
	
	public ArticleDao() {
		articles = new ArrayList<>();
	}

	public void add(Article article) {
		articles.add(article);
		lastId++;
	}

	public int setNewId() {
		int id = lastId + 1;
		return id;
	}

	public List<Article> getArticles() {
		return articles;
	}
	
	private int getArticleIndexById(int id) {
		int i = 0;
		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public Article getArticleById(int id) {
		int index = getArticleIndexById(id);
		if (index == -1) {
			return null;
		}
		return articles.get(index);
	}

	public void remove(Article article) {
		articles.remove(article);
	}

	public void doModify(Article article, String newTitle, String newBody, LocalDateTime updateDate) {
		article.title = newTitle;
		article.body = newBody;
		article.updateDate = updateDate;
		
	}

	public void increaseViewCount(Article article) {
		article.hit++;
	}
}
