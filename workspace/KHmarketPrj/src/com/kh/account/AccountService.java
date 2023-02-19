package com.kh.account;

import java.sql.Connection;
import com.kh.main.Main;

public class AccountService {
	private Account account = new Account();

	public void accountPage(Connection conn) throws Exception {
		System.out.println("==================");
		System.out.println("계좌 페이지");
		System.out.println("------------------");
		System.out.println("1. kh머니 충전\n"
					 	 + "2. kh머니 인출\n"
						 + "3. 이체\n"
						 + "4. 계좌 내역");
		
		System.out.print("번호를 입력하세요 : ");
		String input = Main.SC.nextLine();
		System.out.println("");
		
		switch (input) {
		case "1" : { account.deposit(conn); break; }
		case "2" : { account.withdraw(conn); break; }
		case "3" : { account.transfer(conn); break; }
		case "4" : { account.showAccount(conn); break; }
		default: { System.out.println("잘못된 입력입니다."); } 
		}
	}

}
