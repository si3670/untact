package com.sbs.untact.dao;

import java.util.ArrayList;
import java.util.List;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.util.Util;

public class ArticleDao {
	private int articleLastId;
	private List<Article> articles;

	public ArticleDao() {
		articleLastId = 0;
		articles = new ArrayList<>();

		articles.add(new Article(++articleLastId, "2020-01-25 12:12:12", "2020-01-25 12:12:12", "제목1", "내용1"));
		articles.add(new Article(++articleLastId, "2020-01-25 12:12:12", "2020-01-25 12:12:12", "제목2", "내용2"));
	}

	public Article getArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}
		return null;
	}

	public List<Article> getArticles(String searchKeywordType, String searchKeyword) {
		if (searchKeyword == null) {
			return articles;
		}
		List<Article> filtered = new ArrayList<>();

		for (Article article : articles) {
			boolean contains = false;
			if (searchKeywordType.equals("title")) {
				contains = article.getTitle().contains(searchKeyword);
			} else if (searchKeywordType.equals("body")) {
				contains = article.getBody().contains(searchKeyword);
			} else {
				contains = article.getTitle().contains(searchKeyword);
				if (contains == false) {
					contains = article.getBody().contains(searchKeyword);
				}
			}

			if (contains) {
				filtered.add(article);
			}
		}
		return filtered;
	}

	public int getAddArticle(String title, String body) {
		int id = ++articleLastId;
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;

		articles.add(new Article(id, regDate, updateDate, title, body));
		return id;
	}
}
