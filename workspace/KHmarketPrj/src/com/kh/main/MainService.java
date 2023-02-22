package com.kh.main;

public class MainService {
	private MainProcess mp = new MainProcess();
	
	public boolean startService() {
		// kh 마켓 실행
		try {
			int user_no = Main.login_member_no;
			int admin_no = Main.login_admin_no;
			if(mp.processService(user_no, admin_no)) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + "\n");
		}
		return false;
	}
	
	public void showThema() {
		System.out.println(" _____                            _        _   __                               ");
		System.out.println("/  __ \\                          | |      | | / /                              ");
		System.out.println("| /  \\/  __ _  _ __  _ __   ___  | |_     | |/ /  _ __   ___   __ _  _ __ ___  ");
		System.out.println("| |     / _` || '__|| '__| / _ \\ | __|    |    \\ | '__| / _ \\ / _` || '_ ` _ \\ ");
		System.out.println("| \\__/\\| (_| || |   | |   | (_) || |_     | |\\  \\| |   |  __/| (_| || | | | | |");
		System.out.println(" \\____/ \\__,_||_|   |_|    \\___/  \\__|    \\_| \\_/|_|    \\___| \\__,_||_| |_| |_| \n\n");
	}
}