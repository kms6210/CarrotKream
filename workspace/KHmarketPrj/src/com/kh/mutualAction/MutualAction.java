package com.kh.mutualAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kh.main.Main;

// 테이블 : 좋아요 목록, 거래 후기, 상품, 채팅방, 채팅 내용 

public class MutualAction {
	
	public void setLikeList(Connection conn , int item_no , int user_no) throws Exception {
		// 좋아요(찜) 하기 
		//유저번호 1번이 상품번호 1번을 보고 찜을 눌러서 찜목록에 추가.
		
		String sql = "INSERT INTO LIKELIST(ITEM_NO, USER_NO) VALUES(?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, item_no);
		pstmt.setInt(2, user_no);
		
		int result = pstmt.executeUpdate();
		
		if(result==1) {
			System.out.println("찜 등록 완료");
		}
		else {
			System.out.println("찜 등록 실패");
		}
		
	}//method
	
	public void deleteLikeList() {
		// 좋아요(찜) 삭제하기 
	}
	
	public void selectLikeList() {
		// 좋아요(찜) 조회하기
	}
	
	public void writeReview() {
		// 거래 후기 작성 
	}
	
	public void sellItem() {
		// 판매
	}
	
	public void buyItem() {
		// 구매 
	}
	
	public void chat() {
		// 채팅하기 
	}
	
}