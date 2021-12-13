package pixelmonranking.command.ranksub;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.SignPlaceholder;

public class Knockout {
	
	public static final String name = "ko";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("shiny","legendary","ditto"));

	public static void returnTop10(EntityPlayerMP playerSender, String command,SignPlaceholder placeholder) {
		String reqTop;
		String reqPlayer;
		switch (command.toLowerCase()) {
		case name:
			reqTop = "SELECT Knockout.player, COUNT(DISTINCT Knockout.id) AS score FROM Knockout,Ignore "
					+ "WHERE Knockout.player!=Ignore.player "
					+ "GROUP by Knockout.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Knockout.player, COUNT( DISTINCT Knockout.id) AS score FROM Knockout "
					+ "WHERE Knockout.player=='%s';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de pokémon mis KO");
			return;
		case "shiny":
			reqTop = "SELECT Knockout.player, COUNT(DISTINCT Knockout.id) AS score FROM Knockout,Ignore "
					+ "WHERE Knockout.player!=Ignore.player "
					+ "AND Knockout.shiny "
					+ "GROUP by Knockout.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Knockout.player, COUNT( DISTINCT Knockout.id) AS score FROM Knockout "
					+ "WHERE Knockout.player=='%s' "
					+ "AND Knockout.shiny;";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Shiny mis KO");
			return;
		case "legendary":
			reqTop = "SELECT Knockout.player, COUNT(DISTINCT Knockout.id) AS score FROM Knockout,Ignore "
					+ "WHERE Knockout.player!=Ignore.player "
					+ "AND Knockout.leg "
					+ "GROUP by Knockout.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Knockout.player, COUNT( DISTINCT Knockout.id) AS score FROM Knockout "
					+ "WHERE Knockout.player=='%s' "
					+ "AND Knockout.leg;";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Légendaire mis KO");
			return;
		case "ditto":
			reqTop = "SELECT Knockout.player, COUNT(DISTINCT Knockout.id) AS score FROM Knockout,Ignore "
					+ "WHERE Knockout.player!=Ignore.player "
					+ "AND Knockout.pokemon=='Ditto' "
					+ "GROUP by Knockout.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Knockout.player, COUNT( DISTINCT Knockout.id) AS score FROM Knockout "
					+ "WHERE Knockout.player==''%s' "
					+ "AND Knockout.pokemon=='Ditto';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Métamorph mis KO");
			return;
		default:
			return;
		}
	}
}
