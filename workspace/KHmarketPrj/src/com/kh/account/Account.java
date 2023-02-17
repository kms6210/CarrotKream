package com.kh.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.main.Main;

// 테이블 : 유저. 계좌 내역 

public class Account {
	private int inputPrice() {
		System.out.print("금액 : ");
		int price = Integer.parseInt(Main.SC.nextLine());
		return price;
	}

	private int selectSQL(String sql, int user_no, Connection conn) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_no);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("BALANCE");
		} else {
			return 0;
		}
	}

	private int updateSQL(String sql, int user_no, int price, int balance, Connection conn) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, price + balance);
		pstmt.setInt(2, user_no);
		int result = pstmt.executeUpdate();
		return result;
	}
	
	private int insertSQL(String sql, int user_no, int target_no, int price, Connection conn) throws Exception {
		PreparedStatement pstmt1 = conn.prepareStatement(sql);
		pstmt1.setInt(1, user_no);
		pstmt1.setInt(2, target_no);
		pstmt1.setInt(3, price);
		int result = pstmt1.executeUpdate();
		return result;
	}

	public void deposit(int user_no, Connection conn) throws Exception { 
		// 포인트 충전
		int price = inputPrice();
		String sql = "";
		
		sql = "SELECT BALANCE FROM K_USER WHERE USER_NO = ?";
		int balance = selectSQL(sql, user_no, conn);
		sql = "UPDATE K_USER SET BALANCE = ? WHERE USER_NO = ?";
		int result1 = updateSQL(sql, user_no, price, balance, conn);
		sql = "INSERT INTO USER_ACCOUNT(USE_NO, USER_NO, TARGET_NO, PRICE) VALUES(SEQ_USER_USE_NO.NEXTVAL, ?, ?, ?)";
		int result2 = insertSQL(sql, user_no, user_no, price, conn);
		
		if (result1 == 1 && result2 == 1) {
			System.out.println(price + "원 충전 완료!");
		} else {
			System.out.println("충전 실패...");
		}
	}

	public void withdraw(int user_no, Connection conn) throws Exception { 
		// 포인트 인출
		int price = inputPrice();
		String sql = "";
		
		sql = "SELECT BALANCE FROM K_USER WHERE USER_NO = ?";
		int balance = selectSQL(sql, user_no, conn);
		sql = "UPDATE K_USER SET BALANCE = ? WHERE USER_NO = ?";
		int result1 = updateSQL(sql, user_no, -1 * price, balance, conn);
		sql = "INSERT INTO USER_ACCOUNT(USE_NO, USER_NO, TARGET_NO, PRICE) VALUES(SEQ_USER_USE_NO.NEXTVAL, ?, ?, ?)";
		int result2 = insertSQL(sql, user_no, user_no, -1 * price, conn);
		
		if (result1 == 1 && result2 == 1) {
			System.out.println(price + "원 인출 완료!");
		} else {
			System.out.println("충전 실패...");
		}
	}

	public void transfer(int user_no, int target_no, Connection conn) throws Exception {
		// 이체
		int price = inputPrice();
		String sql = "";
		
		sql = "SELECT BALANCE FROM K_USER WHERE USER_NO = ?";
		int balance1 = selectSQL(sql, user_no, conn);
		if(balance1 - price <= 0) {
			System.out.println("잔액이 부족합니다.");
			return;
		}
		
		sql = "SELECT BALANCE FROM K_USER WHERE USER_NO = ?";
		int balance2 = selectSQL(sql, target_no, conn);
		sql = "UPDATE K_USER SET BALANCE = ? WHERE USER_NO = ?";
		int result1 = updateSQL(sql, user_no, -1 * price, balance1, conn);
		sql = "UPDATE K_USER SET BALANCE = ? WHERE USER_NO = ?";
		int result2 = updateSQL(sql, target_no, price, balance2, conn);
		sql = "INSERT INTO USER_ACCOUNT(USE_NO, USER_NO, TARGET_NO, PRICE) VALUES(SEQ_USER_USE_NO.NEXTVAL, ?, ?, ?)";
		int result3 = insertSQL(sql, user_no, target_no, -1 * price, conn);
		
		if (result1 == 1 && result2 == 1 && result3 == 1) {
			System.out.println("이체 완료!");
		} else {
			System.out.println("이체 실패...");
		}
	}

	public void showAccount() {
		// 계좌 내역 출력
		
	}
}
