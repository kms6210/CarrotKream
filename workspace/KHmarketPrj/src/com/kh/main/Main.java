package com.kh.main;

public class Main {

	public static void main(String[] args) {
		MainService ms = new MainService();
		boolean isFinish = false;
		while (!isFinish) {
			isFinish = ms.startService();
		}
	}

}
