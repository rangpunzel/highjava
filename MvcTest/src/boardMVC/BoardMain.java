package boardMVC;

import java.util.List;
import java.util.Scanner;


public class BoardMain {
	private BoardService boardService;
	
	public BoardMain() {
		boardService = new BoardServiceImpl();
	}

	
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

			
			System.out.print("제목 검색 >> ");
			String titleSearch = scan.next();
			

			System.out.println("번호" + "\t"
					+ "날짜" + "\t"+"\t"
					+ "제목" + "\t"
					+ "작성자" + "\t"+ "\t"
					+ "내용" + "\t"
					);
			BoardVO bv1 = new BoardVO();
			bv1.setTitle(titleSearch);
			
			List<BoardVO> boardList = boardService.search(bv1);
			
			if(boardList.size() == 0) {
				System.out.println("게시글이 없습니다.");
			}else {
				for(BoardVO bv : boardList) {
					
				System.out.println(bv.getNum() + "\t"
								+ bv.getDate() + "\t"
								+ bv.getTitle() + "\t"
								+ bv.getWriter() + "\t"+ "\t"
								+ bv.getContent() + "\t"
								);
			}

			}

		
	}

	private void displayBoardAll() {
			System.out.println("번호" + "\t"
					+ "날짜" + "\t"+"\t"
					+ "제목" + "\t"
					+ "작성자" + "\t"+ "\t"
					+ "내용" + "\t"
					);
			
			List<BoardVO> boardList = boardService.displayBoardAll();
			
			if(boardList.size() == 0) {
				System.out.println("게시글이 없습니다.");
			}else {
				for(BoardVO bv : boardList) {
					
				System.out.println(bv.getNum() + "\t"
								+ bv.getDate() + "\t"
								+ bv.getTitle() + "\t"
								+ bv.getWriter() + "\t"+ "\t"
								+ bv.getContent() + "\t"
								);
			}

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
			
			System.out.println("수정할 내용 >>");
			String content = scan.nextLine();

			BoardVO bv = new BoardVO();
			bv.setTitle(title);
			bv.setWriter(writer);
			bv.setContent(content);
			bv.setNum(num);

			int cnt = boardService.updateBoard(bv);
			
			if(cnt > 0) {
				System.out.println("게시글 수정 성공...");
			}else {
				System.out.println("게시글 수정 실패!!!");
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
			
			
			int cnt = boardService.deleteBoard(num);
			if(cnt > 0) {
				System.out.println("게시글 삭제 성공...");
			}else {
				System.out.println("게시글 삭제 실패!!!");
			}
			
		
	}

	private void insertBoard() {

			System.out.print("작성자 >>");
			String writer = scan.next();
			System.out.print("제목  >> ");
			String title = scan.next();
			scan.nextLine(); //남아있는 버퍼 제거?
			System.out.println("내용 >> ");
			String content = scan.nextLine();

			BoardVO bv = new BoardVO();
			
			bv.setTitle(title);
			bv.setWriter(writer);
			bv.setContent(content);

			int cnt = boardService.insertBoard(bv);

			if(cnt > 0) {
				System.out.println("게시글이 등록 되었습니다.");
			}else {
				System.out.println("게시글 작성 실패!!!");
			}
		
	}

	
	private boolean getNumber(Integer no) {
		boolean chk = false;

		chk = boardService.getNumber(no);
		
		return chk;
	}

	public static void main(String[] args) {
		BoardMain jb = new BoardMain();
		jb.start();
	}

}
