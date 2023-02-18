package com.kh.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.main.Main;

// 테이블 : 유저, 힌트 질문 유형, 제재 내역, Q&A


public class User {
	private final UserInput uInput = new UserInput();
	
	// 회원 가입
	public void join(Connection conn) throws Exception {
		//데이터 입력받기
		UserData data = uInput.UserJoinInput();
		
		
		String sql = "insert into K_USER (USER_NO, ID, PWD, NICK, PHONE_NO, ADDRESS, QUESTION_NO, ANSWER) values (SEQ_USER_NO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, data.getUserId());
		pstmt.setString(2, data.getUserPwd());
		pstmt.setString(3, data.getUserNick());
		pstmt.setString(4, data.getUserPhone());
		pstmt.setString(5, data.getUserAddress());
		pstmt.setString(6, data.getUserQuestion());
		pstmt.setString(7, data.getUserAnswer());
		
		int result = pstmt.executeUpdate();
		
		//결과
		if(result == 1) {
			System.out.println("회원가입 성공!");
		}else {
			System.out.println("회원가입 실패...");
		}
		
		//커넥션 정리
		conn.close();
	}
	
	// 로그인
	public void login(Connection conn) throws Exception {
		//데이터 입력받기
		UserData data = uInput.UserLoginInput();

		
		String sql = "SELECT * FROM K_USER WHERE ID = ? AND PWD = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, data.getUserId());
		pstmt.setString(2, data.getUserPwd());
		ResultSet rs = pstmt.executeQuery();
				
		//결과 출력
		if(rs.next() && rs.getString("USER_STATUS").equals("N")) {
			String nick = rs.getString("NICK");
			String balance = rs.getString("BALANCE");
			String trustLevel = rs.getString("TRUST_LEVEL");
			System.out.println("");
			System.out.println(nick + " 님 환영합니다.");
			System.out.println("매너온도	: "+ trustLevel);
			if(balance != null) {
				System.out.println("잔액	: "+ balance);
			} else {
				System.out.println("생성된 계좌가 없습니다.");
			}
			Main.login_member_nick = nick;
			
		} else if(rs.getString("USER_STATUS").equals("Y")) {
			System.out.println("");
			System.out.println("정지된 회원입니다");
			
		} else if(rs.getString("USER_STATUS").equals("Q")) {
			System.out.println("탈퇴한 회원입니다");
			
		} else {
			System.out.println("로그인 실패");
			
		}
		
		//커넥션 정리
		conn.close();
	}
	
	public void findId(Connection conn) throws SQLException {
		
		// 전화번호 입력 받기
		System.out.print("가입시 입력한 전화번호 : ");
		String joinPhoneNo = Main.SC.nextLine();
		
		// 아이디 찾기(힌트)
		String sql = "SELECT H.QUESTION FROM K_USER U LEFT JOIN HINT_TYPE H ON (U.QUESTION_NO = H.QUESTION_NO) WHERE PHONE_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, joinPhoneNo);
		ResultSet rs = pstmt.executeQuery();
		
		System.out.println(rs.next());
		
		if(rs.next()) {
			UserData data = uInput.findUserIdInput(rs.getString("H.QUESTION"));
			
			sql = "SELECT U.ID  FROM K_USER U LEFT JOIN HINT_TYPE H ON (U.QUESTION_NO = H.QUESTION_NO) WHERE ANSWER = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getUserAnswer());
			rs = pstmt.executeQuery();
			
			if(true) {
				System.out.println("회원님의 아이디는 "+rs.getString("U.ID")+"입니다.");
			} else {
				System.out.println("일치하는 회원정보가 없습니다.");
			}
			
		} else {
			System.out.println("일치하는 회원정보가 없습니다.");
		}
	}
	
	public void findPwd() {
		// 비밀번호 찾기(아이디 & 힌트)
	}
	
	
	
	public void dropUser() {
		// 회원 탈퇴
	}
	
	public void askQuestion() {
		// 질문하기
	}
	
	public void userList(Connection conn) throws SQLException {
		
		String sql = "SELECT * FROM K_USER";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
//		if(rs.next()== false) {
//			System.out.println("false 입니다");
//		}
		while(rs.next()) {
			String userNo = rs.getString("USER_NO");
			String userId = rs.getString("ID");
			String userNick = rs.getString("NICK");
			
			System.out.print(userNo);
			System.out.print(" / ");
			System.out.print(userId );
			System.out.print(" / ");
			System.out.print(userNick);
			System.out.println();
		}
	}
	
}
