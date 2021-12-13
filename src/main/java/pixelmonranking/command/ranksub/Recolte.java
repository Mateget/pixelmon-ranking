package pixelmonranking.command.ranksub;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.SignPlaceholder;

public class Recolte {
	public static final String name = "recolte";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("baies", "noigrumes"));
	
	public static void returnTop10(EntityPlayerMP playerSender, String command,SignPlaceholder placeholder) {
		String reqTop;
		String reqPlayer;
		switch (command.toLowerCase()) {
		case "baies":
			reqTop = "SELECT HarvestBerry.player, COUNT(DISTINCT HarvestBerry.id) AS score FROM HarvestBerry,Ignore "
					+ "WHERE HarvestBerry.player!=Ignore.player "
					+ "GROUP by HarvestBerry.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT HarvestBerry.player, COUNT( DISTINCT HarvestBerry.id) AS score FROM HarvestBerry "
					+ "WHERE HarvestBerry.player=='%s';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de récoltes de Baies");
			return;
		case "noigrumes":
			reqTop = "SELECT HarvestApricorn.player, COUNT(DISTINCT HarvestApricorn.id) AS score FROM HarvestApricorn,Ignore "
					+ "WHERE HarvestApricorn.player!=Ignore.player "
					+ "GROUP by HarvestApricorn.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT HarvestApricorn.player, COUNT( DISTINCT HarvestApricorn.id) AS score FROM HarvestApricorn "
					+ "WHERE HarvestApricorn.player=='%s';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de récoltes de Noigrumes");
			return;
		default:
			return;
		}
	}
}
