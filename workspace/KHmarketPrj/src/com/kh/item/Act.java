package com.kh.item;

import java.sql.Connection;

import com.kh.main.Main;

public class Act {

	ItemSearch is = new ItemSearch();
	
	Item item = new Item();
	
	public void productCategory(Connection conn) throws Exception {
		
		item.findItem(conn);
		
	}
	
	public void productDetail(Connection conn) throws Exception {
	
		item.findItemAbb(conn);
		
	}
	
	public void productedit(Connection conn) throws Exception {
		
		System.out.println("이용하실 서비스를 선택해 주세요.");
		System.out.println("1. 상품 등록");
		System.out.println("2. 상품 수정");
		System.out.println("3. 상품 삭제");
		System.out.println("4. 내가 작성한 글 조회");
		System.out.println("5. 거래 완료된 내 글");
		
		System.out.print("입력: ");
		
		String input = Main.SC.nextLine();
		
		switch(input) {
		case "1": item.registSellItem(conn, Main.login_member_no); break;
		case "2": item.editItem(conn, Main.login_member_no); break;
		case "3": item.deleteItem(conn, Main.login_member_no); break;
		case "4": is.myView(conn, Main.login_member_no);  break;
		case "5": is.tradeEnd(conn, Main.login_member_no); break;
			default: throw new Exception("잘못 입력하셨습니다.");
		}
		
		
		
	}
	
}
