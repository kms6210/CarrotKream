package com.kh.user;

// 테이블 : 유저 테이블, 비밀번호 찾기 힌트, 비밀번호 찾기 질문  

public class User {
	public void join() {
		// 회원가입
	}
	
	public void login() {
		// 로그인
	}
	
	public void findId() {
		// 아이디 찾기(힌트)
	}
	
	public void findPwd() {
		// 비밀번호 찾기(아이디 & 힌트)
	}
	
	public void joinRule() {
		// 회원가입시 규칙 (ex. 특수문자포함, 8~15자리 ...)
	}
	
	public void judgePwdGrade() {
		// 회원가입시 비밀번호 보안 등급 판단 (ex. 낮음 중간 높음 ...)
	}
	
	public void showHint() {
		// 아이디, 비밀번호 힌트 질문 보여주기
	}
	
	public void removeUser() {
		// 회원 탈퇴
	}
}
