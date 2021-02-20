package com.sbs.untact.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.ArticleService;
import com.sbs.untact.util.Util;

//고객응대, 안내데스크
@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/article/detail")
	@ResponseBody
	public Article showDetail(int id) {
		Article article = articleService.getArticle(id);

		return article;
	}

	@RequestMapping("/usr/article/list")
	@ResponseBody
	public List<Article> showlist(String searchKeywordType, String searchKeyword) {
		if (searchKeywordType != null) {
			searchKeywordType = searchKeywordType.trim(); // null아니면 공백 지우기
		}
		if (searchKeywordType == null || searchKeywordType.length() == 0) {
			searchKeywordType = "titleAndBody";
		}

		if (searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}
		if (searchKeyword != null) {
			searchKeyword = searchKeyword.trim();
		}
		return articleService.getArticles(searchKeywordType, searchKeyword);
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	// @RequestParam Map<String, Object> param -> title, body 등등 다 들어갈수 있음
	public ResultData doAdd(@RequestParam Map<String, Object> param, HttpSession session) {
		// 로그인 권한
		int loginedMemberId = Util.getAsInt(session.getAttribute("loginedMemberId"), 0);
		if (loginedMemberId == 0) {
			return new ResultData("F-2", "로그인 후 이용해 주세요.");
		}

		if (param.get("title") == null) {
			return new ResultData("F-1", "title을 입력해주세요.");
		}
		if (param.get("body") == null) {
			return new ResultData("F-1", "body을 입력해주세요.");
		}

		param.put("memberId", loginedMemberId);
		return articleService.addArticle(param);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(Integer id, HttpSession session) {
		// 로그인 권한
		int loginedMemberId = Util.getAsInt(session.getAttribute("loginedMemberId"), 0);
		if (loginedMemberId == 0) {
			return new ResultData("F-1", "로그인 후 이용해주세요");
		}
		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		Article article = articleService.getArticle(id);

		if (article == null) {
			return new ResultData("F-1", "해당 게시물이 존재하지 않습니다.");
		}

		ResultData actorCanDeleteRd = articleService.getActorCanDeleteRd(article, loginedMemberId);
		if (actorCanDeleteRd.isFail()) {
			return actorCanDeleteRd; // 성공, 실패
		}
		return articleService.deleteArticle(id);
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData doModify(Integer id, String title, String body, HttpSession session) {
		// 로그인 권한
		int loginedMemberId = Util.getAsInt(session.getAttribute("loginedMemberId"), 0);
		if (loginedMemberId == 0) {
			return new ResultData("F-1", "로그인 후 이용해주세요");
		}
		if (id == null) {
			return new ResultData("F-1", "id을 입력해주세요.");
		}
		if (title == null) {
			return new ResultData("F-1", "title을 입력해주세요.");
		}
		if (body == null) {
			return new ResultData("F-1", "body을 입력해주세요.");
		}

		Article article = articleService.getArticle(id);

		if (article == null) {
			return new ResultData("F-1", "해당 게시물이 존재하지 않습니다.");
		}

		ResultData actorCanModifyRd = articleService.getActorCanModifyRd(article, loginedMemberId);
		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd; // 성공, 실패
		}

		return articleService.modifyArticle(id, title, body);
	}

}