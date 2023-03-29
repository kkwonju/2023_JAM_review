package kkwo.JAM.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kkwo.JAM.dto.Article;


public class JDBCSelectTest {
	public static void main(String[]args) {
		// 동급 '자원'
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Article> forPrintArticles = new ArrayList<>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
			
			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");
			
			String sql = "SELECT *";
			sql += " FROM article";
			sql += " ORDER BY id DESC;";
			
			System.out.println(sql);
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				int hit = rs.getInt("hit");
				int memberId = rs.getInt("memberId");
				String title = rs.getString("title");
				String body = rs.getString("body");
//				LocalDateTime regDate = rs.getString("regDate");
//				LocalDateTime updateDate = rs.getString("updateDate");
				
//				forPrintArticles.add(new Article(id, hit, memberId, title, body, regDate, updateDate));
				
				System.out.println(forPrintArticles.get(0).id);
				System.out.println(forPrintArticles.get(0).memberId);
				System.out.println(forPrintArticles.get(0).title);
				System.out.println(forPrintArticles.get(0).body);
				System.out.println(forPrintArticles.get(0).regDate);
				System.out.println(forPrintArticles.get(0).updateDate);
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			// 동급 자원, 똑같이 추가
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}