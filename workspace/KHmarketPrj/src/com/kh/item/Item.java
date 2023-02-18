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

		Category ca = new Category();
		
		/*
		--CATEGORY (SQL 입력, 실행)
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (101, '가전');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (102, '디지털');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (103, '의류');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (104, '식품');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (105, '피시,모바일');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (106, '가구');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (107, '생필품');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (108, '잡화');
		insert into ITEM_TYPE (TYPE_NO, TYPE_NAME) VALUES (109, '기타');
		*/
		
		System.out.println("카테고리를 정하십시오");
		System.out.println("1. 가전");
		System.out.println("2. 디지털");
		System.out.println("3. 의류");
		System.out.println("4. 식품");
		System.out.println("5. 피시 모바일");
		System.out.println("6. 가구");
		System.out.println("7. 생필품");
		System.out.println("8. 잡화");
		System.out.println("9. 기타");	
		
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
	
	public void selectItemType() {
		// 상품 유형 선택 (ex. 판매, 구매, ...)
	}
	
	public void registSellItem(Connection conn, int TYPE_NO, int USER_NO) throws Exception {
		// 상품 등록 				
		
		System.out.println("제목: ");
		String itemTitle = Main.SC.nextLine();
		System.out.println("내용: ");
		String itemContent = Main.SC.nextLine();
		System.out.println("가격: ");
		String itemPrice = Main.SC.nextLine();
		
		//SQL
		String sql = "INSERT INTO ITEM(ITEM_NO, TYPE_NO, USER_NO, TITLE,CONTENT,PRICE,WRITE_DATE) VALUES(SEQ_ITEM_NO.NEXTVAL,?,?,?,?,?,SYSDATE)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, TYPE_NO);
		pstmt.setLong(2, USER_NO);
		pstmt.setString(3, itemTitle);
		pstmt.setString(4, itemContent);
		pstmt.setString(5, itemPrice);
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			System.out.println("작성 성공");
		}
		else {
			System.out.println("작성 실패");
		}
		
	}
	
	public void editItem(Connection conn) throws Exception {
		
		EditItem edit = new EditItem();
		
		// 상품 수정
		System.out.println("수정하실 부분을 정하십시오");
		System.out.println("1. 제목");
		System.out.println("2. 글 내용");
		System.out.println("3. 가격");
		
		String input = Main.SC.nextLine();
		
		switch(input) {
		case "1": edit.editTitle(conn, 100); break;
		case "2": edit.editContent(conn, 100); break;
		case "3": edit.editPrice(conn, 100); break;
		default: System.out.println("잘못 입력하셨습니다.");
		return;
		}
	}

	public void deleteItem() {
		// 상품 삭제 (delete_YN? delete?)
	}
	
	public void findItem(Connection conn) throws Exception {
		// 상품 조회 (10개 씩 조회 할 수 있으면 더 좋을 지도..수정 예정)
			
			//SQL
			String sql = "SELECT TITLE,USER_NO,PRICE,WRITE_DATE FROM ITEM";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {

				String title = rs.getString("TITLE");
				String userNo = rs.getString("USER_NO");
				String price = rs.getString("PRICE");
				String enroll_date = rs.getString("WRITE_DATE");
				
				System.out.print(title);
				System.out.print(" | ");
				System.out.print(userNo);
				System.out.print(" | ");
				System.out.print(price);
				System.out.print(" | ");
				System.out.println(enroll_date);
				
			}	
		
	}
	
	public void findItemAbb(Connection conn) throws Exception {
		// 줄임말로 아이템 찾기 (실험중)
		
		System.out.println("검색 할 글 제목: ");
		String Title = Main.SC.nextLine();
		
		//SQL
		String sql = "SELECT TITLE,CONTENT,PRICE,USER_NO,WRITE_DATE FROM ITEM WHERE TITLE=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, Title);
		ResultSet rs = pstmt.executeQuery();
		
		//결과 도출
		if(rs.next()) {
			
			String title = rs.getString("TITLE");
			String content = rs.getString("CONTENT");
			String price = rs.getString("PRICE");
			int userNo = rs.getInt("USER_NO");
			String writeDate = rs.getString("WRITE_DATE");
			
			System.out.print(title);
			System.out.print(" | ");
			System.out.print(content);
			System.out.print(" | ");
			System.out.print(price);
			System.out.print(" | ");
			System.out.print(userNo);
			System.out.print(" | ");
			System.out.println(writeDate);
			
		}
		
	}
		
}
