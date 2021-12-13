package pixelmonranking.command.ranksub;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.SignPlaceholder;

public class Ramassage {
	
	public static final String name = "ramassage";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList());
	
	public static void returnTop10(EntityPlayerMP playerSender, String command,SignPlaceholder placeholder) {
		String reqTop;
		String reqPlayer;
		switch (command.toLowerCase()) {
		case name:
			reqTop = "SELECT Pickup.player, COUNT(DISTINCT Pickup.id) AS score FROM Pickup,Ignore "
					+ "WHERE Pickup.player!=Ignore.player "
					+ "GROUP by Pickup.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Pickup.player, COUNT( DISTINCT Pickup.id) AS score FROM Pickup "
					+ "WHERE Pickup.player=='%s';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Ramassage effectué");
			return;
		default:
			return;
		}
	}

}
