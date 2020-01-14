package report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil2;

public class JdbcBoard {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Scanner scan = new Scanner(System.in); 
	
	public void displayMenu(){
		System.out.println();
		System.out.println("----------------------");
		System.out.println("  === 작 업 선 택 ===");
		System.out.println("  1. 새글 작성");
		System.out.println("  2. 게시글 삭제");
		System.out.println("  3. 게시글 수정");
		System.out.println("  4. 전체 목록 출력");
		System.out.println("  5. 검색");
		System.out.println("  6. 작업 끝.");
		System.out.println("----------------------");
		System.out.print("원하는 작업 선택 >> ");
	}
	
	public void start(){
		int choice;
		do{
			displayMenu(); //메뉴 출력
			choice = scan.nextInt(); // 메뉴번호 입력받기
			switch(choice){
				case 1 :  // 자료 입력
					insertBoard();
					break;
				case 2 :  // 자료 삭제
					deleteBoard();				
					break;
				case 3 :  // 자료 수정
					updateBoard();
					break;
				case 4 :  // 전체 목록 출력
					displayBoardAll();
					break;
				case 5 :  // 검색
					search();
					break;
				case 6 :  // 작업 끝
					System.out.println("작업을 마칩니다.");
					break;
				default :
					System.out.println("번호를 잘못 입력했습니다. 다시입력하세요");
			}
		}while(choice!=6);
	}
	
	private void search() {

		
		try {
			conn = DBUtil2.getConnection();
			
			System.out.print("제목 검색 >> ");
			String titleSearch = scan.next();
			
			String sql = "SELECT * FROM jdbc_board WHERE board_title LIKE ('%" + titleSearch + "%')";

			stmt = conn.createStatement();
			
			
			rs = stmt.executeQuery(sql);
			int cnt = 0;
			
			while(rs.next()) {
				String no = rs.getString("board_no");
				String title = rs.getString("board_title");
				String writer = rs.getString("board_writer");
				String date = rs.getString("board_date");
				String content = rs.getString("board_content");
				cnt++;
				System.out.println(no + "\t"
								+ date + "\t"
								+ title + "\t"
								+ writer + "\t"
								+ content + "\t"
								);
			}
			if(cnt == 0) {
				System.out.println(titleSearch + "로 검색한 결과가 없습니다.");
			}
		
			System.out.println("--------------------------------");
			
			
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("검색 실패");
		}
		
	}

	private void displayBoardAll() {
		try {
			conn = DBUtil2.getConnection();
			
			String sql = "select board_no,board_title,board_writer,board_content,to_char(board_date,'yyyy/mm/dd') board_date  from jdbc_board";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);

			System.out.println("번호" + "\t"
					+ "날짜" + "\t"+"\t"
					+ "제목" + "\t"
					+ "작성자" + "\t"+ "\t"
					+ "내용" + "\t"
					);
			while(rs.next()) {

				String no = rs.getString("board_no");
				String title = rs.getString("board_title");
				String writer = rs.getString("board_writer");
				String date = rs.getString("board_date");
				String content = rs.getString("board_content");
				
				
				System.out.println(no + "\t"
								+ date + "\t"
								+ title + "\t"
								+ writer + "\t"+ "\t"
								+ content + "\t"
								);
			}
			System.out.println("--------------------------------");
			System.out.println("출력 작업 끝...");

			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("조회 실패..");
		}
		
	}

	private void updateBoard() {
				
			boolean chk = false; //중복 체크
			int num = 0;


			do {

			System.out.print("수정할 게시번호 >>");
			num = Integer.parseInt(scan.next());
			
			chk = getNumber(num);
			
			if(chk == false) {
				System.out.println("수정할 게시 번호가 없어요.");
			}
			
			}while(chk == false);
		
			
			
			
			System.out.print("수정할 제목 >>");
			String title = scan.next();
			
			System.out.print("수정할 작성자 >>");
			String writer = scan.next();
			
			scan.nextLine();
			
			
			
			
			try {
				
			conn = DBUtil2.getConnection();
			
			System.out.println("수정할 내용 >>");
			String content = scan.nextLine();

			String sql = "update jdbc_board "
					   + " set board_title = ?, "
					   + " board_writer = ?,"
					   + " board_content = ? "
					   + " where board_no = " + num;
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, writer);
			pstmt.setString(3, content);
			
			int cnt = pstmt.executeUpdate();
			if(cnt > 0) {
				System.out.println("게시글 수정 성공...");
			}else {
				System.out.println("게시글 수정 실패!!!");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("수정 실패");
		}
		
	}

	private void deleteBoard() {


			boolean chk = false; //중복 체크
			int num = 0;


			do {

				System.out.print("삭제할 게시번호 >> ");
			num = Integer.parseInt(scan.next());
			
			chk = getNumber(num);
			
			if(chk == false) {
				System.out.println("삭제할 게시 번호가 없어요.");
			}
			
			}while(chk == false);
			
			
			try {
			conn = DBUtil2.getConnection();
			String sql = "delete from jdbc_board where board_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			int cnt = pstmt.executeUpdate();
			if(cnt > 0) {
				System.out.println("게시글 삭제 성공...");
			}else {
				System.out.println("게시글 삭제 실패!!!");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("게시글 삭제 실패");
		}finally {
			disConnect();
		}
		
	}

	private void insertBoard() {
		try {
			System.out.print("작성자 >>");
			String writer = scan.next();
			System.out.print("제목  >> ");
			String title = scan.next();
			scan.nextLine(); //남아있는 버퍼 제거?
			System.out.println("내용 >> ");
			String content = scan.nextLine();

			conn = DBUtil2.getConnection();
			String sql = "insert into jdbc_board (board_no,"
					+ "board_title,"
					+ "board_writer,"
					+ "board_date,"
					+ "board_content) "
					+ "values(board_seq.nextVal,?,?,sysdate,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, writer);
			pstmt.setString(3, content);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println("게시글이 등록 되었습니다.");
			}else {
				System.out.println("게시글 작성 실패!!!");
			}
		}catch(SQLException e) {
			System.out.println("게시글 작성 실패..");
			e.printStackTrace();
		}finally {
			disConnect(); //자원반납
		}
	}
	

	private void disConnect() {
		//  사용했던 자원 반납
		if(rs!=null)try{ rs.close(); }catch(SQLException ee){}
		if(stmt!=null)try{ stmt.close(); }catch(SQLException ee){}
		if(pstmt!=null)try{ pstmt.close(); }catch(SQLException ee){}
		if(conn!=null)try{ conn.close(); }catch(SQLException ee){}
	}
	
	
	private boolean getNumber(Integer no) {
		boolean chk = false;
		try {
			conn = DBUtil2.getConnection();
			String sql = "select count(*) cnt from jdbc_board"
						+ " where board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			if(rs.next()) {
				cnt = rs.getInt("cnt");
			}
			
			if(cnt > 0) {
				chk = true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
			chk = false;
		}finally {
			disConnect();
		}
	return chk;
}

	public static void main(String[] args) {
		JdbcBoard jb = new JdbcBoard();
		jb.start();
	}

}
