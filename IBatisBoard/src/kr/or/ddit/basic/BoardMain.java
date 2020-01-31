package kr.or.ddit.basic;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;



public class BoardMain {

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
			boolean chk = false;
			
			// 1-1. xml문서 읽어오기
			// 설정파일의 인코딩 설정
			Charset charset = Charset.forName("UTF-8");
			Resources.setCharset(charset);
			Reader rd = Resources.getResourceAsReader("sqlMapConfig.xml");
			
			// 1-2. 위에서 읽어온 Reader객체를 이용하여
			//		실제 작업을 진행할 객체 생성
			SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close(); //Reader객체 닫기
			
			// 응답 결과가 여러개일 경우에는 queryForList메서드를 사용한다.
			// 이 메서드는 여러개의 레코드를 VO에 담은 후 이 VO데이터를 
			// List에 추가해 주는 작업을 자동으로 수행한다.
			List<BoardVO> boardList;
			do {
			System.out.print("제목 검색 >> ");
			String titleSearch = scan.next();
			 
			boardList = smc.queryForList("boardTest.search", titleSearch);

			if(boardList.size() == 0) {
				System.out.println("검색 결과가 없습니다.");
				
			 }else {
				chk = true;
			}
			}while(chk == false);
			

			for(BoardVO boardVO : boardList) {
				System.out.println("번호 : " + boardVO.getBoard_no());
				System.out.println("날짜 : " + boardVO.getBoard_date());
				System.out.println("제목 : " + boardVO.getBoard_title());
				System.out.println("내용 : " + boardVO.getBoard_content());
				System.out.println("작성자 : " + boardVO.getBoard_writer());
				System.out.println();
			}
			System.out.println("--------------------------------");
			System.out.println("출력 작업 끝...");

		}catch(IOException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void displayBoardAll() {
		try {
			Charset charset = Charset.forName("UTF-8");
			Resources.setCharset(charset);
			Reader rd = Resources.getResourceAsReader("sqlMapConfig.xml");
			
			// 1-2. 위에서 읽어온 Reader객체를 이용하여
			//		실제 작업을 진행할 객체 생성
			SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close(); //Reader객체 닫기
			
			// 1) 응답의 결과가 여러개일 경우
			List<BoardVO> boardList;
			
			// 응답 결과가 여러개일 경우에는 queryForList메서드를 사용한다.
			// 이 메서드는 여러개의 레코드를 VO에 담은 후 이 VO데이터를 
			// List에 추가해 주는 작업을 자동으로 수행한다.
			boardList = smc.queryForList("boardTest.displayBoardAll");
			for(BoardVO boardVO : boardList) {
				System.out.println("번호 : " + boardVO.getBoard_no());
				System.out.println("날짜 : " + boardVO.getBoard_date());
				System.out.println("제목 : " + boardVO.getBoard_title());
				System.out.println("내용 : " + boardVO.getBoard_content());
				System.out.println("작성자 : " + boardVO.getBoard_writer());
				System.out.println();
			}
			System.out.println("--------------------------------");
			System.out.println("출력 작업 끝...");

		}catch(IOException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void updateBoard() {
				
			boolean chk = false; //중복 체크
			int num = 0;


			do {

			System.out.print("수정할 게시번호 >>");
			num = Integer.parseInt(scan.next());
			
			scan.nextLine();
			
			
			chk = getNumber(num);
			
			if(chk == false) {
				System.out.println("수정할 게시 번호가 없어요.");
			}
			
			}while(chk == false);

			
			System.out.print("수정할 제목 >>");
			String title = scan.nextLine();

			
			System.out.print("수정할 작성자 >>");
			String writer = scan.next();
			
			scan.nextLine();

			
			try {
				
			
			System.out.println("수정할 내용 >>");
			String content = scan.nextLine();

			// 1-1. xml문서 읽어오기
			// 설정파일의 인코딩 설정
			Charset charset = Charset.forName("UTF-8");
			Resources.setCharset(charset);
			Reader rd = Resources.getResourceAsReader("sqlMapConfig.xml");
			
			// 1-2. 위에서 읽어온 Reader객체를 이용하여
			//		실제 작업을 진행할 객체 생성
			SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close(); //Reader객체 닫기

			BoardVO bv2 = new BoardVO();
			bv2.setBoard_no(num);
			bv2.setBoard_title(title);
			bv2.setBoard_content(content);
			bv2.setBoard_writer(writer);
			
			// update()메서드의 반환값은 성공한 레코드 수이다.
			int cnt = smc.update("boardTest.updateBoard", bv2);
			
			if(cnt > 0) {
				System.out.println("update 작업 성공");
			}else {
				System.out.println("update 작업 실패!!!");
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
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
				// 1-1. xml문서 읽어오기
				// 설정파일의 인코딩 설정
				Charset charset = Charset.forName("UTF-8");
				Resources.setCharset(charset);
				Reader rd = Resources.getResourceAsReader("sqlMapConfig.xml");
				
				// 1-2. 위에서 읽어온 Reader객체를 이용하여
				//		실제 작업을 진행할 객체 생성
				SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
				rd.close(); //Reader객체 닫기
				
				
				int cnt2 = smc.delete("boardTest.deleteBoard", num);
				if(cnt2 > 0) {
					System.out.println("delete 작업 성공");
				}else {
					System.out.println("delete 작업 실패!!!");
				}
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void insertBoard() {
		try {
			// 1-1. xml문서 읽어오기
			// 설정파일의 인코딩 설정
			Charset charset = Charset.forName("UTF-8");
			Resources.setCharset(charset);
			Reader rd = Resources.getResourceAsReader("sqlMapConfig.xml");
			
			// 1-2. 위에서 읽어온 Reader객체를 이용하여
			//		실제 작업을 진행할 객체 생성
			SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close(); //Reader객체 닫기
			
			System.out.print("작성자 >>");
			String writer = scan.next();
			System.out.print("제목  >> ");
			String title = scan.next();
			scan.nextLine(); //남아있는 버퍼 제거?
			System.out.println("내용 >> ");
			String content = scan.nextLine();

			BoardVO bv = new BoardVO();
			
			bv.setBoard_writer(writer);
			bv.setBoard_title(title);
			bv.setBoard_content(content);
			
			Object obj = smc.insert("boardTest.insertBoard", bv);
			if(obj == null) {
				System.out.println("게시글 등록 성공");
			}else {
				System.out.println("게시글 등록 실패");
			}
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private boolean getNumber(Integer no) {
		boolean chk = false;
		try {
			// 1-1. xml문서 읽어오기
			// 설정파일의 인코딩 설정
			Charset charset = Charset.forName("UTF-8");
			Resources.setCharset(charset);
			Reader rd = Resources.getResourceAsReader("sqlMapConfig.xml");
			
			// 1-2. 위에서 읽어온 Reader객체를 이용하여
			//		실제 작업을 진행할 객체 생성
			SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close(); //Reader객체 닫기
			
			BoardVO bv = (BoardVO)smc.queryForObject("boardTest.getNumber", no);


			if(bv != null) {
				chk = true;
			}
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	return chk;
}

	public static void main(String[] args) {
		BoardMain jb = new BoardMain();
		jb.start();
	}

}
