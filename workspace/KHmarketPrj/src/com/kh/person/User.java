package com.kh.person;

import java.util.ArrayList;

import com.kh.account.Account;
import com.kh.board.Board;

public class User {
	// 아이디, 비밀번호, 비밀번호 보안등급, 힌트, 계좌, 내가 작성한 글들 
	// 판매내역, 구매내역, 관심목록, 후기, 매너온도, 비밀번호인증키
	
    private String id;
    private String pwd;
    private String pwdGrade;
    private String hint;
    private Account account;
    private ArrayList<Board> boardList; 
    private ArrayList<String> saleList;
    private ArrayList<String> buyList;
    private ArrayList<String> interestList;
    private ArrayList<String> reviewList;
    private double mannerTemperature;
    private boolean authenticated = false;
    
	public User(String id, String pwd, String pwdGrade, String hint, Account account, ArrayList<Board> boardList,
			ArrayList<String> saleList, ArrayList<String> buyList, ArrayList<String> interestList,
			ArrayList<String> reviewList, double mannerTemperature, boolean authenticated) {
		this.id = id;
		this.pwd = pwd;
		this.pwdGrade = pwdGrade;
		this.hint = hint;
		this.account = account;
		this.boardList = boardList;
		this.saleList = saleList;
		this.buyList = buyList;
		this.interestList = interestList;
		this.reviewList = reviewList;
		this.mannerTemperature = mannerTemperature;
		this.authenticated = authenticated;
	}
    
	

}
