package com.kh.center;

import java.sql.Connection;

public class Center {
	private CenterInput ci = new CenterInput();
	
	// 자주묻는질문 출력
	public void showFAQ(Connection conn) throws Exception {
		ci.showFAQ(conn);
	}
	
	// 탈퇴한 아이디 복구
	public void askIdRestore(Connection conn) throws Exception {
		int result = ci.restoreId(conn);
		if(result == 1) { System.out.println("\n※ 아이디 복구 완료 ※"); }
		else { throw new Exception("※ 복구 오류 ※"); }
	}
	
	// 문의
	public void ask(Connection conn) throws Exception {
		int result = ci.ask(conn);
		if(result == 1) { System.out.println("\n※ 문의 완료 ※"); }
		else { throw new Exception("※ 문의 오류 ※"); }
	}
}
