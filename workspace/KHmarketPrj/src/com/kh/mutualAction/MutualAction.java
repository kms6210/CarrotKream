package com.kh.mutualAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import com.kh.account.AccountInput;
import com.kh.account.AccountSQL;
import com.kh.main.Main;

// 테이블 : 좋아요 목록, 거래 후기, 상품, 채팅방, 채팅 내용 

public class MutualAction {
	
	private AccountSQL aSQL = new AccountSQL();
	
	public void reviewPage(Connection conn) throws Exception {
		while(true) {
		System.out.println("\n==================");
        System.out.println("★ 후기작성 페이지 ★");
        System.out.println("[REVIEW]");
		System.out.println("\n1.구매 후기 작성\n2.판매 후기 작성");
		System.out.println("==================");
		
		System.out.print("번호를 입력하세요 : ");
		int num = Main.integerParseInt();
		if(num == 99) {
			break;
		}
		if(num == 1) {
			buyReview(conn);
		} else if (num == 2) {
			sellReview(conn);
		} else {
			System.out.println("※ 잘못된 입력입니다 ※");
		}
		}
	}
	
	public void setLikeList(int item_no, Connection conn) throws Exception{
		// 좋아요(찜) 하기 
	try	 {
//		System.out.println("=== [좋아요 등록한 상품 목록] ===");
//		String sql ="SELECT * FROM ITEM WHERE TRADE_STATUS ='N'";
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		ResultSet rs = pstmt.executeQuery();
//		
//		sql="SELECT * FROM LIKELIST WHERE USER_NO = ? ";
//		pstmt = conn.prepareStatement(sql);
//		pstmt.setInt(1, Main.login_member_no);
//		rs = pstmt.executeQuery();
//		
//		int num = 1;
//		while(rs.next()) {
//			int itemNo = rs.getInt("ITEM_NO");
//			System.out.println(num++ + ". " + itemNo+"번 상품");
//		}
//		System.out.println("\n===========================");
//		
//		System.out.print("상품 번호를 입력하세요 : ");
//		int item_no = Main.integerParseInt();
		System.out.print("1. 좋아요 등록 / 2. 좋아요 삭제 : "); 
		String no = Main.SC.nextLine();
		
		if(no.equals("1")) {
			String sql = "INSERT INTO LIKELIST(ITEM_NO, USER_NO) VALUES(?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, item_no);
			pstmt.setInt(2, Main.login_member_no);
			
			int result = pstmt.executeUpdate();
			
			if(result==1) {
				System.out.println("\n※ 해당 상품을 '좋아요' 표시했습니다 ※ \n");
			}
			else {
				throw new Exception("※ 해당 상품이 존재하지 않습니다 ※");
			}
		} else if(no.equals("2")){
			deleteLikeList(item_no, conn);
		} else {
			throw new Exception("※ 잘못된 입력입니다 ※ ");
		}
				
	} 
	catch (SQLIntegrityConstraintViolationException e) {
		System.out.println("\n※ 이미 '좋아요' 한 상품입니다 ※ \n");
	}
	catch (Exception e) {
		System.out.println("\n※ 해당 상품을 찾을 수 없습니다 ※ \n");
	}
		
	}//method
	
