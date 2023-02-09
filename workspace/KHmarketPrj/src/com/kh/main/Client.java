package com.kh.main;

public class Client {
	// 아이디, 패스워드, 판매내역, 구매내역, 관심목록, 후기, 매너온도, 비밀번호인증키
	private int numAccounts;

    private String id;
    private String password;
    private String[] saleHistory;
    private String[] purchaseHistory;
    private String[] interestList;
    private String[] review;
    private int mannerTemperature;
    private boolean authenticated = false;
    
	public Client(String id, String password) {
		this.id = id;
		this.password = password;
	}
	
	public void authenticate() {
		// 회원 인증
		authenticated = false;
	}

	    
	
	
}
