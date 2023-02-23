package com.kh.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.kh.item.ItemSearch;
import com.kh.main.Main;
import com.kh.main.MainProcess;

// 테이블 : 유저, 상품, 제재 내역, 품질 검증, 계좌, Q&A, 공지사항

public class Admin {
	
	public void publicWandD(Connection conn) throws Exception {
		System.out.print("1.조회 / 2.작성 / 3.삭제 : ");
		String input = Main.SC.nextLine();
		if (input.equals("1")) {
			new AdminTemp().showNoticeList(conn);
		} else if (input.equals("2")) {
			writePublic(conn);
		} else if (input.equals("3")){
			deletePublic(conn);
		} else {
			throw new Exception("※ 잘못된 입력입니다 ※");
		}
	}
	
	public void itemAdmin(Connection conn) throws Exception {
		System.out.print("1. 상품 조회 / 2. 상품 삭제 / 3. 품질 검사 : ");
		String input = Main.SC.nextLine();
		if (input.equals("1")) {
			new ItemSearch().itemView(conn);
		} else if(input.equals("2")) {
			deleteItem(conn);
		} else if(input.equals("3")) {
			judgeQuality(conn);
		} else {
			throw new Exception("※ 잘못된 입력입니다 ※");
		}
	}
	
	public void showAccount(Connection conn) throws Exception {
		// 계좌 내역 출력
		String sql = "SELECT * FROM ADMIN_ACCOUNT WHERE ADMIN_NO = ? ORDER BY USE_DATE";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Main.login_admin_no);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int user_no = rs.getInt("USER_NO");
			int price = rs.getInt("PRICE");
			String use_date = rs.getString("USE_DATE");
			 
			System.out.println("[+] " + user_no + "번 유저에게서 " + price + " 포인트 입금받음    " + use_date);
		}
		System.out.println();
	}
	
//	public void adminPage(Connection conn) throws Exception {
//		boolean isFinish = false;
//		while(!isFinish) {
//		System.out.println("\n==================");
//        System.out.println("★ 관리자 페이지 ★");
//        getAdminBalance(conn);
//		System.out.println("\n1.계좌 내역 조회");
//		System.out.println("==================");
//		
//		String num = new MainProcess().inputNum();
//		if(num.equals("1")) {
//			showUserList(conn);
//		} else if(num.equals("99")){
//			System.out.println(); isFinish = true;
//		} else {
//			System.out.println("\n※ 잘못된 입력입니다 ※\n");
//		}
//		}
//	}
	
	
	public void userAdmin(Connection conn) throws Exception {
		boolean isFinish = false;
		while(!isFinish) {
		System.out.println("\n==================");
        System.out.println("★ 유저 관리 페이지 ★");
        System.out.print("[총 회원 수 : " + userNum(conn) + "]\n");
		System.out.println("\n1.회원 목록 조회\n2.계정 정지");
		System.out.println("==================");

		String num = new MainProcess().inputNum();
		if(num.equals("1")) {
			showUserList(conn);
		} else if (num.equals("2")) {
			banId(conn);
		} else if(num.equals("99")){
			isFinish = true;
		} else {
			System.out.println("\n※ 잘못된 입력입니다 ※\n");
		}
		}
	}

	public void adminlogin(Connection conn) throws Exception {
		//관리자 로그인
		//데이터 입력받기
		String sql = "SELECT * FROM K_ADMIN WHERE UPPER(ID) = UPPER( ? ) AND PWD = ? AND KEY = ? AND QUIT_YN ='N' ";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		System.out.println("\n[관리자 계정 로그인]");
		System.out.print("ID 입력 : ");
		String id = Main.SC.nextLine();

		System.out.print("PWD 입력 : ");
		String pwd = Main.SC.nextLine();

		System.out.print("KEY 입력 : ");
		String key = Main.SC.nextLine();

		pstmt.setString(1, id);
		pstmt.setString(2, pwd);
		pstmt.setString(3, key);

		ResultSet rs = pstmt.executeQuery();

		if(rs.next()) {
			System.out.println("\n※ 관리자 로그인 완료 ※\n");
			Main.login_admin_no = rs.getInt("ADMIN_NO");
		}else {
			throw new Exception("※ 관리자 로그인 실패 ※");
		}
	}

	public int userNum(Connection conn) throws Exception {
		String sql = "SELECT * FROM K_USER";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		int num = 0;
		while(rs.next()) {
			num++;
		}
		return num;
	}
	
	// 회원 목록 조회
	public void showUserList(Connection conn) throws Exception {

		String sql = "SELECT * FROM K_USER";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		System.out.println("--------------------------------------------------------회원 목록------------------------------------------------------------------------");
		System.out.println("\n[ userNo | id | pwd | nick | phone_no | trust_level | address | balance | question_no | answer | user_status | sign_date ]");

		while(rs.next()) {
			 String userNo= rs.getString("USER_NO");
			 String id = rs.getString("ID");
			 String pwd = rs.getString("PWD");
			 String nick = rs.getString("NICK");
			 String phoneNo = rs.getString("PHONE_NO");
			 String trustLevel = rs.getString("TRUST_LEVEL");
			 String address = rs.getString("ADDRESS");
			 String balance = rs.getString("BALANCE");
			 String questionNo = rs.getString("QUESTION_NO");
			 String answer = rs.getString("ANSWER");
			 String userStatus = rs.getString("USER_STATUS");
			 String signDate = rs.getString("SIGN_DATE");
			System.out.println(userNo+" | "+id+" | "+pwd+" | "+nick+" | "+phoneNo+" | "+trustLevel+" | "+address+" | "+balance+" | "+questionNo+" | "+answer+" | "+userStatus+" | "+signDate);
		}
	}

