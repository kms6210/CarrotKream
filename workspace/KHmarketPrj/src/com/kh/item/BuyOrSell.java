package com.kh.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuyOrSell {
	
	public void buying(Connection conn) throws Exception {
		
		//SQL
				String sql = "SELECT *\r\n"
						+ "FROM(\r\n"
						+ "SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
						+ "FROM (\r\n"
						+ "SELECT ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
						+ "FROM ITEM\r\n"
						+ "WHERE TRADE_STATUS != 'D'\r\n"
						+ "AND TRADE_STATUS = 'B'\r\n"
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
					
					System.out.print("물건 번호: " + itemNo);
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

	public void selling(Connection conn) throws Exception {
		
		//SQL
				String sql = "SELECT *\r\n"
						+ "FROM(\r\n"
						+ "SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
						+ "FROM (\r\n"
						+ "SELECT ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
						+ "FROM ITEM\r\n"
						+ "WHERE TRADE_STATUS != 'D'\r\n"
						+ "AND TRADE_STATUS = 'S'\r\n"
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
					
					System.out.print("물건 번호: " + itemNo);
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
	
}
