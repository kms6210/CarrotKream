package com.kh.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.kh.admin.Admin;
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
		System.out.println("┌─────────────────────────────────────────────────┐");
		System.out.println("		   🥕 로그인 🥕");
		System.out.println("		   ---------\n");
		
		UserData data;
		System.out.print("		아이디　　: ");
		String userId = Main.SC.nextLine();
		if(userId.equals("aaa")) {
			System.out.println("\n└─────────────────────────────────────────────────┘");
		    new Admin().adminlogin(conn);
		    return Main.login_admin_no;
		} 
		else {
			data = uInput.UserLoginInput(conn,userId);
		}
		
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
	
	// 아이디 / 비밀번호 찾기
	public void findIdPwd(Connection conn) throws Exception {
		boolean back = false;
		while(!back) {
			System.out.println("┌──────────────────┤ 계정  찾기 ├──────────────────┐\n");
			System.out.println("		1.Id 찾기  2.Pwd 찾기");
			System.out.println("\n└────────────────────────────────────────────────┘");
			System.out.print("	       ▶ 번호를 입력하세요 : ");
			String select = Main.SC.nextLine();
			switch (select)	 {
			case "1" : if(findId(conn)!=0) {back = true;}; break;
			case "2" : if(findPwd(conn)!=0) {back = true;}; break;
			case "99" : back = true ; break;
			default: System.out.println("잘못 입력하셨습니다"); break;
			}
		}
		
	}
	
	// 아이디 찾기 (전화번호 , 힌트)
	public int findId(Connection conn) throws Exception {
		System.out.println("\n ┌-----------------┤ ID  찾기 ├-----------------┐");
		// 전화번호 입력 받기
		String joinPhoneNo = uInput.phoneNoInput();
		
		String sql = "SELECT H.QUESTION FROM K_USER U LEFT JOIN HINT_TYPE H ON (U.QUESTION_NO = H.QUESTION_NO) WHERE PHONE_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, joinPhoneNo);
		ResultSet rs = pstmt.executeQuery();
		
		int check = 0;
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
				System.out.println("\n	   회원님의 아이디는 [ "+rs.getString("ID")+" ] 입니다.");
				System.out.println("\n └----------------------------------------------┘");
			} else {
				System.out.println("\n └----------------------------------------------┘");
				System.out.println("틀린 답변입니다.");
			}
			check++;
			return check;
		} else {
			System.out.println("\n └----------------------------------------------┘");
			System.out.println("전화번호와 일치하는 회원정보가 없습니다.\n");
			return check;
		}
	}
	
	// 비밀번호 찾기(아이디 & 힌트)
	public int findPwd(Connection conn) throws Exception {
		System.out.println("\n ┌-----------------┤ PWD 찾기 ├-----------------┐");
		// 정보입력 받기
		System.out.print("\n	  가입시 입력한 아이디 : ");
		String userId = Main.SC.nextLine();
		String joinPhoneNo = uInput.phoneNoInput();
		
		
		String sql = "SELECT H.QUESTION FROM K_USER U LEFT JOIN HINT_TYPE H ON (U.QUESTION_NO = H.QUESTION_NO) WHERE UPPER(ID) = UPPER( ? ) AND PHONE_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userId);
		pstmt.setString(2, joinPhoneNo);
		ResultSet rs = pstmt.executeQuery();
		
		int check = 0;
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
				
				System.out.println("\n	       회원님의 비밀번호는 [ "+rs.getString("PWD")+"] 입니다.");
				System.out.println("\n └----------------------------------------------┘");
			} else {
				System.out.println("\n └----------------------------------------------┘");
				System.out.println("틀린 답변입니다.");
			}
			check++;
			return check;
		} else {
			System.out.println("\n └----------------------------------------------┘");
			System.out.println("전화번호와 일치하는 회원정보가 없습니다.\n");
			return check;
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
	
	// 내가한 질문 목록 관리
	public void QuestionList(Connection conn) throws Exception	 {
		
		String status = null ;
		// 질문 번호 담기
		int[] qnArr = new int[99];
		
		
		String sql = "SELECT QUESTION_NO, QUESTION, ANSWER FROM QNA WHERE USER_NO = ? AND QUIT_YN = 'N'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_member_no);
		ResultSet rs = pstmt.executeQuery();
		
		
		
		int i = 0;
		System.out.println("-------------- 질문 목록 --------------");
		while (rs.next()) {
			
			int questionNo = rs.getInt("QUESTION_NO");
			qnArr[i] = questionNo;
			
			
			String question = rs.getString("QUESTION");
			if(question.length() > 10) {
				question = question.substring(0,7) + "...";
			}
			
			String answer = rs.getString("ANSWER");
			if(answer == null) { answer = "미답변"; status = "수정가능";}
			else if(answer != null) { status = "수정불가"; };
			
			System.out.println((++i)+"	|	"+question+"	|	"+answer+"	|	"+ status);
			if(questionNo == 0) { System.out.println("없음"); };
		}
		System.out.println("-------------------------------------");
		
		System.out.print("1. 수정 / 2. 삭제");
		System.out.println("번호를 입력해 주세요 :");
		int select = Main.SC.nextInt(); Main.SC.nextLine(); 
		int questionNo;
		switch (select) {
			case 1 : 
				System.out.print("수정할 질문번호을 선택해 주세요 : ");
				questionNo = Main.SC.nextInt(); Main.SC.nextLine(); 
				if(questionNo-1 > i || questionNo-1 < 0) {System.out.println("잘못된 입력입니다.");QuestionList(conn);break;};
				questionNo = qnArr[questionNo-1];
				
				System.out.print("수정할 질문 내용 : ");
				String cancelQuestion = Main.SC.nextLine();
				
				sql = "UPDATE QNA SET QUESTION = ? WHERE QUESTION_NO = ? AND QUIT_YN = 'N' ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, cancelQuestion);
				pstmt.setInt(2, questionNo);
				int rsQ = pstmt.executeUpdate();
				
				if(rsQ == 0) {
					System.out.println("수정을 실패했습니다. 이미 답변이 완료된 질문입니다.");
				} else {
					System.out.println("질문 수정 성공");
				}
				break;
			case 2 : 
				System.out.print("삭제할 질문번호를 선택해 주세요 : ");
				questionNo = Main.SC.nextInt(); Main.SC.nextLine();
				questionNo = qnArr[questionNo-1];
				
				System.out.println("정말로 삭제 하시겠습니까?");
				System.out.print("1. 네 / 2. 아니오 : ");
				int deletQuestion = Main.SC.nextInt(); Main.SC.nextLine(); 
				
				if(deletQuestion == 1) {
					sql = "UPDATE QNA SET QUIT_YN = 'Y' WHERE QUESTION_NO = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, questionNo);
					int delQ = pstmt.executeUpdate();
					
					if(delQ == 0) {
						System.out.println("질문 삭제를 실패했습니다.");
					} else {
						System.out.println("질문 삭제 성공");
					}
				} 
				else if(deletQuestion == 2) {
					QuestionList(conn);
				}
				break;
			case 99 : System.out.println("돌아가기"); break;
			default: System.out.println("잘못된 입력입니다."); QuestionList(conn);
		}
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
