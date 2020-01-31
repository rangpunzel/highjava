package kr.or.ddit.board.dao;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.board.vo.BoardVO;

public class BoardDaoImpl implements BoardDao {
	
	private static BoardDaoImpl dao;
	
	private SqlMapClient smc;
	
	private BoardDaoImpl(){
		// 1-1. xml문서 읽어오기
		// 설정파일의 인코딩 설정
		Charset charset = Charset.forName("UTF-8");
		Resources.setCharset(charset);
		Reader rd;
		try {
			rd = Resources.getResourceAsReader("sqlMapConfig.xml");
			// 1-2. 위에서 읽어온 Reader객체를 이용하여
			//		실제 작업을 진행할 객체 생성
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close(); //Reader객체 닫기
		} catch (IOException e) {
			System.out.println("SqlMapClient 객체 생성 실패!!!");
			e.printStackTrace();
		}
					
	}
	
	public static BoardDaoImpl getInstance() {
		if(dao == null) {
			dao = new BoardDaoImpl();
		}
		return dao;
	}


	@Override
	public int insertBoard(BoardVO bv) {
		int cnt =0;
		try {
			Object obj = smc.insert("boardTest.insertBoard", bv);
			
			if(obj == null) {  //성공
				cnt = 1;
			}

			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int deleteBoard(int num) {
		int cnt = 0;
		try {
			cnt = smc.delete("boardTest.deleteBoard",num);

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	

	@Override
	public int updateBoard(BoardVO bv) {
		int cnt = 0;
		try {
			cnt = smc.update("boardTest.updateBoard",bv);

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<BoardVO> displayBoardAll() {
		
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		
		try {
			boardList = smc.queryForList("boardTest.displayBoardAll");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return boardList;
	}
	

	@Override
	public List<BoardVO> search(BoardVO bv) {
		List<BoardVO> boardList = new ArrayList<BoardVO>();
			
			try {
				boardList = smc.queryForList("boardTest.search",bv);

			}catch(SQLException e) {
				boardList = null;
				e.printStackTrace();
			}
			return boardList;
	
	}

	@Override
	public boolean getNumber(Integer no) {
		boolean chk = false;
		
		try {
				BoardVO bv = (BoardVO)smc.queryForObject("boardTest.getNumber", no);


				if(bv != null) {
					chk = true;
				}

		}catch(SQLException e) {
			e.printStackTrace();
			chk = false;
		}
	return chk;
	}

}
