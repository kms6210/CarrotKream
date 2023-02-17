package com.kh.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.jdbc.JdbcTemplate;
import com.kh.main.Main;

// 테이블 : 상품 유형, 상품
public class Item {
	public void selectItemCategory() {
		// 상품 카테고리 선택 (ex. 가전, 디지털, 잡화 ...)
	}
	
	public void selectItemType() {
		// 상품 유형 선택 (ex. 판매, 구매, ...)
	}
	
	public void registSellItem(Connection conn, int TYPE_NO, int USER_NO) throws Exception {
		// 상품 등록 				
		
		System.out.println("제목: ");
		String itemTitle = Main.SC.nextLine();
		System.out.println("내용: ");
		String itemContent = Main.SC.nextLine();
		System.out.println("가격: ");
		String itemPrice = Main.SC.nextLine();
		
		//SQL
		String sql = "INSERT INTO ITEM(ITEM_NO, TYPE_NO, USER_NO, TITLE,CONTENT,PRICE,WRITE_DATE) VALUES(SEQ_ITEM_NO.NEXTVAL,?,?,?,?,?,SYSDATE)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, TYPE_NO);
		pstmt.setLong(2, USER_NO);
		pstmt.setString(3, itemTitle);
		pstmt.setString(4, itemContent);
		pstmt.setString(5, itemPrice);
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			System.out.println("작성 성공");
		}
		else {
			System.out.println("작성 실패");
		}
		
	}
	
	public void editItem() {
		// 상품 수정
	}

	public void deleteItem() {
		// 상품 삭제
	}
	
	public void findItem(Connection conn) throws Exception {
		// 상품 조회 
			
			//SQL
			String sql = "SELECT TITLE,USER_NO,PRICE,WRITE_DATE FROM ITEM";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {

				String title = rs.getString("TITLE");
				String userNo = rs.getString("USER_NO");
				String price = rs.getString("PRICE");
				String enroll_date = rs.getString("WRITE_DATE");
				
				System.out.print(title);
				System.out.print(" | ");
				System.out.print(userNo);
				System.out.print(" | ");
				System.out.print(price);
				System.out.print(" | ");
				System.out.println(enroll_date);
				
			}	
		
	}
	
	public void findItemAbb() {
		// 줄임말로 아이템 찾기 
	}
}
