package com.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcTemplate {
	public static Connection m01() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "C##KH";
		String pwd = "KH";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pwd);
		
		return conn;
	}
}