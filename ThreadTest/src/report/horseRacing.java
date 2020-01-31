package report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class horseRacing {
	static int rank = 0;
	
	public static void main(String[] args) {
		
		List<Horse> horse = new ArrayList<>();
		
		horse.add(new Horse("선라이팅"));
		horse.add(new Horse("대일신화"));
		horse.add(new Horse("스트롱캣"));
		horse.add(new Horse("울트라펀치"));
		horse.add(new Horse("강토마"));
		horse.add(new Horse("문학번개"));
		horse.add(new Horse("그레이돌"));
		horse.add(new Horse("비욘드오버"));
		horse.add(new Horse("라온스톰"));
		horse.add(new Horse("청운시대"));
		

		for(int i=0;i<horse.size();i++) {
			horse.get(i).start();
		}

		for(Horse dc : horse){
			try {
				dc.join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}

		
		System.out.println("경기 끝...");
		System.out.println("---------------------------------");
		System.out.println();
		System.out.println("경기 결과");
		
		Collections.sort(horse);
		
		for(int i =0;i<horse.size();i++) {
			System.out.println(horse.get(i));
		}
		
		
	}

}


class Horse extends Thread implements Comparable<Horse>{
	
	private String name;
	private int rank;
	
	public Horse(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "이름 : " + name +" 순위 : " + rank;
	}

	@Override
	public void run() {
		

		for(int i =0;i<50;i++) {
			
			try {
				// sleep()메서드의 값을 200~500사이의 난수로 한다.
				Thread.sleep((int)(Math.random()*301+100));
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.print(name + "\t");
			
			for(int j =0;j < 50; j++) {
				if(i==j) {
					System.out.print(">");
				}else
					System.out.print("-");
			}
			System.out.println();
			
		}
		
		
		horseRacing.rank += 1;
		this.setRank(horseRacing.rank);

	}//run종료

	@Override
	public int compareTo(Horse horse) {
		return Integer.compare(this.rank, horse.rank);
	}

}
