package com.kh.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.main.Main;

// 테이블 : 유저, 상품, 제재 내역, 품질 검증, 계좌, Q&A, 공지사항 

	public class Admin {
		// 회원 목록 조회
		public void showUserList(Connection conn) throws Exception {

			String sql = "SELECT * FROM K_USER";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				 String userNo= rs.getString("USER_NO");
				 String id = rs.getString("ID");
				 String pwd = rs.getString("PWD");
				 String nick = rs.getString("NICK");
				 String phoneNo = rs.getString("PHONE_NO");
				 String trustLevel = rs.getString("TRUST_LEVEL");
				 String address = rs.getString("ADDRESS");
				 String balance = rs.getString("BALANCE");
				 String questionNo = rs.getString("QUESTION_NO");
				 String answer = rs.getString("ANSWER");
				 String userStatus = rs.getString("USER_STATUS");
				 String signDate = rs.getString("SIGN_DATE");

				System.out.println("--------------------------------------------------------회원 목록------------------------------------------------------------------------");
				System.out.println(userNo+"|"+id+"|"+pwd+"|"+nick+"|"+phoneNo+"|"+trustLevel+"|"+address+"|"+balance+"|"+questionNo+"|"+answer+"|"+userStatus+"|"+signDate);
			}
			conn.close();
		}
	
	public void deleteItem(Connection conn) throws Exception {
		// 상품 삭제
		String sql = "DELETE FROM ITEM WHERE ITEM_NO, TYPE_NO, USER_NO, TITLE, CONTENT, PRICE, VIEW, TRADE_STATUS, WRITE_DATE";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int result = pstmt.executeUpdate();
		
		if (result ==1) {
			System.out.println("삭제완료");
		}else {
			System.out.println("삭제 실패..");
		}
	}	
	
	public void banId(Connection conn) {
		// 계정 정지하기 (ex. 7일 / 30일 / 1년 / 영구 ...)
	}

	public void judgeQuality(Connection conn, String sql) throws Exception {
		// 품질 판정하기
		sql = "INSERT INTO QUALITY(ITEM_NO, GRADE, CHECK_DATE) VALUES(?,?,SYSDATE)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		System.out.print("물건 번호: ");
		String itemNo = Main.SC.nextLine();
		
		System.out.print("물건 등급 점수: ");
		String grade = Main.SC.nextLine();
		
		int result =pstmt.executeUpdate();
		
		if(result ==1) {
			System.out.println("등급 판정 완료");
		}else {
			System.out.println("실패...");
		}
	}
	
	public void receiveFee(Connection conn) {
		// 수수료(요금) 취득
	}
	
	public void answerQuestion(Connection conn, String sql) throws Exception {
		// 답변하기
		sql = "INSERT INTO QNA (ANSWER,ANSWER_DATE) VALUES(?,SYSDATE)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		String answer = Main.SC.nextLine();
		System.out.print("답변 : ");
		
		int result = pstmt.executeUpdate();
		
		if(result ==1) {
			System.out.println("답변 작성 완료");
		}else {
			System.out.println("답변 작성 실패..");
		}
		
	}
	
	public void writePublic(Connection conn) throws Exception {
		// 공지사항 작성하기
		String sql = "INSERT INTO PUBLIC (PUBLIC_NO, TITLE, CONTENT, QUIT_YN, WRITE_DATE) VALUES(SEQ_PUBLIC_NO.NEXTVAL,?,?,?,SYSDATE)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		System.out.print("제목을 입력하세요 : ");
		String title = Main.SC.nextLine();
		
		System.out.print("내용을 입력하세요 : ");
		String content = Main.SC.nextLine();
		
		
		System.out.print("삭제여부 : ");
		String quitYn = Main.SC.nextLine();
		
		int result = pstmt.executeUpdate();
		
		if(result ==1) {
			System.out.println("공지사항 작성 완료");
	}else {
		System.out.println("공지사항 작성 실패");
	}
		conn.close();
	}
	
	public void deletePublic(Connection conn) throws Exception {
		// 공지사항 삭제하기
		String sql = "DELETE FROM PUBLIC WHERE PUBLIC_NO, TITLE, CONTENT, QUIT_YN, WRITE_DATE ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int result = pstmt.executeUpdate();
		
		if(result ==1) {
			System.out.println("삭제완료");
		}else {
			System.out.println("삭제실패..");
		}
		conn.close();
	}
}
