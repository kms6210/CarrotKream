package com.kh.main;

import java.util.Scanner;

public class Main {
	public static final Scanner SC = new Scanner(System.in);
	public static String login_member_nick;

	public static void main(String[] args) throws Exception {
		// 예외 처리 해야함 
		MainService ms = new MainService();
		boolean isFinish = false;
		while (!isFinish) {
			System.out.println("==================");
			isFinish = ms.startService();
		}
	}

}