public void deleteItem(Connection conn) throws Exception {
	// 상품 삭제
	String sql = "UPDATE ITEM SET TRADE_STATUS =? WHERE ITEM_NO =? ";
	PreparedStatement pstmt = conn.prepareStatement(sql);

	System.out.print("상품 번호를 입력 하세요 : ");
	String itemNo = Main.SC.nextLine();


	pstmt.setString(1, "D");
	pstmt.setString(2, itemNo);

	int result = pstmt.executeUpdate();

	if (result ==1) {
		System.out.println("\n※ " +  itemNo + "번 상품을 삭제하였습니다 ※\n");
	}else {
		throw new Exception("※ 실패 ※");
	}
}

public void banId(Connection conn) throws Exception {
	// 계정 정지하기 (ex. 7일 / 30일 / 1년 / 영구 ...)
	String sql = "UPDATE BANNED SET STOP_REASON = ? WHERE USER_NO =?";
	PreparedStatement pstmt = conn.prepareStatement(sql);

	System.out.print("정지할 유저 번호 : ");
	String userNo = Main.SC.nextLine();

	System.out.print("정지 사유 : ");
	String stopReason = Main.SC.nextLine();

	pstmt.setString(1, stopReason);
	pstmt.setString(2, userNo);

	int result = pstmt.executeUpdate();
	if(result ==1) {
		System.out.println("\n※ " + userNo + "번 유저의 계정을 정지시켰습니다 ※\n");
	}else {
		throw new Exception("※ 실패 ※");
	}

}

public void judgeQuality(Connection conn) throws Exception {
	// 품질 판정하기
	String sql = "UPDATE QUALITY SET GRADE  = ?   WHERE ITEM_NO= ?";
	PreparedStatement pstmt = conn.prepareStatement(sql);

	System.out.print("상품 번호: ");
	String itemNo = Main.SC.nextLine();

	System.out.print(itemNo +"번 상품 등급 점수: ");
	String grade = Main.SC.nextLine();

	pstmt.setString(1, grade);
	pstmt.setString(2, itemNo);
	int result =pstmt.executeUpdate();

	if(result ==1) {
		System.out.println("\n※ " + itemNo+ "번 상품 등급 판정 완료 ※\n");
	}else {
		throw new Exception("※ 실패 ※");
	}
}


