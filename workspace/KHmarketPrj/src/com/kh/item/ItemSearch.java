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
				+ "SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE, TRADE_STATUS\r\n"
				+ "FROM (\r\n"
				+ "SELECT *\r\n"
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
			String price = rs.getString("PRICE");
			String write_date = rs.getString("WRITE_DATE");
			String trade_status = rs.getString("TRADE_STATUS");
			
			System.out.print(itemNo + ". " + title);
			System.out.print("[" + trade_status + "]");
			System.out.print("    가격: " + price);
			System.out.println("    작성일: "+ write_date);
			
		}	
	}
	
	public void rankedByView(Connection conn) throws Exception {
		
		try {
		
			//SQL
			String sql = "SELECT ROWNUM R ,순위 , ITEM_NO, TITLE, USER_NO, PRICE, TRADE_STATUS, \"VIEW\", WRITE_DATE\r\n"
					+ "FROM (\r\n"
					+ "SELECT RANK() OVER(ORDER BY \"VIEW\" DESC) AS 순위 ,\r\n"
					+ "ITEM_NO, TITLE, USER_NO, PRICE, TRADE_STATUS, \"VIEW\", WRITE_DATE\r\n"
					+ "FROM ITEM\r\n"
					+ "WHERE TRADE_STATUS != 'D'\r\n"
					+ ")WHERE ROWNUM < 11";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			//상품 보기
			while(rs.next()) {
				
				String itemNo = rs.getString("ITEM_NO");
				String title = rs.getString("TITLE");
				String trade_status = rs.getString("TRADE_STATUS");
				String price = rs.getString("PRICE");
				String view = rs.getString("VIEW");
				String write_date = rs.getString("WRITE_DATE");
				
				System.out.print(itemNo + ". " + title);
				System.out.print("[" + trade_status + "]");
				System.out.print("    가격: " + price);
				System.out.print("    조회수: "+view);
				System.out.println("    작성일: "+ write_date);
				
			}
				
		} catch (Exception e) {

		System.out.println("※ 오류가 발생했습니다 ※");
		
		} 
	
	}

	public void categoryView(Connection conn) throws Exception {
		
		System.out.print("[상품 유형] 1. 가전, ");
		System.out.print("2. 디지털, ");
		System.out.print("3. 의류, ");
		System.out.print("4. 식품, ");
		System.out.print("5. 전자, ");
		System.out.print("6. 가구, ");
		System.out.print("7. 생필품, ");
		System.out.print("8. 잡화, ");
		System.out.println("9. 기타");
		System.out.print("번호를 입력하세요 : ");
		int input = Main.integerParseInt();
		System.out.println("");
		
		//SQL
		String sql = "SELECT * FROM ITEM WHERE TYPE_NO = ? AND TRADE_STATUS != 'D'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, input);
		ResultSet rs = pstmt.executeQuery();
		
		//상품 보기
		int flag = 0;
		while(rs.next()) {
			flag++;
			String itemNo = rs.getString("ITEM_NO");
			String title = rs.getString("TITLE");
			String price = rs.getString("PRICE");
			String write_date = rs.getString("WRITE_DATE");
			String trade_status = rs.getString("TRADE_STATUS");
			
			System.out.print(itemNo + ". " + title);
			System.out.print("[" + trade_status + "]");
			System.out.print("    가격: " + price);
			System.out.println("    작성일: "+ write_date);
		}
		if(flag == 0) { throw new Exception("※ 선택한 카테고리에 해당되는 상품이 없습니다 ※");}
	}
	
	public void myView(Connection conn, int userNo) throws Exception {
		
		//SQL
		String sql = "SELECT *\r\n"
				+ "FROM(\r\n"
				+ "    SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE, TRADE_STATUS\r\n"
				+ "    FROM (\r\n"
				+ "        SELECT *\r\n"
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
			String price = rs.getString("PRICE");
			String write_date = rs.getString("WRITE_DATE");
			String trade_status = rs.getString("TRADE_STATUS");
			
			System.out.print(itemNo + ". " + title);
			System.out.print("[" + trade_status + "]");
			System.out.print("    가격: " + price);
			System.out.println("    작성일: "+ write_date);
			
		}	
		
	}
	
	public void buyOrSell(Connection conn) throws Exception{
		
		BuyOrSell bos = new BuyOrSell();
		
		System.out.print("1. 구매글 조회 / 2. 판매글 조회 : ");
		String input = Main.SC.nextLine();
		
		switch(input) {
		case "1": bos.buying(conn); break;
		case "2": bos.selling(conn); break;
		default: throw new Exception("※ 잘못된 입력입니다 ※");
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
		System.out.println();
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
