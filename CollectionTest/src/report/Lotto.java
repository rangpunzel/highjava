package report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Lotto {
	
	public static void main(String[] args) {
		start();
		

	}
	
	static void start(){
		Scanner s = new Scanner(System.in);

		int input = 0;
		
		do {
			System.out.println("=================");
			System.out.println("Lotto 프로그램");
			System.out.println("-----------------");
			System.out.println("1. Lotto 구입");
			System.out.println("2. 프로그램 종료");
			System.out.println("=================");
			System.out.print("메뉴 선택 : ");
			input = Integer.parseInt(s.nextLine());
			if (input == 1) {
				buy();
			}
		
		}while(input != 2);
		
		System.out.println("감사합니다.");
		
	}
	
	static void buy() {
		int input,buy,a,cnt = 0;
	
		Scanner s = new Scanner(System.in);
		System.out.println("Lotto 구입 시작");
		System.out.println("(1000원에 로또번호 하나입니다.)");
		System.out.print("금액 입력 : ");
		input = Integer.parseInt(s.nextLine());
		buy = input/1000;
		a = input%1000;
		
		for(int i =0;i <= buy-1;i++) {
			cnt++;
			System.out.print("로또번호 " + cnt + " : ");
			random();
		}
		System.out.println();
		System.out.println("받은 금액은 " + input + "원이고 거스름돈은 " + a + "원입니다.");
		start();
		
	}
	
	
	static void random() {
		Set<Integer> random = new HashSet<>();
		while(random.size() < 6) {  //Set의 데이터가 6개 될때까지 반복함.
			int num = (int)(Math.random()*45 + 1); //1~45사이의 난수
			random.add(num);
		}
		
		List<Integer> randomList = new ArrayList<>();
		
		for(Integer num : random) {
			randomList.add(num);
		}
		
		Collections.sort(randomList);
		
		for(int i =0;i<randomList.size();i++) {
			System.out.print(randomList.get(i));
			if(i != randomList.size()-1) {
				System.out.print(", ");
			}
		}
		System.out.println();

		
		
	}

}
