package com.sbs.untact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //생성자, getter setter, tostring 자동으로 만들어줌
@NoArgsConstructor
@AllArgsConstructor //모든 생성자
public class Article {
	private int id;
	private String regDate;
	private String updateDate;
	private String title;
	private String body;	
}
