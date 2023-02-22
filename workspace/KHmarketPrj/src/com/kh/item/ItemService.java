package com.kh.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.main.Main;

public class ItemService {
	Item item = new Item();
	ItemSearch is = new ItemSearch();
	Act act = new Act();
	
	public void itemPage(Connection conn) throws Exception {
		
		boolean keep = true;
		
		while(keep) {
			
			System.out.println("");
			System.out.println("==================");
			System.out.println("상품 페이지");
			System.out.println("------------------");
			System.out.println("");
			
			String sql = "SELECT *\r\n"
					+ "FROM(\r\n"
					+ "SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
					+ "FROM (\r\n"
					+ "SELECT ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
					+ "FROM ITEM\r\n"
					+ "WHERE TRADE_STATUS != 'D'\r\n"
					+ "ORDER BY ITEM_NO DESC\r\n"
					+ ")\r\n"
					+ ")WHERE R <= 5";
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
				System.out.println("가격: " + price);
			
			}	
			
			System.out.println("==========================");
			System.out.print("1. 상품 분류로 보기\n"
					+ "2. 상품 상세보기\n"
					+ "3. 상품 등록, 관리\n");
			System.out.println("==========================");
			
			System.out.print("번호를 입력하세요 : ");
			String input = Main.SC.nextLine();
			System.out.println("");
			
			switch (input) {
			case "1" : 
				try {
					act.productCategory(conn);
				} catch (Exception e1) {
					System.out.println("잘못된 입력값입니다.");
				} break;
			case "2" : 
				try {
					act.productDetail(conn);
				} catch (Exception e) {
					System.out.println("잘못 입력하셨습니다");
				} break;
			case "3" : 
				try {
					act.productedit(conn);
				} catch (Exception e) {
					System.out.println("입력값을 다시 확인해부십시오.");
				} break;
			case "99" : System.out.println("메인 화면으로 돌아갑니다.");
			keep = false;	break;
			default:
				System.out.println("잘못 입력하셨습니다….");
			}
			
		}
		
	}
	
}
