package com.kh.main;

import java.sql.Connection;

import com.kh.account.AccountService;
import com.kh.admin.Admin;
import com.kh.auction.Auction;
import com.kh.auction.AuctionService;
import com.kh.item.Item;
import com.kh.item.ItemService;
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
		new AccountService().accountPage(conn);
	}

	public void executeAdmin(Connection conn) {
		if(!authenticate()) { return; }
		// new AdminService().adminPage(conn);
	}

	public void executeAuction(Connection conn) throws Exception {
		if(!authenticate()) { return; }
		new AuctionService().auctionPage(conn);
	}

	public void executeItem(Connection conn) throws Exception {
		if(!authenticate()) { return; }
		 new ItemService().itemPage(conn);
	}

	public void executemutualAction(Connection conn) {
		if(!authenticate()) { return; }
		// new MutualActionService().mutualActionPage(conn);
	}

	public void executeUser(Connection conn) throws Exception {
		new UserService().userPage(conn);
	}
	
}
