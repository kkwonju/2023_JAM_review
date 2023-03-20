package kkwo.JAM.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class JDBCInsertTest {
	public static void main(String[]args) {
		// 동급 '자원'
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
			
			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");
			
			// 공백 넣어주기
			// 말이 되게끔
			String sql = "INSERT INTO article";
			sql += " SET regDate = NOW(),";
			sql += "updateDate = NOW(),";
			sql += "title = CONCAT('제목',RAND()),";
			sql += "`body` = CONCAT('내용',RAND());";
			
			System.out.println(sql);
			
			// sql 태워 보내기
			pstmt = conn.prepareStatement(sql);
			
			int affectedRow = pstmt.executeUpdate();
			
			System.out.println("affectedRow : " + affectedRow);
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 동급 자원, 똑같이 추가
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}