package com.kh.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuyOrSell {

	public void buying(Connection conn) throws Exception {

		// SQL
		String sql = "SELECT *\r\n" + "FROM(\r\n"
				+ "SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE, TRADE_STATUS\r\n" + "FROM (\r\n"
				+ "SELECT *\r\n" + "FROM ITEM\r\n" + "WHERE TRADE_STATUS != 'D'\r\n" + "AND TRADE_STATUS = 'B'\r\n"
				+ "ORDER BY ITEM_NO DESC\r\n" + ")\r\n" + ")";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		// 상품 보기
		int flag = 0;
		while (rs.next()) {
			flag++;
			String itemNo = rs.getString("ITEM_NO");
			String title = rs.getString("TITLE");
			String price = rs.getString("PRICE");
			String write_date = rs.getString("WRITE_DATE");
			String trade_status = rs.getString("TRADE_STATUS");

			System.out.print(itemNo + ". " + title);
			System.out.print("[" + trade_status + "]");
			System.out.print("    가격: " + price);
			System.out.println("    작성일: " + write_date);

		}
		if (flag == 0) {
			throw new Exception("※ 현재 구매글이 존재하지 않습니다 ※");
		}
	}

	public void selling(Connection conn) throws Exception {

		// SQL
		String sql = "SELECT *\r\n" + "FROM(\r\n"
				+ "SELECT ROWNUM R,ITEM_NO,TITLE,USER_NO,PRICE,WRITE_DATE, TRADE_STATUS\r\n" + "FROM (\r\n"
				+ "SELECT *\r\n" + "FROM ITEM\r\n" + "WHERE TRADE_STATUS != 'D'\r\n" + "AND TRADE_STATUS = 'S'\r\n"
				+ "ORDER BY ITEM_NO DESC\r\n" + ")\r\n" + ")";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		// 상품 보기
		int flag = 0;
		while (rs.next()) {
			flag++;
			String itemNo = rs.getString("ITEM_NO");
			String title = rs.getString("TITLE");
			String price = rs.getString("PRICE");
			String write_date = rs.getString("WRITE_DATE");
			String trade_status = rs.getString("TRADE_STATUS");

			System.out.print(itemNo + ". " + title);
			System.out.print("[" + trade_status + "]");
			System.out.print("    가격: " + price);
			System.out.println("    작성일: " + write_date);

		}
		if (flag == 0) {
			throw new Exception("※ 현재 판매글이 존재하지 않습니다 ※");
		}

	}

}
