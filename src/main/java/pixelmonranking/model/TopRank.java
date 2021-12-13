package pixelmonranking.model;

import java.util.ArrayList;

import pixelmonranking.PixelmonRanking;

public class TopRank {
	
	private ArrayList<PlayerScore> players = new ArrayList<PlayerScore>();
	
	public TopRank() {
		
	}
	
	public void add(PlayerScore playerscore) {
		players.add(playerscore);
	}
	
	public boolean hasPlayer(String playerName) {
		for(PlayerScore playerscore : players) {
			if(playerscore.getPlayer().equals(playerName)) return true;
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
		if(i>=players.size()) return null;
		return players.get(i);
	}

}
