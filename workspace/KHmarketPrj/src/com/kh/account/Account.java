package com.kh.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kh.main.Main;

// 테이블 : 유저. 계좌 내역 

public class Account {
	public void deposit(int user_no, int target_no, Connection conn) throws Exception { 
		// 충전(유저)
		System.out.print("금액 : ");
		int price = Integer.parseInt(Main.SC.nextLine());
		String sql = "INSERT INTO USER_ACCOUNT(USE_NO, USER_NO, TARGET_NO, PRICE, USE_DATE) VALUES(SEQ_USER_ACCOUNT_NO.NEXTVAL, ?, ?, ?, SYSDATE)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_no);
		pstmt.setInt(2, target_no);
		pstmt.setInt(3, price);
		int result = pstmt.executeUpdate();
		
		if (result == 1) {
			System.out.println("충전 완료");
		} else {
			System.out.println("충전 실패...");
		}
	}
	
	public void withdraw() {
		// 인출 
	}
	
	public void transfer() {
		// 이체 
	}
	
	public void showAccount() {
		// 계좌 내역 출력
	}
}
