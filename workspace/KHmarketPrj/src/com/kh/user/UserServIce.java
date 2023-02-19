package com.kh.user;

import java.sql.Connection;

import com.kh.main.Main;

public class UserServIce {
	
	private User user = new User();
	
	public void userPage(Connection conn) throws Exception {
		System.out.println("==================");
		System.out.println("유저 페이지");
		System.out.println("------------------");
		System.out.println("1. 회원가입\n"
						 + "2. 로그인\n"
						 + "3. 아이디 찾기(힌트)\n"
						 + "4. 비밀번호 찾기(아이디 & 힌트)\n"
						 + "5. 회원 탈퇴\n"
						 + "6. 질문하기\n"
						 + "99. 유저조회(테스트용)");
		
		System.out.print("번호를 입력하세요 : ");
		String input = Main.SC.nextLine();
		System.out.println("");
		
		switch (input) {
		case "1" : user.join(conn); break;
		case "2" : user.login(conn); break;
		case "3" : user.findId(conn); break;
		case "99" : user.userList(conn); break;
		default:
			System.out.println("잘못 입력하셨습니다….");
		}
		
	}
	
}
