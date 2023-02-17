package com.kh.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.main.Main;

// 테이블 : 유저. 계좌 내역 

public class Account {
	public void deposit(int user_no, Connection conn) throws Exception { 
		// 충전(유저) : 자기 자신에게 입금 
		System.out.print("금액 : ");
		int price = Integer.parseInt(Main.SC.nextLine());
		
		String sql1 = "INSERT INTO USER_ACCOUNT(USE_NO, USER_NO, TARGET_NO, PRICE) VALUES(SEQ_USER_USE_NO.NEXTVAL, ?, ?, ?)";
		PreparedStatement pstmt1 = conn.prepareStatement(sql1);
		pstmt1.setInt(1, user_no);
		pstmt1.setInt(2, user_no);
		pstmt1.setInt(3, price);
		int result1 = pstmt1.executeUpdate();
		
		String sql2 = "SELECT BALANCE FROM K_USER WHERE USER_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql2);
		pstmt.setInt(1, user_no);
		ResultSet rs = pstmt.executeQuery();
		
		int balance = 0;
		if(rs.next()) {
			balance = rs.getInt("BALANCE");
		}
		
		String sql3 = "UPDATE K_USER SET BALANCE = ? WHERE USER_NO = ?";
		PreparedStatement pstmt3 = conn.prepareStatement(sql3);
		pstmt3.setInt(1, price + balance);
		pstmt3.setInt(2, user_no);
		int result3 = pstmt3.executeUpdate();
		
		if (result1 == 1 && result3 == 1) {
			System.out.println(price + "원 충전 완료!");
		} else {
			System.out.println(result1);
			System.out.println(result3);
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
