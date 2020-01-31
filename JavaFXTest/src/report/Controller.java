package report;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

public class Controller implements Initializable  {

	@FXML TextField txtField;
	@FXML RadioButton gender1;
	@FXML RadioButton gender2;
	@FXML CheckBox chkBox1;
	@FXML CheckBox chkBox2;
	@FXML CheckBox chkBox3;
	@FXML CheckBox chkBox4;
	@FXML CheckBox chkBox5;
	@FXML CheckBox chkBox6;
	@FXML CheckBox chkBox7;
	@FXML CheckBox chkBox8;
	@FXML Button btn;
	@FXML TextArea txtArea;
	
	CheckBox[] box;
	RadioButton[] gender;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		box = new CheckBox[] {chkBox1,chkBox2,chkBox3,chkBox4,chkBox5,chkBox6,chkBox7,chkBox8};
		gender = new RadioButton[] {gender1,gender2};
	}

	@FXML public void btnClicked(ActionEvent event) {
		
		
		if(txtField.getText().isEmpty()) {
		Alert alertError = new Alert(AlertType.ERROR);
		alertError.setTitle("ERROR");
		alertError.setContentText("이름을 입력해주세요.");
		alertError.showAndWait();
		}else {

		txtArea.setText("이름 : " + txtField.getText()+"\n\n");
		
		txtArea.appendText("성별 : ");
		for(int i=0; i < gender.length;i++) {
			if(gender[i].isSelected()) {
				txtArea.appendText(gender[i].getText()+"\n\n");
			}
		}
		
		txtArea.appendText("취미 : ");
		for(int i=0; i < box.length;i++) {
			if(box[i].isSelected()) {
				txtArea.appendText(box[i].getText()+" ");
			}
		}

	}
	}
	
	
	

}
