package com.kh.user;

public class UserData {
	
	private String userId;
	private String userPwd;
	private String userNick;
	private String userPhone;
	private String userAddress;
	private String userQuestion;
	private String userAnswer;
	
	public UserData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserData(String userId, String userPwd, String userNick, String userPhone, String userAddress,
			String userQuestion, String answer) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userNick = userNick;
		this.userPhone = userPhone;
		this.userAddress = userAddress;
		this.userQuestion = userQuestion;
		this.userAnswer = answer;
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	
	public String getUserQuestion() {
		return userQuestion;
	}
	public void setUserQuestion(String userQuestion) {
		this.userQuestion = userQuestion;
	}
	public String getUserAnswer() {
		return userAnswer;
	}
	public void setUserAnswer(String answer) {
		userAnswer = answer;
	}
	

	@Override
	public String toString() {
		return "UserData [userId=" + userId + ", userPwd=" + userPwd + ", userNick=" + userNick + ", userPhone="
				+ userPhone + ", userAddress=" + userAddress + ", userQestion=" + userQuestion + ", Answer=" + userAnswer
				+ "]";
	}

}
