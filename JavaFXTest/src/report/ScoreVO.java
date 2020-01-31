package report;

public class ScoreVO {
	private String name;
	private int korScore;
	private int MatScore;
	private int engScore;

	public ScoreVO(String name, int korScore, int matScore, int engScore) {
		super();
		this.name = name;
		this.korScore = korScore;
		MatScore = matScore;
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
		return MatScore;
	}
	public void setMatScore(int matScore) {
		MatScore = matScore;
	}
	public int getEngScore() {
		return engScore;
	}
	public void setEngScore(int engScore) {
		this.engScore = engScore;
	}
	
	


}
