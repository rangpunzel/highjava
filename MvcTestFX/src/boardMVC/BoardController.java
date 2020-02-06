package boardMVC;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class BoardController implements Initializable {

	@FXML TableView<BoardVO> tableView;
	@FXML TableColumn<BoardVO,Integer> numCol;
	@FXML TableColumn<BoardVO,String> titleCol;
	@FXML TableColumn<BoardVO,String> writerCol;
	@FXML TableColumn<BoardVO,String> dateCol;
	@FXML Button addBtn;
	@FXML Button searchBtn;
	@FXML TextField search;
	@FXML Pagination pagination;
	
	private int from, to, itemsForPage;
	private ObservableList<BoardVO> allTableData, currentPageData;
	
	Stage dialog = new Stage(StageStyle.UTILITY);
	Parent parent = null;
	
	private BoardService boardService;
	
	public BoardController() {
		boardService = new BoardServiceImpl();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		numCol.setCellValueFactory(new PropertyValueFactory<>("board_no")); //컬럼 참조.
		titleCol.setCellValueFactory(new PropertyValueFactory<>("board_title"));
		writerCol.setCellValueFactory(new PropertyValueFactory<>("board_writer"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("board_date"));
		
		allTableData = FXCollections.observableArrayList();
		
		allTableData = FXCollections.observableArrayList(boardService.displayBoardAll()); //어레이리스트에 담은 데이터를 observableArrayList에 담음
		
		tableView.setItems(allTableData); //observableArrayList에 담은 데이터를 테이블뷰에 세팅.
		
		tableSetting();
		
		
		////////////////////검색버튼
		searchBtn.setOnAction(e->{
			
			if(search.getText().isEmpty()) {
				errMsg("", "검색어를 입력하세요.");
				search.requestFocus(); //해당 객체에 포커스 주기
				return;
			}
			
			BoardVO bv1 = new BoardVO();
			bv1.setBoard_title(search.getText());
			
			List<BoardVO> boardList = boardService.search(bv1);
			
			if(boardList.size() == 0) {
				errMsg("", "검색하신 게시글이 없습니다.");
				return;
			}else {
				
				allTableData = FXCollections.observableArrayList(boardList); //어레이리스트에 담은 데이터를 observableArrayList에 담음
				
				tableView.setItems(allTableData); 
			}
		});
		
		
		//게시글 읽기
		tableView.setOnMouseClicked(e->{
			
			dialog.setTitle("게시글보기");
			
			// 4. 자식창이 나타날 컨테이너 객체 생성

			try {
				parent = FXMLLoader.load(getClass().getResource("read.fxml"));
			}catch (IOException ex) {
				ex.printStackTrace();
			}
			// 5. Scene객체 생성해서 컨테이너 객체 추가
			Scene scene = new Scene(parent);
			
			// 6. Stage객체에 Scene객체 추가
			dialog.setScene(scene);
			dialog.setResizable(false);//크기고정
			dialog.show();
			//TableView에서 선택한 줄의 데이터를 가져온다.
			BoardVO bv = tableView.getSelectionModel().getSelectedItem();
			
			
			TextField r_writer = (TextField) parent.lookup("#r_writer"); 
			TextField r_title =  (TextField) parent.lookup("#r_title"); 
			TextArea r_content = (TextArea) parent.lookup("#r_content");
			
			r_writer.setText(bv.getBoard_writer());
			r_title.setText(bv.getBoard_title());
			r_content.setText(bv.getBoard_content());
			
			Button updateBtn = (Button) parent.lookup("#updateBtn"); 
			updateBtn.setOnAction(e1->{

				
		  });
			
			
			Button deleteBtn = (Button) parent.lookup("#deleteBtn"); 
			deleteBtn.setOnAction(e2->{
				
				Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
				alertConfirm.setTitle("삭제");
				alertConfirm.setContentText("삭제하시겠습니까?");
				
				// Alert창을 보여주고 사용자가 누른 버튼 값 읽어오기
				ButtonType confirmResult = alertConfirm.showAndWait().get();
				
				if(confirmResult == ButtonType.OK) {
					
					boardService.deleteBoard(bv.getBoard_no());
					//////////////삭제 테이블 새로고침이 안됨..ㅠ
					infoMsg("","삭제되었습니다.");
					
					dialog.close();
				}else if(confirmResult == ButtonType.CANCEL) {
					
				}
				
				
		  });
			
			
			
			
			
		});//게시글 읽기 끝
		
	}
	
	public void tableSetting() {
		
		itemsForPage = 5; // 한페이지에 보여줄 항목 수 설정
		int totPageCount = allTableData.size()%itemsForPage == 0 ?
				allTableData.size()/itemsForPage
				:allTableData.size()/itemsForPage + 1;
		pagination.setPageCount(totPageCount); //전체 페이지 수 설정
		
		pagination.setPageFactory(new Callback<Integer, Node>(){

			@Override
			public Node call(Integer pageIndex) {
				from = pageIndex * itemsForPage;
				to = from + itemsForPage - 1;
				tableView.setItems(getTableViewData(from, to));
				return tableView;
			}
			
			/**
			 * TableView에 채워줄 데이터를 가져오는 메서드
			 * @param from
			 * @param to
			 * @return
			 */
			private ObservableList<BoardVO> getTableViewData(int from, int to) {
				
				//현재 페이지의 데이터 초기화
				currentPageData = FXCollections.observableArrayList();
				
				int totSize = allTableData.size();
				for(int i = from; i<=to && i < totSize;i++) {
					currentPageData.add(allTableData.get(i));
				}
				return currentPageData;
			}
			
		});
		
		
	}
	
	

	////////////새글작성 버튼
	@FXML public void addBtnClicked(ActionEvent event) {

		dialog.setTitle("새글작성");
		
		// 4. 자식창이 나타날 컨테이너 객체 생성

		try {
			parent = FXMLLoader.load(getClass().getResource("posting.fxml"));
		}catch (IOException ex) {
			ex.printStackTrace();
		}
		// 5. Scene객체 생성해서 컨테이너 객체 추가
		Scene scene = new Scene(parent);
		
		// 6. Stage객체에 Scene객체 추가
		dialog.setScene(scene);
		dialog.setResizable(false);//크기고정
		dialog.show();
		
		
		TextField writer = (TextField) parent.lookup("#writer"); 
		TextField title =  (TextField) parent.lookup("#title"); 
		TextArea content = (TextArea) parent.lookup("#content");
		 
		
		Button sbtn = (Button) parent.lookup("#saveBtn");
		sbtn.setOnAction(e->{
			
			BoardVO bv = new BoardVO();
			bv.setBoard_title(title.getText());
			bv.setBoard_writer(writer.getText());
			bv.setBoard_content(content.getText());

			int cnt = boardService.insertBoard(bv);
			
			List<BoardVO> b = boardService.search(bv);
			BoardVO bb = new BoardVO();
			bb.setBoard_no(b.get(0).getBoard_no());
			bb.setBoard_title(b.get(0).getBoard_title());
			bb.setBoard_content(b.get(0).getBoard_content());
			bb.setBoard_writer(b.get(0).getBoard_writer());
			bb.setBoard_date(b.get(0).getBoard_date());
			
			
			allTableData.add(bb);
			
			if(cnt == 1) {
				infoMsg("", "새글이 작성되었습니다.");
			}else {
				errMsg("", "새글 작성 실패");
			}
			
			tableSetting();
			
			
			dialog.close();
		});
		
		
		Button canbtn = (Button) parent.lookup("#canBtn"); 
		  	  canbtn.setOnAction(e->{
			  Stage stage = (Stage) canbtn.getScene().getWindow(); 
			  stage.close(); 
		  });
		 
		Button resetbtn = (Button) parent.lookup("#resetBtn");
		  resetbtn.setOnAction(e->{ 
			  writer.clear(); 
			  title.clear(); 
			  content.clear();
		  });
		 
		
	}


	public void errMsg(String headerText, String msg) {
		Alert errAlert = new Alert(AlertType.ERROR);
		errAlert.setTitle("오류");
		errAlert.setHeaderText(headerText);
		errAlert.setContentText(msg);
		errAlert.showAndWait();
	}
	
	public void infoMsg(String headerText, String msg) {
		Alert infoAlert = new Alert(AlertType.INFORMATION);
		infoAlert.setTitle("확인");
		infoAlert.setHeaderText(headerText);
		infoAlert.setContentText(msg);
		infoAlert.showAndWait();
	}
	


}
