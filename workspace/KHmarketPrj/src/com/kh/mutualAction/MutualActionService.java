package com.kh.mutualAction;

import java.sql.Connection;

import com.kh.main.Main;

public class MutualActionService {
	
	MutualAction ma=new MutualAction();
	
	public void mutualActionPage(Connection conn) throws Exception {
		
		System.out.println("=============");
		System.out.println("마이페이지");
		System.out.println("=============");
		System.out.println("1. 좋아요 등록하기 ");
		System.out.println("2. 좋아요 삭제하기 ");
		System.out.println("3. 좋아요 조회하기 ");
		
		String input = Main.SC.nextLine();
		
		switch(input) {
		
		case "1" : ma.setLikeList(conn); break;
		case "2" : ma.deleteLikeList(conn); break;
		case "3" : ma.selectLikeList(conn); break;
		case "4" : ma.writeReview(conn);
		
		}
		
	}//method
	
	
}//class
