package com.kh.center;

import java.sql.Connection;
import java.sql.ResultSet;

import com.kh.main.Main;

public class CenterInput {
	private CenterSQL cSQL = new CenterSQL();

	public void showFAQ(Connection conn) throws Exception {
		ResultSet rs = cSQL.showFAQ(conn);
		System.out.println();
		
		System.out.println("[자주 묻는 질문]");
		while (rs.next()) {
			int question_no = rs.getInt("QUESTION_NO");
			String question = rs.getString("QUESTION");
			String answer = rs.getString("ANSWER");
			
			System.out.println(question_no + ". " + question + " ?   ||   " + answer);
		}
	}
	
	public int restoreId(Connection conn) throws Exception {
		String phone_no = cSQL.makePhoneNo();
		cSQL.showSelectedId(phone_no, conn);
		System.out.print("\n복구할 아이디 : ");
		String id = Main.SC.nextLine();
		return cSQL.restoreId(id, phone_no, conn);
	}
	
	public int ask(Connection conn) throws Exception {
		return cSQL.ask(conn);
	}
}
