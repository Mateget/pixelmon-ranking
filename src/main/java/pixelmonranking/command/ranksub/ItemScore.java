package pixelmonranking.command.ranksub;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.SignPlaceholder;

public class ItemScore {
	
	public static final String name = "item";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("bonbon", "chainerouge","fluteazure","masterball"));
	
	public static void returnTop10(EntityPlayerMP playerSender, String command,SignPlaceholder placeholder) {
		String reqTop;
		String reqPlayer;
		switch (command.toLowerCase()) {
		case "bonbon":
			reqTop = "SELECT ItemUse.player, COUNT(DISTINCT ItemUse.id) AS score FROM ItemUse,Ignore "
					+ "WHERE ItemUse.player!=Ignore.player "
					+ "AND ItemUse.item=='Rare Candy' "
					+ "GROUP by ItemUse.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT ItemUse.player, COUNT( DISTINCT ItemUse.id) AS score FROM ItemUse "
					+ "WHERE ItemUse.player=='%s' "
					+ "AND ItemUse.item=='Rare Candy' ;";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Super Bonbon utilisé");
			return;
		case "chainerouge":
			reqTop = "SELECT ItemUse.player, COUNT(DISTINCT ItemUse.id) AS score FROM ItemUse,Ignore "
					+ "WHERE ItemUse.player!=Ignore.player "
					+ "AND ItemUse.item=='Red Chain' "
					+ "GROUP by ItemUse.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT ItemUse.player, COUNT( DISTINCT ItemUse.id) AS score FROM ItemUse "
					+ "WHERE ItemUse.player=='%s' "
					+ "AND ItemUse.item=='Red Chain' ;";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Chaîne Rouge utilisé");
			return;
		case "fluteazure":
			reqTop = "SELECT ItemUse.player, COUNT(DISTINCT ItemUse.id) AS score FROM ItemUse,Ignore "
					+ "WHERE ItemUse.player!=Ignore.player "
					+ "AND ItemUse.item=='Azure Flute' "
					+ "GROUP by ItemUse.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT ItemUse.player, COUNT( DISTINCT ItemUse.id) AS score FROM ItemUse "
					+ "WHERE ItemUse.player=='%s' "
					+ "AND ItemUse.item=='Azure Flute' ;";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Flute Azure utilisé");
			return;
		case "masterball":
			reqTop = "SELECT Capture.player, COUNT(DISTINCT Capture.id) AS score FROM Capture,Ignore "
					+ "WHERE Capture.player!=Ignore.player "
					+ "AND Capture.ball=='Master Ball' "
					+ "GROUP by Capture.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT Capture.player, COUNT( DISTINCT Capture.id) AS score FROM Capture "
					+ "WHERE Capture.player=='%s' "
					+ "AND Capture.ball=='Master Ball';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Master Ball utilisé");
			return;
		default:
			return;
		}
	}

}
