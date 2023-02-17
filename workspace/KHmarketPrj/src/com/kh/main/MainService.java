package com.kh.main;

import java.sql.Connection;

import com.kh.jdbc.JdbcTemplate;
import com.kh.mutualAction.MutualAction;

public class MainService {
	public boolean startService() throws Exception {
		// kh 마켓 실행
		showMarket();
		System.out.print("번호를 입력하세요 : ");
		String input = Main.SC.nextLine();
		if(input.equals("9")) {
			System.out.println("프로그램을 종료합니다.");
			return true;
		}
		else {
			processService(input);
			return false;
		}
	}

	public void showMarket() {
		// 정보 보여주기
	}
	
	public void processService() {
		// 서비스 진행
	}
	
	public void showPopularItems() {
		// 인기 품목 노출
	}
	
	public void showFAQ() {
		// 자주 묻는 질문 출력 
	}
	
	public void processService(String input) throws Exception {
		Connection conn = JdbcTemplate.m01();
		
		MutualAction mutualAction = new MutualAction();
		switch(input) {
		case "1" : mutualAction.setLikeList(conn, 1, 1); break;
		case "2" : break;
		case "3" : break;
		case "4" : break;
		case "5" : break;
		default : System.out.println("잘못 입력하셨습니다….");
		}
		
		System.out.println("");
		conn.close();
	}
}