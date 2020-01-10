package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Insert 예제
 * @author PC-04
 *
 */
public class T03_JdbcTest {
/*
   lprod_id : 101, lprod_gu : N101, lprod_nm : 농산물
   lprod_id : 102, lprod_gu : N102, lprod_nm : 수산물
   lprod_id : 103, lprod_gu : N103, lprod_nm : 축산물
   
   위 3개의 자료를 추가하기
 */
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. DB에 접속(Connection객체 생성)
			// 실제적으로 OracleDriver가 사용되는 부분
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "pc04", "java");
			/*// 3. Statement객체 생성 => Connection객체를 이용한다.
			stmt = conn.createStatement();
			
			String sql = "insert into lprod " 
					+ "(lprod_id, lprod_gu, lprod_nm) "
					+ " values(101, 'N101', '농산물')";
			
			//SQL문이 select문이 아닐 경우에는 executeUpdate()메서드를 사용한다.
			// execute()메서드는 실행에 성공한 레코드 수를 반환한다.
			int cnt = stmt.executeUpdate(sql);
			
			System.out.println("첫번째 반환값 : " + cnt);
			//---------------------------------------
			sql = "insert into lprod " 
					+ "(lprod_id, lprod_gu, lprod_nm) "
					+ " values(102, 'N102', '수산물')";
			cnt = stmt.executeUpdate(sql);
			System.out.println("두번째 반환값 : " + cnt);
			//---------------------------------------
			sql = "insert into lprod " 
					+ "(lprod_id, lprod_gu, lprod_nm) "
					+ " values(103, 'N103', '축산물')";
			cnt = stmt.executeUpdate(sql);
			System.out.println("세번째 반환값 : " + cnt);*/
			
			
			// PreparedStatement객체를 이용한 자료 추가 방법
			
			// SQL문 작성(데이터가 들어갈 자리에 물음표(?)를 넣는다)
			String sql = "insert into lprod " 
					+ "(lprod_id, lprod_gu, lprod_nm) "
					+ " values(?, ?, ?)";
			//PreparedStatement객체를 생성할때 SQL문을 넣어서 생성한다.
			pstmt = conn.prepareStatement(sql);
			
			//쿼리문의 물음표(?)자리에 들어갈 데이터를 셋팅한다.
			// 형식) pstmt.set자료형이름(물음표순번, 데이터);
			//     물음표 순번은 1번부터 시작한다.
			pstmt.setInt(1, 101);
			pstmt.setString(2, "N101");
			pstmt.setString(3, "농산물");
			
			// 데이터를 세팅한 후 쿼리문을 실행한다.
			int cnt = pstmt.executeUpdate();
			System.out.println("첫번째 반환값 : " + cnt);
			//-------------------------------------------
			pstmt.setInt(1, 102);
			pstmt.setString(2, "N102");
			pstmt.setString(3, "수산물");
			
			cnt = pstmt.executeUpdate();
			System.out.println("두번째 반환값 : " + cnt);
			//-------------------------------------------
			pstmt.setInt(1, 103);
			pstmt.setString(2, "N103");
			pstmt.setString(3, "축산물");
			
			cnt = pstmt.executeUpdate();
			System.out.println("세번째 반환값 : " + cnt);
			
			System.out.println("작업 끝...");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 6. 종료 ( 사용했던 자원을 모두 반납한다. )
			if(pstmt != null) try {pstmt.close();} catch(SQLException e2) {}
			if(stmt != null) try {stmt.close();} catch(SQLException e2) {}
			if(conn != null) try {conn.close();} catch(SQLException e2) {}
		}
}
}
