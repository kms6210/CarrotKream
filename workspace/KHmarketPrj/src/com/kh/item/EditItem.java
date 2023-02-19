package com.kh.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kh.main.Main;

public class EditItem {
	
	public void editTitle(Connection conn, int ITEM_NO) throws Exception {
		
		System.out.println("수정할 제목: ");
		String title = Main.SC.nextLine();
		
		String sql = "UPDATE ITEM SET TITLE = ? WHERE ITEM_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, title);
		pstmt.setLong(2, ITEM_NO);
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			System.out.println("수정 성공");
		}
		else {
			System.out.println("수정 실패");
		}
	}
	
	public void editContent(Connection conn, int ITEM_NO) throws Exception {
		
		System.out.println("수정할 내용: ");
		String content = Main.SC.nextLine();
		
		String sql = "UPDATE ITEM SET CONTENT = ? WHERE ITEM_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, content);
		pstmt.setLong(2, ITEM_NO);
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			System.out.println("수정 성공");
		}
		else {
			System.out.println("수정 실패");
		}
	}
	
	public void editPrice(Connection conn, int ITEM_NO) throws SQLException {
		
		System.out.println("수정할 가격: ");
		String price = Main.SC.nextLine();
		
		String sql = "UPDATE ITEM SET PRICE = ? WHERE ITEM_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, price);
		pstmt.setLong(2, ITEM_NO);
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			System.out.println("수정 성공");
		}
		else {
			System.out.println("수정 실패");
		}
	}
	
}
