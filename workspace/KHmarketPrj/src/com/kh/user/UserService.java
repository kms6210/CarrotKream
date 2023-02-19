package com.kh.user;

import java.sql.Connection;
import java.sql.SQLException;

import com.kh.main.Main;

public class UserService {
	
	public static final int ARAMDOM = (int) (Math.round(Math.random()*10000)+1);
	private User user = new User();
	
	public void userPage(Connection conn){
		System.out.println("==================");
		System.out.println("유저 페이지");
		System.out.println("------------------");
		
		if(Main.login_member_no == 0) {
			System.out.println("안녕하세요");
			System.out.println("방문자 "+ ARAMDOM  +" 님\n");
			System.out.println("1. 회원가입\n"
					 		 + "2. 로그인\n"
					 		 + "3. 아이디 찾기(힌트)\n"
							 + "4. 비밀번호 찾기(아이디 & 힌트)\n");
			System.out.print("번호를 입력하세요 : ");
			String input = Main.SC.nextLine();
			System.out.println("");
			
			switch (input) {
			case "1" : 
				try {
					user.join(conn);
				} catch (Exception e1) {
					System.out.println("회원가입 실패");
				} break;
			case "2" : 
				try {
					user.login(conn);
				} catch (Exception e) {
					System.out.println("로그인 실패");
					System.out.println("\n아이디나 패스워드를 확인해주세요");
				} break;
			case "3" : 
				try {
					user.findId(conn);
				} catch (SQLException e) {
					System.out.println("아이디 찾기 실패");
				} break;
			case "4" :
				try {
					user.findPwd(conn);
				} catch (SQLException e) {
					System.out.println("비밀번호 찾기 실패");
				} break;
			default:
				System.out.println("잘못 입력하셨습니다….");
			}
		}
		else {
			System.out.println("안녕하세요");
//			System.out.println(" +" 님\n");
			System.out.println(
							   "1. 회원 탈퇴\n"
							 + "2. 질문하기\n"
							 + "99. 유저조회(테스트용)");
			
			System.out.print("번호를 입력하세요 : ");
			String input = Main.SC.nextLine();
			System.out.println("");
			
			switch (input) {
			case "1" : 
			if(Main.login_member_no == 0) {
				System.out.println("로그인이 필요한 서비스입니다");
				} else {
					try {
						user.dropUser(conn);
					} catch (SQLException e) {
						System.out.println("회원탈퇴 에러 :" + e);
					}
				}break;
			case "2" : 
				if(Main.login_member_no == 0) {
					System.out.println("로그인이 필요한 서비스입니다");
				} else {
					try {
						user.askQuestion(conn);
					} catch (SQLException e1) {
						System.out.println("질문 등록에 실패 했습니다...");
					} 
				} break;
			case "99" : try {
					user.userList(conn);
				} catch (SQLException e) {
					System.out.println("유저목록 불러오기 실패");
				} break;
			default:
				System.out.println("잘못 입력하셨습니다….");
			}
		}
		
	}

	
}
