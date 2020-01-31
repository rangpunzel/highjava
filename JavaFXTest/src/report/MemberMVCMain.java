package report;

import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kr.or.ddit.basic.T14_TableViewTest.Member;

public class MemberMVCMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TableView에 나타낼 데이터 구성하기
		ObservableList<Member> data =
				FXCollections.observableArrayList();
		
		BorderPane root = new BorderPane();
		
		//TableView에 데이터 세팅하기
		TableView<Member> table = new TableView<>(data);
		
		
		//해당컬럼에 나타낼 데이터 연결하기
		// (출력할 객체의 멤버변수와 출력할 컬럼을 매칭시킨다.)
		TableColumn<Member, String> memIdCol = new TableColumn<>("회원ID");
		memIdCol.setCellValueFactory(new PropertyValueFactory<>("memId"));
		memIdCol.setStyle("-fx-alignment: CENTER;"); //중앙정렬
		
		TableColumn<Member, String> memNmCol = new TableColumn<>("회원이름");
		memNmCol.setCellValueFactory(new PropertyValueFactory<>("memNm"));
		
		TableColumn<Member, Integer> telCol = new TableColumn<>("회원전화");
		telCol.setCellValueFactory(new PropertyValueFactory<>("tel"));
		
		TableColumn<Member, Integer> addrCol = new TableColumn<>("회원주소");
		addrCol.setCellValueFactory(new PropertyValueFactory<>("addr"));
		
		// 생성된 각 컬럼들을 TableView에 추가한다.
		table.getColumns().addAll(memIdCol, memNmCol, telCol, addrCol);
		
		// table.setItems(data);
		
		//----------------------------------------------------
		
		GridPane grid = new GridPane();
		Text txt1 = new Text("회원 ID :");
		Text txt2 = new Text("회원이름 :");
		Text txt4 = new Text("회원전화 :");
		Text txt5 = new Text("회원주소 :");
		
		TextField txtMemId = new TextField();
		TextField txtMemNm = new TextField();
		TextField txtTel = new TextField();
		TextField txtAddr = new TextField();
		
		txtMemId.setPromptText("회원 ID");
		txtMemNm.setPromptText("회원 이름");
		txtTel.setPromptText("회원 전화");
		txtAddr.setPromptText("회원 주소");
		
		grid.add(txt1, 1, 1);
		grid.add(txt2, 1, 2);
		grid.add(txt4, 1, 3);
		grid.add(txt5, 1, 4);
		
		grid.add(txtMemId, 2, 1);
		grid.add(txtMemNm, 2, 2);
		grid.add(txtTel, 2, 3);
		grid.add(txtAddr, 2, 4);
		
		grid.setVgap(5);
		grid.setHgap(10);
		grid.setPadding(new Insets(10,0,10,0));
		grid.setStyle("-fx-alignment: CENTER;"); //중앙정렬
		//------------------------------------------------------
		HBox hbox = new HBox(10);
		hbox.setPadding(new Insets(10));
		hbox.setStyle("-fx-alignment: CENTER;"); //중앙정렬

		
		Button btnAdd = new Button("추가");
		Button btnEdit = new Button("수정");
		Button btnDel = new Button("삭제");
		Button btnTest1 = new Button("확 인");
		Button btnTest2 = new Button("취 소");
		
		btnAdd.setMaxWidth(100);
		
		btnTest1.setDisable(true);
		btnTest2.setDisable(true);
		
		txtMemId.setEditable(false);
		txtMemNm.setEditable(false);
		txtTel.setEditable(false);
		txtAddr.setEditable(false);
		
		//------------------------------추가

		btnAdd.setOnAction(e->{	
			if(btnEdit.isDisable() == true) {
				Alert alertError = new Alert(AlertType.ERROR);
				alertError.setTitle("ERROR");
				alertError.setHeaderText("ERROR");
				alertError.setContentText("추가하시려면 확인버튼, 취소하시려면 취소버튼을 눌러주세요.");
				alertError.showAndWait();
			}
			
			txtMemId.setEditable(true);
			txtMemNm.setEditable(true);
			txtTel.setEditable(true);
			txtAddr.setEditable(true);
				//객체를 비활성화 또는 활성화 시키는 메서드 => setDisabled()
				//이 메서드에 true를 설정하면 '비활성화'
				//false를 설정하면 '활성화'된다.
				btnAdd.setDisable(false);
				btnEdit.setDisable(true);
				btnDel.setDisable(true);
				
				btnTest1.setDisable(false);
				btnTest2.setDisable(false);
				

		});

		//---------------------- 수정


		
		
		btnEdit.setOnAction(e->{
			if(btnAdd.isDisable() == true) {
				Alert alertError = new Alert(AlertType.ERROR);
				alertError.setTitle("ERROR");
				alertError.setHeaderText("ERROR");
				alertError.setContentText("수정하시려면 확인버튼, 취소하시려면 취소버튼을 눌러주세요.");
				alertError.showAndWait();
			}
			
			//객체를 비활성화 또는 활성화 시키는 메서드 => setDisabled()
			//이 메서드에 true를 설정하면 '비활성화'
			//false를 설정하면 '활성화'된다.
			txtMemId.setEditable(true);
			txtMemNm.setEditable(true);
			txtTel.setEditable(true);
			txtAddr.setEditable(true);
			
			btnAdd.setDisable(true);
			btnEdit.setDisable(false);
			btnDel.setDisable(true);
			
			btnTest1.setDisable(false);
			btnTest2.setDisable(false);
	});
	
		

		//-----------------------------삭제
		btnDel.setOnAction(e->{
			if(table.getSelectionModel().isEmpty()) {
				errMsg("작업 오류", "삭제할 자료를 선택한 후 삭제하세요.");
				return;
			}
			
			Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
			alertConfirm.setTitle("삭제 확인");
			alertConfirm.setContentText("정말로 삭제하시겠습니까?");
			
			// Alert창을 보여주고 사용자가 누른 버튼 값 읽어오기
			ButtonType confirmResult = alertConfirm.showAndWait().get();
			
			if(confirmResult == ButtonType.OK) {
				infoMsg("작업 결과", txtMemId.getText() + "님 정보를 삭제했습니다.");
				data.remove(table.getSelectionModel().getSelectedIndex());
			}else if(confirmResult == ButtonType.CANCEL) {

			}
			

			
			txtMemId.clear();
			txtMemNm.clear();
			txtTel.clear();
			txtAddr.clear();
		});
		
		
		
		// TableView를 클릭했을때 처리
		table.setOnMouseClicked(e->{
			//TableView에서 선택한 줄의 데이터를 가져온다.
			Member mem = table.getSelectionModel().getSelectedItem();
			
			if(mem == null) { //테이블 내용이 빈상태에서 눌렀을때 오류 생기는거 방지하는거.
				return;
			}
			
			txtMemId.setText(mem.getMemId());
			txtMemNm.setText(mem.getMemNm());
			txtTel.setText(mem.getTel());
			txtAddr.setText(mem.getAddr());
		});
		
		//-------------------------------확인버튼-----------------------------
		btnTest1.setOnAction(e->{
			if(btnAdd.isDisable() == false) { //추가버튼 활성
			if(txtMemId.getText().isEmpty()
					|| txtMemNm.getText().isEmpty()
					|| txtTel.getText().isEmpty()
					|| txtAddr.getText().isEmpty()
					) {
				//System.out.println("빈 항목이 있습니다.");
				errMsg("작업오류", "빈 항목이 있습니다.");
				return;
			}
			
			data.add(new Member(txtMemId.getText(),
					txtMemNm.getText(),
								txtTel.getText(),
								txtAddr.getText()));
			infoMsg("작업 결과", txtMemId.getText() + "님 정보를 추가했습니다.");
			
			}else if(btnEdit.isDisable() == false) { //수정버튼 활성

				if(txtMemId.getText().isEmpty()
						|| txtMemNm.getText().isEmpty()
						|| txtTel.getText().isEmpty()
						|| txtAddr.getText().isEmpty()
						) {
					errMsg("작업오류", "빈 항목이 있습니다.");
					
					return;
				}
				
				data.set(table.getSelectionModel().getSelectedIndex(),
						new Member(txtMemId.getText(),
									txtMemNm.getText(),
									txtTel.getText(),
									txtAddr.getText()));
				infoMsg("작업 결과", txtMemId.getText() + "님 정보를 수정했습니다.");
				
				txtMemId.clear();
				txtMemNm.clear();
				txtTel.clear();
				txtAddr.clear();
	}
			txtMemId.setEditable(false);
			txtMemNm.setEditable(false);
			txtTel.setEditable(false);
			txtAddr.setEditable(false);
			
			btnAdd.setDisable(false);
			btnEdit.setDisable(false);
			btnDel.setDisable(false);
			
			btnTest1.setDisable(true);
			btnTest2.setDisable(true);
			
			txtMemId.clear();
			txtMemNm.clear();
			txtTel.clear();
			txtAddr.clear();
			return;
		});


		//-------------------------------쥐소버튼-----------------------------
		
		btnTest2.setOnAction(e->{
			
			//객체를 비활성화 또는 활성화 시키는 메서드 => setDisabled()
			//이 메서드에 true를 설정하면 '비활성화'
			//false를 설정하면 '활성화'된다.
			txtMemId.setEditable(false);
			txtMemNm.setEditable(false);
			txtTel.setEditable(false);
			txtAddr.setEditable(false);
			
			btnAdd.setDisable(false);
			btnEdit.setDisable(false);
			btnDel.setDisable(false);
			
			btnTest1.setDisable(true);
			btnTest2.setDisable(true);
			
			txtMemId.clear();
			txtMemNm.clear();
			txtTel.clear();
			txtAddr.clear();

		});

		hbox.getChildren().addAll(btnAdd,btnEdit,btnDel,btnTest1,btnTest2);
		
		root.setTop(grid);
		root.setCenter(hbox);
		root.setBottom(table);
		root.setPadding(new Insets(10));
		
		Scene scene = new Scene(root, 600, 600);
		
		primaryStage.setTitle("MemberMVCMain");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
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
		infoAlert.setTitle("정보 확인");
		infoAlert.setHeaderText(headerText);
		infoAlert.setContentText(msg);
		infoAlert.showAndWait();
	}
	
	public class Member{
		private String memId;
		private String memNm;
		private String tel;
		private String addr;
		public String getMemId() {
			return memId;
		}
		public void setMemId(String memId) {
			this.memId = memId;
		}
		public String getMemNm() {
			return memNm;
		}
		public void setMemNm(String memNm) {
			this.memNm = memNm;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getAddr() {
			return addr;
		}
		public void setAddr(String addr) {
			this.addr = addr;
		}
		public Member(String memId, String memNm, String tel, String addr) {
			super();
			this.memId = memId;
			this.memNm = memNm;
			this.tel = tel;
			this.addr = addr;
		}
		

		


		
		
	}
}
