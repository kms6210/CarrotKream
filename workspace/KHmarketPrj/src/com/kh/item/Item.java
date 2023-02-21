package com.kh.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.jdbc.JdbcTemplate;
import com.kh.main.Main;

// 테이블 : 상품 유형, 상품
public class Item {
	
	public void selectItemCategory(Connection conn) throws Exception {
		//여기는 일단 서비스 종료
		
		Category ca = new Category();
		
		/*
		--CATEGORY (SQL 입력, 실행)
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (1, '가전');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (2, '디지털');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (3, '의류');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (4, '식품');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (5, '피시,모바일');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (6, '가구');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (7, '생필품');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (8, '잡화');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (9, '기타');
		*/
		
		System.out.println("카테고리를 정하십시오");
		System.out.println("101. 가전");
		System.out.println("102. 디지털");
		System.out.println("103. 의류");
		System.out.println("104. 식품");
		System.out.println("105. 피시 모바일");
		System.out.println("106. 가구");
		System.out.println("107. 생필품");
		System.out.println("108. 잡화");
		System.out.println("109. 기타");	
		
		String input = Main.SC.nextLine();
		
		// 상품 카테고리 선택 (ex. 가전, 디지털, 잡화 ...)
		switch(input) {
		case "1": ca.category01(conn); break;
		case "2": ca.category02(conn); break;
		case "3": ca.category03(conn); break;
		case "4": ca.category04(conn); break;
		case "5": ca.category05(conn); break;
		case "6": ca.category06(conn); break;
		case "7": ca.category07(conn); break;
		case "8": ca.category08(conn); break;
		case "9": ca.category09(conn); break;
		default: System.out.println("잘못 입력하셨습니다.");
		return;
		}
	}
	
