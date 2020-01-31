package score;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import address.AddrVO;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import report.MemberMVCMain.Member;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;

public class ScoreController implements Initializable {


	Stage dialog = new Stage(StageStyle.UTILITY);
	Parent parent = null;
	@FXML Button addBtn;
	@FXML Button grapeBtn;
	@FXML TableView<ScoreVO> tableView;
	@FXML TableColumn<ScoreVO, String> nameCol;
	@FXML TableColumn<ScoreVO, Integer> korCol;
	@FXML TableColumn<ScoreVO, Integer> matCol;
	@FXML TableColumn<ScoreVO, Integer> engCol;
	
	//addScore
	@FXML TextField name;
	@FXML TextField korScore;
	@FXML TextField matScore;
	@FXML TextField engScore;
	@FXML Button saveBtn;
	@FXML Button canBtn;



	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		korCol.setCellValueFactory(new PropertyValueFactory<>("korScore"));
		matCol.setCellValueFactory(new PropertyValueFactory<>("matScore"));
		engCol.setCellValueFactory(new PropertyValueFactory<>("engScore"));
		

	}



	@FXML public void saveBtn() {
		

	}

	@FXML public void canBtn() { //취소버튼 누를시 현재 창 닫기
		    Stage stage = (Stage) canBtn.getScene().getWindow();
		    stage.close();
	}



	@FXML public void addBtn(ActionEvent event) {
		
		// 새 창 띄우기
		//1. Stage객체 생성
		
		dialog.setTitle("추가");
		
		// 4. 자식창이 나타날 컨테이너 객체 생성

		try {
			parent = FXMLLoader.load(getClass().getResource("addScore.fxml"));
		}catch (IOException ex) {
			ex.printStackTrace();
		}
		// 5. Scene객체 생성해서 컨테이너 객체 추가
		Scene scene = new Scene(parent);
		
		// 6. Stage객체에 Scene객체 추가
		dialog.setScene(scene);
		dialog.setResizable(false);//크기고정
		dialog.show();
	}



	@FXML public void grapeBtn(ActionEvent event) {

	dialog.setTitle("추가");
	
	// 4. 자식창이 나타날 컨테이너 객체 생성

	try {
		parent = FXMLLoader.load(getClass().getResource("barChart.fxml"));
	}catch (IOException ex) {
		ex.printStackTrace();
	}
	// 5. Scene객체 생성해서 컨테이너 객체 추가
	Scene scene = new Scene(parent);
	
	// 6. Stage객체에 Scene객체 추가
	dialog.setScene(scene);
	dialog.setResizable(false);//크기고정
	dialog.show();
	}
}
