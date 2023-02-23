package com.kh.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kh.main.Main;

public class PickMe {
	
	public void like(Connection conn, int item_no) throws Exception {
		
		try {
			
			String sql = "INSERT INTO LIKELIST(ITEM_NO, USER_NO) VALUES(?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, item_no);
			pstmt.setInt(2, Main.login_member_no);
			
			int result = pstmt.executeUpdate();
			
			if(result==1) {
				System.out.println("※ 좋아요 등록이 되었습니다 ※");
			}
			else {
				throw new Exception("※ 좋아요 등록 실패 ※");
			}
		} catch (Exception e) {
			
			throw new Exception("※ 이미 좋아요 한 상품입니다 ※");
			
		}
		
	}
	
	public void unlike(Connection conn, int item_no) throws Exception {
		
		String sql ="DELETE LIKELIST WHERE ITEM_NO = ? AND USER_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, item_no);
		pstmt.setInt(2, Main.login_member_no);
		
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			throw new Exception("※ 좋아요가 삭제 되었습니다 ※");
		}
		else {
			throw new Exception("※ 좋아요 삭제 실패 ※");
		}
		
		
	}
	
}
