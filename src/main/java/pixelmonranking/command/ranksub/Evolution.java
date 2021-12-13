package pixelmonranking.command.ranksub;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.SignPlaceholder;

public class Evolution {
	
	public static final String name = "evolution";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("shiny"));
	
	public static void returnTop10(EntityPlayerMP playerSender, String command,SignPlaceholder placeholder) {
		String reqTop;
		String reqPlayer;
		switch (command.toLowerCase()) {
		case name:
			reqTop = "SELECT Evolve.player, COUNT(DISTINCT Evolve.id) AS score FROM Evolve,Ignore "
					+ "WHERE Evolve.player!=Ignore.player "
					+ "GROUP by Evolve.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Evolve.player, COUNT( DISTINCT Evolve.id) AS score FROM Evolve "
					+ "WHERE Evolve.player=='%s';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de pokémon évolué");
			return;
		case "shiny":
			reqTop = "SELECT Evolve.player, COUNT(DISTINCT Evolve.id) AS score FROM Evolve,Ignore "
					+ "WHERE Evolve.player!=Ignore.player "
					+ "AND Evolve.shiny "
					+ "GROUP by Evolve.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Evolve.player, COUNT( DISTINCT Evolve.id) AS score FROM Evolve "
					+ "WHERE Evolve.player=='%s' "
					+ "AND Evolve.shiny;";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de shiny évolué");
			return;
		default:
			return;
		}
	}

}
