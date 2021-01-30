package com.sbs.untact.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.util.Util;

//핵심부서
@Service
public class ArticleService {
	private int articleLastId;
	private List<Article> articles;
	
	public ArticleService() {
		articleLastId = 0;
		articles = new ArrayList<>();

		articles.add(new Article(++articleLastId, "2020-01-25 12:12:12", "2020-01-25 12:12:12", "제목1", "내용1"));
		articles.add(new Article(++articleLastId, "2020-01-25 12:12:12", "2020-01-25 12:12:12", "제목2", "내용2"));
	}

	public Article getArticle(int id) {
		for(Article article : articles) {
			if(article.getId() == id) {
				return article;
			}
		}
		return null;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public ResultData add(String title, String body) {
		int id = ++articleLastId;
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;

		articles.add(new Article(id, regDate, updateDate, title, body));
		return new ResultData("P-1", "성공", "id", id);
	}
	
	public ResultData deleteArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				articles.remove(article);
				return new ResultData("P-1", "삭제 성공", "id", articleLastId);
			}
		}
		return new ResultData("F-1", "해당 게시물이 존재하지 않습니다.");
	}

	public ResultData modify(int id, String title, String body) {
		Article article = getArticle(id);
		
		article.setTitle(title);
		article.setBody(body);
		article.setUpdateDate(Util.getNowDateStr());
		
		return new ResultData("P-1", "수정 성공", "id", id);
	}
}
