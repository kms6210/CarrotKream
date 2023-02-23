package com.kh.auction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.account.Account;
import com.kh.account.AccountInput;
import com.kh.main.Main;

public class AuctionSQL {
	private ResultSet selectAuction(String sql, Connection conn) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "A");
		pstmt.setString(2, "N");
		return pstmt.executeQuery();
	}
	
	public ResultSet showDeadlineAuction(Connection conn) throws Exception {
		String sql = "SELECT I.ITEM_NO, TITLE, PRICE, END_TIME - SYSDATE 남은시간 FROM ITEM I JOIN AUCTION A ON I.ITEM_NO = A.ITEM_NO WHERE TRADE_STATUS = ? AND AUCTION_YN = ? AND END_TIME < (SYSDATE + 1) ORDER BY 남은시간";
		return selectAuction(sql, conn);
	}
	
	public ResultSet showAuction(Connection conn) throws Exception {
		String sql = "SELECT I.ITEM_NO, TITLE, PRICE, END_TIME - SYSDATE 남은시간 FROM ITEM I JOIN AUCTION A ON I.ITEM_NO = A.ITEM_NO WHERE TRADE_STATUS = ? AND AUCTION_YN = ? ORDER BY 남은시간";
		return selectAuction(sql, conn);
	}
	
	public ResultSet showMyAuction(Connection conn) throws Exception {
		String sql = "select * from bid b join item i ON i.item_no = b.item_no where b.user_no = ? AND trade_status = 'A'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		return pstmt.executeQuery();
	}
	
	public int findItem(Connection conn) throws Exception {
		int item_no = Main.integerParseInt();
		String sql = "SELECT ITEM_NO FROM AUCTION WHERE AUCTION_YN = ? AND ITEM_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "N");
		pstmt.setInt(2, item_no);
		ResultSet rs = pstmt.executeQuery();

		if(rs.next()) {
			return item_no;
		} else {
			throw new Exception("※ 해당 상품은 경매란에 존재하지 않습니다 ※");
		}
	}
	
	public int findPrice(int item_no, Connection conn) throws Exception {
		String sql = "SELECT MAX(PRICE) 최고가 FROM BID WHERE USER_NO = ? AND ITEM_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		pstmt.setInt(2, item_no);
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			return rs.getInt("최고가");
		} else {
			throw new Exception("※ 해당 상품에 입찰한 기록이 없습니다 ※");
		}
	}
	
	public int getMaxPrice(int item_no, Connection conn) throws Exception {
		String sql = "SELECT MAX(PRICE) 최고가 FROM BID WHERE ITEM_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, item_no);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("최고가");
		} else {
			throw new Exception("※ 해당상품에는 입찰가가 존재하지 않습니다 ※");
		}
	}
	
	public int selectPrice(int item_no, Connection conn) throws Exception {
		String sql = "SELECT MAX(PRICE) 최고가 FROM BID WHERE ITEM_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, item_no);
		ResultSet rs = pstmt.executeQuery();
		
		System.out.print("입찰할 금액 : ");
		int price = Integer.parseInt(Main.SC.nextLine());
		
		if(!rs.next()) {
			throw new Exception("※ 해당상품에는 입찰가가 존재하지 않습니다 ※");
		}
		
		if(price <= rs.getInt("최고가")) {
			throw new Exception("※ 현재 경매가 이하의 금액은 입찰이 불가능합니다 ※");
		} else if(new AccountInput().seeBalance(conn) < price) {
			throw new Exception("※ 잔액이 부족합니다 ※");
		} else	{
			return price;
		}
	}
	
	public int bid(int item_no, int price, Connection conn) throws Exception {
		if(selectBid(item_no, conn).next()) {
			return updateBid(item_no, price, conn);
		} else {
			return insertBid(item_no, price, conn);
		}
	}
	
	private ResultSet selectBid(int item_no, Connection conn) throws Exception {
		String sql = "SELECT * FROM BID WHERE USER_NO = ? AND ITEM_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		pstmt.setInt(2, item_no);
		
		return pstmt.executeQuery();
	}

	private int insertBid(int item_no, int price, Connection conn) throws Exception {
		String sql = "INSERT INTO BID(BID_NO, USER_NO, ITEM_NO, PRICE, QUIT_YN) VALUES(SEQ_BID_NO.NEXTVAL, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		pstmt.setInt(2, item_no);
		pstmt.setInt(3, price);
		pstmt.setString(4, "N");
		
		return pstmt.executeUpdate();
	}
	
	private int updateBid(int item_no, int price, Connection conn) throws Exception {
		String sql = "UPDATE BID SET QUIT_YN = ?, PRICE = ? WHERE USER_NO = ? AND ITEM_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "N");
		pstmt.setInt(2, price);
		pstmt.setInt(3, Main.login_member_no);
		pstmt.setInt(4, item_no);
		
		return pstmt.executeUpdate();
	}
	
	public int updateBidToD(int item_no, int price, Connection conn) throws Exception {
		String sql = "UPDATE BID SET QUIT_YN = ? WHERE USER_NO = ? AND ITEM_NO = ? AND PRICE = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "D");
		pstmt.setInt(2, Main.login_member_no);
		pstmt.setInt(3, item_no);
		pstmt.setInt(4, price);
		
		return pstmt.executeUpdate();
	}
}
