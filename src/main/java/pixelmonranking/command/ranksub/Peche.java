package pixelmonranking.command.ranksub;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.SignPlaceholder;


public class Peche {
	
	public static final String name = "peche";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("pokemon","objets"));
	
	
	
	public static void returnTop10(EntityPlayerMP playerSender, String command,SignPlaceholder placeholder) {
		String reqTop;
		String reqPlayer;
		switch (command.toLowerCase()) {
		case name:
			reqTop = "SELECT Peche.player, COUNT(DISTINCT Peche.id) AS score FROM Peche,Ignore "
					+ "WHERE Peche.player!=Ignore.player "
					+ "GROUP by Peche.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Peche.player, COUNT( DISTINCT Peche.id) AS score FROM Peche "
					+ "WHERE Peche.player=='%s';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de pêche effectué");
			return;
		case "pokemon":
			reqTop = "SELECT Peche.player, COUNT(DISTINCT Peche.id) AS score FROM Peche,Ignore "
					+ "WHERE Peche.player!=Ignore.player "
					+ "AND Peche.pokemon "
					+ "GROUP by Peche.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Peche.player, COUNT( DISTINCT Peche.id) AS score FROM Peche "
					+ "WHERE Peche.player=='%s' "
					+ "AND Peche.pokemon;";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Pokemon pêché");
			return;
		case "objets":
			reqTop = "SELECT Peche.player, COUNT(DISTINCT Peche.id) AS score FROM Peche,Ignore "
					+ "WHERE Peche.player!=Ignore.player "
					+ "AND NOT Peche.pokemon "
					+ "GROUP by Peche.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Peche.player, COUNT( DISTINCT Peche.id) AS score FROM Peche "
					+ "WHERE Peche.player=='%s' "
					+ "AND NOT Peche.pokemon;";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus d'objets pêché");
			return;
		default:
			break;
		}
	}
}
