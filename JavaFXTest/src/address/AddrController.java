package address;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class AddrController implements Initializable{

	@FXML TableView<AddrVO> tableView;
	@FXML ComboBox comboBox;
	@FXML TextField txtField;
	@FXML Button btnSearch;	
	@FXML TableColumn<AddrVO, String> codeCol;
	@FXML TableColumn<AddrVO, String> dongCol;
	@FXML TableColumn<AddrVO, String> sidoCol;
	@FXML TableColumn<AddrVO, String> gugunCol;
	@FXML TableColumn<AddrVO, String> bunjiCol;
	private ObservableList<AddrVO> tableData;
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//컬럼에 VO변수와 매칭되는 값셋팅해주기.
		codeCol.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
		dongCol.setCellValueFactory(new PropertyValueFactory<>("dong"));
		sidoCol.setCellValueFactory(new PropertyValueFactory<>("sido"));
		gugunCol.setCellValueFactory(new PropertyValueFactory<>("gugun"));
		bunjiCol.setCellValueFactory(new PropertyValueFactory<>("bunji"));
		
		
		//콤보박스 세팅
		ObservableList<String> list = 
				FXCollections.observableArrayList("우편번호","동이름");
		comboBox.setItems(list);
		comboBox.setValue("동이름");

	}

	@FXML public void btnSearch() {
		
		// DB연결에 필요한 객체변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null; // 쿼리문이 select일 경우에 필요함. 
		
		try {
			// JDBC드라이버 로딩(오라클 기준)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 해당DB에 접속(Connection객체 생성)
			String url = "jdbc:oracle:thin:@localhost:1521/xe";
			String userId = "js_home";  //오라클 접속아이디
			String password = "java";//오라클 접속아이디의 비밀번호
			
			// OracleDriver가 사용되는 부분
			conn = DriverManager.getConnection(url, userId, password);
			
			// Statement객체 생성 => Connection객체를 이용한다.
			stmt = conn.createStatement();
			
			// SQL문을 Statement객체를 이용하여 실행하고  ResultSet객체에 저장한다.
			String sql = "";
			
			if(comboBox.getValue().equals("동이름")) {
			sql = "select * from ziptb where dong like '%" + txtField.getText()+"%'"; //실행할 SQL문
			}else if(comboBox.getValue().equals("우편번호")) {
			sql = "select * from ziptb where zipcode like '%" + txtField.getText()+"%'"; //실행할 SQL문
			}
			
			// SQL문이 select일 경우에는 executeQuery()메서드 사용
			rs = stmt.executeQuery(sql); 
			
			
			List<AddrVO> tableList = new ArrayList<AddrVO>();
			// ResultSet객체에 저장되어 있는 자료를 반복문과 next()메서드를 이용하여 차례로 읽어와 처리한다.
			 while(rs.next()) {
				 
				 AddrVO advo = new AddrVO();
				 advo.setZipcode(rs.getString("zipcode"));
				 advo.setDong(rs.getString("dong"));
				 advo.setSido(rs.getString("sido"));
				 advo.setGugun(rs.getString("gugun"));
				 advo.setBunji(rs.getString("bunji"));
				 
				 tableList.add(advo);//VO 객체에 담아서 어레이리스트에 담음.

				 
			 }
			 tableData = FXCollections.observableArrayList(tableList); //어레이리스트에 담은 데이터를 observableArrayList에 담음
			 
			 tableView.setItems(tableData); //observableArrayList에 담은 데이터를 테이블뷰에 세팅.
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원반납
			if(rs != null) try {rs.close();} catch(SQLException e2) {}
			if(stmt != null) try {stmt.close();} catch(SQLException e2) {}
			if(conn != null) try {conn.close();} catch(SQLException e2) {}
		}
	}

}
