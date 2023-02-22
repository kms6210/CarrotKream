package com.kh.item;

import java.sql.Connection;
import java.sql.SQLException;

import com.kh.main.Main;

public class ItemService {
	Item item = new Item();
	
	public void itemPage(Connection conn) throws Exception {
		
		boolean keep = true;
		
		while(keep) {

			System.out.println("==================");
			System.out.println("상품 페이지");
			System.out.println("------------------");
			System.out.println("1. 상품 등록 및 게시\n"
					+ "2. 상품 수정\n"
					+ "3. 상품 삭제\n"
					+ "4. 상품 조회\n"
					+ "5. 상품 상세보기\n"
					+ "99. 메인 메뉴로 나가기\n");
			
			System.out.print("번호를 입력하세요 : ");
			String input = Main.SC.nextLine();
			System.out.println("");
			
			switch (input) {
			case "1" : 
				try {
					item.registSellItem(conn, Main.login_member_no);
				} catch (Exception e1) {
					System.out.println("잘못된 입력값입니다.");
				} break;
			case "2" : 
				try {
					item.editItem(conn, Main.login_member_no);
				} catch (Exception e) {
					System.out.println("잘못 입력하셨습니다");
				} break;
			case "3" : 
				try {
					item.deleteItem(conn, Main.login_member_no);
				} catch (Exception e) {
					System.out.println("입력값을 다시 확인해부십시오.");
				} break;
			case "4" :
				try {
					item.findItem(conn);
				} catch (Exception e) {
					System.out.println("잘못된 입력값입니다.");
				} break;
			case "5" :
				try {
					item.findItemAbb(conn);
				} catch (Exception e) {
					System.out.println("글 번호를 다시 한번 확인해 주시기 바랍니다.");
				} break;
			case "99" : System.out.println("메인 화면으로 돌아갑니다.");
			keep = false;	break;
			default:
				System.out.println("잘못 입력하셨습니다….");
			}
			
		}
		
	}
	
}
