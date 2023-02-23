package com.kh.item;

import java.sql.Connection;

import com.kh.account.Account;
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
		System.out.println("\n==================");
        System.out.println("★ 내 상품 관리 페이지 ★");
        new Account().seeBalance(conn);
        System.out.println("\n1.상품 등록\n2.상품 수정\n3.상품 삭제\n4.내 상품 조회\n5.거래 완료된 상품");
        System.out.println("==================");
		
		System.out.print("번호를 입력하세요 : ");
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
