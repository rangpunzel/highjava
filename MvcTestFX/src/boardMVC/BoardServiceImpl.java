package boardMVC;

import java.util.List;


public class BoardServiceImpl implements BoardService {
	private BoardDao boardDao = new BoardDaoImpl(); //객체 생성을 하지 않으면 NullPointer가 떨어짐
	
	@Override
	public int insertBoard(BoardVO bv) {
		return boardDao.insertBoard(bv);
	}

	@Override
	public int deleteBoard(int num) {
		return boardDao.deleteBoard(num);
	}

	@Override
	public int updateBoard(BoardVO bv) {
		return boardDao.updateBoard(bv);
	}

	@Override
	public List<BoardVO> displayBoardAll() {
		return boardDao.displayBoardAll();
	}

	@Override
	public List<BoardVO> search(BoardVO bv) {
		return boardDao.search(bv);
	}

	@Override
	public boolean getNumber(Integer no) {
		return boardDao.getNumber(no);
	}

}
