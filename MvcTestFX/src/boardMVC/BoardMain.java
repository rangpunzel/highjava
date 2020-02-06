package boardMVC;

import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class BoardMain extends Application  {
	private BoardService boardService;
	
	public BoardMain() {
		boardService = new BoardServiceImpl();
	}

	
	private Scanner scan = new Scanner(System.in); 
	
	
	public void start(){
		int choice;
		do{
			choice = scan.nextInt(); // 메뉴번호 입력받기
			switch(choice){
				case 2 :  // 자료 삭제
					deleteBoard();				
					break;
				case 3 :  // 자료 수정
					updateBoard();
					break;
				case 6 :  // 작업 끝
					System.out.println("작업을 마칩니다.");
					break;
				default :
					System.out.println("번호를 잘못 입력했습니다. 다시입력하세요");
			}
		}while(choice!=6);
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
			bv.setBoard_title(title);
			bv.setBoard_writer(writer);
			bv.setBoard_content(content);
			bv.setBoard_no(num);

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


	
	private boolean getNumber(Integer no) {
		boolean chk = false;

		chk = boardService.getNumber(no);
		
		return chk;
	}

	public static void main(String[] args) {
		launch(args);
		/*
		 * BoardMain jb = new BoardMain(); 
		 * jb.start();
		 */
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("board.fxml"));
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("게시판");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
