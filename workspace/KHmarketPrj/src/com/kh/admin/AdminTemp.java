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
		
			System.out.println(userNo + " | " + question + " | " +writeDate);
		System.out.println("===========================================");
		}
		

	}
	public void showNoticeList(Connection conn) throws Exception {
		//공지사항 보여주기
		String sql = "SELECT * FROM NOTICE";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		System.out.println("========== 공지 사항  ===========");
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			String publicNo = rs.getString("PUBLIC_NO");
			String title = rs.getString("TITLE");
			String content = rs.getString("CONTENT");
			String quitYn = rs.getString("QUIT_YN");
			String writeDate = rs.getString("WRITE_DATE");
			
			System.out.println(publicNo+"|"+title+"|"+content+"|"+quitYn+"|"+writeDate);
			System.out.println("===========================================");
		}
	}

}
