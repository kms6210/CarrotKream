package com.kh.auction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.main.Main;

public class AuctionInput {
	private AuctionSQL aSQL = new AuctionSQL();

	private void printAuction(ResultSet rs, Connection conn) throws Exception {
		int flag = 0;
		while (rs.next()) {
			flag++;
			int item_no = rs.getInt("ITEM_NO");
			int price = rs.getInt("PRICE");
			String title = rs.getString("TITLE");
			String remained_time = rs.getString("남은시간");
			String[] sArr = remained_time.split(" ");
			String[] timeArr = sArr[1].split(":");
			String day = sArr[0];
			int maxPrice = aSQL.getMaxPrice(item_no, conn);
			System.out.println(item_no + ". " + title + "    경매 시작가 : " + price +  "    현재 최고가 : " + maxPrice + "    남은시간 : " + day + "일 " + timeArr[0] + "시간 " + timeArr[1] + "분 ");
		}
		if(flag == 0) { System.out.println("※ 마감 직전 상품이 없습니다 ※");}
	}
	
	public void showAuction(Connection conn) throws Exception {
		ResultSet rs = aSQL.showDeadlineAuction(conn);
		System.out.println("\n★★★★★★★★ 마감 임박 상품 ★★★★★★★★");
		printAuction(rs, conn);
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		System.out.println();
		
		rs = aSQL.showAuction(conn);
		printAuction(rs, conn);
	}

	public int[] bid(Connection conn) throws Exception {
		System.out.print("\n입찰할 상품 번호 : ");
		int[] arr = new int[3];
		arr[0] = aSQL.findItem(conn);
		arr[1] = aSQL.selectPrice(arr[0], conn);
		arr[2] = aSQL.bid(arr[0], arr[1], conn);
		
		return arr;
	}
	
	public int[] closeBid(Connection conn) throws Exception {
		ResultSet rs = aSQL.showMyAuction(conn);
		
		System.out.println("\n[나의 입찰 목록]");
		int flag = 0;
		while(rs.next()) {
			flag++;
			int item_no = rs.getInt("ITEM_NO");
			int price = rs.getInt("PRICE");
			String title = rs.getString("TITLE");
			String bid_date = rs.getString("BID_DATE");
			System.out.println(item_no + ". " + title + "    입찰 : " + price + "    입찰 시간 : " + bid_date);
		}
		if(flag == 0) { System.out.println("※ 입찰 상품이 없습니다 ※"); }
		System.out.println("");

		System.out.print("입찰 취소할 상품 번호 : ");
		int[] arr = new int[3];
		arr[0] = aSQL.findItem(conn);
		arr[1] = aSQL.findPrice(arr[0], conn);
		arr[2] = aSQL.updateBidToD(arr[0], arr[1], conn);
		return arr;
	}
}
