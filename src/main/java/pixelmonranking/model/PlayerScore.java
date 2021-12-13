package pixelmonranking.model;

public class PlayerScore {

	private String player;
	private int score;
	
	public PlayerScore(String player,int score) {
		this.player = player;
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public String getPlayer() {
		return player;
	}
	
}
