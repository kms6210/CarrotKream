package com.kh.auction;

import java.sql.Connection;

import com.kh.main.Main;

public class Auction {
	int user_no = Main.login_member_no;
	private AuctionSQL aSQL = new AuctionSQL();
	
	public void showAuction(Connection conn) throws Exception {
		// 경매 페이지 출력
		aSQL.showAuction(conn);
	}

	public void bid(Connection conn) throws Exception {
		// 입찰하기
		aSQL.bid(user_no, conn);
	}
	
	public void closeBid(Connection conn) {
		// 입찰 취소 (ex. 대신 수수료 발생)
	}

	
}
