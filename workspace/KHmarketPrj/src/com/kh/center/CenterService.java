package com.kh.center;

import java.sql.Connection;

import com.kh.main.Main;

public class CenterService {
	private Center center = new Center();

	public void centerPage(Connection conn) throws Exception {
		boolean isFinish = false;
		
		while(!isFinish) {
			System.out.println("\n==================");
			System.out.println("고객센터 페이지");
			System.out.println("------------------");
			System.out.println("1. FAQ 보기\n"
						 	 + "2. 계정 복구 문의\n"
							 + "3. 개인 문의\n"
							 + "99. 메인 페이지로 돌아가기");
			
			System.out.print("\n번호를 입력하세요 : ");
			String input = Main.SC.nextLine();
			
			try {
				switch (input) {
					case "1" : { center.showFAQ(conn); break; }
					case "2" : { center.askIdRestore(conn); break; }
					case "3" : { center.ask(conn);; break; }
					case "4" : { ; break; }
					case "99" : { isFinish = true; break; }
					default: { throw new Exception("잘못된 입력입니다."); } 
				}
			} catch(Exception e) {
				System.out.println("\n" + e.getMessage());
			}
		}
	}
}
