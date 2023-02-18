package com.kh.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Category {

	
	
	public void category01(Connection conn) throws Exception {
		String sql = "SELECT TYPE_NO, TYPE_NAME FROM ITEM_TYPE WHERE TYPE_NO = '101'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
				
		while(rs.next()) {
			int typeNo = rs.getInt("TYPE_NO");
			String typeName = rs.getString("TYPE_NAME");
			
			System.out.println(typeNo);
			System.out.println(typeName);
			
		}

	}
	public void category02(Connection conn) throws Exception {
		String sql = "SELECT TYPE_NO, TYPE_NAME FROM ITEM_TYPE WHERE TYPE_NO = '102'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
				
		while(rs.next()) {
			int typeNo = rs.getInt("TYPE_NO");
			String typeName = rs.getString("TYPE_NAME");
			
			System.out.println(typeNo);
			System.out.println(typeName);
			
		}

	}
	public void category03(Connection conn) throws Exception {
		String sql = "SELECT TYPE_NO, TYPE_NAME FROM ITEM_TYPE WHERE TYPE_NO = '103'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
				
		while(rs.next()) {
			int typeNo = rs.getInt("TYPE_NO");
			String typeName = rs.getString("TYPE_NAME");
			
			System.out.println(typeNo);
			System.out.println(typeName);
			
		}
			
		

	}
	public void category04(Connection conn) throws Exception {
		String sql = "SELECT TYPE_NO, TYPE_NAME FROM ITEM_TYPE WHERE TYPE_NO = '104'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
				
		while(rs.next()) {
			int typeNo = rs.getInt("TYPE_NO");
			String typeName = rs.getString("TYPE_NAME");
			
			System.out.println(typeNo);
			System.out.println(typeName);
			
		}
	}	
	public void category05(Connection conn) throws Exception {
		String sql = "SELECT TYPE_NO, TYPE_NAME FROM ITEM_TYPE WHERE TYPE_NO = '105'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
				
		while(rs.next()) {
			int typeNo = rs.getInt("TYPE_NO");
			String typeName = rs.getString("TYPE_NAME");
			
			System.out.println(typeNo);
			System.out.println(typeName);
			
		}

	}
	public void category06(Connection conn) throws Exception {
		String sql = "SELECT TYPE_NO, TYPE_NAME FROM ITEM_TYPE WHERE TYPE_NO = '106'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
				
		while(rs.next()) {
			int typeNo = rs.getInt("TYPE_NO");
			String typeName = rs.getString("TYPE_NAME");
			
			System.out.println(typeNo);
			System.out.println(typeName);
			
		}

	}	
	public void category07(Connection conn) throws Exception {
		String sql = "SELECT TYPE_NO, TYPE_NAME FROM ITEM_TYPE WHERE TYPE_NO = '107'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
				
		while(rs.next()) {
			int typeNo = rs.getInt("TYPE_NO");
			String typeName = rs.getString("TYPE_NAME");
			
			System.out.println(typeNo);
			System.out.println(typeName);
			
		}

	}
	public void category08(Connection conn) throws Exception {
		String sql = "SELECT TYPE_NO, TYPE_NAME FROM ITEM_TYPE WHERE TYPE_NO = '108'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
				
		while(rs.next()) {
			int typeNo = rs.getInt("TYPE_NO");
			String typeName = rs.getString("TYPE_NAME");
			
			System.out.println(typeNo);
			System.out.println(typeName);
			
		}

	}
	public void category09(Connection conn) throws Exception {
		String sql = "SELECT TYPE_NO, TYPE_NAME FROM ITEM_TYPE WHERE TYPE_NO = '109'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
				
		while(rs.next()) {
			int typeNo = rs.getInt("TYPE_NO");
			String typeName = rs.getString("TYPE_NAME");
			
			System.out.println(typeNo);
			System.out.println(typeName);
			
		}

	}
	
}