	public void deleteLikeList(int item_no, Connection conn) throws Exception {
		// 좋아요(찜) 삭제하기 
		
		
		String sql ="DELETE LIKELIST WHERE ITEM_NO = ? AND USER_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, item_no);
		pstmt.setInt(2, Main.login_member_no);
		
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			System.out.println("\n※ 해당 상품의 '좋아요' 를 삭제했습니다 ※ \n");
		}
		else {
			throw new Exception("※ '좋아요' 삭제 실패 ※");
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
	
	public void sellItem(int item_no, int target_no, int price, Connection conn) throws Exception {
		// 판매
		int userBalance = aSQL.selectBalance(Main.login_member_no, conn);
		int targetBalance = aSQL.selectBalance(target_no, conn);
		int[] arr = new int[3];
	
		if(userBalance - price <= 0) { throw new Exception("※ 잔액이 부족합니다 ※"); }		
		
		String sql ="UPDATE ITEM SET TRADE_STATUS = 'Y' WHERE ITEM_NO =? and trade_status != 'Y'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, item_no);
		int result = pstmt.executeUpdate();
		
		if(result != 1) {
			throw new Exception("※ 거래 완료된 상품입니다 ※");
		}
		
		arr[0] =  aSQL.updateBalance(target_no, price, targetBalance, conn);
		if(arr[0] == 0) { throw new Exception("※ 거래 대상을 찾을 수 없습니다 ※"); }
		arr[1] = aSQL.updateBalance(Main.login_member_no, -1 * price, userBalance, conn);
		arr[2] = aSQL.insertAccount(Main.login_member_no, target_no, -1 * price, conn);
		
		sql="INSERT INTO REVIEW(REVIEW_NO, USER_NO, ITEM_NO) VALUES (SEQ_REVIEW_NO.NEXTVAL,?,?)";
		pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		pstmt.setInt(2, item_no);
		result=pstmt.executeUpdate();
		if(result * arr[0] * arr[1] * arr[2] == 1 ) {
			System.out.println("\n※ 거래가 완료되었습니다 ※");
			System.out.println("※ " + target_no + "번 유저에게 " + price + " 포인트가 이체되었습니다 ※");
			System.out.println("내 잔고 : " + new AccountInput().seeBalance(conn) + "\n");
		}
		else {
			throw new Exception("※ 거래 완료된 상품입니다 ※ ");
		}
		
		
	}//method
	
	
//	public void buyItem(int item_no, Connection conn) throws Exception {
//		// 구매
////		PreparedStatement pstmt = conn.prepareStatement(sql);
////		ResultSet rs = pstmt.executeQuery();
////		
////		while(rs.next()) {
////			int itemNo = rs.getInt("ITEM_NO");
////			int typeNo = rs.getInt("TYPE_NO");
////			String title = rs.getString("TITLE");
////			String content = rs.getString("CONTENT");
////			int price = rs.getInt("PRICE");
////			int view = rs.getInt("VIEW");
////			String tradeStatus = rs.getString("TRADE_STATUS");
////		
////			System.out.print("상품 번호: "+itemNo);
////			System.out.print(" | ");
////			System.out.print("카테고리: "+typeNo);
////			System.out.print(" | ");
////			System.out.print("제목: "+title);
////			System.out.print(" | ");
////			System.out.print("내용: "+content);
////			System.out.print(" | ");
////			System.out.println("가격: "+price);
////			System.out.print(" | ");
////			System.out.println("조회 수: "+view);
////			System.out.print(" | ");
////			System.out.println("거래 상태: "+tradeStatus);
////		}
//		
//		String sql ="UPDATE ITEM SET TRADE_STATUS ='Y' WHERE ITEM_NO=? AND TRADE_STATUS ='N'";
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		pstmt.setInt(1, item_no);
//		int result = pstmt.executeUpdate();
//		
//		if(result!=1) {
//			throw new Exception("※ 거래 완료된 상품입니다 ※ ");
//		}
//		
//		sql = "INSERT INTO REVIEW(REVIEW_NO,ITEM_NO,USER_NO) VALUES(SEQ_REVIEW_NO.NEXTVAL,?,?)";
//		pstmt=conn.prepareStatement(sql);
//		pstmt.setInt(1, item_no);
//		pstmt.setInt(2, Main.login_member_no);
//		result=pstmt.executeUpdate();
//		if(result==1) {
//			System.out.println("※ 거래가 완료되었습니다 ※ ");
//		}
//		else {
//			throw new Exception("※ 거래 완료된 상품입니다 ※");
//		}
//	}
	
	public void buyReview(Connection conn) throws Exception {
		//구매 후기 작성
		System.out.println("\n=== [구매한 상품 목록] ===");
		String sql ="select r.user_no, i.user_no, i.ITEM_NO\r\n"
				+ "from review r\r\n"
				+ "join item i\r\n"
				+ "on i.item_no = r.item_no\r\n"
				+ "where r.user_no = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		ResultSet rs = pstmt.executeQuery();
		
		int num = 1;
		while(rs.next()) {
			num++;
			int itemNo = rs.getInt("ITEM_NO");
			System.out.println(itemNo+"번 상품");
		}
		if(num ==1) {throw new Exception("※ 구매 상품이 없습니다 ※");}
		System.out.println("\n===========================");
		
		System.out.print("리뷰 쓸 상품 번호 : ");
		int item_no = Main.integerParseInt();
		
		System.out.print("구매 후기를 작성해주세요: ");
		String buyReview = Main.SC.nextLine();
		
		sql = "UPDATE REVIEW SET BUY_REVIEW = ? WHERE ITEM_NO = ? AND USER_NO = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, buyReview);
		pstmt.setInt(2, item_no);
		pstmt.setInt(3, Main.login_member_no);
		int result = pstmt.executeUpdate();

		if(result==1) {
			System.out.println("\n 후기작성이 완료되었습니다 \n");
		}
		else {
			throw new Exception("※ 후기 작성 실패 ※");
		}
	}
	
	public void sellReview(Connection conn) throws Exception {
		//판매 후기 작성
		System.out.println("\n=== [판매한 상품 목록] ===");
		String sql ="select r.user_no, i.user_no, i.ITEM_NO\r\n"
				+ "from review r\r\n"
				+ "join item i\r\n"
				+ "on i.item_no = r.item_no\r\n"
				+ "where i.user_no = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		ResultSet rs = pstmt.executeQuery();
		
		int num = 1;
		while(rs.next()) {
			num++;
			int itemNo = rs.getInt("ITEM_NO");
			System.out.println(itemNo+"번 상품");
		}
		if(num ==1) {throw new Exception("※ 판매 상품이 없습니다 ※");}
		System.out.println("\n===========================");
		
		System.out.print("리뷰 쓸 상품 번호 : ");
		int item_no = Main.integerParseInt();
		
		System.out.print("판매 후기를 작성해주세요 : ");
		String sellReview = Main.SC.nextLine();
		
		sql = "UPDATE REVIEW SET SELL_REVIEW = ? WHERE ITEM_NO = ? AND USER_NO = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, sellReview);
		pstmt.setInt(2, item_no);
		pstmt.setInt(3, Main.login_member_no);
		int result = pstmt.executeUpdate();
		
		if(result == 1 ) {
			System.out.println("\n※ 후기작성이 완료되었습니다 ※\n");
		}
		else {
			throw new Exception("※ 후기 작성 실패 ※ ");
		}
	}
	
	public void makeChatRoom(Connection conn) throws Exception {
		//채팅방 개설
		
		System.out.print("상품을 선택하세요 : ");
		int item_no = Main.integerParseInt();
		
		String sql0 = "SELECT * FROM CHAT_ROOM WHERE ITEM_NO = ? AND USER_NO = ?";
		PreparedStatement pst = conn.prepareStatement(sql0);
		pst.setInt(1, item_no);
		pst.setInt(2, Main.login_member_no);
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
			throw new Exception("※ 이미 개설된 채팅방 입니다 ※");
		}
		
		
		String sql = "INSERT INTO CHAT_ROOM(ROOM_NO,ITEM_NO,USER_NO) VALUES (SEQ_ROOM_NO.NEXTVAL,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, item_no);
		pstmt.setInt(2, Main.login_member_no);
		int result = pstmt.executeUpdate();
		
		if(result==1) {
			System.out.println("\n※ 채팅방이 개설되었습니다 ※\n");
		}
		else {
			throw new Exception("※ 채팅방 개설에 실패했습니다 ※");
		}
		
	}
	