public void getAdminBalance(Connection conn) throws Exception {
	//3.updateAdminBalance (유저번호 가격 밸런스)
//	//1,상품이랑 품질 테이블이랑 join 후 가격 상품번호 유저번호 select -품질판정하기 
//	String sql = "SELECT Q.ITEM_NO ,I.PRICE , I.USER_NO FROM ITEM I JOIN QUALITY Q ON I.ITEM_NO = Q.ITEM_NO";
//	PreparedStatement pstmt = conn.prepareStatement(sql);
//	ResultSet rs = pstmt.executeQuery();
//	
//	while(rs.next()) {
//		String qItemNo = rs.getString("Q.ITEM_NO");
//		String iPrice = rs.getString("I.PRICE");
//		String userNo = rs.getString("I.USER_NO");
//		System.out.println(userNo+"번 유저의 "+qItemNo+"번 상품은 "+iPrice+"원 입니다" );
//	}
	
	
	//2.admin balance <- admin테이블에서 balcan 값을 조회하기 select
	String sql = "SELECT BALANCE FROM K_ADMIN WHERE ADMIN_NO =1";
	PreparedStatement pstmt = conn.prepareStatement(sql);
	ResultSet rs = pstmt.executeQuery();
	
	if(rs.next()) {
		String balance = rs.getString("BALANCE");
		System.out.println("[잔고 : " + balance + "]");
	}else {
		throw new Exception("※ 실패 ※");
	}
	
}

public void answerQuestion(Connection conn) throws Exception {
	//질문 목록 보여주기
	AdminTemp adminTemp = new AdminTemp();
	adminTemp.showQuestionList(conn);

	// 답변하기
	String sql = "UPDATE QNA SET ANSWER  =? WHERE QUESTION_NO = ?";
	PreparedStatement pstmt = conn.prepareStatement(sql);

	System.out.print("질문 번호 : ");
	String questionNo = Main.SC.nextLine();

	System.out.print("답변 : ");
	String answer = Main.SC.nextLine();

	pstmt.setString(1,answer);
	pstmt.setString(2,questionNo);

	int result = pstmt.executeUpdate();

	if(result ==1) {
		System.out.println("\n※ 답변 작성 완료 ※\n");
	}else {
		throw new Exception("※ 답변 작성 실패 ※");
	}

}

public void writePublic(Connection conn) throws Exception {
	// 공지사항 작성하기
	String sql = "INSERT INTO NOTICE (PUBLIC_NO, TITLE, CONTENT, WRITE_DATE) VALUES(SEQ_PUBLIC_NO.NEXTVAL,?,?,SYSDATE)";
	PreparedStatement pstmt = conn.prepareStatement(sql);

	System.out.print("제목을 입력하세요 : ");
	String title = Main.SC.nextLine();

	System.out.print("내용을 입력하세요 : ");
	String content = Main.SC.nextLine();

	pstmt.setString(1, title);
	pstmt.setString(2, content);

	int result = pstmt.executeUpdate();

	if(result ==1) {
		System.out.println("\n※ 공지사항 작성 완료 ※\n");
}else {
	throw new Exception("※ 공지사항 작성 실패 ※");
}
}

public void deletePublic(Connection conn) throws Exception {
	// 공지사항 삭제하기
	String sql = "UPDATE NOTICE SET QUIT_YN =? WHERE PUBLIC_NO =? ";
	PreparedStatement pstmt = conn.prepareStatement(sql);

	System.out.print("삭제할 공지사항 번호 선택 : ");
	String publicNo = Main.SC.nextLine();

	System.out.print("삭제 하시겠습니까? (Y / N) : ");
	String quitYn = Main.SC.nextLine();

	pstmt.setString(1, quitYn);
	pstmt.setString(2, publicNo);

	int result = pstmt.executeUpdate();

	if(result ==1) {
		System.out.println("\n※ " + publicNo + "번 공지사항 삭제 완료 ※ \n");
	}else {
		throw new Exception("※ 삭제실패 ※");
	}
}

public void updateFaqList (Connection conn) throws Exception {
	//자주묻는 질문 수정
	String sql = "UPDATE FAQ SET QUESTION = ? ,ANSWER =? WHERE QUESTION_NO = ?";
	PreparedStatement pstmt = conn.prepareStatement(sql);

	System.out.print("질문 등록 번호: ");
	String questionNo = Main.SC.nextLine();

	System.out.print("질문 : ");
	String question = Main.SC.nextLine();

	System.out.print("답변 : ");
	String answer = Main.SC.nextLine();

	pstmt.setString(1, question);
	pstmt.setString(2, answer);
	pstmt.setString(3, questionNo);

	int result = pstmt.executeUpdate();

	if(result ==1) {
		System.out.println("※ 자주묻는 질문 수정 완료 ※");
	} else {
		throw new Exception("※ 실패 ※");
	}
}
}