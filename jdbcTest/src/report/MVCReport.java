package report;

public class MVCReport {
	
	/*
	   MVC란 Model, View, Controller의 약자.
	   	장점 : 각각 맡은 바에만 집중할수 있게된다. 유지보수성, 확장성, 유연성이 증가하고 중복 코딩이라는 문제점도 사라지게된다.
	   
	   1. Model, 모델
	   	- 애플리케이션의 정보, 데이터를 나타낸다. 데이터베이스, 처음의 정의하는 상수, 초기화값, 변수 등을 뜻한다.
	   	- 또한 이러한 Data, 정보들의 가공을 책임지는 컴포넌트, 서비스를 말한다.
	   	
	   	- 사용자가 편집하길 원하는 모든 데이터를 가지고 있어야하낟.
	   	- View나 Controller에 대해서 어떤 정보도 알지 말아야 한다.
	   	- 변경이 일어나면 변경 통지에 대한 처리 방법을 구현해야만 한다.
	   	
	   	
	   2. View, 뷰
	   	- input텍스트, table,체크박스 등과 같은 사용자 인터페이스 요소를 나타낸다. 
	   	- 다시말해 데이터 및 객체의 입력, 그리고 보여주는 출력을 담당 . 데이터를 기반으로 사용자들이 볼수 있는 화면이다.
	   	
	   	- 모델이 가지고 있는 정보를 따로 저장해서는 안된다.
	   	  화면에 글자를 표시하기 위해 모델이 가지고 있는 정보를 전달받게 될 텐데, 그 정보를 유지하기 위해서 임의의 뷰 내부에 저장하면 안된다.
	   	- 모델이나 컨트롤러와 같이 다른 구성요소들은 몰라야 된다.
	   		그냥 뷰는 데이터를 받으면 화면에 표시해주는 역할만 가진다고 보면 된다.
	   	- 변경이 일어나면 변경 통지에 대한 처리 방법을 구현해야만 한다.
	   	- 재사용 가능하게 끔 설계를 해야 하며 다른 정보들을 표현 할때 쉽게 설계를 해야한다.
	   	
	   3. Controller, 컨트롤러
	   	- 데이터와 사용자 인터페이스 요소들을 잇는 다리역할을 한다. 즉 사용자가 데이터를 클릭하고 수정하는 것에 대한 '이벤트'들을 처리하는 부분을 뜻한다.
	   	
	   	- 모델이나 뷰에 대해서 알고 있어야한다.
	   	 모델이나 뷰는 서로의 존재를 모르고 변경을 외부로 알리고 수신하는 방법만 가지고 있는데 이를 컨트롤러가 중재하기 위해 모델과 그에 관련된 뷰에 대해서 알고 있어야 한다.
	   	- 모델이나 뷰의 변경을 모니터링 해야한다.
	   		모델이나 뷰의 변경 통지를 받으면 이를 해석해서 각각의 구성 요소에게 통지를 받으면 이를 해석해서 각각의 구성 요소에게 통지를 해야한다.
	   		
	   
	   	
	 */

}
