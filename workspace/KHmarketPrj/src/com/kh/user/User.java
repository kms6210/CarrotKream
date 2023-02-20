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
	public int join(Connection conn) throws Exception {
		
		// 데이터 입력받기
		UserData data = uInput.UserJoinInput(conn);
		
		// 데이터 처리
		String sql = "INSERT INTO K_USER (USER_NO, ID, PWD, NICK, PHONE_NO, ADDRESS, QUESTION_NO, ANSWER)"
													+ " VALUES (SEQ_USER_NO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, data.getUserId());
		pstmt.setString(2, data.getUserPwd());
		pstmt.setString(3, data.getUserNick());
		pstmt.setString(4, data.getUserPhone());
		pstmt.setString(5, data.getUserAddress());
		pstmt.setString(6, data.getUserQuestion());
		pstmt.setString(7, data.getUserAnswer());
		
		//리턴
		return pstmt.executeUpdate();
	}
	
	// 로그인
	public int login(Connection conn) throws Exception {
		
		//데이터 입력받기
		UserData data = uInput.UserLoginInput();
		
		String sql = "SELECT * FROM K_USER WHERE UPPER(ID) = UPPER( ? ) AND PWD = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, data.getUserId());
		pstmt.setString(2, data.getUserPwd());
		ResultSet rs = pstmt.executeQuery();
		
		//결과 출력
		String userTatus;
		
		if(rs.next()) {
			userTatus = rs.getString("USER_STATUS");
			Main.login_member_no = rs.getInt("USER_NO");
			
			// 정상 계정
			if(userTatus.equals("N")) {
				String nick = rs.getString("NICK");
				String balance = rs.getString("BALANCE");
				String trustLevel = rs.getString("TRUST_LEVEL");
				
				System.out.println("\n"+nick + " 님 환영합니다.");
				System.out.println("매너온도	: "+ trustLevel);
				if(balance != null) {
					System.out.println("잔액	: "+ balance);
				} else {
					System.out.println("생성된 계좌가 없습니다.");
				}
			}
			
			// 정지 계정
			else if(userTatus.equals("S")) {
				
				System.out.println("\n정지된 회원입니다");
				
				sql = "SELECT BAN.STOP_REASON, BAN.RELEASE_DATE FROM BANNED BAN LEFT JOIN K_USER U ON (U.USER_NO = BAN.USER_NO) WHERE U.USER_NO = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Main.login_member_no);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					System.out.println("정지된 이유 : " + rs.getString("STOP_REASON"));
					System.out.println("정지 해제일 : " + rs.getDate("RELEASE_DATE"));
					
					System.out.println("\n자동으로 로그아웃 됩니다.");
					Main.login_member_no = 0;
				}
			}
			
			// 탈퇴 계정
			else if(userTatus.equals("Q")) {
				System.out.println("\n탈퇴한 회원입니다");
				System.out.println("\n자동으로 로그아웃 됩니다.");
				Main.login_member_no = 0;
			} 
		}
		
		return Main.login_member_no;
	}
	
	// 아이디 찾기 (전화번호 , 힌트)
	public void findId(Connection conn) throws Exception {
		
		// 전화번호 입력 받기
		String joinPhoneNo = uInput.phoneNoInput();
		
		String sql = "SELECT H.QUESTION FROM K_USER U LEFT JOIN HINT_TYPE H ON (U.QUESTION_NO = H.QUESTION_NO) WHERE PHONE_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, joinPhoneNo);
		ResultSet rs = pstmt.executeQuery();
		
		// 힌트 답 확인
		if(rs.next()) {
			UserData data = uInput.findUserIdInput(joinPhoneNo,rs.getString("QUESTION"));
			
			sql = "SELECT U.ID  FROM K_USER U LEFT JOIN HINT_TYPE H ON (U.QUESTION_NO = H.QUESTION_NO) WHERE PHONE_NO = ? AND ANSWER = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getUserPhone());
			pstmt.setString(2, data.getUserAnswer());
			rs = pstmt.executeQuery();
			
			//결과 출력
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
	public void findPwd(Connection conn) throws Exception {
		
		// 정보입력 받기
		System.out.print("가입시 입력한 아이디 : ");
		String userId = Main.SC.nextLine();
		String joinPhoneNo = uInput.phoneNoInput();
		
		String sql = "SELECT H.QUESTION FROM K_USER U LEFT JOIN HINT_TYPE H ON (U.QUESTION_NO = H.QUESTION_NO) WHERE UPPER(ID) = UPPER( ? ) AND PHONE_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userId);
		pstmt.setString(2, joinPhoneNo);
		ResultSet rs = pstmt.executeQuery();
		
		//결과 출력
		if(rs.next()) {
			UserData data = uInput.findUserIdInput(joinPhoneNo,rs.getString("QUESTION"));
			
			sql = "SELECT U.PWD  FROM K_USER U LEFT JOIN HINT_TYPE H ON (U.QUESTION_NO = H.QUESTION_NO) WHERE UPPER(ID) = UPPER( ? ) AND PHONE_NO = ? AND ANSWER = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, data.getUserPhone());
			pstmt.setString(3, data.getUserAnswer());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				sql = "UPDATE K_USER SET PWD = ? WHERE USER_NO = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userId);
				pstmt.setString(2, data.getUserPhone());
				
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
	public int dropUser(Connection conn) throws Exception {
		
		int rrs = 0;
		
		System.out.println("정말로 탈퇴 하시겠습니까?");
		System.out.print("1. 네 / 2. 아니오 : ");
		String yn = Main.SC.nextLine();
		
		if(yn.equals("1")) {
						
			String sql = "SELECT ID FROM K_USER WHERE USER_NO = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Main.login_member_no);
			ResultSet rs = pstmt.executeQuery();
			
			// 아이디 일치 체크
			if(rs.next()) {
				System.out.println("탈퇴 하려면 [" + rs.getString("ID") + "]을 입력하세요");
				String input = Main.SC.nextLine();
				
				// 탈퇴 처리
				if(rs.getString("ID").equals(input)) {
					System.out.println("");
					sql = "UPDATE K_USER SET USER_STATUS = 'Q' WHERE USER_NO = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, Main.login_member_no);
					rrs = pstmt.executeUpdate();
					
					if(rrs != 0) {
						System.out.println("탈퇴처리를 완료했습니다.");
						Main.login_member_no = 0;
					}
				} else { System.out.println("입력한 값이 다릅니다."); }
			}
		}
		else { System.out.println("탈퇴 취소"); }
		
		return rrs; 
	}
	
	// 질문하기
	public int askQuestion(Connection conn) throws Exception {
		
		
		//질문 받기
		System.out.print("질문을 입력해 주세요 : ");
		String userQuestion = Main.SC.nextLine();
		
		String sql = "INSERT INTO QNA (QUESTION_NO,USER_NO,QUESTION) VALUES (SEQ_QNA_QUESTION_NO.NEXTVAL,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		pstmt.setString(2, userQuestion);
		int result = pstmt.executeUpdate();
		
		if(result == 1) { System.out.println("질문 등록완료"); }
		
		return result;
	}
	
	
	public ResultSet userList(Connection conn) throws Exception {
		
		ResultSet rs;
		
		String sql = "SELECT * FROM K_USER";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

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
		
		return rs;
	}
	
}
