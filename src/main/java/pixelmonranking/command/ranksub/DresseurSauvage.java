package pixelmonranking.command.ranksub;


import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.SignPlaceholder;


public class DresseurSauvage {
	
	public static final String name = "dresseursauvage";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList());
	
	public static void returnTop10(EntityPlayerMP playerSender, String command,SignPlaceholder placeholder) {
		String reqTop;
		String reqPlayer;
		switch (command.toLowerCase()) {
		case name:
			reqTop = "SELECT BeatTrainer.player, COUNT(DISTINCT BeatTrainer.id) AS score FROM BeatTrainer,Ignore "
					+ "WHERE BeatTrainer.player!=Ignore.player "
					+ "GROUP by BeatTrainer.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT BeatTrainer.player, COUNT( DISTINCT BeatTrainer.id) AS score FROM BeatTrainer "
					+ "WHERE BeatTrainer.player=='%s';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Dresseur Sauvage battu");
			return;
		default:
			return;
		}
	}

}
