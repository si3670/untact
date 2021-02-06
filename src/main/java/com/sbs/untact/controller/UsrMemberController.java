package com.sbs.untact.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.MemberService;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	//@RequestParam Map<String, Object> param -> title, body 등등 다 들어갈수 있음
	public ResultData doJoin(@RequestParam Map<String, Object> param) {
		if(param.get("loginId") == null) {
			return new ResultData("F-1", "loginId을 입력해주세요.");
		}
		if(param.get("loginPw") == null) {
			return new ResultData("F-1", "loginPw을 입력해주세요.");
		}
		if(param.get("name") == null) {
			return new ResultData("F-1", "name을 입력해주세요.");
		}
		if(param.get("nickname") == null) {
			return new ResultData("F-1", "nickname을 입력해주세요.");
		}
		if(param.get("cellphoneNo") == null) {
			return new ResultData("F-1", "cellphoneNo을 입력해주세요.");
		}
		if(param.get("email") == null) {
			return new ResultData("F-1", "email을 입력해주세요.");
		}

		return memberService.addMember(param);
	}
	
	
	
	
}
