package report;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class ScoreController implements Initializable {

	@FXML TableView tableView;
	@FXML Button addBtn;
	@FXML Button grapeBtn;
	@FXML TextField name;
	@FXML TextField korScore;
	@FXML TextField matScore;
	@FXML TextField engScore;
	@FXML Button saveBtn;
	@FXML Button canBtn;
	Stage dialog = new Stage(StageStyle.UTILITY);
	Parent parent = null;
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@FXML public void addBtn(ActionEvent event) {

		addBtn.setOnAction(e->{
			// 새 창 띄우기
			//1. Stage객체 생성

/*			// 2. 모달창 여부 설정
			// 모달창은 자식창이 나타나면 부모창을 사용할 수 없다.
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			// 3. 부모창 지정
			dialog.initOwner();*/
			
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
		});
		
	}

	@FXML public void grapeBtn(ActionEvent event) {
		
		grapeBtn.setOnAction(e->{
			// 새 창 띄우기
			//1. Stage객체 생성

/*			// 2. 모달창 여부 설정
			// 모달창은 자식창이 나타나면 부모창을 사용할 수 없다.
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			// 3. 부모창 지정
			dialog.initOwner();*/
			
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
		});
		
	}

	@FXML public void saveBtn() {
		
	}

	@FXML public void canBtn() { //취소버튼 누를시 현재 창 닫기
		canBtn.setOnAction(e->{
		    Stage stage = (Stage) canBtn.getScene().getWindow();
		    stage.close();
		});
	}



}
