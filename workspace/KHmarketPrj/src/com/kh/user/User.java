package com.kh.user;

// 테이블 : 유저, 힌트 질문 유형, 제재 내역, Q&A

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
	
	public void applyjoinRule() {
		// 회원가입 규칙 (ex. 특수문자포함, 8~15자리 ...)
	}
	
	public void showPwdGrade() {
		// 비번 설정시 보안 등급 출력 (ex. 낮음 중간 높음 ...)
	}
	
	public void dropUser() {
		// 회원 탈퇴
	}
	
	public void askQuestion() {
		// 질문하기
	}
}
