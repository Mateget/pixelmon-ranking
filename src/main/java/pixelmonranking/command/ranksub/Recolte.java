package pixelmonranking.command.ranksub;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.PixelmonRanking;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.PlayerScore;
import pixelmonranking.model.TopRank;
import pixelmonranking.utils.ChatUtils;

public class Recolte {
	public static final String name = "recolte";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("baies", "noigrumes"));
	
	public static void returnTop10(EntityPlayerMP playerSender, String command) {
		final String playerName = playerSender.getName();
		switch (command.toLowerCase()) {
		case "baies":
			String request = "SELECT HarvestBerry.player, COUNT(DISTINCT HarvestBerry.id) AS score FROM HarvestBerry,Ignore "
					+ "WHERE HarvestBerry.player!=Ignore.player "
					+ "GROUP by HarvestBerry.player "
					+ "ORDER BY score DESC LIMIT(10);";
			PixelmonRanking.log.info(request);
			Thread sqlThread = new Thread(() -> {
			    ResultSet result = DatabaseHandler.queryWithResult(request);
			    TopRank ranks = new TopRank(playerName);
			    try {
					while(result.next()) {
						ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
					}
					if(!ranks.hasPlayer()) {
				    	String playerrequest = "SELECT HarvestBerry.player, COUNT( DISTINCT HarvestBerry.id) AS score FROM HarvestBerry "
								+ "WHERE HarvestBerry.player=='"+playerName+"';";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de récoltes de Baies");
			});
			sqlThread.start();
			return;
		case "noigrumes":
			String request1 = "SELECT HarvestApricorn.player, COUNT(DISTINCT HarvestApricorn.id) AS score FROM HarvestApricorn,Ignore "
					+ "WHERE HarvestApricorn.player!=Ignore.player "
					+ "GROUP by HarvestApricorn.player "
					+ "ORDER BY score DESC LIMIT(10);";
			PixelmonRanking.log.info(request1);
			Thread sqlThread1 = new Thread(() -> {
			    ResultSet result = DatabaseHandler.queryWithResult(request1);
			    TopRank ranks = new TopRank(playerName);
			    try {
					while(result.next()) {
						ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
					}
					if(!ranks.hasPlayer()) {
				    	String playerrequest = "SELECT HarvestApricorn.player, COUNT( DISTINCT HarvestApricorn.id) AS score FROM HarvestApricorn "
								+ "WHERE HarvestApricorn.player=='"+playerName+"';";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de récoltes de Noigrumes");
			});
			sqlThread1.start();
			return;
		default:
			return;
		}
	}
}