	public void SearchChatRoomByUserNumber(Connection conn) throws Exception {
		// 유저번호로 채팅방 목록 조회하는 메소드
		String sql ="SELECT ROOM_NO,ITEM_NO,USER_NO FROM CHAT_ROOM WHERE USER_NO =? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
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
			
		}
		chating(conn);
		
		
	}//method
	
	public void SearchChatRoomByItemNumber(Connection conn) throws Exception {
		// 상품번호로 채팅방 목록 조회하는 메소드
		System.out.println();
		System.out.print("찾으시려는 채팅방에 상품번호를 입력하세요 : ");
		int item_no = Main.integerParseInt();
		
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
			
		}
		chating(conn);
		
	}//method
	
	public void chating(Connection conn) throws Exception {
		// 채팅방 번호를 입력하여 들어가서 채팅하기 
		System.out.print("채팅방 번호를 선택해주세요 : ");
		int input = Main.integerParseInt();
		System.out.println();
		System.out.println("※ 채팅방에 입장하셨습니다 ※");
		
		System.out.println("[나가기 : EXIT]\n");
		while(true) {
			System.out.print("메시지를 입력하세요 : ");
			String content = Main.SC.nextLine();

			if(content.equals("EXIT")) {
				throw new Exception("※ 채팅방에서 나갔습니다 ※");
			}
			
			String sql ="INSERT INTO CHAT_CONTENT(CHAT_NO, ROOM_NO , USER_NO , CONTENT, READ_YN) VALUES (SEQ_CHAT_NO.NEXTVAL,?  , ?, ?, 'Y')";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input);
			pstmt.setInt(2, Main.login_member_no);
			pstmt.setString(3, content);
			
			pstmt.executeUpdate();
			
		}
	}//method
	
}