package kr.or.ddit.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class T04_ArrayListTest {
	public static void main(String[] args) {
		
			//문제1) 5명의 별명을 입력하여 ArrayList에 저장하고
	//		별명의 길이가 제일긴 별명을 출력하시오.
	//		(단, 각 별명의 길이는 모두 다르게 입력한다.)
	
	List<String> name = new ArrayList<String>();
	Scanner s = new Scanner(System.in);
	String input = "";
	
	for(int i = 1; i <= 5; i++) {
		System.out.println(i+"번째 별명을 입력하세요.");
		input = s.nextLine();
		name.add(input);
	}
	
	String tmp = name.get(0);
	
	for(int i = 1; i < name.size();i++) {
		if(tmp.length() < name.get(i).length()){
			tmp = name.get(i);
		}
		
	}
	System.out.println(tmp);

	
	
	//문제2) 문제1에서 별명의 길이가 같은 것을 여러개 입력했을 때도 처리되도록 하시오.
	List<String> nametmp = new ArrayList<String>();

	for(int i = 0; i < name.size();i++) {
		if(tmp.length() == name.get(i).length()){
			nametmp.add(name.get(i));
		}
	}
	System.out.println(nametmp);



	}

}
