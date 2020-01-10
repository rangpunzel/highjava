package report;

import java.math.BigDecimal;

public class EnumPlanet {

	public enum Planet {
		수성(2439), 금성(6052), 지구(6371), 화성(3390), 목성(69911), 토성(58232), 천왕성(25362), 해왕성(24622);

		// 괄호속의 값이 저장될 변수 선언
		private long r;

		// 생성자 만들기(열거형의 생성자는 제어자가 묵시적으로 'private'이다.)
		private Planet(long data) {
			r = data;
		}

		// 값을 반환하는 메서드 작성
		public double getR() {
			return r;
		}
	}
	
	public static void main(String[] args) {
		Planet enumPlanet;
		
		Planet[] enumArr = Planet.values();
		
		
		for(int i =0; i<enumArr.length;i++) {
			BigDecimal bd = new BigDecimal(enumArr[i].getR()*enumArr[i].getR()*3.14*4);
			System.out.println(enumArr[i].name() + " : "
					 		  + bd.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		
/*		for(int i =0; i<enumArr.length;i++) {
			System.out.println(enumArr[i].name() + " : "
					 		  + enumArr[i].getR());
		}*/

		System.out.println();
	}
	
}
