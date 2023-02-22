package com.kh.center;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.main.Main;

public class CenterSQL {
	public ResultSet showFAQ(Connection conn) throws Exception {
		String sql = "SELECT * FROM FAQ";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		return pstmt.executeQuery();
	}

	public void showSelectedId(String phone_no, Connection conn) throws Exception {
		String sql = "SELECT * FROM K_USER WHERE PHONE_NO = ? AND USER_STATUS = ?";
		String id = "";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, phone_no);
		pstmt.setString(2, "Q");
		ResultSet rs = pstmt.executeQuery();
		int flag = 0;
		while(rs.next()) {
			if(flag == 0) {System.out.print("탈퇴한 아이디 : "); flag++;}
			id = rs.getString("ID");
			System.out.print(id + "  ");
		}
		if(id.equals("")) { throw new Exception("전화번호와 일치하는 탈퇴한 아이디가 없습니다.\n"); }
	}
	
	public String makePhoneNo() {
		System.out.print("전화번호를 입력하세요 : ");
		String phone_no = Main.SC.nextLine();
		phone_no = phone_no.replaceAll("-", "");
		String s1 = phone_no.substring(0, 3);
		String s2 = phone_no.substring(3, 7);
		String s3 = phone_no.substring(7, 11);
		
		phone_no = s1 + "-" + s2 + "-" + s3;
		return phone_no;
	}
	
	public int restoreId(String id, String phone_no, Connection conn) throws Exception {
		String sql = "UPDATE K_USER SET USER_STATUS = 'N' WHERE ID = ? AND PHONE_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, phone_no);
		return pstmt.executeUpdate();
	}

	public int ask(int user_no, Connection conn) throws Exception{
		String sql = "INSERT INTO QNA (QUESTION_NO,USER_NO,QUESTION) VALUES (SEQ_QNA_QUESTION_NO.NEXTVAL, ?, ?)";
		System.out.print("문의할 내용 : ");
		String question = Main.SC.nextLine();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_no);
		pstmt.setString(2, question);
		return pstmt.executeUpdate();
	}
	
}