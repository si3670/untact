package com.sbs.untact.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.untact.dto.Article;

//1차원적인 일
@Mapper
public interface ArticleDao {

	public Article getArticle(@Param("id")int id);

	public List<Article> getArticles(@Param("searchKeywordType")String searchKeywordType, @Param("searchKeyword")String searchKeyword);

//select가 아닌건 void
	public void addArticle(Map<String, Object> param);

	public void deleteArticle(@Param("id")int id);

	public void modifyArticle(@Param("id")int id, @Param("title")String title, @Param("body")String body);
}
