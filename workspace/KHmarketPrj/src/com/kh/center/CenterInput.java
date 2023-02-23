package com.kh.center;

import java.sql.Connection;
import java.sql.ResultSet;

import com.kh.main.Main;

public class CenterInput {
	private CenterSQL cSQL = new CenterSQL();

	public void showFAQ(Connection conn) throws Exception {
		ResultSet rs = cSQL.showFAQ(conn);
		System.out.println();
		
		while (rs.next()) {
			int question_no = rs.getInt("QUESTION_NO");
			String question = rs.getString("QUESTION");
			String answer = rs.getString("ANSWER");
			
			System.out.println(question_no + ". " + question + "?   :   " + answer);
		}
	}
	
	public int restoreId(Connection conn) throws Exception {
		String phone_no = cSQL.makePhoneNo();
		String id = cSQL.showSelectedId(phone_no, conn);
		return cSQL.restoreId(id, phone_no, conn);
	}
	
	public int ask(Connection conn) throws Exception {
		return cSQL.ask(Main.login_member_no, conn);
	}
}
