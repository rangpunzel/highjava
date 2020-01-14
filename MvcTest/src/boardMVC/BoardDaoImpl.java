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
					+ "values(board_seq.nextVal,?,?,sysdate,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bv.getTitle());
			pstmt.setString(2, bv.getWriter());
			pstmt.setString(3, bv.getContent());
			
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
			pstmt.setString(1, bv.getTitle());
			pstmt.setString(2, bv.getWriter());
			pstmt.setString(3, bv.getContent());
			pstmt.setInt(4, bv.getNum());
			
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
				
				bv.setNum(rs.getInt("board_no"));
				bv.setTitle(rs.getString("board_title"));
				bv.setWriter(rs.getString("board_writer"));
				bv.setDate(rs.getString("board_date"));
				bv.setContent(rs.getString("board_content"));
				
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
			
			String sql = "SELECT * FROM jdbc_board WHERE board_title LIKE ('%" + bv.getTitle() + "%')";

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				BoardVO boardVO = new BoardVO();
				
				boardVO.setNum(rs.getInt("board_no"));
				boardVO.setTitle(rs.getString("board_title"));
				boardVO.setWriter(rs.getString("board_writer"));
				boardVO.setDate(rs.getString("board_date"));
				boardVO.setContent(rs.getString("board_content"));
				
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
