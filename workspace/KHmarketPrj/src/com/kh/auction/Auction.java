package com.kh.auction;

import java.sql.Connection;

import com.kh.main.Main;

public class Auction {
	private AuctionInput ai = new AuctionInput();

	public void showAuction(Connection conn) throws Exception {
		// 경매 페이지 출력
		ai.showAuction(conn);
	}

	public void bid(Connection conn) throws Exception {
		// 입찰하기
		int[] arr = ai.bid(conn);

		if (arr[2] == 1) {
			System.out.println(arr[0] + "번 상품에 " + arr[1] + "원 입찰 완료!");
		} else {
			System.out.println("입찰 실패...");
		}
	}

	public void closeBid(Connection conn) throws Exception {
		// 입찰 취소 (ex. 대신 수수료 발생)
		int[] arr = ai.closeBid(conn);

		if (arr[2] == 1) {
			System.out.println("\n" + arr[0] + "번 상품에 등록된 입찰이 취소되었습니다.");
			System.out.println("입찰 취소로 인해 " + (int)(arr[1] * 0.1) + " 포인트가 차감됩니다.");
			// 계좌 포인트 변동 구현해야함
		} else {
			System.out.println("입찰 취소 실패...");
		}
	}

}
