package pixelmonranking.model;

import java.util.ArrayList;

public class SignPlaceholder {
	
	private String rank = "capture";
	private String subrank = "";
	private String title ="&6Le plus de pokémon capturé";	
	private int worldID =0 ;
	private ArrayList<PlaceholderLocation> signs = new ArrayList<PlaceholderLocation>();
	private ArrayList<PlaceholderLocation> heads = new ArrayList<PlaceholderLocation>();
		
	public SignPlaceholder() {
		signs.add(new PlaceholderLocation(0, 0, 0));
		signs.add(new PlaceholderLocation(0, 0, 0));
		signs.add(new PlaceholderLocation(0, 0, 0));
		
		heads.add(new PlaceholderLocation(0, 0, 0));
		heads.add(new PlaceholderLocation(0, 0, 0));
		heads.add(new PlaceholderLocation(0, 0, 0));	
	}

	public ArrayList<PlaceholderLocation> getSigns() {
		return signs;
	}

	public ArrayList<PlaceholderLocation> getHeads() {
		return heads;
	}

	public String getRank() {
		return rank;
	}

	public String getSubrank() {
		return subrank;
	}

	public String getTitle() {
		return title;
	}

	public int getWorldID() {
		return worldID;
	}
	
}
