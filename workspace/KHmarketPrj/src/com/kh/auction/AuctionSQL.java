package com.kh.auction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.kh.main.Main;

public class AuctionSQL {
	public ResultSet showAuction(Connection conn) throws Exception {
		String sql = "SELECT ITEM_NO, END_TIME - SYSDATE 남은시간 FROM AUCTION WHERE AUCTION_YN = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "Y");
		return pstmt.executeQuery();
	}
	
	public int findItem(Connection conn) throws Exception {
		System.out.print("입찰할 상품 번호 : ");
		int item_no = Integer.parseInt(Main.SC.nextLine());
		
		String sql = "SELECT ITEM_NO FROM AUCTION WHERE AUCTION_YN = ? AND ITEM_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "Y");
		pstmt.setInt(2, item_no);
		ResultSet rs = pstmt.executeQuery();

		if(rs.next()) {
			return item_no;
		} else {
			throw new Exception("해당 상품은 경매란에 존재하지 않습니다.");
		}
	}
	
	public int selectPrice(int item_no, Connection conn) throws Exception {
		String sql = "SELECT MAX(PRICE) 최고가 FROM BID WHERE ITEM_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, item_no);
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			System.out.println(item_no + "번 상품의 입찰 최고가 : " + rs.getInt("최고가"));
		} else {
			throw new Exception("경매 입찰가가 존재하지 않습니다.");
		}
		
		System.out.print("입찰할 금액 : ");
		int price = Integer.parseInt(Main.SC.nextLine());
		
		if(price <= rs.getInt("최고가")) {
			throw new Exception("현재 경매가 이하의 금액은 입찰이 불가능합니다.");
		} else {
			return price;
		}
	}

	public int insertBid(int user_no, int item_no, int price, Connection conn) throws Exception {
		String sql = "INSERT INTO BID(BID_NO, USER_NO, ITEM_NO, PRICE, QUIT_YN) VALUES(SEQ_BID_NO.NEXTVAL, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_no);
		pstmt.setInt(2, item_no);
		pstmt.setInt(3, price);
		pstmt.setString(4, "Y");
		
		return pstmt.executeUpdate();
	}
}
