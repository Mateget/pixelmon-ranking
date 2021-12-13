package pixelmonranking.command.ranksub;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.SignPlaceholder;


public class Capture {
	
	public static final String name = "capture";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("shiny","legendary","ditto","dw"));
	
	
	
	public static void returnTop10(EntityPlayerMP playerSender, String command,SignPlaceholder placeholder) {
		String reqTop;
		String reqPlayer;
		switch (command.toLowerCase()) {
		case name:
			reqTop = "SELECT Capture.player, COUNT(DISTINCT Capture.id) AS score FROM Capture,Ignore "
					+ "WHERE Capture.player!=Ignore.player "
					+ "GROUP by Capture.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Capture.player, COUNT( DISTINCT Capture.id) AS score FROM Capture "
					+ "WHERE Capture.player=='%s';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de pokémon capturé");
			return;
		case "shiny":
			reqTop = "SELECT Capture.player, COUNT(DISTINCT Capture.id) AS score FROM Capture,Ignore "
					+ "WHERE Capture.player!=Ignore.player "
					+ "AND Capture.shiny "
					+ "GROUP by Capture.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Capture.player, COUNT( DISTINCT Capture.id) AS score FROM Capture "
					+ "WHERE Capture.player=='%s' "
					+ "AND Capture.shiny;";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Shiny capturé");
			return;
		case "legendary":
			reqTop = "SELECT Capture.player, COUNT(DISTINCT Capture.id) AS score FROM Capture,Ignore "
					+ "WHERE Capture.player!=Ignore.player "
					+ "AND Capture.leg "
					+ "GROUP by Capture.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Capture.player, COUNT( DISTINCT Capture.id) AS score FROM Capture "
					+ "WHERE Capture.player=='%s' "
					+ "AND Capture.leg;";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Légendaire capturé");
			return;
		case "ditto":
			reqTop = "SELECT Capture.player, COUNT(DISTINCT Capture.id) AS score FROM Capture,Ignore "
					+ "WHERE Capture.player!=Ignore.player "
					+ "AND Capture.pokemon=='Ditto' "
					+ "GROUP by Capture.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Capture.player, COUNT( DISTINCT Capture.id) AS score FROM Capture "
					+ "WHERE Capture.player=='%s' "
					+ "AND Capture.pokemon=='Ditto';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Métamorph capturé");
			return;
		case "dw":
			reqTop = "SELECT Capture.player, COUNT(DISTINCT Capture.id) AS score FROM Capture,Ignore "
					+ "WHERE Capture.player!=Ignore.player "
					+ "AND Capture.DW "
					+ "GROUP by Capture.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Capture.player, COUNT( DISTINCT Capture.id) AS score FROM Capture "
					+ "WHERE Capture.player=='%s' "
					+ "AND Capture.DW;";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de pokémon Talent Caché capturé");
		default:
			break;
		}
	}
}
