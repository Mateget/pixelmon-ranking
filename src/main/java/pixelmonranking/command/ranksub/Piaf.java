package pixelmonranking.command.ranksub;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.SignPlaceholder;

public class Piaf {
	
	public static final String name = "piaf";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList());
	
	public static void returnTop10(EntityPlayerMP playerSender, String command,SignPlaceholder placeholder) {
		String reqTop;
		String reqPlayer;
		switch (command.toLowerCase()) {
		case name:
			reqTop = "SELECT Shrine.player, COUNT(DISTINCT Shrine.id) AS score FROM Shrine,Ignore "
					+ "WHERE Shrine.player!=Ignore.player "
					+ "GROUP by Shrine.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Shrine.player, COUNT( DISTINCT Shrine.id) AS score FROM Shrine "
					+ "WHERE Shrine.player=='%s';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Piaf invoqué");
			return;
		default:
			return;
		}
	}

}
