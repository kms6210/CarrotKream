package com.kh.main;

import java.util.Scanner;

public class Main {
	public static final Scanner SC = new Scanner(System.in);
	public static int login_member_no = 0;
	public static int login_admin_no = 0;
	
	public static int integerParseInt() throws Exception {
		try {
			return Integer.parseInt(SC.nextLine());
		} catch (Exception e){
			throw new Exception("※ 숫자를 입력해주세요 ※");
		}
	}
	
	public static void main(String[] args) {
		new MainService().showThema();
		
		boolean isFinish = false;
		while (!isFinish) {
			MainService ms = new MainService();
			isFinish = ms.startService();
		}
	}

}
