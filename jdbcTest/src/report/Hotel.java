package report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Hotel {
	Scanner s = new Scanner(System.in);
	
	public static void main(String[] args) {   //메인
		new Hotel().start();
		
	}
	
	private void displayMenu(){   //디스플레이메뉴
		System.out.println("**************************************");
		System.out.println("어떤 업무를 하시겠습니까?");
		System.out.println("1.체크인   2.체크아웃   3.객실상태   4.업무종료");
		System.out.println("**************************************");
		System.out.println("메뉴선택 => ");
	}
	

	private void start(){    //시작
		System.out.println("**************************************");
		System.out.println("호텔 문을 열었습니다.");
		System.out.println("**************************************");
		System.out.println();
		
		while(true){
		displayMenu();  // 메뉴 출력
		int menuNum = s.nextInt();   // 메뉴 번호 입력
		
		switch(menuNum){
		case 1 : checkIn();		// 체크인
			break;
		case 2 : checkOut();		// 체크아웃
			break;
		case 3 : roomList();		// 객실상태
			break;
		case 4 :
			System.out.println("**************************************");
			System.out.println("호텔 문을 닫았습니다.");
			System.out.println("**************************************");
			return;
		default :
			System.out.println("잘못 입력했습니다. 다시입력하세요.");
			} // switch문
		} // while문
	}
	
	private void roomList(){

		// DB작업에 필요한 객체변수 선언
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null; // 쿼리문이 select일 경우에 필요함.
				
				try {

					// 1. 드라이버 로딩
					Class.forName("oracle.jdbc.driver.OracleDriver");
					
					// 2. DB에 접속(Connection객체 생성)
					String url = "jdbc:oracle:thin:@localhost:1521/xe";
					String userId = "pc04";
					String password = "java";
					
					// 실제적으로 OracleDriver가 사용되는 부분
					conn = DriverManager.getConnection(url, userId, password);
					// 3. Statement객체 생성 => Connection객체를 이용한다.
					stmt = conn.createStatement();
					
					// 4. SQL문을 Statement객체를 이용하여 실행하고
					// 실행결과를 ResultSet객체에 저장한다.

										
					String sql = "select * from hotel_mng"; //실행할 SQL문
					
					// SQL문이 select일 경우에는 executeQuery()메서드를 사용하고
					// insert,update,delete일 경우에는 executeUpdate()메서드 사용함.
					rs = stmt.executeQuery(sql); 
					
					// 5. ResultSet객체에 저장되어 있는 자료를 반복문과 next()메서드를
					// 이용하여 차례로 읽어와 처리한다.
					 System.out.println("실행한 쿼리문 : " + sql);
					 System.out.println("=== 쿼리문 실행결과 ===");
					 
					 // re.next() => ResultSet객체의 데이터를 가리키는 포인터를
					 //				다음 레코드로 이동시키고 그 곳에 자료가 있으면
					 //				true, 없으면 false를 반환한다.
					 while(rs.next()) {
						 //컬럼의 자료를 가져오는 방법
						 //방법1) rs.get자료형이름("컬럼명")
						 //방법2) rs.get자료형이름(컬럼번호) => 컬럼번호는 1번부터 시작.
						 System.out.println("방번호 : " + rs.getString("room_num") + ", 투숙객 : " 
								 						+ rs.getString("guest_name"));
					 }
					 System.out.println("------------------------------------------");

				}catch(ClassNotFoundException e) {
					e.printStackTrace();
				}catch(SQLException e) {
					e.printStackTrace();
				} finally {
					// 6. 종료 ( 사용했던 자원을 모두 반납한다. )
					if(rs != null) try {rs.close();} catch(SQLException e2) {}
					if(stmt != null) try {stmt.close();} catch(SQLException e2) {}
					if(conn != null) try {conn.close();} catch(SQLException e2) {}
				}

		

	}
	
	private void checkOut(){

		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. DB에 접속(Connection객체 생성)
			// 실제적으로 OracleDriver가 사용되는 부분
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "pc04", "java");

			// PreparedStatement객체를 이용한 자료 추가 방법
			
			// SQL문 작성(데이터가 들어갈 자리에 물음표(?)를 넣는다)
			String roomNum;
			
			System.out.println("어느방을 체크아웃 하시겠습니까?");
			System.out.print("방번호 입력 => ");
			roomNum = s.next();
			
			String sql = "DELETE FROM hotel_mng WHERE room_num = (?)";
			

			//PreparedStatement객체를 생성할때 SQL문을 넣어서 생성한다.
			pstmt = conn.prepareStatement(sql);
			
			//쿼리문의 물음표(?)자리에 들어갈 데이터를 셋팅한다.
			// 형식) pstmt.set자료형이름(물음표순번, 데이터);
			//     물음표 순번은 1번부터 시작한다.
			stmt = conn.createStatement();
			
			String sql1 = "SELECT room_num FROM hotel_mng WHERE room_num = "+ roomNum;
			
			rs = stmt.executeQuery(sql1); 

			if(rs.next() == false) {
				System.out.println(roomNum + "방에는 체크인한 사람이 없습니다.");
				return;
			}

			
			pstmt.setString(1, roomNum);

			
			// 데이터를 세팅한 후 쿼리문을 실행한다.
			int cnt = pstmt.executeUpdate();
			System.out.println("첫번째 반환값 : " + cnt);
			//-------------------------------------------

			System.out.println("체크아웃 되었습니다.");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 6. 종료 ( 사용했던 자원을 모두 반납한다. )
			if(rs != null) try {rs.close();} catch(SQLException e2) {}
			if(pstmt != null) try {pstmt.close();} catch(SQLException e2) {}
			if(stmt != null) try {stmt.close();} catch(SQLException e2) {}
			if(conn != null) try {conn.close();} catch(SQLException e2) {}
		}

		
	}
	
	
	private void checkIn(){
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			System.out.println("어느 방에 체크인 하시겠습니까?");
			System.out.print("방번호 입력 => ");
			String roomNum = s.next();
			s.nextLine(); 
			
			System.out.println("누구를 체크인 하시겠습니까?");
			System.out.print("이름 입력 => ");
			String name = s.next();
			
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. DB에 접속(Connection객체 생성)
			// 실제적으로 OracleDriver가 사용되는 부분
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "pc04", "java");

			// PreparedStatement객체를 이용한 자료 추가 방법
			
			// SQL문 작성(데이터가 들어갈 자리에 물음표(?)를 넣는다)
			String sql = "insert into hotel_mng " 
					+ "(room_num, guest_name) "
					+ " values(?, ?)";
			

			//PreparedStatement객체를 생성할때 SQL문을 넣어서 생성한다.
			pstmt = conn.prepareStatement(sql);
			
			//쿼리문의 물음표(?)자리에 들어갈 데이터를 셋팅한다.
			// 형식) pstmt.set자료형이름(물음표순번, 데이터);
			//     물음표 순번은 1번부터 시작한다.
			
			
			stmt = conn.createStatement();
			
			String sql1 = "SELECT room_num FROM hotel_mng WHERE room_num = "+ roomNum;
			
			rs = stmt.executeQuery(sql1); 

			if(rs.next() == true) {
				System.out.println(roomNum + "방에는 이미 사람이 있습니다.");
				return;
			}
			
			
			pstmt.setString(1, roomNum);
			pstmt.setString(2, name);
			
			// 데이터를 세팅한 후 쿼리문을 실행한다.
			int cnt = pstmt.executeUpdate();
			System.out.println("첫번째 반환값 : " + cnt);
			//-------------------------------------------

			System.out.println("체크인 되었습니다.");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 6. 종료 ( 사용했던 자원을 모두 반납한다. )
			if(pstmt != null) try {pstmt.close();} catch(SQLException e2) {}
			if(rs != null) try {rs.close();} catch(SQLException e2) {}
			if(stmt != null) try {stmt.close();} catch(SQLException e2) {}
			if(conn != null) try {conn.close();} catch(SQLException e2) {}
		}

	}

}
