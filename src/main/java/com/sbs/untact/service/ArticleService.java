package com.sbs.untact.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact.dao.ArticleDao;
import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.util.Util;

//핵심부서
@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;

	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	public List<Article> getArticles(String searchKeywordType, String searchKeyword) {
		return articleDao.getArticles(searchKeywordType, searchKeyword);

	}

	public ResultData addArticle(String title, String body) {
		return articleDao.getAddArticle(title, body);
				
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
