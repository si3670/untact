package com.sbs.untact.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact.dao.ArticleDao;
import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.util.Util;

//핵심부서
@Service
public class ArticleService {
	@Autowired //new ArticleDao 연결
	private ArticleDao articleDao;

	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	public List<Article> getArticles(String searchKeywordType, String searchKeyword) {
		return articleDao.getArticles(searchKeywordType, searchKeyword);

	}

	public ResultData addArticle(Map<String, Object> param) {
		articleDao.addArticle(param);
		int id = Util.getAsInt(param.get("id"), 0);
		return new ResultData("P-1", "성공", "id", id);
	}
	
	public ResultData deleteArticle(int id) {
		articleDao.deleteArticle(id);
		
		return new ResultData("P-1", "삭제 성공", "id", id);
	}

	public ResultData modifyArticle(int id, String title, String body) {
		articleDao.modifyArticle(id, title, body);
		
		return new ResultData("P-1", "수정 성공", "id", id);
	}
}
