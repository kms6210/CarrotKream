package com.kh.main;

import java.sql.Connection;

import com.kh.account.AccountService;
import com.kh.admin.Admin;
import com.kh.auction.Auction;
import com.kh.item.Item;
import com.kh.mutualAction.MutualAction;
import com.kh.user.UserService;

public class MainProcess {
	private boolean authenticate() {
		if (Main.login_member_no == 0) {
			System.out.println("로그인 한 유저만 작업이 가능합니다.");
			return false;
		} else {
			return true;
		}
	}
	
	public void executeAccount(Connection conn) throws Exception {
		if(!authenticate()) { return; }
		AccountService as = new AccountService();
		as.accountPage(conn);
	}

	public void executeAdmin(Connection conn) {
		if(!authenticate()) { return; }
		Admin admin = new Admin();
		// admin.adminPage(conn);
	}

	public void executeAuction(Connection conn) {
		if(!authenticate()) { return; }
		Auction auction = new Auction();
		// auction.AuctionPage(conn);
	}

	public void executeItem(Connection conn) {
		if(!authenticate()) { return; }
		Item item = new Item();
		// item.ItemPage(conn);
	}

	public void executemutualAction(Connection conn) {
		if(!authenticate()) { return; }
		MutualAction ma = new MutualAction();
		// ma.MutualActionPage(conn);
	}

	public void executeUser(Connection conn) {
		UserService us = new UserService();
		us.userPage(conn);
	}
	
	
	public void showPopularItems() {
		// 인기 품목 노출
	}

	public void showFAQ() {
		// 자주 묻는 질문 출력
	}
}
