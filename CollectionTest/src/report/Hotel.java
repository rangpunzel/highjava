package report;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Hotel {
	private Map<String, HotelVO> hotelMap= new HashMap<>();
	Scanner s = new Scanner(System.in);
	
	public static void main(String[] args) {   //메인
		new Hotel().start();
	}
	
	private void displayMenu(){   //디스플레이메뉴
		System.out.println("**************************************");
		System.out.println("어떤 업무를 하시겠습니까?");
		System.out.println("1.체크인   2.체크아웃   3.객실상태   4.업무종료");
		System.out.println("**************************************");
		System.out.println("메뉴선택 => ");
	}
	

	private void start(){    //시작
		System.out.println("**************************************");
		System.out.println("호텔 문을 열었습니다.");
		System.out.println("**************************************");
		System.out.println();
		
		while(true){
		displayMenu();  // 메뉴 출력
		int menuNum = s.nextInt();   // 메뉴 번호 입력
		
		switch(menuNum){
		case 1 : checkIn();		// 체크인
			break;
		case 2 : checkOut();		// 체크아웃
			break;
		case 3 : roomList();		// 객실상태
			break;
		case 4 :
			System.out.println("**************************************");
			System.out.println("호텔 문을 닫았습니다.");
			System.out.println("**************************************");
			return;
		default :
			System.out.println("잘못 입력했습니다. 다시입력하세요.");
			} // switch문
		} // while문
	}
	
	private void roomList(){
		Set<String> keySet = hotelMap.keySet();

		if(keySet.size() == 0) {
			System.out.println("체크인한 사람이 없습니다.");
		}else {
			Iterator<String> it = keySet.iterator();
			while(it.hasNext()) {
				String name = it.next(); //키값을 얻는다.
				HotelVO hotelVO = hotelMap.get(name);
				
				System.out.println("방번호 : " + hotelVO.getRoomNum()+ ", 투숙객 : " + hotelVO.getName());
			}
		}

	}
	
	private void checkOut(){
		String roomNum;
		
		System.out.println("어느방을 체크아웃 하시겠습니까?");
		System.out.print("방번호 입력 => ");
		roomNum = s.next();
		
		if(hotelMap.remove(roomNum) == null) {
			System.out.println(roomNum + "방에는 체크인한 사람이 없습니다.");
			return; //메서드 종료
		}
		System.out.println("체크아웃 되었습니다.");
		
		
	}
	
	
	private void checkIn(){
		
		System.out.println("어느 방에 체크인 하시겠습니까?");
		System.out.print("방번호 입력 => ");
		String roomNum = s.next();
		s.nextLine(); 
		
		System.out.println("누구를 체크인 하시겠습니까?");
		System.out.print("이름 입력 => ");
		String name = s.next();
		
		
		if(hotelMap.get(roomNum) != null) {
			System.out.println(roomNum + "방에는 이미 사람이 있습니다.");
			return; //메서드 종료
		}
		
		hotelMap.put(roomNum, new HotelVO(roomNum, name));
		System.out.println("체크인 되었습니다.");
	}


}

class HotelVO{
	private String roomNum;
	private String name;
	
	public HotelVO(String roomNum, String name) {
		super();
		this.roomNum = roomNum;
		this.name = name;
	}
	
	
	public String getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}


