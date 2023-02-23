package com.kh.account;

import java.sql.Connection;
import java.sql.ResultSet;
import com.kh.main.Main;

public class Account {
	private AccountInput ai = new AccountInput();

	public void deposit(Connection conn) throws Exception { 
		// 포인트 충전
		int[] arr = ai.deposit(conn);
		
		if (arr[0] == 1 && arr[1] == 1) {
			System.out.println("\n※ " + arr[2] + "포인트 충전 완료 ※\n");
		} else {
			throw new Exception("※ 충전 실패 ※");
		}
	}

	public void withdraw(Connection conn) throws Exception { 
		// 포인트 인출
		int[] arr = ai.withdraw(conn);
		
		if (arr[0] == 1 && arr[1] == 1) {
			System.out.println("\n※ " + arr[2] + "포인트 인출 완료 ※\n");
		} else {
			throw new Exception("※ 충전 실패 ※");
		}
	}

	public void transfer(Connection conn) throws Exception {
		// 이체
		int[] arr = ai.transfer(conn);
		
		if (arr[0] == 1 && arr[1] == 1 && arr[2] == 1) {
			System.out.println("\n※ 이체 완료 ※\n");
		} else {
			throw new Exception("※ 이체 실패 ※");
		}
	}

	public void showAccount(Connection conn) throws Exception {
		// 계좌 내역 출력
		ai.showAccount(conn);
	}
	
	public void seeBalance(Connection conn) throws Exception {
		// 내 잔액 보기
		System.out.println("[잔고 : " + ai.seeBalance(conn) + "]");
	}
}