	public void registSellItem(Connection conn, int USER_NO) throws Exception {
		// 상품 등록 				
		
		System.out.println("카테고리를 정하십시오");
		System.out.println("101. 가전");
		System.out.println("102. 디지털");
		System.out.println("103. 의류");
		System.out.println("104. 식품");
		System.out.println("105. 피시 모바일");
		System.out.println("106. 가구");
		System.out.println("107. 생필품");
		System.out.println("108. 잡화");
		System.out.println("109. 기타");	
		System.out.print("카테고리: ");			
		String typeNo = Main.SC.nextLine();		
		System.out.println("판매를 위한 글인 지 구매를 위한 글인 지 정하십시오.");
		System.out.println("| 판매: S | 구매: B |");		
		System.out.print("글 유형: ");			
		String tradeStatus = Main.SC.nextLine();
		System.out.print("제목: ");
		String itemTitle = Main.SC.nextLine();
		System.out.print("내용: ");
		String itemContent = Main.SC.nextLine();
		System.out.print("가격: ");
		String itemPrice = Main.SC.nextLine();
		
		//SQL
		String sql = "INSERT INTO ITEM(ITEM_NO, TYPE_NO, TRADE_STATUS, USER_NO, TITLE,CONTENT,PRICE,WRITE_DATE) VALUES(SEQ_ITEM_NO.NEXTVAL,?,UPPER(?),?,?,?,?,SYSDATE)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, typeNo);
		pstmt.setString(2, tradeStatus);
		pstmt.setLong(3, USER_NO);
		pstmt.setString(4, itemTitle);
		pstmt.setString(5, itemContent);
		pstmt.setString(6, itemPrice);
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			System.out.println("작성 성공");
		}
		else {
			System.out.println("작성 실패");
		}
		
	}
	
	public void editItem(Connection conn, int userNo) throws Exception {
		
		EditItem edit = new EditItem();
		
		//SQL
		System.out.println("=======================================================");
		System.out.println("[내가 작성한 글 목록]");
		
		String sql = "SELECT *\r\n"
				+ "FROM(\r\n"
				+ "    SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
				+ "    FROM (\r\n"
				+ "        SELECT ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
				+ "        FROM ITEM\r\n"
				+ "        WHERE USER_NO = ? AND TRADE_STATUS != 'D'"
				+ "        ORDER BY ITEM_NO DESC\r\n"
				+ "        )\r\n"
				+ "    )\r\n";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, userNo);
		ResultSet rs = pstmt.executeQuery();
		
		//상품 보기
		while(rs.next()) {
			
			String itemNo = rs.getString("ITEM_NO");
			String title = rs.getString("TITLE");
			String user_no = rs.getString("USER_NO");
			String price = rs.getString("PRICE");
			String write_date = rs.getString("WRITE_DATE");
			
			System.out.print("물건 번호: "+itemNo);
			System.out.print(" | ");
			System.out.print("제목: "+title);
			System.out.print(" | ");
			System.out.print("유저 번호: "+user_no);
			System.out.print(" | ");
			System.out.print("가격: "+price);
			System.out.print(" | ");
			System.out.println("작성일: "+write_date);
			
		}	
		System.out.println("=======================================================");
		
		// 상품 수정
		
		System.out.println("[수정하실 글의 번호를 입력해 주십시오.]");
		System.out.print("수정 할 글의 번호:");
		
		int editNum = Main.SC.nextInt();
		
		System.out.println("[수정하실 부분을 정하십시오]");
		System.out.println("1. 제목");
		System.out.println("2. 글 내용");
		System.out.println("3. 가격");
		System.out.print("번호를 입력하세요 : ");
		
		int input = Main.SC.nextInt();
		
		String emptyClear = Main.SC.nextLine();
		
		switch(input) {
		case 1: edit.editTitle(conn, editNum, Main.login_member_no); break;
		case 2: edit.editContent(conn, editNum, Main.login_member_no); break;
		case 3: edit.editPrice(conn, editNum, Main.login_member_no); break;
		default: System.out.println("잘못 입력하셨습니다.");
		return;
		}
	}

	public void deleteItem(Connection conn, int userNo) throws Exception {
		// 상품 삭제 
		
		//SQL
				System.out.println("=======================================================");
				System.out.println("[내가 작성한 글 목록]");
				
				String sql = "SELECT *\r\n"
						+ "FROM(\r\n"
						+ "    SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
						+ "    FROM (\r\n"
						+ "        SELECT ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE\r\n"
						+ "        FROM ITEM\r\n"
						+ "        WHERE USER_NO = ? AND TRADE_STATUS != 'D'"
						+ "        ORDER BY ITEM_NO DESC\r\n"
						+ "        )\r\n"
						+ "    )\r\n";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, userNo);
				ResultSet rs = pstmt.executeQuery();
				
				//상품 보기
				while(rs.next()) {
					
					String itemNo = rs.getString("ITEM_NO");
					String title = rs.getString("TITLE");
					String user_no = rs.getString("USER_NO");
					String price = rs.getString("PRICE");
					String write_date = rs.getString("WRITE_DATE");
					
					System.out.print("물건 번호: "+itemNo);
					System.out.print(" | ");
					System.out.print("제목: "+title);
					System.out.print(" | ");
					System.out.print("유저 번호: "+user_no);
					System.out.print(" | ");
					System.out.print("가격: "+price);
					System.out.print(" | ");
					System.out.println("작성일: "+write_date);
					
				}	
				System.out.println("=======================================================");
		
		System.out.println("삭제하실 글의 번호를 입력하시오.");
		System.out.print("글 번호: ");
		
		int delete = Main.SC.nextInt();
		
		//SQL
		
		 sql = "UPDATE ITEM SET TRADE_STATUS = 'D' WHERE ITEM_NO = ? AND USER_NO = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, delete);
		pstmt.setInt(2, userNo);
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			System.out.println("삭제 성공");
		}
		else {
			System.out.println("삭제 실패");
		}
		
	}
	
	public void findItem(Connection conn) throws Exception {
			
		ItemSearch is = new ItemSearch();
		
		System.out.println("조회하실 글들을 선택하십시오.");
		System.out.println("1. 모든 품목 조회");
		System.out.println("2. 구매 혹은 판매글만 보기");
		System.out.println("3. 인기 상품");
		System.out.println("4. 카테고리로 선택");
		System.out.println("5. 내 작성 글 조회");
		
		String input = Main.SC.nextLine();
		
		switch(input) {
		case "1": is.itemView(conn); break;
		case "2": is.buyOrSell(conn); break;
		case "3": is.rankedByView(conn); break;
		case "4": is.categoryView(conn); break;
		case "5": is.myView(conn, Main.login_member_no); break;
		}
		
		
		
	}
	
	public void findItemAbb(Connection conn) throws Exception {
		// 글 번호로 글 상세 보기 
		
		System.out.print("검색 할 글 번호: ");
		String itemNo = Main.SC.nextLine();
		
		Item item = new Item();
		item.increasingView(conn, itemNo);
		
		//SQL
		String sql = "SELECT ITEM_NO,TITLE,\"VIEW\",CONTENT,PRICE,USER_NO,WRITE_DATE FROM ITEM WHERE ITEM_NO=? AND TRADE_STATUS != 'D'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, itemNo);
		ResultSet rs = pstmt.executeQuery();
		
		//결과 도출
		if(rs.next()) {
			
			String item_no = rs.getString("ITEM_NO");
			String title = rs.getString("TITLE");
			String view = rs.getString("VIEW");
			String content = rs.getString("CONTENT");
			String price = rs.getString("PRICE");
			int userNo = rs.getInt("USER_NO");
			String writeDate = rs.getString("WRITE_DATE");
			
			System.out.print("아이템 번호: "+item_no);
			System.out.print(" | ");
			System.out.print("제목: " + title);
			System.out.print(" | ");
			System.out.print("조회수: " + view);
			System.out.println(" | ");
			System.out.println(" ------------------------------------------------------ ");
			System.out.println("");
			System.out.println("내용: " + content);
			System.out.println("");
			System.out.println(" ------------------------------------------------------ ");
			System.out.print("가격: " + price);
			System.out.print(" | ");
			System.out.print("작성자: " + userNo);
			System.out.print(" | ");
			System.out.println(writeDate);
			
		}
		
	}
	
	public void increasingView(Connection conn, String itemNo) throws Exception {
		
		String sql = "UPDATE ITEM SET \"VIEW\" = \"VIEW\" + 1 WHERE ITEM_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, itemNo);
		int result = pstmt.executeUpdate();
		
	}
		
}
