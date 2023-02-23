package com.kh.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminTemp {
	
	public void showQuestionList(Connection conn) throws Exception {
		// 미답변 질문 목록 보여주기	
		String sql= "SELECT USER_NO, QUESTION,WRITE_DATE  FROM QNA WHERE QUIT_YN ='N'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		System.out.println("========== 질문 목록 ===========");
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			String userNo = rs.getString("USER_NO");
			String question = rs.getString("QUESTION");
			String writeDate = rs.getString("WRITE_DATE");
		
			System.out.println(userNo + "|" + question + "|" +writeDate);
		System.out.println("===========================================");
		}
		
		
	}
	
	public void showItmeList(Connection conn) throws Exception {
		//1,상품이랑 품질 테이블이랑 join 후 가격 상품번호 유저번호 select -품질판정하기 
		String sql = "SELECT Q.ITEM_NO ,I.PRICE , I.USER_NO ,Q.GRADE FROM ITEM I JOIN QUALITY Q ON I.ITEM_NO = Q.ITEM_NO";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			String ItemNo = rs.getString("ITEM_NO");
			String Price = rs.getString("PRICE");
			String userNo = rs.getString("USER_NO");
			String grade = rs.getString("GRADE");
			System.out.println(userNo+"번 유저의 "+ItemNo+"번 상품은 "+Price+"원 입니다. 상품 등급점수 :"+grade  );
		}
	}
	
	public void adminBalanceList(Connection conn) throws Exception {
	//	2.admin balance <- admin테이블에서 balcan 값을 조회하기 select
	String sql = "SELECT BALANCE FROM K_ADMIN WHERE ADMIN_NO =1";
	PreparedStatement pstmt = conn.prepareStatement(sql);
	ResultSet rs = pstmt.executeQuery();
	
	if(rs.next()) {
		String balance = rs.getString("BALANCE");
		System.out.println("관리자의 잔액은"+ balance+"원 입니다.");
	}else {
		System.out.println("실패...");
	}
	
		
	}

}
