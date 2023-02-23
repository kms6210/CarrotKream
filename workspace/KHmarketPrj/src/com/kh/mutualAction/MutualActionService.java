package com.kh.mutualAction;

import java.sql.Connection;

import com.kh.main.Main;

public class MutualActionService {
	
	MutualAction ma=new MutualAction();
	
	public void mutualActionPage(Connection conn) throws Exception {
		
		System.out.println("=============");
		System.out.println("   마이페이지   ");
		System.out.println("=============");
		System.out.println("1. 좋아요 등록하기 ");
		System.out.println("2. 좋아요 삭제하기 ");
		System.out.println("3. 좋아요 조회하기 ");
		System.out.println("4. 상품 판매 ");
		System.out.println("5. 상품 구매 ");
		System.out.println("6. 구매 후기 작성 ");
		System.out.println("7. 판매 후기 작성 ");
		System.out.println("8. 채팅방 개설 ");
		System.out.println("9. 유저번호로 채팅방 조회후 채팅하기 ");
		System.out.println("10. 상품번호로 채팅방 조회후 채팅하기 ");
		System.out.print("실행할 서비스 번호를 입력하세요 : ");
		
		String input = Main.SC.nextLine();
		
		switch(input) {
		
//		case "1" : ma.setLikeList(conn); break;
//		case "2" : ma.deleteLikeList(conn); break;
		case "3" : ma.selectLikeList(conn); break;
//		case "4" : ma.sellItem(conn); break;
//		case "5" : ma.buyItem(conn); break;
		case "6" : ma.buyReview(conn);	break;
		case "7" : ma.sellReview(conn); break;
		case "8" : ma.makeChatRoom(conn); break;
		case "9" : ma.SearchChatRoomByUserNumber(conn); break;
		case "10" : ma.SearchChatRoomByItemNumber(conn); break;
		
		}
		
	}//method
	
	
}//class
