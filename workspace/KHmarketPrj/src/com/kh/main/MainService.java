package com.kh.main;

public class MainService {
	private MainProcess mp = new MainProcess();
	
	public boolean startService() {
		// kh 마켓 실행
		
		int user_no = Main.login_member_no;
		int admin_no = Main.login_admin_no;
		try {
			if(mp.processService(user_no, admin_no)) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("※ 데이터베이스와 연결해주세요 ※");
		}
		
		return false;
	}
	
	public void showThema() {
		System.out.println(" _____                            _        _   __                               ");
		System.out.println("/  __ \\                          | |      | | / /                              ");
		System.out.println("| /  \\/  __ _  _ __  _ __   ___  | |_     | |/ /  _ __   ___   __ _  _ __ ___  ");
		System.out.println("| |     / _` || '__|| '__| / _ \\ | __|    |    \\ | '__| / _ \\ / _` || '_ ` _ \\ ");
		System.out.println("| \\__/\\| (_| || |   | |   | (_) || |_     | |\\  \\| |   |  __/| (_| || | | | | |");
		System.out.println(" \\____/ \\__,_||_|   |_|    \\___/  \\__|    \\_| \\_/|_|    \\___| \\__,_||_| |_| |_| \n");
		System.out.println("                                           99 : 이전 페이지로 이동  /  9 : 프로그램 종료\n");	}
}