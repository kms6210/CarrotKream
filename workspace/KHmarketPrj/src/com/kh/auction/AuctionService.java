package com.kh.auction;

import java.sql.Connection;

import com.kh.main.Main;

public class AuctionService {
	private Auction auction = new Auction();

	public void auctionPage(Connection conn) throws Exception {
		System.out.println("==================");
		System.out.println("경매 페이지");
		System.out.println("------------------");
		System.out.println("1. 경매 페이지 출력\n"
					 	 + "2. 입찰\n"
						 + "3. 입찰 취소(미완성)");
		
		System.out.print("번호를 입력하세요 : ");
		String input = Main.SC.nextLine();
		System.out.println("");
		
		switch (input) {
		case "1" : { auction.showAuction(conn); break; }
		case "2" : { auction.bid(conn); break; }
		case "3" : { auction.closeBid(conn); break; }
		default: { System.out.println("잘못된 입력입니다."); } 
		}
	}
}
