package kr.or.ddit.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class T03_ArrayListTest {
	public static void main(String[] args) {
		//문제) 5명의 사람 이름을 입력하여 ArrayList에 저장하고
		//	  이 중에 '김'씨 성의 이름을 출력하시오.
		//	 (단, 입력은 Scanner를 이용하여 입력 받는다)
		
		List<String> name = new ArrayList<String>();
		Scanner s = new Scanner(System.in);
		String input = "";
		
		for(int i = 1; i <= 5; i++) {
			System.out.println(i+"번째 이름을 입력하세요.");
			input = s.nextLine();
			name.add(input);
		}
		
		for(int i = 0; i<name.size();i++) {
			if(name.get(i).charAt(0) == '김') {
				System.out.println(name.get(i));
			}
			
		}
		
		

		
		
	}

}
