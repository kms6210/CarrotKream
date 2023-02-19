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
		
		
		String sql = "INSERT INTO K_USER (USER_NO, ID, PWD, NICK, PHONE_NO, ADDRESS, QUESTION_NO, ANSWER) VALUES (SEQ_USER_NO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
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
		
	}
	
	// 로그인
	public void login(Connection conn) throws Exception {
		
		//데이터 입력받기
		UserData data = uInput.UserLoginInput();
		
		String sql = "SELECT * FROM K_USER WHERE UPPER(ID) = UPPER( ? ) AND PWD = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, data.getUserId());
		pstmt.setString(2, data.getUserPwd());
		ResultSet rs = pstmt.executeQuery();
		
		rs.next();
		
		String userTatus = rs.getString("USER_STATUS");
		
		//결과 출력
		if(userTatus.equals("N")) {
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
			
			Main.login_member_no = rs.getInt("USER_NO");;
			
		} else if(userTatus.equals("Y")) {
			System.out.println("");
			System.out.println("정지된 회원입니다");
			
			sql = "SELECT BAN.STOP_REASON, BAN.RELEASE_DATE FROM BANNED BAN LEFT JOIN k_user U ON (U.USER_NO = BAN.USER_NO) WHERE U.USER_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getUserId());
			
			System.out.println("정지된 이유 : ");
		} else if(userTatus.equals("Q")) {
			System.out.println("탈퇴한 회원입니다");
			
		} else {
			System.out.println("로그인 실패");
		}
		
	}
	
	// 아이디 찾기 (전화번호 , 힌트)
	public void findId(Connection conn) throws SQLException {
		
		// 전화번호 입력 받기
		String joinPhoneNo = uInput.phoneNoInput();
		
		String sql = "SELECT H.QUESTION FROM K_USER U LEFT JOIN HINT_TYPE H ON (U.QUESTION_NO = H.QUESTION_NO) WHERE PHONE_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, joinPhoneNo);
		ResultSet rs = pstmt.executeQuery();
		
		//결과 출력
		if(rs.next()) {
			UserData data = uInput.findUserIdInput(joinPhoneNo,rs.getString("QUESTION"));
			
			sql = "SELECT U.ID  FROM K_USER U LEFT JOIN HINT_TYPE H ON (U.QUESTION_NO = H.QUESTION_NO) WHERE PHONE_NO = ? AND ANSWER = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getUserPhone());
			pstmt.setString(2, data.getUserAnswer());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("");
				System.out.println("회원님의 아이디는 "+rs.getString("ID")+"입니다.");
			} else {
				System.out.println("틀린 답변입니다.");
			}
			
		} else {
			System.out.println("전화번호와 일치하는 회원정보가 없습니다. ");
		}
		
	}
	
	// 비밀번호 찾기(아이디 & 힌트)
	public void findPwd(Connection conn) throws SQLException {
		
		// 정보입력 받기
		System.out.print("가입시 입력한 아이디 : ");
		String userId = Main.SC.nextLine();
				
		String joinPhoneNo = uInput.phoneNoInput();
		
		String sql = "SELECT H.QUESTION FROM K_USER U LEFT JOIN HINT_TYPE H ON (U.QUESTION_NO = H.QUESTION_NO) WHERE ID = ? AND PHONE_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, userId);
		pstmt.setString(2, joinPhoneNo);
		ResultSet rs = pstmt.executeQuery();
		
		//결과 출력
		if(rs.next()) {
			UserData data = uInput.findUserIdInput(joinPhoneNo,rs.getString("QUESTION"));
			
			sql = "SELECT U.PWD  FROM K_USER U LEFT JOIN HINT_TYPE H ON (U.QUESTION_NO = H.QUESTION_NO) WHERE ID = ? AND PHONE_NO = ? AND ANSWER = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, data.getUserPhone());
			pstmt.setString(3, data.getUserAnswer());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("");
				System.out.println("회원님의 비밀번호는 "+rs.getString("PWD")+"입니다.");
			} else {
				System.out.println("틀린 답변입니다.");
			}
			
		} else {
			System.out.println("전화번호와 일치하는 회원정보가 없습니다. ");
		}
		
	}
	
	// 회원 탈퇴
	public void dropUser() {
		
		System.out.println("");
		
	}
	
	// 질문하기
	public void askQuestion(Connection conn) throws SQLException {
		
		if(Main.login_member_no == 0) {
			
		}
		//질문 받기
		System.out.println("질문을 입력해 주세요");
		String userQuestion = Main.SC.nextLine();
		
		String sql = "INSERT INTO QNA (QUESTION_NO,USER_NO,QUESTION,ANSWER) VALUES (SEQ_QNA_QUESTION_NO.NEXTVAL,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		pstmt.setString(2, userQuestion);
		pstmt.setString(3, "미답변");
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			System.out.println("질문 등록완료");
		}
	}
	public void userList(Connection conn) throws SQLException {
		
		String sql = "SELECT * FROM K_USER";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while(rs.next()) {
			String userNo = rs.getString("USER_NO");
			String userId = rs.getString("ID");
			String userNick = rs.getString("PHONE_NO");
			
			System.out.print(userNo);
			System.out.print(" / ");
			System.out.print(userId );
			System.out.print(" / ");
			System.out.print(userNick);
			System.out.println();
		}
	}
	
}
