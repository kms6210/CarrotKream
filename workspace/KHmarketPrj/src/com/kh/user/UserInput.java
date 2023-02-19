package com.kh.user;

import com.kh.main.Main;

public class UserInput {
	
	public boolean tf = false;
	private String qusCheck = "[0-9]+";
	private String pwdCheck = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$"; // 영문, 숫자, 특수문자 , 8~15
	private String userPwd = null;
	private String userQuestion = null;
	
	// 회원 가입 정보입력
	public UserData UserJoinInput() {
		
		System.out.print("아이디 : ");
		String userId = Main.SC.nextLine();
		
		do {
			tf = false;
			while(!tf) {
				System.out.print("비밀번호 : ");
				userPwd = Main.SC.nextLine();
				tf = applyjoinRule(userPwd);
			}
		} while(false);	
								
		System.out.print("닉네임 : ");
		String userNick = Main.SC.nextLine();
		System.out.print("전화번호 : ");
		String userPhone = Main.SC.nextLine();
		System.out.print("주소 : ");
		String userAddress = Main.SC.nextLine();
				
		do {
			tf = false;
			while(!tf) {
				System.out.print("힌트 질문 번호 : ");
				userQuestion = Main.SC.nextLine();
				tf = questionCheck(userQuestion);
			}
		} while(false);	
		
		System.out.print("힌트 답변 : ");
		String userAnswer = Main.SC.nextLine();	
		
		UserData data = new UserData();
		data.setUserId(userId);
		data.setUserPwd(userPwd);
		data.setUserNick(userNick);
		data.setUserPhone(userPhone);
		data.setUserAddress(userAddress);
		data.setUserQuestion(userQuestion);
		data.setUserAnswer(userAnswer);
		
		return data;
		
	}
	
	// 비밀번호 유효성 체크 (ex. 특수문자포함, 8~15자리 ...)
	public boolean applyjoinRule(String userPwd) {
		if (userPwd.matches(pwdCheck)) {
			return true;
		} else {
			System.out.println("8~15자 영문, 숫자, 특수문자를 포함해야합니다\n");
			return false;
		}
	}

	// 힌트 번호 유효성 체크
	private boolean questionCheck(String userQuestion)	{
		if (userQuestion.matches(qusCheck)) {
			return true;
		} else {
			System.out.println("숫자만 입력하세요\n");
			return false;
		}
	}
	
	// 비번 설정시 보안 등급 출력 (ex. 낮음 중간 높음 ...)
	public void showPwdGrade() {
		
	}
	
	//로그인 정보 입력
	public UserData UserLoginInput() {
		
		System.out.print("아이디 : ");
		String userId = Main.SC.nextLine();
		System.out.print("비밀번호 : ");
		String userPwd = Main.SC.nextLine();
		
		UserData data = new UserData();
		data.setUserId(userId);
		data.setUserPwd(userPwd);
		return data;
	}
	
	
	// 아이디 찾기 입력 받기
	public UserData findUserIdInput(String phoneNo, String question) {
		System.out.println("질문 : "+question);
		System.out.print("답변 : ");
		String joinAnswer = Main.SC.nextLine();
		
		UserData data = new UserData();
		
		data.setUserPhone(phoneNo);
		data.setUserAnswer(joinAnswer);
		return data;
	}
	
	
	//전화번호 입력 받기
	public String phoneNoInput() {
		System.out.print("가입시 입력한 전화번호 : ");
		String joinPhoneNo = Main.SC.nextLine();
		
		// 전화번호 길이 맞추기
		if(joinPhoneNo.length() < 20) {
			joinPhoneNo = String.format("%-20s" , joinPhoneNo);
		}
		
		return joinPhoneNo;
	}
	
}
