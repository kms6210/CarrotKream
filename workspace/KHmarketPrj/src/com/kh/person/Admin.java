package com.kh.person;

import com.kh.account.Account;

public class Admin {
	// 아이디, 패스워드, 세션키, 계좌
	private String id;
    private String pwd;
    private String sessionKey;
    private Account account;
    
	public Admin(String id, String pwd, String sessionKey, Account account) {
		this.id = id;
		this.pwd = pwd;
		this.sessionKey = sessionKey;
		this.account = account;
	}
    
    
}

