package com.kh.mutualAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.item.BuyOrSell;
import com.kh.main.Main;

// 테이블 : 좋아요 목록, 거래 후기, 상품, 채팅방, 채팅 내용 

public class MutualAction {
	
	BuyOrSell buyorsell=new BuyOrSell();
	
	public void setLikeList(Connection conn) throws Exception{
		// 좋아요(찜) 하기 
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
	
	public void sellItem(Connection conn) throws Exception {
		// 판매등록
		System.out.print("판매 등록 할 상품의 카테고리 번호를 입력하세요 : ");
		int type_no = Integer.parseInt(Main.SC.nextLine());
		
		System.out.print("상품의 제목을 입력하세요 : ");
		String title = Main.SC.nextLine();

		System.out.print("상품의 정보를 입력하세요 : ");
		String content = Main.SC.nextLine();
		
		System.out.print("가격을 적어주세요 : ");
		int price = Integer.parseInt(Main.SC.nextLine());
		
		String sql ="INSERT INTO ITEM(ITEM_NO,TYPE_NO,USER_NO,TITLE,CONTENT,PRICE,TRADE_STATUS) VALUES (SEQ_ITEM_NO.NEXTVAL,?,?,?,?,?,'N')";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, type_no);
		pstmt.setInt(2, Main.login_member_no);
		pstmt.setString(3, title);
		pstmt.setString(4, content);
		pstmt.setInt(5, price);
		int result = pstmt.executeUpdate();
		
		if(result==1) {
			System.out.println("판매등록이 완료되었습니다.");
		}
		else {
			throw new Exception("등록이 실패했습니다.");
		}
	}
	
	
	public void buyItem(Connection conn) throws Exception {
		// 상품 확인후 구매
		System.out.println("===========");
		System.out.println("  상품 목록  ");
		System.out.println("===========");
		String sql ="SELECT * FROM ITEM WHERE TRADE_STATUS = 'N' ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int itemNo = rs.getInt("ITEM_NO");
			int typeNo = rs.getInt("TYPE_NO");
			String title = rs.getString("TITLE");
			String content = rs.getString("CONTENT");
			int price = rs.getInt("PRICE");
			int view = rs.getInt("VIEW");
			String tradeStatus = rs.getString("TRADE_STATUS");
		
			System.out.print("상품 번호: "+itemNo);
			System.out.print(" | ");
			System.out.print("카테고리: "+typeNo);
			System.out.print(" | ");
			System.out.print("제목: "+title);
			System.out.print(" | ");
			System.out.print("내용: "+content);
			System.out.print(" | ");
			System.out.println("가격: "+price);
			System.out.print(" | ");
			System.out.println("조회 수: "+view);
			System.out.print(" | ");
			System.out.println("거래 상태: "+tradeStatus);
		}
		
		System.out.println("구매 하고싶은 상품의 번호를 입력하세요 : ");
		int item_no = Integer.parseInt(Main.SC.nextLine());
		
