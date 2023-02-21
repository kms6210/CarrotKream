package com.kh.main;

import java.sql.Connection;
import com.kh.account.AccountService;
import com.kh.admin.Admin;
import com.kh.admin.AdminService;
import com.kh.auction.AuctionService;
import com.kh.center.CenterService;
import com.kh.item.ItemService;
import com.kh.user.UserService;

public class MainProcess {
	private void authenticate() throws Exception {
		if (Main.login_member_no == 0) {
			throw new Exception("로그인 한 유저만 작업이 가능합니다.");
		}
	}
	
	public void executeAccount(Connection conn) throws Exception {
		authenticate();
		new AccountService().accountPage(conn);
	}

	public void executeAdmin(Connection conn) throws Exception {
		authenticate();
		 new AdminService().adminPage(conn);
	}

	public void executeAuction(Connection conn) throws Exception {
		authenticate();
		new AuctionService().auctionPage(conn);
	}

	public void executeItem(Connection conn) throws Exception {
		authenticate();
		new ItemService().itemPage(conn);
	}

	public void executemutualAction(Connection conn) throws Exception {
//		authenticate();
		// new MutualActionService().mutualActionPage(conn);
	}

	public void executeUser(Connection conn) throws Exception {
		new UserService().userPage(conn);
	}
	
	public void executeCenter(Connection conn) throws Exception {
		new CenterService().centerPage(conn);
	}
	
}
