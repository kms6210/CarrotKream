package com.kh.admin;

import java.sql.Connection;

import com.kh.main.Main;

public class AdminService {

	public void adminPage(Connection conn) throws Exception {
		Admin admin = new Admin();
		System.out.print("1. 유저 목록 보여주기\n"
				+ "2. 물건 판매 여부 처리하기\n"
				+ "3. 아이디 정지하기\n"
				+ "4. 품질 판정하기\n"
				+ "5. 수수료 받기\n"
				+ "6. 질문에 대해 답변 하기\n"
				+ "7. 공지사항 작성하기\n"
				+ "8. 공지사항 삭제하기\n"
				+ "9. 자주 묻는 질문 목록\n");
		
		System.out.print("번호를 입력 하세요 : ");
		String inPut = Main.SC.nextLine();
		
		switch(inPut) {
		
		case "1" : admin.showUserList(conn); {break;} // 유저 목록 보여주기
		case "2" : admin.deleteItem(conn); {break;} //물건 판매여부 처리하기
		case "3" : admin.banId(conn); {break;} //아이디 정지하기
		case "4" : admin.judgeQuality(conn); {break;} // 품질 판정하기
		case "5" : admin.updateAdminBalance(conn); {break;} // 수수료 받기
		case "6" : admin.answerQuestion(conn); {break;} //질문에 대해 답변하기
		case "7" : admin.writePublic(conn); {break;} // 공지사항 작성하기
		case "8" : admin.deletePublic(conn); {break;} // 공지사항 삭제하기
		case "9" : admin.showFaqList(conn); {break;} // 자주묻는 질문 목록
		default : System.out.println("잘못입력 하셨습니다.");
		}
	}
}
