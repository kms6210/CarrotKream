package com.kh.main;

import java.sql.Connection;

import com.kh.account.Account;
import com.kh.jdbc.JdbcTemplate;
import com.kh.mutualAction.MutualAction;

public class MainService {
	public boolean startService() throws Exception {
		// kh 마켓 실행
		showMarket();
		System.out.print("번호를 입력하세요 : ");
		String input = Main.SC.nextLine();
		if (input.equals("9")) {
			System.out.println("프로그램을 종료합니다.");
			return true;
		} else {
			processService(input);
			return false;
		}
	}

	public void showMarket() {
		// 정보 보여주기
		System.out.println("1. 찜하기, 2. 내 계좌 관리");
	}

	public void processService(String input) throws Exception {
		Connection conn = JdbcTemplate.m01();

		MutualAction mutualAction = new MutualAction();
		Account account = new Account();

		switch (input) {
		case "1":
			try {
				mutualAction.setLikeList(conn, 1, 2);
			} catch (Exception e) {
				System.out.println("이미 찜한 상품입니다.");
			}
			break;
			
		case "2":
			System.out.print("유저 번호를 입력하세요 : "); // 로그인으로 대체해야함
			int user_no = 0;
			
			try {
				user_no = Integer.parseInt(Main.SC.nextLine());
				System.out.print("[1. 충전, 2. 인출, 3. 이체] 번호를 선택하세요 : ");
				String num = Main.SC.nextLine();
				if (num.equals("1")) {
					account.deposit(user_no, conn);
				} else if (num.equals("2")) {
					account.withdraw(user_no, conn);
				} else if (num.equals("3")) {
					System.out.print("상대의 유저 번호를 입력하세요 : ");
					int target_no = Integer.parseInt(Main.SC.nextLine());
					account.transfer(user_no, target_no, conn);
				} else {
					System.out.println("잘못 입력하셨습니다.");
				}
			} catch (Exception e) {
				System.out.println("숫자를 입력해주세요.");
			}

			break;
			
		case "3":
			break;
			
		case "4":
			break;
			
		case "5":
			break;
			
		default:
			System.out.println("잘못 입력하셨습니다….");
		}

		System.out.println("");
		conn.close();
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
}