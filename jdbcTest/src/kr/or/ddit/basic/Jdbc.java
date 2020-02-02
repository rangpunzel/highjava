package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdbc {
	public static void main(String[] args) {
		// DB연결에 필요한 객체변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null; // 쿼리문이 select일 경우에 필요함. 
		
		try {
			// JDBC드라이버 로딩(오라클 기준)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 해당DB에 접속(Connection객체 생성)
			String url = "jdbc:oracle:thin:@localhost:1521/xe";
			String userId = "pc04";  //오라클 접속아이디
			String password = "java";//오라클 접속아이디의 비밀번호
			
			// OracleDriver가 사용되는 부분
			conn = DriverManager.getConnection(url, userId, password);
			
			// Statement객체 생성 => Connection객체를 이용한다.
			stmt = conn.createStatement();
			
			// SQL문을 Statement객체를 이용하여 실행하고  ResultSet객체에 저장한다.
			
			String sql = "select * from lprod"; //실행할 SQL문
			
			// SQL문이 select일 경우에는 executeQuery()메서드 사용
			rs = stmt.executeQuery(sql); 
			
			// ResultSet객체에 저장되어 있는 자료를 반복문과 next()메서드를 이용하여 차례로 읽어와 처리한다.
			 System.out.println("실행한 쿼리문 : " + sql);
			 System.out.println("=== 쿼리문 실행결과 ===");
			 
			 while(rs.next()) {
				 //컬럼의 자료를 가져옴
				 System.out.println("1prod_id = " + rs.getInt("lprod_id"));
				 System.out.println("1prod_gu = " + rs.getString("lprod_gu"));
				 System.out.println("1prod_nm = " + rs.getString("lprod_nm"));
				 System.out.println("------------------------------------------");
			 }
			 System.out.println("출력 끝...");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원반납
			if(rs != null) try {rs.close();} catch(SQLException e2) {}
			if(stmt != null) try {stmt.close();} catch(SQLException e2) {}
			if(conn != null) try {conn.close();} catch(SQLException e2) {}
		}
	}

}
