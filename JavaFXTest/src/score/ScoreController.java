package score;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScoreController implements Initializable {
	ObservableList<ScoreVO> data =
			FXCollections.observableArrayList();

	@FXML TableColumn<ScoreVO,String> nameCol;
	@FXML TableColumn<ScoreVO,Integer> korCol;
	@FXML TableColumn<ScoreVO,Integer> matCol;
	@FXML TableColumn<ScoreVO,Integer> engCol;
	@FXML Button addBtn;
	@FXML Button barchartBtn;
	TextField name;
	TextField korScore;
	TextField matScore;
	TextField engScore;
	@FXML Button saveBtn;
	@FXML Button canBtn;
	@FXML TableView<ScoreVO> tableView= new TableView<>(data);
	private ObservableList<ScoreVO> tableData;
	List<ScoreVO> tableList = new ArrayList<ScoreVO>();
	
	BarChart<String, Number> barchart;
	CategoryAxis xAxis;
	NumberAxis yAxis;
	
	PieChart pieChart;


	Stage dialog = new Stage(StageStyle.UTILITY);
	Parent parent = null;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		tableView.setOnMouseClicked(e->{
			
			dialog.setTitle("파이 그래프");
			
			// 4. 자식창이 나타날 컨테이너 객체 생성

			try {
				parent = FXMLLoader.load(getClass().getResource("pieChart.fxml"));
			}catch (IOException ex) {
				ex.printStackTrace();
			}
			// 5. Scene객체 생성해서 컨테이너 객체 추가
			Scene scene = new Scene(parent);
			
			pieChart = (PieChart) parent.lookup("#pieChart");
			
			ScoreVO sv = tableView.getSelectionModel().getSelectedItem();
		
			
			//차트에 나타날 데이터 구성하기
			ObservableList<PieChart.Data> pieChartData = 
					FXCollections.observableArrayList(
							new PieChart.Data("국어", sv.getKorScore()),
							new PieChart.Data("수학", sv.getMatScore()),
							new PieChart.Data("영어", sv.getEngScore())
							);
			
			pieChart.setLabelLineLength(50);
			pieChart.setLegendSide(Side.BOTTOM);//범례가 나타날 위치
			pieChart.setData(pieChartData); //데이터 설정
			
			
			// 6. Stage객체에 Scene객체 추가
			dialog.setScene(scene);
			dialog.setResizable(false);//크기고정
			dialog.show();
			//TableView에서 선택한 줄의 데이터를 가져온다.
			
		});

		
	}

	
	@FXML public void addBtnClicked() {

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
		
		name = (TextField) parent.lookup("#name");
		korScore = (TextField) parent.lookup("#korScore");
		matScore = (TextField) parent.lookup("#matScore");
		engScore = (TextField) parent.lookup("#engScore");
		
		Button sbtn = (Button) parent.lookup("#saveBtn");
		sbtn.setOnAction(e->saveBtn(e));

	}
	
	public void saveBtn(ActionEvent e) {
		

		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		korCol.setCellValueFactory(new PropertyValueFactory<>("korScore"));
		matCol.setCellValueFactory(new PropertyValueFactory<>("matScore"));
		engCol.setCellValueFactory(new PropertyValueFactory<>("engScore"));
		


		// ResultSet객체에 저장되어 있는 자료를 반복문과 next()메서드를 이용하여 차례로 읽어와 처리한다.
			 
			 ScoreVO scvo = new ScoreVO();
			 scvo.setName(name.getText());
			 scvo.setKorScore(Integer.parseInt(korScore.getText()));
			 scvo.setMatScore(Integer.parseInt(matScore.getText()));
			 scvo.setEngScore(Integer.parseInt(engScore.getText()));
			 
			 tableList.add(scvo);//VO 객체에 담아서 어레이리스트에 담음.

			 
		 tableData = FXCollections.observableArrayList(tableList); //어레이리스트에 담은 데이터를 observableArrayList에 담음
		 
		 tableView.setItems(tableData); //observableArrayList에 담은 데이터를 테이블뷰에 세팅.

		 dialog.close();
	
		
	}

	@FXML public void barchartBtnClicked() {
		
		
		dialog.setTitle("막대 그래프");
		
		// 4. 자식창이 나타날 컨테이너 객체 생성

		try {
			parent = FXMLLoader.load(getClass().getResource("barChart.fxml"));
		}catch (IOException ex) {
			ex.printStackTrace();
		}
		// 5. Scene객체 생성해서 컨테이너 객체 추가
		Scene scene = new Scene(parent);
		
		
		barchart = (BarChart<String, Number>) parent.lookup("#barchart");
		xAxis = (CategoryAxis) parent.lookup("#xAxis");
		yAxis =  (NumberAxis) parent.lookup("#yAxis");
		
		
		
		// BarChart에 나타날 데이터 구성하기
		XYChart.Series<String, Number> ser1 = new XYChart.Series<>();//내부클래스
		XYChart.Series<String, Number> ser2 = new XYChart.Series<>();//내부클래스
		XYChart.Series<String, Number> ser3 = new XYChart.Series<>();//내부클래스
		
		ser1.setName("국어");
		ser2.setName("수학");
		ser3.setName("영어");
		for(int i =0; i < tableList.size();i++) {
			
		ser1.getData().add(new XYChart.Data<String, Number>(tableList.get(i).getName(),tableList.get(i).getKorScore()));
		ser2.getData().add(new XYChart.Data<String, Number>(tableList.get(i).getName(),tableList.get(i).getMatScore()));
		ser3.getData().add(new XYChart.Data<String, Number>(tableList.get(i).getName(),tableList.get(i).getEngScore()));
		}

		barchart.getData().addAll(ser1, ser2, ser3);

		// 6. Stage객체에 Scene객체 추가
		dialog.setScene(scene);
		dialog.setResizable(false);//크기고정
		dialog.show();
		//TableView에서 선택한 줄의 데이터를 가져온다.
		


		
	}




	@FXML public void canBtn() {
		
	    Stage stage = (Stage) canBtn.getScene().getWindow();
	    stage.close();
	}

}

