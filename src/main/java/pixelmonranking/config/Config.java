package pixelmonranking.config;

import java.util.ArrayList;

import pixelmonranking.model.SignPlaceholder;

public class Config {
	
	
	private boolean enabled;	
	private ArrayList<SignPlaceholder> placeholders =  new ArrayList<SignPlaceholder>();

    public Config() {
    	this.enabled = false;
    	this.placeholders.add(new SignPlaceholder());
    }

	public boolean isEnabled() {
		return enabled;
	}
	
	public ArrayList<SignPlaceholder> getPlaceholders(){
		return this.placeholders;
		
	}

}
