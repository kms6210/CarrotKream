package com.kh.main;

import java.sql.Connection;

import com.kh.account.AccountService;
import com.kh.auction.Auction;
import com.kh.auction.AuctionService;
import com.kh.center.CenterService;
import com.kh.item.ItemSearch;
import com.kh.item.ItemService;
import com.kh.jdbc.JdbcTemplate;
import com.kh.user.User;
import com.kh.user.UserInput;
import com.kh.user.UserService;



public class MainProcess {
	public static final int ARAMDOM = (int) (Math.round(Math.random()*10000)+1);
	
	private User user = new User();
	private ItemSearch ish = new ItemSearch();
	private Auction auction = new Auction();

	private AccountService acs = new AccountService();
	private UserService us = new UserService();
	private ItemService ise = new ItemService();
	private AuctionService ats = new AuctionService();
	private CenterService cs = new CenterService();
	
	private String showVPage() {
		System.out.println("==================");
		System.out.println("방문자 페이지");
        System.out.println("방문자 페이지");
        System.out.println("\n안녕하세요");
        System.out.println("방문자 "+ ARAMDOM  +" 님\n");
		System.out.println("------------------");
        System.out.println("1.회원가입, 2.로그인, 3.Id/Pwd 찾기");
        System.out.println("4.상품조회, 5.경매조회, 6.고객센터 페이지, 9. 프로그램 종료\n");
		return inputNum();
	}
	
    private String showUPage(Connection conn) throws Exception {
		System.out.println("===== 사용자 페이지 =====");
        UserInput.userInfo(conn);
		System.out.println("1.마이페이지, 2.채팅하기, 3.상품 페이지");
		System.out.println("4.경매 페이지, 5.고객센터 페이지, 9.프로그램 종료 \n");
		return inputNum();
	}
	
	private String showAPage() {
		System.out.println("===== 관리자 페이지 =====");
		System.out.println("1.마이페이지, 2.상품관리, 3.유저관리, 4.공지사항관리, 5.QNA관리, 9.프로그램 종료 \n");
		return inputNum();
	}
	
	private String inputNum() {
		System.out.print("번호를 입력하세요 : ");
		String input = Main.SC.nextLine();
		System.out.println();
		return input;
	}
	
	private boolean closeProgram(String input) {
		if(input.equals("9")) { 
			System.out.println("프로그램을 종료합니다");
			return true; 
		} else {
			return false;
		}
	}
	
	public boolean processService(int user_no, int admin_no) throws Exception {
		// 세부 서비스 진행
		Connection conn = JdbcTemplate.m01();
		Boolean isFinish = false;
		
		if(user_no == 0 && admin_no == 0) {
			String input = showVPage();
			if(closeProgram(input)) { return true;}
			processVservice(input, conn);
		} else if (user_no != 0) {
            String input = showUPage(conn);
			if(closeProgram(input)) { return true;}
			processUservice(input, conn);
		} else {
			String input = showAPage();
			if(closeProgram(input)) { return true;}
			processAservice(input, conn);
		}
		
		System.out.println("");
		conn.close();
	
		
		return isFinish;
	}
	
	private void processVservice(String input, Connection conn) throws Exception {
		switch (input) {
			case "1": if(user.join(conn) == 0) { throw new Exception("회원가입 실패"); } else { System.out.println("\n회원등록 완료"); } break;
			case "2": if(user.login(conn) == 0) { throw new Exception("로그인 실패"); } break;
			case "3": user.findIdPwd(conn); break;
			case "4": ish.itemView(conn); break;
			case "5": auction.showAuction(conn); break;
			case "6": cs.centerPage(conn); break;
			default: throw new Exception("잘못된 입력입니다.");
		}
	}
	
	private void processUservice(String input, Connection conn) throws Exception {
		switch (input) {
			case "1": 
				System.out.println("1.계좌 페이지 / 2.유저 페이지"); 
				String num = inputNum();
				if(num.equals("1")) { acs.accountPage(conn); } 
                else if (num.equals("2")){ us.userPage(conn , ARAMDOM); } 
				else { throw new Exception("잘못 입력했습니다.");} break;
			case "2": break;
			case "3": ise.itemPage(conn); break;
			case "4": ats.auctionPage(conn); break;
			case "5": cs.centerPage(conn); break;
			default: throw new Exception("잘못된 입력입니다.");
		}
	}
	
	private void processAservice(String input, Connection conn) throws Exception {
		switch (input) {
			case "1": break;
			case "2": break;
			case "3": break;
			case "4": break;
			case "5": break;
			default: throw new Exception("잘못된 입력입니다.");
		}
		
	}
}
