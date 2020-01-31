package kr.or.ddit.board.dao;

import java.util.List;

import kr.or.ddit.board.vo.BoardVO;

public interface BoardDao {
	
	public int insertBoard(BoardVO bv);
	
	public int deleteBoard(int num);
	
	public int updateBoard(BoardVO bv);
	
	public List<BoardVO> displayBoardAll();
	
	public List<BoardVO> search(BoardVO bv);
	
	public boolean getNumber(Integer no);
	

}
