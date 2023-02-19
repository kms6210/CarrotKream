package com.kh.auction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.kh.main.Main;

public class AuctionSQL {
	public void showAuction(Connection conn) throws Exception {
		String sql = "SELECT ITEM_NO, END_TIME - SYSDATE 남은시간 FROM AUCTION WHERE AUCTION_YN = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "Y");
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			int item_no = rs.getInt("ITEM_NO");
			String remainedTime = rs.getString("남은시간");

			System.out.println("상품번호 : " + item_no + "번 // 남은시간 : " + remainedTime);
		}
	}

	public void bid(int user_no, Connection conn) throws Exception {
		System.out.print("입찰할 상품 번호 : ");
		int item_no = Integer.parseInt(Main.SC.nextLine());
		
		String sql = "SELECT ITEM_NO FROM AUCTION WHERE AUCTION_YN = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "Y");
		ResultSet rs = pstmt.executeQuery();
		boolean isExist = false;
		while(rs.next()) {
			if(item_no == rs.getInt("ITEM_NO")) {
				isExist = true;
				break;
			}
		}
		
		if(!isExist) {
			System.out.println("해당 상품은 경매란에 존재하지 않습니다.");
			return;
		}
		
		sql = "SELECT MAX(PRICE) 최고가 FROM BID WHERE ITEM_NO = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, item_no);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			System.out.println(item_no + "번 상품의 입찰 최고가 : " + rs.getInt("최고가"));
		} else {
			System.out.println("잘못된 입력입니다.");
		}
		
		System.out.print("입찰할 금액 : ");
		int price = Integer.parseInt(Main.SC.nextLine());
		if(price <= rs.getInt("최고가")) {
			System.out.println("현재 경매가 이하의 금액은 입찰이 불가능합니다.");
			return;
		}
		
		sql = "INSERT INTO BID(BID_NO, USER_NO, ITEM_NO, PRICE, QUIT_YN) VALUES(SEQ_BID_NO.NEXTVAL, ?, ?, ?, ?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_no);
		pstmt.setInt(2, item_no);
		pstmt.setInt(3, price);
		pstmt.setString(4, "Y");
		
		int result = pstmt.executeUpdate();
		if(result == 1) {
			System.out.println("입찰 완료");
		} else {
			System.out.println("입찰 실패...");
		}
	}
}
