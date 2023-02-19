package com.kh.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.kh.main.Main;

// 테이블 : 유저. 계좌 내역 

public class Account {
	String sql;
	ResultSet rs;
	
	private int inputPrice() throws Exception {
		System.out.print("금액 : ");
		int price = Integer.parseInt(Main.SC.nextLine());
		return price;
	}

	private ResultSet selectSQL(String sql, int user_no, Connection conn) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_no);
		ResultSet rs = pstmt.executeQuery();
		return rs;
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
		int balance = 0;
		
		sql = "SELECT BALANCE FROM K_USER WHERE USER_NO = ?";
		rs = selectSQL(sql, user_no, conn);
		if (rs.next()) {
			balance = rs.getInt("BALANCE");
		}
		
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
		int balance = 0;
		
		sql = "SELECT BALANCE FROM K_USER WHERE USER_NO = ?";
		rs = selectSQL(sql, user_no, conn);
		if (rs.next()) {
			balance = rs.getInt("BALANCE");
		}
		
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
		int userBalance = 0;
		int targetBalance = 0;
		
		sql = "SELECT BALANCE FROM K_USER WHERE USER_NO = ?";
		rs = selectSQL(sql, user_no, conn);
		if (rs.next()) {
			userBalance = rs.getInt("BALANCE");
		}
		
		rs = selectSQL(sql, target_no, conn);
		if (rs.next()) {
			targetBalance = rs.getInt("BALANCE");
		} else {
			System.out.println("이체할 대상을 찾을 수 없습니다");
			return;
		}

		if(userBalance - price <= 0) {
			System.out.println("잔액이 부족합니다.");
			return;
		}		
		
		sql = "UPDATE K_USER SET BALANCE = ? WHERE USER_NO = ?";
		int result1 = updateSQL(sql, target_no, price, targetBalance, conn);
		
		sql = "UPDATE K_USER SET BALANCE = ? WHERE USER_NO = ?";
		int result2 = updateSQL(sql, user_no,  -1 * price, userBalance, conn);
		
		sql = "INSERT INTO USER_ACCOUNT(USE_NO, USER_NO, TARGET_NO, PRICE) VALUES(SEQ_USER_USE_NO.NEXTVAL, ?, ?, ?)";
		int result3 = insertSQL(sql, user_no, target_no, -1 * price, conn);
		
		if (result1 == 1 && result2 == 1 && result3 == 1) {
			System.out.println("이체 완료!");
		} else {
			System.out.println("이체 실패...");
		}
	}

	public void showAccount(int user_no, Connection conn) throws Exception {
		// 계좌 내역 출력
		sql = "SELECT TARGET_NO, PRICE, USE_DATE FROM USER_ACCOUNT WHERE USER_NO = ? ORDER BY USE_NO";
		rs = selectSQL(sql, user_no, conn);
		
		while (rs.next()) {
			int target_no = rs.getInt("TARGET_NO");
			int price = rs.getInt("PRICE");
			String use_date = rs.getString("USE_DATE");

			System.out.println(target_no + "번 유저에게 " + price + "원 //" + use_date);
		}
	}
}
