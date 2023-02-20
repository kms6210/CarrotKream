package com.kh.item;

import java.sql.Connection;

import com.kh.main.Main;

public class ItemService {
	Item item = new Item();
	
	public void itemPage(Connection conn) throws Exception {
		System.out.println("==================");
		System.out.println("게시판 페이지");
		System.out.println("------------------");
		System.out.println("1. 게시글 등록 및 게시\n"
					 	 + "2. 게시글 수정\n"
						 + "3. 게시글 삭제\n"
						 + "4. 게시글 조회\n"
						 + "5. 게시글 상세보기\n"
						 + "9. 카테고리 분류 보기\n");
		
		System.out.print("번호를 입력하세요 : ");
		String input = Main.SC.nextLine();
		System.out.println("");
		
		switch (input) {
		case "1" : { item.registSellItem(conn, Main.login_member_no); break; }
		case "2" : { item.editItem(conn); break; }
		case "3" : { item.deleteItem(conn); break; }
		case "4" : { item.findItem(conn); break; }
		case "5" : { item.findItemAbb(conn); break; }
		case "9" : { item.selectItemCategory(conn); break; }
		default: { System.out.println("잘못된 입력입니다."); } 
		}
	}
	
}
