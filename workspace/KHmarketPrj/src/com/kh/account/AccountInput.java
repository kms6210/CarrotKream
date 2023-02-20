package com.kh.account;

import java.sql.Connection;
import java.sql.ResultSet;

import com.kh.main.Main;

public class AccountInput {
	private int user_no = Main.login_member_no;
	private AccountSQL aSQL = new AccountSQL();
	
	private int inputPrice() throws Exception {
		System.out.print("금액 : ");
		int price = Integer.parseInt(Main.SC.nextLine());
		return price;
	}

	public int[] deposit(Connection conn) throws Exception { 
		// 포인트 충전
		int price = inputPrice();
		int balance = aSQL.selectBalance(user_no, conn);
		int[] arr = new int[3];

		if(price <= 0) { throw new Exception(); }
		
		arr[0] = aSQL.updateBalance(user_no, price, balance, conn);
		arr[1] = aSQL.insertAccount(user_no, user_no, price, conn);
		arr[2] = price;
		
		return arr;
	}

	public int[] withdraw(Connection conn) throws Exception { 
		// 포인트 인출
		int price = inputPrice();
		int balance = aSQL.selectBalance(user_no, conn);
		int[] arr = new int[3];

		if(price <= 0) { throw new Exception(); }
		
		arr[0] = aSQL.updateBalance(user_no, -1 * price, balance, conn);
		arr[1] = aSQL.insertAccount(user_no, user_no, -1 * price, conn);
		arr[2] = price;
		
		return arr;
	}

	public int[] transfer(Connection conn) throws Exception {
		// 이체
		System.out.print("상대의 유저 번호를 입력하세요 : ");
		int target_no = Integer.parseInt(Main.SC.nextLine());
		int price = inputPrice();
		int userBalance = aSQL.selectBalance(user_no, conn);
		int targetBalance = aSQL.selectBalance(target_no, conn);
		int[] arr = new int[3];
	
		if(price <= 0) { throw new Exception(); }
		if(userBalance - price <= 0) { System.out.println("잔액이 부족합니다."); return null; }		
		
		arr[0] =  aSQL.updateBalance(target_no, price, targetBalance, conn);
		if(arr[0] == 0) { System.out.println("거래 대상을 찾을 수 없습니다"); return null; }
		arr[1] = aSQL.updateBalance(user_no, -1 * price, userBalance, conn);
		arr[2] = aSQL.insertAccount(user_no, target_no, -1 * price, conn);
		
		return arr;
	}

	public void showAccount(Connection conn) throws Exception {
		// 계좌 내역 출력
		ResultSet rs = aSQL.showAccount(user_no, conn);
		
		while (rs.next()) {
			int target_no = rs.getInt("TARGET_NO");
			int price = rs.getInt("PRICE");
			String use_date = rs.getString("USE_DATE");
			
			if(user_no == target_no) {
				if(price > 0) {
					System.out.println("[+] 내 kh머니에 " + price + "원 충전함 // " + use_date);
				} else {
					System.out.println("[-] 내 kh머니에서 " + -1 * price + "원 인출함 // " + use_date);
				}
			} else {
				if(price < 0) {
					System.out.println("[-] " + target_no + "번 유저에게 " + -1 * price + "원 입금함 // " + use_date);
				} else {
					System.out.println("[+] " + target_no + "번 유저에게서 " + price + "원 입금받음 // " + use_date);
				}
			}
		}
	}
}
