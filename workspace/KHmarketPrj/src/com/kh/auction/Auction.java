package com.kh.auction;

import java.sql.Connection;

import com.kh.account.AccountSQL;
import com.kh.main.Main;

public class Auction {
	private AccountSQL aSQL = new AccountSQL();
	private AuctionInput ai = new AuctionInput();

	public void showAuction(Connection conn) throws Exception {
		// 경매 페이지 출력
		ai.showAuction(conn);
	}

	public void bid(Connection conn) throws Exception {
		// 입찰하기
		ai.showAuction(conn);
		int[] arr = ai.bid(conn);

		if (arr[2] == 1) {
			System.out.println(arr[0] + "번 상품에 " + arr[1] + "원 입찰 완료!");
		} else {
			throw new Exception("입찰 실패...");
		}
	}

	public void closeBid(Connection conn) throws Exception {
		// 입찰 취소 (ex. 대신 수수료 발생)
		int[] arr = ai.closeBid(conn);
		
		if (arr[2] == 1) {
			int price = (int) (arr[1] * 0.1);
			int balance = aSQL.selectBalance(Main.login_member_no, conn);
			int result1 = aSQL.updateBalance(Main.login_member_no, -1 * price, balance, conn);
			int result2 = aSQL.insertAccount(Main.login_member_no, -1, -1 * price, conn);
			if(result1 == 0 || result2 == 0) {
				throw new Exception("입찰 취소 오류...");
			}
			
			System.out.println("\n☆ " + arr[0] + "번 상품에 등록된 입찰이 취소되었습니다. ☆");
			System.out.println("입찰 취소로 인해 " + price + " 포인트가 차감됩니다.");
		} else {
			throw new Exception("입찰 취소 실패...");
		}
	}

}
