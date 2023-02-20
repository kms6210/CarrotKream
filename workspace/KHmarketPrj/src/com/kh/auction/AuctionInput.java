package com.kh.auction;

import java.sql.Connection;
import java.sql.ResultSet;

import com.kh.main.Main;

public class AuctionInput {
	private AuctionSQL aSQL = new AuctionSQL();

	public void showAuction(Connection conn) throws Exception {
		ResultSet rs = aSQL.showAuction(conn);

		while (rs.next()) {
			int item_no = rs.getInt("ITEM_NO");
			String remained_time = rs.getString("남은시간");
			System.out.println("상품번호 : " + item_no + " // 남은시간 : " + remained_time);
		}
	}

	public int[] bid(Connection conn) throws Exception {
		int[] arr = new int[3];
		arr[0] = aSQL.findItem(conn);
		arr[1] = aSQL.selectPrice(arr[0], conn);
		arr[2] = aSQL.insertBid(Main.login_member_no, arr[1], arr[2], conn);
		
		return arr;
	}

	public void closeBid(Connection conn) {

	}
}
