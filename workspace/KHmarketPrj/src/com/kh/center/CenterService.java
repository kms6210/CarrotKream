package com.kh.center;

import java.sql.Connection;

import com.kh.main.Main;
import com.kh.user.User;
import com.kh.user.UserInput;

public class CenterService {
	private Center center = new Center();

	public void centerPage(Connection conn) throws Exception {
		boolean isFinish = false;
		
		while(!isFinish) {
			System.out.println("\n==================");
			System.out.println("★ 방문자 고객센터 페이지 ★\n\n");
			System.out.println("1. FAQ 보기\n"
						 	 + "2. 탈퇴 계정 복구\n"
							 + "3. 일반 문의 하기");
			System.out.println("==================");
			
			System.out.print("번호를 입력하세요 : ");
			String input = Main.SC.nextLine();
			
			try {
				switch (input) {
					case "1" : { center.showFAQ(conn); System.out.println(); break; }
					case "2" : { center.askIdRestore(conn); System.out.println(); break; }
					case "3" : { center.ask(conn); System.out.println(); break; }
					case "99" : { System.out.println(); isFinish = true; break; }
					default: { throw new Exception("※ 잘못된 입력입니다 ※"); } 
				}
			} catch(Exception e) {
				System.out.println("\n" + e.getMessage());
			}
		}
	}
	
	public void userCenterPage(Connection conn) throws Exception {
		boolean isFinish = false;
		
		while(!isFinish) {
			System.out.println("\n==================");
			System.out.println("★ 사용자 고객센터 페이지 ★\n");
			UserInput.usernick(conn);
			System.out.println("1. FAQ 보기\n"
							 + "2. 내가 쓴 QnA 확인하기 \n"
							 + "3. 일반 문의하기");
			System.out.println("==================");
			
			System.out.print("번호를 입력하세요 : ");
			String input = Main.SC.nextLine();
			
			try {
				switch (input) {
					case "1" : { center.showFAQ(conn); System.out.println(); break; }
					case "2" : { new User().QuestionList(conn); break; }
					case "3" : { center.ask(conn); System.out.println(); break; }
					case "99" : { System.out.println(); isFinish = true; break; }
					default: { throw new Exception("※ 잘못된 입력입니다 ※"); } 
				}
			} catch(Exception e) {
				System.out.println("\n" + e.getMessage());
			}
		}
	}
}
