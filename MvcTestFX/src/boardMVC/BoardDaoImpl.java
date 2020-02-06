package boardMVC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.util.DBUtil2;
import kr.or.ddit.util.DBUtil3;

public class BoardDaoImpl implements BoardDao {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	/**
	 * 자원반납용 메서드
	 */
		private void disConnect() {
			//  사용했던 자원 반납
			if(rs!=null)try{ rs.close(); }catch(SQLException ee){}
			if(stmt!=null)try{ stmt.close(); }catch(SQLException ee){}
			if(pstmt!=null)try{ pstmt.close(); }catch(SQLException ee){}
			if(conn!=null)try{ conn.close(); }catch(SQLException ee){}
		}
	

	@Override
	public int insertBoard(BoardVO bv) {
		int cnt =0;
		try {
			conn = DBUtil3.getConnection();
			String sql = "insert into jdbc_board (board_no,"
					+ "board_title,"
					+ "board_writer,"
					+ "board_date,"
					+ "board_content) "
					+ "values(jdbc_board_seq.nextVal,?,?,sysdate,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bv.getBoard_title());
			pstmt.setString(2, bv.getBoard_writer());
			pstmt.setString(3, bv.getBoard_content());
			
			cnt = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disConnect(); //자원반납
		}
		return cnt;
	}

	@Override
	public int deleteBoard(int num) {
		int cnt =0;
		try {
			conn = DBUtil3.getConnection();
			String sql = "delete from jdbc_board where board_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			cnt = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("게시글 삭제 실패");
		}finally {
			disConnect();
		}
		return cnt;
	}
	
	

	@Override
	public int updateBoard(BoardVO bv) {
		int cnt = 0;
		try {
			conn = DBUtil3.getConnection();
			
			String sql = "update jdbc_board "
					   + " set board_title = ?, "
					   + " board_writer = ?,"
					   + " board_content = ? "
					   + " where board_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bv.getBoard_title());
			pstmt.setString(2, bv.getBoard_writer());
			pstmt.setString(3, bv.getBoard_content());
			pstmt.setInt(4, bv.getBoard_no());
			
			cnt = pstmt.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<BoardVO> displayBoardAll() {
		
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		try {
			conn = DBUtil3.getConnection();
			
			String sql = "select * from jdbc_board";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);

			while(rs.next()) {
				BoardVO bv = new BoardVO();
				
				bv.setBoard_no(rs.getInt("board_no"));
				bv.setBoard_title(rs.getString("board_title"));
				bv.setBoard_writer(rs.getString("board_writer"));
				bv.setBoard_date(rs.getString("board_date"));
				bv.setBoard_content(rs.getString("board_content"));
				
				boardList.add(bv);
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return boardList;
	}
	

	@Override
	public List<BoardVO> search(BoardVO bv) {
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		try {
			conn = DBUtil3.getConnection();
			
			String sql = "SELECT * FROM jdbc_board WHERE board_title = ('" + bv.getBoard_title()+ "')";

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				BoardVO boardVO = new BoardVO();
				
				boardVO.setBoard_no(rs.getInt("board_no"));
				boardVO.setBoard_title(rs.getString("board_title"));
				boardVO.setBoard_writer(rs.getString("board_writer"));
				boardVO.setBoard_date(rs.getString("board_date"));
				boardVO.setBoard_content(rs.getString("board_content"));
				
				boardList.add(boardVO);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return boardList;
	
	}

	@Override
	public boolean getNumber(Integer no) {
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

}
