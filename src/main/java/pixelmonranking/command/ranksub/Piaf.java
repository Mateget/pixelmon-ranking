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

public class Piaf {
	
	public static final String name = "piaf";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList());
	
	public static void returnTop10(EntityPlayerMP playerSender, String command) {
		final String playerName = playerSender.getName();
		switch (command.toLowerCase()) {
		case name:
			String request = "SELECT Shrine.player, COUNT(DISTINCT Shrine.id) AS score FROM Shrine,Ignore "
					+ "WHERE Shrine.player!=Ignore.player "
					+ "GROUP by Shrine.player "
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
				    	String playerrequest = "SELECT Shrine.player, COUNT( DISTINCT Shrine.id) AS score FROM Shrine "
								+ "WHERE Shrine.player=='"+playerName+"';";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Piaf invoqué");
			});
			sqlThread.start();
			return;
		default:
			return;
		}
	}

}
