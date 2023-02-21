package com.kh.auction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.main.Main;

public class AuctionInput {
	private AuctionSQL aSQL = new AuctionSQL();

	private void printAuction(ResultSet rs) throws Exception {
		while (rs.next()) {
			int item_no = rs.getInt("ITEM_NO");
			int price = rs.getInt("PRICE");
			String remained_time = rs.getString("남은시간");
			System.out.println("상품번호 : " + item_no + " | 경매 시작가 : " + price + " || 남은시간 : " + remained_time);
		}
	}
	
	public void showAuction(Connection conn) throws Exception {
		ResultSet rs = aSQL.showAuction(conn);
		System.out.println("\n[경매중인 상품]");
		printAuction(rs);
	}

	public int[] bid(Connection conn) throws Exception {
		ResultSet rs = aSQL.showDeadlineAuction(conn);
		System.out.println("\n★★★★★★★★ 마감 임박 상품 ★★★★★★★★");
		printAuction(rs);
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		
		System.out.print("\n입찰할 상품 번호 : ");
		int[] arr = new int[3];
		arr[0] = aSQL.findItem(conn);
		arr[1] = aSQL.selectPrice(arr[0], conn);
		arr[2] = aSQL.bid(arr[0], arr[1], conn);
		
		return arr;
	}
	
	public int[] closeBid(Connection conn) throws Exception {
		ResultSet rs = aSQL.showMyAuction(conn);
		
		System.out.println("----- 나의 입찰 목록 -----");
		while(rs.next()) {
			int item_no = rs.getInt("ITEM_NO");
			int price = rs.getInt("PRICE");
			String bid_date = rs.getString("BID_DATE");
			System.out.println("상품번호 : " + item_no + " | 입찰 : " + price + " || 입찰 시간 : " + bid_date);
		}
		System.out.println("-----------------------");

		System.out.print("입찰 취소할 상품 번호 : ");
		int[] arr = new int[3];
		arr[0] = aSQL.findItem(conn);
		arr[1] = aSQL.findPrice(arr[0], conn);
		arr[2] = aSQL.updateBidToD(arr[0], arr[1], conn);
		return arr;
	}
}
