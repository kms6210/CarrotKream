package com.kh.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.account.Account;
import com.kh.main.Main;

public class ItemService {
	Item item = new Item();
	ItemSearch is = new ItemSearch();
	Act act = new Act();
	
	public void itemPage(Connection conn) throws Exception {
		
		boolean keep = true;
		
		while(keep) {
//			System.out.println("[금일 추천 상품]");
//			String sql = "SELECT *\r\n"
//					+ "FROM(\r\n"
//					+ "SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
//					+ "FROM (\r\n"
//					+ "SELECT ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
//					+ "FROM ITEM\r\n"
//					+ "WHERE TRADE_STATUS != 'D'\r\n"
//					+ "ORDER BY ITEM_NO DESC\r\n"
//					+ ")\r\n"
//					+ ")WHERE R <= 5";
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			ResultSet rs = pstmt.executeQuery();
//			
//			//상품 보기
//			while(rs.next()) {
//				
//				String itemNo = rs.getString("ITEM_NO");
//				String title = rs.getString("TITLE");
//				String userNo = rs.getString("USER_NO");
//				String price = rs.getString("PRICE");
//				String write_date = rs.getString("WRITE_DATE");
//				
//				System.out.print(itemNo + ". ");
//				System.out.print(title);
//				System.out.print("    ");
//				System.out.println("가격: " + price);
//			
//			}	
			System.out.println("\n==================");
			System.out.println("★ 상품 페이지 ★");
			new Account().seeBalance(conn);
			System.out.println("\n1.상품 조회 페이지\n2.내 상품 관리 페이지");
			System.out.println("==================");
			System.out.print("번호를 입력하세요 : ");
			String input = Main.SC.nextLine();
			System.out.println("");
			
			switch (input) {
			case "1" : 
				try {
					act.productCategory(conn);
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage() + "\n");
				} break;
			case "2" : 
				try {
					act.productedit(conn);
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage() + "\n");
				} break;
			case "99" : keep = false;	break;
			default:
				System.out.println("\n※ 잘못된 입력입니다 ※\n");
			}
			
		}
		
	}
	
}
