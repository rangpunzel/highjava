package prod;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ComboBox;

public class ProdController implements Initializable{

	@FXML TableView<ProdVO> tableView;
	@FXML TableColumn<ProdVO, String> idCol;
	@FXML TableColumn<ProdVO, String> nameCol;
	@FXML TableColumn<ProdVO, String> lguCol;
	@FXML TableColumn<ProdVO, String> buyerCol;
	@FXML TableColumn<ProdVO, Integer> costCol;
	@FXML TableColumn<ProdVO, Integer> priceCol;
	@FXML TableColumn<ProdVO, Integer> saleCol;
	@FXML TableColumn<ProdVO, String> outLineCol;
	@FXML TableColumn<ProdVO, String> detailCol;
	@FXML ComboBox<String> combo1;
	@FXML ComboBox<String> combo2;
	
	private ObservableList<ProdVO> tableData;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		//컬럼에 VO변수와 매칭되는 값셋팅해주기.
		idCol.setCellValueFactory(new PropertyValueFactory<>("prod_id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
		lguCol.setCellValueFactory(new PropertyValueFactory<>("prod_lgu"));
		buyerCol.setCellValueFactory(new PropertyValueFactory<>("prod_buyer"));
		costCol.setCellValueFactory(new PropertyValueFactory<>("prod_cost"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("prod_price"));
		saleCol.setCellValueFactory(new PropertyValueFactory<>("prod_sale"));
		outLineCol.setCellValueFactory(new PropertyValueFactory<>("prod_outline"));
		detailCol.setCellValueFactory(new PropertyValueFactory<>("bunji"));
		
		
		//--------------------------------------------------------------------comboBox1셋팅
		// DB연결에 필요한 객체변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null; // 쿼리문이 select일 경우에 필요함. 
		
		try {
			// JDBC드라이버 로딩(오라클 기준)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 해당DB에 접속(Connection객체 생성)
			String url = "jdbc:oracle:thin:@localhost:1521/xe";
			String userId = "pc04";  //오라클 접속아이디
			String password = "java";//오라클 접속아이디의 비밀번호
			
			// OracleDriver가 사용되는 부분
			conn = DriverManager.getConnection(url, userId, password);
			
			// Statement객체 생성 => Connection객체를 이용한다.
			stmt = conn.createStatement();
			
			// SQL문을 Statement객체를 이용하여 실행하고  ResultSet객체에 저장한다.
			String sql = "select * from lprod"; //실행할 SQL문

			// SQL문이 select일 경우에는 executeQuery()메서드 사용
			rs = stmt.executeQuery(sql); 
			
			
			List<String> combo1List = new ArrayList<>();
			// ResultSet객체에 저장되어 있는 자료를 반복문과 next()메서드를 이용하여 차례로 읽어와 처리한다.
			 while(rs.next()) {
				 combo1List.add(rs.getString("lprod_nm"));//VO 객체에 담아서 어레이리스트에 담음.
				 System.out.println(rs.getString("lprod_nm"));
			 }
			 ObservableList<String> list = 
						FXCollections.observableArrayList(combo1List);
			 
			combo1.setItems(list);
			combo1.setValue("대분류");
			combo2.setValue("중분류");
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
			//---------------------------------------------------------------------comboBox2셋팅
		
			combo1.valueProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, 
																 String oldValue, 
																 String newValue) {
					combo2.setDisable(false);
					// DB연결에 필요한 객체변수 선언
					Connection conn = null;
					Statement stmt = null;
					ResultSet rs = null; // 쿼리문이 select일 경우에 필요함. 
					
					try {
						// JDBC드라이버 로딩(오라클 기준)
						Class.forName("oracle.jdbc.driver.OracleDriver");
						
						// 해당DB에 접속(Connection객체 생성)
						String url = "jdbc:oracle:thin:@localhost:1521/xe";
						String userId = "pc04";  //오라클 접속아이디
						String password = "java";//오라클 접속아이디의 비밀번호
						
						// OracleDriver가 사용되는 부분
						conn = DriverManager.getConnection(url, userId, password);
						
						// Statement객체 생성 => Connection객체를 이용한다.
						stmt = conn.createStatement();

						// SQL문을 Statement객체를 이용하여 실행하고  ResultSet객체에 저장한다.
						String sql = "SELECT *\r\n" + 
								"FROM lPROD, prod\r\n" + 
								"where lprod.lprod_gu=prod.prod_lgu\r\n" + 
								"and lprod.lprod_nm = '" + newValue + "'"; //실행할 SQL문

						// SQL문이 select일 경우에는 executeQuery()메서드 사용
						rs = stmt.executeQuery(sql); 
						
						
						List<String> combo2List = new ArrayList<>();
						// ResultSet객체에 저장되어 있는 자료를 반복문과 next()메서드를 이용하여 차례로 읽어와 처리한다.
						 while(rs.next()) {
							 combo2List.add(rs.getString("prod_name"));//VO 객체에 담아서 어레이리스트에 담음.

						 }
						 ObservableList<String> list = 
									FXCollections.observableArrayList(combo2List);
						 
						combo2.setItems(list);

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
			});
			
			combo2.valueProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, 
																 String oldValue, 
																 String newValue) {
					// DB연결에 필요한 객체변수 선언
					Connection conn = null;
					Statement stmt = null;
					ResultSet rs = null; // 쿼리문이 select일 경우에 필요함. 
					
					try {
						// JDBC드라이버 로딩(오라클 기준)
						Class.forName("oracle.jdbc.driver.OracleDriver");
						
						// 해당DB에 접속(Connection객체 생성)
						String url = "jdbc:oracle:thin:@localhost:1521/xe";
						String userId = "pc04";  //오라클 접속아이디
						String password = "java";//오라클 접속아이디의 비밀번호
						
						// OracleDriver가 사용되는 부분
						conn = DriverManager.getConnection(url, userId, password);
						
						// Statement객체 생성 => Connection객체를 이용한다.
						stmt = conn.createStatement();

						// SQL문을 Statement객체를 이용하여 실행하고  ResultSet객체에 저장한다.
						String sql = "SELECT * FROM prod WHERE prod_name = '" + newValue + "'"; //실행할 SQL문

						// SQL문이 select일 경우에는 executeQuery()메서드 사용
						rs = stmt.executeQuery(sql); 

						
						
							List<ProdVO> tableList = new ArrayList<ProdVO>();
							// ResultSet객체에 저장되어 있는 자료를 반복문과 next()메서드를 이용하여 차례로 읽어와 처리한다.
							 while(rs.next()) {
								 ProdVO pvo = new ProdVO();
								 pvo.setProd_buyer(rs.getString("prod_buyer"));
								 pvo.setProd_cost(rs.getInt("prod_cost"));
								 pvo.setProd_detail(rs.getString("prod_detail"));
								 pvo.setProd_id(rs.getString("prod_id"));
								 pvo.setProd_lgu(rs.getString("prod_lgu"));
								 pvo.setProd_name(rs.getString("prod_name"));
								 pvo.setProd_outline(rs.getString("prod_outline"));
								 pvo.setProd_price(rs.getInt("prod_price"));
								 pvo.setProd_sale(rs.getInt("prod_sale"));

								 
								 tableList.add(pvo);//VO 객체에 담아서 어레이리스트에 담음.

								 
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
			});

		
	}

}
