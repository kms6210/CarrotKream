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
				+ "SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
				+ "FROM (\r\n"
				+ "SELECT ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
				+ "FROM ITEM\r\n"
				+ "WHERE TRADE_STATUS != 'D'\r\n"
				+ "ORDER BY ITEM_NO DESC\r\n"
				+ ")\r\n"
				+ ")";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		//상품 보기
		while(rs.next()) {
			
			String itemNo = rs.getString("ITEM_NO");
			String title = rs.getString("TITLE");
			String userNo = rs.getString("USER_NO");
			String price = rs.getString("PRICE");
			String write_date = rs.getString("WRITE_DATE");
			
			System.out.print("상품 번호: " + itemNo);
			System.out.print(" | ");
			System.out.print("제목: "+ title);
			System.out.print(" | ");
			System.out.print("유저 번호: "+ userNo);
			System.out.print(" | ");
			System.out.print("가격: " + price);
			System.out.print(" | ");
			System.out.println("작성일: "+ write_date);
			
		}	
	}
	
	public void rankedByView(Connection conn) throws Exception {

		//SQL
		String sql = "SELECT RANK() OVER(ORDER BY \"VIEW\" DESC) AS 순위 ,\r\n"
				+ "ITEM_NO, TITLE, USER_NO, PRICE, \"VIEW\", WRITE_DATE\r\n"
				+ "FROM ITEM\r\n"
				+ "WHERE TRADE_STATUS != 'D'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		//상품 보기
		while(rs.next()) {
			
			String itemNo = rs.getString("ITEM_NO");
			String title = rs.getString("TITLE");
			String userNo = rs.getString("USER_NO");
			String price = rs.getString("PRICE");
			String view = rs.getString("VIEW");
			String write_date = rs.getString("WRITE_DATE");
			
			System.out.print("상품 번호: "+itemNo);
			System.out.print(" | ");
			System.out.print("제목: "+title);
			System.out.print(" | ");
			System.out.print("유저 번호: "+userNo);
			System.out.print(" | ");
			System.out.print("가격: "+price);
			System.out.print(" | ");
			System.out.print("조회수: "+view);
			System.out.print(" | ");
			System.out.println("작성일"+write_date);
		
		}
	
	}

	public void categoryView(Connection conn) throws Exception {
		
		System.out.println("조회하실 상품의 카테고리를 입력하시오.");
		System.out.println("1. 가전");
		System.out.println("2. 디지털");
		System.out.println("3. 의류");
		System.out.println("4. 식품");
		System.out.println("5. 피시 모바일");
		System.out.println("6. 가구");
		System.out.println("7. 생필품");
		System.out.println("8. 잡화");
		System.out.println("9. 기타");	
		System.out.println("카테고리: ");
		String input = Main.SC.nextLine();
		
		//SQL
		String sql = "SELECT * FROM ITEM WHERE TYPE_NO = ? AND TRADE_STATUS != 'D'";
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
			
			System.out.print("상품 번호: "+itemNo);
			System.out.print(" | ");
			System.out.print("제목: "+title);
			System.out.print(" | ");
			System.out.print("유저 번호: "+userNo);
			System.out.print(" | ");
			System.out.print("가격: "+price);
			System.out.print(" | ");
			System.out.println("작성일: "+write_date);
		
		}
			
	}
	
	public void myView(Connection conn, int userNo) throws Exception {
		
		//SQL
		String sql = "SELECT *\r\n"
				+ "FROM(\r\n"
				+ "    SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
				+ "    FROM (\r\n"
				+ "        SELECT ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
				+ "        FROM ITEM\r\n"
				+ "        WHERE USER_NO = ? AND TRADE_STATUS != 'D'"
				+ "        ORDER BY ITEM_NO DESC\r\n"
				+ "        )\r\n"
				+ "    )\r\n";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, userNo);
		ResultSet rs = pstmt.executeQuery();
		
		//상품 보기
		while(rs.next()) {
			
			String itemNo = rs.getString("ITEM_NO");
			String title = rs.getString("TITLE");
			String user_no = rs.getString("USER_NO");
			String price = rs.getString("PRICE");
			String write_date = rs.getString("WRITE_DATE");
			
			System.out.print("상품 번호: "+itemNo);
			System.out.print(" | ");
			System.out.print("제목: "+title);
			System.out.print(" | ");
			System.out.print("유저 번호: "+user_no);
			System.out.print(" | ");
			System.out.print("가격: "+price);
			System.out.print(" | ");
			System.out.println("작성일: "+write_date);
			
		}	
		
	}
	
	public void buyOrSell(Connection conn) throws Exception{
		
		BuyOrSell bos = new BuyOrSell();
		
		System.out.println("조회하실 글의 유형을 선택해주십시오.");
		System.out.println("1. 구매글");
		System.out.println("2. 판매글");
		
		String input = Main.SC.nextLine();
		
		switch(input) {
		case "1": bos.buying(conn); break;
		case "2": bos.selling(conn); break;
		default: System.out.println("잘못 입력하셨습니다."); break;
		}
		
	}
	
	public void tradeEnd(Connection conn, int userNo) throws Exception {
		
		//SQL
		String sql = "SELECT *\r\n"
				+ "FROM(\r\n"
				+ "    SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
				+ "    FROM (\r\n"
				+ "        SELECT ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
				+ "        FROM ITEM\r\n"
				+ "        WHERE USER_NO = ? AND TRADE_STATUS != 'D'"
				+ "		   AND TRADE_STATUS = 'Y'"
				+ "        ORDER BY ITEM_NO DESC\r\n"
				+ "        )\r\n"
				+ "    )\r\n";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, userNo);
		ResultSet rs = pstmt.executeQuery();
		
		//상품 보기
		while(rs.next()) {
			
			String itemNo = rs.getString("ITEM_NO");
			String title = rs.getString("TITLE");
			String user_no = rs.getString("USER_NO");
			String price = rs.getString("PRICE");
			String write_date = rs.getString("WRITE_DATE");
			
			System.out.print("상품 번호: "+itemNo);
			System.out.print(" | ");
			System.out.print("제목: "+title);
			System.out.print(" | ");
			System.out.print("유저 번호: "+user_no);
			System.out.print(" | ");
			System.out.print("가격: "+price);
			System.out.print(" | ");
			System.out.println("작성일: "+write_date);
		
		}
	}
	
}
