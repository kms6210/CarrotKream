package com.kh.main;

import java.sql.Connection;

import com.kh.jdbc.JdbcTemplate;


public class MainService {
	public void showMarket() {
		// 정보 보여주기 -> 꾸며야함
		System.out.println("========================= Main Page =========================");
		System.out.println("1.Account, 2.Admin, 3.Auction, 4.Item, 5.MutualAction, 6.User, 7.Center");
		System.out.println("9.프로그램 종료 \n");
	}

	public boolean startService() {
		// kh 마켓 실행
		showMarket();
		
		System.out.print("번호를 입력하세요 : ");
		String input = Main.SC.nextLine();
		
		if (input.equals("9")) {
			System.out.println("\n프로그램을 종료합니다.");
			return true;
		} else {
			try {
				processService(input);
			} catch (Exception e) {
				System.out.println("\n" + e.getMessage() + "\n");
			}
			return false;
		}
	}


	public void processService(String input) throws Exception {
		// 세부 서비스 진행
		
		Connection conn = JdbcTemplate.m01();
		MainProcess mp = new MainProcess();
		
		switch (input) {
		case "1": mp.executeAccount(conn); break;
		case "2": mp.executeAdmin(conn); break;
		case "3": mp.executeAuction(conn); break;
		case "4": mp.executeItem(conn); break;
		case "5": mp.executemutualAction(conn); break;
		case "6": mp.executeUser(conn); break;
		case "7": mp.executeCenter(conn); break;
		default: throw new Exception("잘못된 입력입니다."); 
		}

		System.out.println("");
		conn.close();
	
	}
}