package com.kh.account;

import java.sql.Connection;

import com.kh.auction.Auction;
import com.kh.main.Main;

public class AccountService {
	private Account account = new Account();

	public void accountPage(Connection conn) throws Exception {
		boolean isFinish = false;
		
		while(!isFinish) {
			System.out.println("\n==================");
	        System.out.println("★ 계좌 페이지 ★");
	        account.seeBalance(conn);
	        System.out.println("\n1. kh머니 충전\n2. kh머니 인출\n3. 이체\n4. 계좌 내역");
	        System.out.println("==================");
			System.out.print("번호를 입력하세요 : ");
			String input = Main.SC.nextLine();
			
			try {
				switch (input) {
					case "1" : { account.deposit(conn); break; }
					case "2" : { account.withdraw(conn); break; }
					case "3" : { account.transfer(conn); break; }
					case "4" : { account.showAccount(conn); break; }
					case "99" : { System.out.println(); isFinish = true; break; }
					default: { throw new Exception("※ 잘못된 입력입니다 ※"); } 
				}
			} catch(Exception e) {
				System.out.println("\n" + e.getMessage() +"\n");
			}
		}
	}

}
