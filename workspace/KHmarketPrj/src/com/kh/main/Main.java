package com.kh.main;

import java.util.Scanner;

public class Main {
	public static final Scanner SC = new Scanner(System.in);
	public static int login_member_no = 0;

	public static void main(String[] args) {
		boolean isFinish = false;
		while (!isFinish) {
			System.out.println("==================");
			MainService ms = new MainService();
			isFinish = ms.startService();
		}
	}

}
