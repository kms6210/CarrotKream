package com.kh.main;

import java.sql.Connection;

import com.kh.account.Account;
import com.kh.item.Item;
import com.kh.jdbc.JdbcTemplate;
import com.kh.mutualAction.MutualAction;
import com.kh.user.User;

public class MainService {
	int user_no = Main.login_member_no;
	private boolean authenticate() {
		if (Main.login_member_no == 0) {
			System.out.println("로그인 한 유저만 작업이 가능합니다.");
			return false;
		} else {
			return true;
		}
	}

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
		// 정보 보여주기 -> 꾸며야함
		System.out.println("1.Account, 4.Item, 5.MutualAction, 6.User");
	}

	public void processService(String input) throws Exception {
		Connection conn = JdbcTemplate.m01();

		switch (input) {
		case "1":
			if(!authenticate()) {
				break;
			}
			executeAccount(conn);
			break;
		case "2":
			executeAdmin(conn);
			break;
		case "3":
			executeAuction(conn);
			break;
		case "4":
			if(!authenticate()) {
				break;
			}
			executeItem(conn);
			break;
		case "5":
			if(!authenticate()) {
				break;
			}
			executemutualAction(conn);
			break;
		case "6":
			executeUser(conn);
			break;
		default:
			System.out.println("잘못 입력하셨습니다….");
		}

		System.out.println("");
		conn.close();
	}

	private void executeAccount(Connection conn) {
		try {
			Account account = new Account();
			System.out.print("[1.충전, 2.인출, 3.이체, 4.계좌 내역] 번호를 선택하세요 : ");
			String num = Main.SC.nextLine();
			if (num.equals("1")) {
				account.deposit(user_no, conn);
			} else if (num.equals("2")) {
				account.withdraw(user_no, conn);
			} else if (num.equals("3")) {
				System.out.print("상대의 유저 번호를 입력하세요 : ");
				int target_no = Integer.parseInt(Main.SC.nextLine());
				account.transfer(user_no, target_no, conn);
			} else if (num.equals("4")){
				account.showAccount(user_no, conn);
			} else {
				System.out.println("잘못 입력하셨습니다.");
			}
		} catch (Exception e) {
			System.out.println("숫자를 입력해주세요.");
		}
	}

	private void executeAdmin(Connection conn) {

	}

	private void executeAuction(Connection conn) {

	}

	private void executeItem(Connection conn) {
		try {
			Item item = new Item();
			System.out.println("========글쓰기========");
			item.registSellItem(conn, 0, user_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executemutualAction(Connection conn) {
		try {
			MutualAction mutualAction = new MutualAction();
			System.out.print("상품 번호를 입력하세요 : ");
			int item_no = Integer.parseInt(Main.SC.nextLine());
			mutualAction.setLikeList(conn, 1, item_no);
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요.");
		} catch (Exception e) {
			System.out.println("이미 찜한 상품입니다.");
		}
	}

	private void executeUser(Connection conn) {
		try {
			User user = new User();
			System.out.print("[1. 회원가입, 2. 로그인] : ");
			int num = Integer.parseInt(Main.SC.nextLine());
			if (num == 1) {
				user.join(conn);
			} else if (num == 2) {
				user.login(conn);
			} else {
				System.out.println("잘못 입력하셨습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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