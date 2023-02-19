package com.kh.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.main.Main;

public class ItemSearch {
	
	public void itemView(Connection conn) throws Exception {
		
		//SQL
		String sql = "SELECT *\r\n"
				+ "FROM(\r\n"
				+ "    SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
				+ "    FROM (\r\n"
				+ "        SELECT ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
				+ "        FROM ITEM\r\n"
				+ "        ORDER BY ITEM_NO DESC\r\n"
				+ "        )\r\n"
				+ "    )\r\n";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		//상품 보기
		while(rs.next()) {
			
			String itemNo = rs.getString("ITEM_NO");
			String title = rs.getString("TITLE");
			String userNo = rs.getString("USER_NO");
			String price = rs.getString("PRICE");
			String write_date = rs.getString("WRITE_DATE");
			
			System.out.print(itemNo);
			System.out.print(" | ");
			System.out.print(title);
			System.out.print(" | ");
			System.out.print(userNo);
			System.out.print(" | ");
			System.out.print(price);
			System.out.print(" | ");
			System.out.println(write_date);
			
		}	
	}
	
	public void rankedByView(Connection conn) throws Exception {

		//SQL
		String sql = "SELECT RANK() OVER(ORDER BY \"VIEW\" DESC) AS 순위 ,\r\n"
				+ "ITEM_NO, TITLE, USER_NO, PRICE, \"VIEW\", WRITE_DATE\r\n"
				+ "FROM ITEM";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		//상품 보기
		while(rs.next()) {
			
			String itemNo = rs.getString("ITEM_NO");
			String title = rs.getString("TITLE");
			String userNo = rs.getString("USER_NO");
			String price = rs.getString("PRICE");
			String write_date = rs.getString("WRITE_DATE");
			
			System.out.print(itemNo);
			System.out.print(" | ");
			System.out.print(title);
			System.out.print(" | ");
			System.out.print(userNo);
			System.out.print(" | ");
			System.out.print(price);
			System.out.print(" | ");
			System.out.println(write_date);
		
		}
	
	}

	public void categoryView(Connection conn) throws Exception {
		
		System.out.println("조회하실 물건의 카테고리를 입력하시오.");
		System.out.println("101. 가전");
		System.out.println("102. 디지털");
		System.out.println("103. 의류");
		System.out.println("104. 식품");
		System.out.println("105. 피시 모바일");
		System.out.println("106. 가구");
		System.out.println("107. 생필품");
		System.out.println("108. 잡화");
		System.out.println("109. 기타");	
		System.out.println("카테고리: ");
		String input = Main.SC.nextLine();
		
		//SQL
		String sql = "SELECT * FROM ITEM WHERE TYPE_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, input);
		ResultSet rs = pstmt.executeQuery();
		
		//상품 보기
		while(rs.next()) {
			
			String itemNo = rs.getString("ITEM_NO");
			String title = rs.getString("TITLE");
			String userNo = rs.getString("USER_NO");
			String price = rs.getString("PRICE");
			String write_date = rs.getString("WRITE_DATE");
			
			System.out.print(itemNo);
			System.out.print(" | ");
			System.out.print(title);
			System.out.print(" | ");
			System.out.print(userNo);
			System.out.print(" | ");
			System.out.print(price);
			System.out.print(" | ");
			System.out.println(write_date);
		
		}
			
	}
	
}