		sql ="UPDATE ITEM SET TRADE_STATUS ='Y' WHERE ITEM_NO=? AND TRADE_STATUS ='N'";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, item_no);
		int result = pstmt.executeUpdate();
		
		if(result==1) {
			System.out.println("구매가 완료되었습니다.");
		}
		else {
			throw new Exception("판매된 상품입니다.");
		}
	}
	
	public void buyReview(Connection conn) throws Exception {
		//구매 후기 작성
		System.out.print("거래완료한 상품 번호 : ");
		int item_no = Integer.parseInt(Main.SC.nextLine());
		
		System.out.print("구매 후기를 작성해주세요: ");
		String buyReview = Main.SC.nextLine();
		
		String sql = "INSERT INTO REVIEW(REVIEW_NO,USER_NO,ITEM_NO,BUY_REVIEW) VALUES(SEQ_REVIEW_NO.NEXTVAL,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		pstmt.setInt(2, item_no);
		pstmt.setString(3, buyReview);
		int result = pstmt.executeUpdate();

		if(result==1) {
			System.out.println("후기작성이 완되었습니다.");
		}
		else {
			throw new Exception("후기 작성 실패");
		}
	}
	
	public void sellReview(Connection conn) throws Exception {
		//판매 후기 작성
		System.out.print("거래완료한 상품의 번호 : ");
		int item_no = Integer.parseInt(Main.SC.nextLine());
		
		System.out.print("판매 후기를 작성해주세요: ");
		String sellReview = Main.SC.nextLine();
		
		String sql = "INSERT INTO REVIEW(REVIEW_NO,USER_NO,ITEM_NO,SELL_REVIEW) VALUES(SEQ_REVIEW_NO.NEXTVAL,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		pstmt.setInt(2, item_no);
		pstmt.setString(3, sellReview);
		int result = pstmt.executeUpdate();
		
		if(result == 1 ) {
			System.out.println("후기작성이 완료되었습니다.");
		}
		else {
			throw new Exception("후기 작성 실패");
		}
	}
	
	public void makeChatRoom(Connection conn) throws Exception {
		//채팅방 개설
		// 마음에 드는 상품을 보고 채팅방을 생성
		System.out.println("===========");
		System.out.println("  상품 목록  ");
		System.out.println("===========");
		String sql ="SELECT * FROM ITEM WHERE TRADE_STATUS = 'N' ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int itemNo = rs.getInt("ITEM_NO");
			int typeNo = rs.getInt("TYPE_NO");
			String title = rs.getString("TITLE");
			String content = rs.getString("CONTENT");
			int price = rs.getInt("PRICE");
			int view = rs.getInt("VIEW");
			String tradeStatus = rs.getString("TRADE_STATUS");
		
			System.out.print("상품 번호: "+itemNo);
			System.out.print(" | ");
			System.out.print("카테고리: "+typeNo);
			System.out.print(" | ");
			System.out.print("제목: "+title);
			System.out.print(" | ");
			System.out.print("내용: "+content);
			System.out.print(" | ");
			System.out.println("가격: "+price);
			System.out.print(" | ");
			System.out.println("조회 수: "+view);
			System.out.print(" | ");
			System.out.println("거래 상태: "+tradeStatus);
		}
		
		System.out.println("상품을 선택하세요 : ");
		int item_no = Integer.parseInt(Main.SC.nextLine());
		
		sql = "INSERT INTO CHAT_ROOM(ROOM_NO,ITEM_NO,USER_NO) VALUES (SEQ_ROOM_NO.NEXTVAL,?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, item_no);
		pstmt.setInt(2, Main.login_member_no);
		int result = pstmt.executeUpdate();
		
		if(result==1) {
			System.out.println("채팅방이 개설되었습니다.");
		}
		else {
			System.out.println("채팅방 개설 실패");
		}
		
	}
	
	public void SearchChatRoomByUserNumber(Connection conn) throws Exception {
		// 유저번호로 채팅방 목록 조회하는 메소드
		System.out.print("찾으시려는 채팅방에 유저번호를 입력하세요 : ");
		int user_no = Integer.parseInt(Main.SC.nextLine());
		
		String sql ="SELECT ROOM_NO,ITEM_NO,USER_NO FROM CHAT_ROOM WHERE USER_NO =? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_no);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int roomNo = rs.getInt("ROOM_NO");
			int itemNo = rs.getInt("ITEM_NO");
			int userNo = rs.getInt("USER_NO");
			
			System.out.println("-------------------------");
			System.out.println("|	채팅방 번호 : "+roomNo+"	|");
			System.out.println("|	상품 번호 : "+itemNo+"	|");
			System.out.println("|	유저 번호 : "+userNo+"	|");
			System.out.println("-------------------------");
			
			chating(conn);
		}
		
		
	}//method
	
	public void SearchChatRoomByItemNumber(Connection conn) throws Exception {
		// 상품번호로 채팅방 목록 조회하는 메소드
		System.out.println();
		System.out.print("찾으시려는 채팅방에 상품번호를 입력하세요 : ");
		int item_no = Integer.parseInt(Main.SC.nextLine());
		
		String sql ="SELECT ROOM_NO,ITEM_NO,USER_NO FROM CHAT_ROOM WHERE ITEM_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, item_no);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int roomNo = rs.getInt("ROOM_NO");
			int itemNo = rs.getInt("ITEM_NO");
			int userNo = rs.getInt("USER_NO");
			
			System.out.println("-------------------------");
			System.out.println("|	채팅방 번호 : "+roomNo+"	|");
			System.out.println("|	상품 번호 : "+itemNo+"	|");
			System.out.println("|	유저 번호 : "+userNo+"	|");
			System.out.println("-------------------------");
			
			chating(conn);
		}
		
	}//method
	
	public void chating(Connection conn) throws Exception {
		// 채팅방 번호를 입력하여 들어가서 채팅하기 
		System.out.print("채팅방 번호를 선택해주세요 : ");
		int input = Integer.parseInt(Main.SC.nextLine());
		System.out.println();
		System.out.println("채팅방에 입장하셨습니다.");
		
		while(true) {
			System.out.print("메시지를 입력하세요 : ");
			String content = Main.SC.nextLine();

			String sql ="INSERT INTO CHAT_CONTENT(CHAT_NO, ROOM_NO , USER_NO , CONTENT, READ_YN) VALUES (SEQ_CHAT_NO.NEXTVAL,?  , ?, ?, 'Y')";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input);
			pstmt.setInt(2, Main.login_member_no);
			pstmt.setString(3, content);
			
			pstmt.executeUpdate();
			
			if(!content.equals("EXIT")) {
				System.out.println("나가기 : EXIT");
			}
			else {
				throw new Exception("채팅방에서 나갔습니다.");
			}
		}
	}//method
	
}