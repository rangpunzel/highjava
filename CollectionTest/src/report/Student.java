package report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Student {

	public static void main(String[] args) {
		List<StudentList> studentList = new ArrayList<>();
		
		studentList.add(new StudentList("22013505","춘향이",50,85,95));
		studentList.add(new StudentList("20818008","김지선",100,95,95));
		studentList.add(new StudentList("20115485","몽룡이",85,95,100));
		studentList.add(new StudentList("21812004","길동이",85,95,100));
		
		for(int i = 0; i<studentList.size();i++) { //총점 계산
		studentList.get(i).setSum(studentList.get(i).getEng()
								 + studentList.get(i).getKor()
								 + studentList.get(i).getMath());
		}
		
		
		System.out.println("학번 정렬 출력");
		Collections.sort(studentList);//학번으로 정렬하기
		for(int i = 0; i<studentList.size();i++) {
			System.out.println(studentList.get(i));
		}
		
		
		System.out.println("총점 정렬");
		Collections.sort(studentList, new SumSort());
		for(int i = 0; i<studentList.size();i++) {
			studentList.get(i).setRank(i+1);
		}
		for(int i = 0; i<studentList.size();i++) {
			System.out.println(studentList.get(i));
		}
		
	}

}

class SumSort implements Comparator<StudentList>{

	@Override
	public int compare(StudentList stu1, StudentList stu2) {
		if(new Integer(stu1.getSum()).compareTo(stu2.getSum())==0) {
			return stu1.getNum().compareTo(stu2.getNum())*-1;
		}else {
			return new Integer(stu1.getSum()).compareTo(stu2.getSum())*-1;
		}
	}


	
}

class StudentList implements Comparable<StudentList>{
	private String num;
	private String name;
	private int kor;
	private int eng;
	private int math;
	private int sum;
	private int rank;
	
	

	public StudentList(String num, String name, int kor, int eng, int math) {
		super();
		this.num = num;
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
	}
	
	@Override
	public String toString() {
		return "student [학번=" + num + ", 이름=" + name + ", 국어=" + kor 
				+ ", 영어=" + eng + ", 수학=" + math + ", 총점=" + sum+ ", 등수=" + rank + "]";
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}


	@Override
	public int compareTo(StudentList stu) {
		return getNum().compareTo(stu.getNum());
	}
	
}