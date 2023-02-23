package com.kh.auction;

import java.sql.Connection;

import com.kh.account.Account;
import com.kh.main.Main;

public class AuctionService {

	public void auctionPage(Connection conn) throws Exception {
		Auction auction = new Auction();
		boolean isFinish = false;

		while(!isFinish) {
			System.out.println("\n==================");
	        System.out.println("★ 경매 페이지 ★");
	        new Account().seeBalance(conn);
	        System.out.println("\n1. 경매 품목 조회\n2. 물품 입찰\n3. 입찰 취소");
	        System.out.println("==================");
			System.out.print("번호를 입력하세요 : ");
			String input = Main.SC.nextLine();
			
			try {
				switch (input) {
					case "1" : { auction.showAuction(conn); break; }
					case "2" : { auction.bid(conn); break; }
					case "3" : { auction.closeBid(conn); break; }
					case "99" : { isFinish = true; break; }
					default: { throw new Exception("※ 잘못된 입력입니다 ※"); } 
				}
			} catch(Exception e) {
				System.out.println("\n" + e.getMessage() + "\n");
			}
		}
	}
}
