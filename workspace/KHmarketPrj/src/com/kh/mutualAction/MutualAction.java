package com.kh.mutualAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.main.Main;

// 테이블 : 좋아요 목록, 거래 후기, 상품, 채팅방, 채팅 내용 

public class MutualAction {
	
	
	public void setLikeList(Connection conn) throws Exception{
		// 좋아요(찜) 하기 
		//유저번호 1번이 상품번호 1번을 보고 찜을 눌러서 찜목록에 추가.
		try {
			System.out.println("상품 번호를 입력하세요 : ");
			int item_no = Integer.parseInt(Main.SC.nextLine());
			
			String sql = "INSERT INTO LIKELIST(ITEM_NO, USER_NO) VALUES(?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, item_no);
			pstmt.setInt(2, Main.login_member_no);
			
			int result = pstmt.executeUpdate();
			
			if(result==1) {
				System.out.println("좋아요 등록이 되었습니다.");
			}
			else {
				System.out.println("좋아요 등록 실패");
			}
		} catch (Exception e) {
			throw new Exception("이미 찜 한 상품입니다.");
		}
		
		
		
		
	}//method
	
	public void deleteLikeList(Connection conn) throws Exception {
		// 좋아요(찜) 삭제하기 
		
		System.out.print("상품 번호를 입력하세요 : ");
		int item_no = Integer.parseInt(Main.SC.nextLine());
		
		String sql ="DELETE LIKELIST WHERE ITEM_NO = ? AND USER_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, item_no);
		pstmt.setInt(2, Main.login_member_no);
		
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			System.out.println("좋아요 가 삭제 되었습니다.");
		}
		else {
			throw new Exception("좋아요 삭제 실패");
		}
	}
	
	public void selectLikeList(Connection conn ) throws Exception {
		// 좋아요(찜) 조회하기
		String sql ="SELECT ITEM_NO,USER_NO FROM LIKELIST WHERE USER_NO = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int user_no = rs.getInt("USER_NO");
			int item_no = rs.getInt("ITEM_NO");
			System.out.println(user_no +"번 유저님 의 좋아요 목록은 " + item_no + "번 상품 입니다.");
		}
	}
	
	public void buyReview(Connection conn) throws Exception {
		//구매 후기 작성
		System.out.print("거래완료 한 상품 번호 : ");
		int item_no = Integer.parseInt(Main.SC.nextLine());
		
		System.out.println("구매 후기 작성 : ");
		String content = Main.SC.nextLine();
		
		String sql = "INSERT INTO REVIEW(REVIEW_NO, USER_NO, ITEM_NO, BUY_REVIEW) VALUES (SEQ_REVIEW_NO,NEXTVAL,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		pstmt.setInt(2, item_no);
		pstmt.setString(3, content);
		int result = pstmt.executeUpdate();
		
		if(result==1) {
			System.out.println("후기가 작성되었습니다.");
		}
		else {
			throw new Exception("후기 작성 실패");
		}
	}
	
	public void sellReview() {
		//판매 후기 작성
		
	}
	
	public void writeReview(Connection conn) throws Exception {
		// 거래 후기 작성 
		
		System.out.println("거래완료한 상품의 번호 : ");
		int item_no = Integer.parseInt(Main.SC.nextLine());
		
		System.out.println("구매 후기를 입력해주세요 :");
		String buyReview = Main.SC.nextLine();

		System.out.println("판매 후기를 입력해주세요 :");
		String sellReview = Main.SC.nextLine();
		
		String sql = "INSERT INTO REVIEW(REVIEW_NO,USER_NO , ITEM,NO , BUY_REVIEW , SELL_REVIEW) VALUES (SEQ_REVIEW_NO.NEXTVAL,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		pstmt.setInt(2, item_no);
		pstmt.setString(3, buyReview);
		pstmt.setString(4, sellReview);
	}
	
	public void sellItem() {
		// 판매
	}
	
	public void buyItem() {
		// 구매 
	}
	
	public void makeChatRoom(Connection conn, int item_no , int user_no ) throws Exception {
		//채팅방 개설
		String sql = "INSERT INTO CHAT_ROOM(ROOM_NO,ITEM_NO,USER_NO) VALUES (SEQ_ROOM_NO.NEXTVAL,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_no);
		pstmt.setInt(2, item_no);
		int result = pstmt.executeUpdate();
		
		if(result==1) {
			System.out.println("채팅방이 개설되었습니다.");
		}
		else {
			System.out.println("채팅방 개설 실패");
		}
		
	}
	
	public void chating(Connection conn, int user_no) throws Exception {
		// 채팅하기 
		System.out.println("메시지를 입력하세요 : ");
		String content = Main.SC.nextLine();
		
		String sql ="INSERT INTO CHAT_CONTENT(CHAT_NO, ROOM_NO , USER_NO , CONTENT, READ_YN) VALUES (SEQ_CHAT_NO.NEXTVAL , SEQ_ROOM_NO.NEXTVAL , ?, ?, 'Y')";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_no);
		pstmt.setString(2, content);
	}
	
}