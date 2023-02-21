package com.kh.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminTamp {
	
	public void showQuestionList(Connection conn) throws Exception {
		// 미답변 질문 목록 보여주기	
		String sql= "SELECT USER_NO, QUESTION,WRITE_DATE  FROM QNA WHERE QUIT_YN ='N'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			String userNo = rs.getString("USER_NO");
			String question = rs.getString("QUESTION");
			String writeDate = rs.getString("WRITE_DATE");
		
			System.out.println(userNo + "|" + question + "|" +writeDate);
		}
		
		
	}

}
