package pixelmonranking.model;

import java.util.ArrayList;

public class TopRank {
	
	private ArrayList<PlayerScore> players = new ArrayList<PlayerScore>();
	private String sender;
	
	public TopRank(String sender) {
		this.sender =sender;
	}
	
	public void add(PlayerScore playerscore) {
		players.add(playerscore);
	}
	
	public boolean hasPlayer() {
		for(PlayerScore playerscore : players) {
			if(playerscore.getPlayer().equals(this.sender)) return true;
		}
		return false;
		
	}
	
	public boolean isEmpty() {
		return players.isEmpty();
	}
	
	public int size() {
		return players.size();
	}
	
	public PlayerScore get(int i) {
		return players.get(i);
	}

}
