package com.kh.center;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.main.Main;
import com.kh.user.UserInput;

public class CenterSQL {
	private String PhoneCheck = "^01([0-9])-?([0-9]{4})-?([0-9]{4})$"; // 01로 시작하는 전화번호 -는 랜덤
	
	public ResultSet showFAQ(Connection conn) throws Exception {
		String sql = "SELECT * FROM FAQ";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		return pstmt.executeQuery();
	}

	public String showSelectedId(String phone_no, Connection conn) throws Exception {
		String sql = "SELECT * FROM K_USER WHERE PHONE_NO = ? AND USER_STATUS = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, phone_no);
		pstmt.setString(2, "Q");
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			String id = rs.getString("ID");
			System.out.println("\n※ 복구할 아이디가 " + id + " 이(가) 맞습니까? ※");
			System.out.print("1. 예 / 2. 아니오 : ");
			String input = Main.SC.nextLine();
			if(input.equals("1")) {
				return id;
			} else if (input.equals("2")) {
				throw new Exception("※ 계정 복구가 취소됩니다 ※");
			}
		}
		throw new Exception("※ 해당 전화번호로 가입된 탈퇴 아이디가 없습니다 ※\n");
	}
	
	public String makePhoneNo() {
		System.out.println();
		while(true) {
			System.out.print("전화번호를 입력하세요 : ");
			String phone_no = Main.SC.nextLine();
			if(phone_no.matches(PhoneCheck)) {
				return phone_no;
			}
			else {
				System.out.println("※ 형식에 맞춰 입력해주세요 (ex. 010-1234-5678 or 01012345678) ※\n");
			}
		}
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