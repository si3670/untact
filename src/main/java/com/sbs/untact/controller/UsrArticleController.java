package com.sbs.untact.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.Article;

@Controller
public class UsrArticleController {
	private int articleLastId;
	private List<Article> articles;
	
	public UsrArticleController() {
		articleLastId = 0;
		articles = new ArrayList<>();
		
		articles.add(new Article(++articleLastId, "2020-01-25", "제목1", "내용1"));
		articles.add(new Article(++articleLastId, "2020-01-25", "제목2", "내용2"));	
	}

	@RequestMapping("/usr/article/detail")
	@ResponseBody
	public Article showDetail(int id) {
		return articles.get(id - 1);
	}
	
	@RequestMapping("/usr/article/list")
	@ResponseBody
	public List<Article> showlist() {
		return articles;
	}
	
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Map<String, Object> doAdd(String regDate, String title, String body) {
		articles.add(new Article(++articleLastId, regDate, title, body));
		
		Map<String, Object> rs = new HashMap<>();
		rs.put("resultCode", "P-1");
		rs.put("msg", "성공");
		rs.put("id", articleLastId);
		
		return rs;
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public Map<String, Object> doDelete(int id) {
		boolean deleteArticleRs = deleteArticle(id);
		Map<String, Object> rs = new HashMap<>();
		
		if(deleteArticleRs) {
			rs.put("resultCode", "P-1");
			rs.put("msg", "성공");
		}
		else {
			rs.put("resultCode", "F-1");
			rs.put("msg", "해당 게시물이 존재하지 않습니다.");
		}
		
		rs.put("id", articleLastId);
		
		return rs;
	}

	private boolean deleteArticle(int id) {
		for(Article article : articles) {
			if(article.getId() == id) {
				articles.remove(article);
				return true;
			}
		}
		return false;
	}
	
	
	
}