package pixelmonranking.command.ranksub;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.SignPlaceholder;

public class Oeuf {
	
	public static final String name = "oeuf";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("shiny","fullivs","ditto"));
	
	public static void returnTop10(EntityPlayerMP playerSender, String command,SignPlaceholder placeholder) {
		String reqTop;
		String reqPlayer;
		switch (command.toLowerCase()) {
		case name:
			reqTop = "SELECT Hatch.player, COUNT(DISTINCT Hatch.id) AS score FROM Hatch,Ignore "
					+ "WHERE Hatch.player!=Ignore.player "
					+ "GROUP by Hatch.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Hatch.player, COUNT( DISTINCT Hatch.id) AS score FROM Hatch "
					+ "WHERE Hatch.player=='%s';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de pokémon obtenu dans les oeufs");
			return;
		case "shiny":
			reqTop = "SELECT Hatch.player, COUNT(DISTINCT Hatch.id) AS score FROM Hatch,Ignore "
					+ "WHERE Hatch.player!=Ignore.player "
					+ "AND Hatch.shiny "
					+ "GROUP by Hatch.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Hatch.player, COUNT( DISTINCT Hatch.id) AS score FROM Hatch "
					+ "WHERE Hatch.player=='%s' "
					+ "AND Hatch.shiny;";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Shiny obtenu dans les oeufs");
			return;
		case "fullivs":
			reqTop = "SELECT Hatch.player, COUNT(DISTINCT Hatch.id) AS score FROM Hatch,Ignore "
					+ "WHERE Hatch.player!=Ignore.player "
					+ "AND Hatch.isperfect "
					+ "GROUP by Hatch.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Hatch.player, COUNT( DISTINCT Hatch.id) AS score FROM Hatch "
					+ "WHERE Hatch.player=='%s' "
					+ "AND Hatch.isperfect;";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de 100 IVs obtenu dans les oeufs");
			return;
		case "ditto":
			reqTop = "SELECT Hatch.player, COUNT(DISTINCT Hatch.id) AS score FROM Hatch,Ignore "
					+ "WHERE Hatch.player!=Ignore.player "
					+ "AND Hatch.pokemon=='Ditto' "
					+ "GROUP by Hatch.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Hatch.player, COUNT( DISTINCT Hatch.id) AS score FROM Hatch "
					+ "WHERE Hatch.player=='%s' "
					+ "AND Hatch.pokemon=='Ditto';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Métamorph obtenu dans les oeufs");
			return;
		default:
			return;
		}
	}
}
