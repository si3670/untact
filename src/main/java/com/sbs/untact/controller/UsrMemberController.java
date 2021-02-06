package com.sbs.untact.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.Member;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.MemberService;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	// @RequestParam Map<String, Object> param -> title, body 등등 다 들어갈수 있음
	public ResultData doJoin(@RequestParam Map<String, Object> param) {
		if (param.get("loginId") == null) {
			return new ResultData("F-1", "loginId을 입력해주세요.");
		}

		// 아이디 중복 사용 확인
		Member existingMember = memberService.getMemberByLoginId((String) param.get("loginId"));
		if (existingMember != null) {
			return new ResultData("F-2", "이미 사용 중인 아이디입니다.");
		}

		if (param.get("loginPw") == null) {
			return new ResultData("F-1", "loginPw을 입력해주세요.");
		}
		if (param.get("name") == null) {
			return new ResultData("F-1", "name을 입력해주세요.");
		}
		if (param.get("nickname") == null) {
			return new ResultData("F-1", "nickname을 입력해주세요.");
		}
		if (param.get("cellphoneNo") == null) {
			return new ResultData("F-1", "cellphoneNo을 입력해주세요.");
		}
		if (param.get("email") == null) {
			return new ResultData("F-1", "email을 입력해주세요.");
		}

		return memberService.addMember(param);
	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public ResultData doLogin(String loginId, String loginPw, HttpSession session) {
		if (session.getAttribute("loginedMemberId") != null) {
			return new ResultData("F-4", "이미 로그인 되었습니다.");
		}
		if (loginId == null) {
			return new ResultData("F-1", "loginId을 입력해주세요.");
		}
		// 회원 존재 여부
		Member existingMember = memberService.getMemberByLoginId(loginId);
		if (existingMember == null) {
			return new ResultData("F-2", "존재하지 않는 아이디입니다.");
		}

		if (loginPw == null) {
			return new ResultData("F-1", "loginPw을 입력해주세요.");
		}

		if (existingMember.getLoginPw().equals(loginPw) == false) {
			return new ResultData("F-3", "비밀번호를 확인해주세요.");
		}

		session.setAttribute("loginedMemberId", existingMember.getId());

		return new ResultData("S-1", "로그인 성공");
	}

	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public ResultData doLogout(HttpSession session) {
		if (session.getAttribute("loginedMemberId") == null) {
			return new ResultData("S-2", "이미 로그아웃 되었습니다.");
		}
		session.removeAttribute("loginedMemberId");

		return new ResultData("S-1", "로그아웃 성공");

	}

	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public ResultData doModify(@RequestParam Map<String, Object> param, HttpSession session) {
		if (session.getAttribute("loginedMemberId") == null) {
			return new ResultData("F-1", "로그인이 필요한 사항입니다.");
		}
		
		if(param.isEmpty()) {
			return new ResultData("F-2", "수정할 정보를 입력해주세요.");
		}
		
		int loginedMemberId = (int) session.getAttribute("loginedMemberId");
		param.put("id", loginedMemberId);

		return memberService.modifyMember(param);

	}

}
