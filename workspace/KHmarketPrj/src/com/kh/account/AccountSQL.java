package com.kh.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountSQL {
	public int selectBalance(int user_no, Connection conn) throws Exception {
		int balance = 0;
		String sql = "SELECT BALANCE FROM K_USER WHERE USER_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_no);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) { balance = rs.getInt("BALANCE"); }
		return balance;
	}
	
	public int updateBalance(int user_no, int price, int balance, Connection conn) throws Exception {
		String sql = "UPDATE K_USER SET BALANCE = ? WHERE USER_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, price + balance);
		pstmt.setInt(2, user_no);
		return pstmt.executeUpdate();
	}
	
	public int insertAccount(int user_no, int target_no, int price, Connection conn) throws Exception {
		String sql = "INSERT INTO USER_ACCOUNT(USE_NO, USER_NO, TARGET_NO, PRICE) VALUES(SEQ_USER_USE_NO.NEXTVAL, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_no);
		pstmt.setInt(2, target_no);
		pstmt.setInt(3, price);
		return pstmt.executeUpdate();
	}
	
	public ResultSet showAccount(int user_no, Connection conn) throws Exception {
		String sql = "SELECT TARGET_NO, PRICE, USE_DATE FROM USER_ACCOUNT WHERE USER_NO = ? ORDER BY USE_NO";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_no);
		return pstmt.executeQuery();
	}
}
