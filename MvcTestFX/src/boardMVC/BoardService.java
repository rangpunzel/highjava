package boardMVC;

import java.util.List;

public interface BoardService {

	public int insertBoard(BoardVO bv);
	
	public int deleteBoard(int num);
	
	public int updateBoard(BoardVO bv);
	
	public List<BoardVO> displayBoardAll();
	
	public List<BoardVO> search(BoardVO bv);
	
	public boolean getNumber(Integer no);

}
