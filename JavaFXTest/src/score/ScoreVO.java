package score;

public class ScoreVO {
	private String name;
	private int korScore;
	private int matScore;
	private int engScore;
	
	public ScoreVO() {
		
	}
	
	
	
	public ScoreVO(String name, int korScore, int matScore, int engScore) {
		super();
		this.name = name;
		this.korScore = korScore;
		this.matScore = matScore;
		this.engScore = engScore;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKorScore() {
		return korScore;
	}
	public void setKorScore(int korScore) {
		this.korScore = korScore;
	}
	public int getMatScore() {
		return matScore;
	}
	public void setMatScore(int matScore) {
		this.matScore = matScore;
	}
	public int getEngScore() {
		return engScore;
	}
	public void setEngScore(int engScore) {
		this.engScore = engScore;
	}
	
	

}
