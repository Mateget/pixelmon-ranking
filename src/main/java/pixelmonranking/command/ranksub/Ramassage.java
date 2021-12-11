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

public class Ramassage {
	
	public static final String name = "ramassage";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList());
	
	public static void returnTop10(EntityPlayerMP playerSender, String command) {
		final String playerName = playerSender.getName();
		switch (command.toLowerCase()) {
		case name:
			String request = "SELECT Pickup.player, COUNT(DISTINCT Pickup.id) AS score FROM Pickup,Ignore "
					+ "WHERE Pickup.player!=Ignore.player "
					+ "GROUP by Pickup.player "
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
				    	String playerrequest = "SELECT Pickup.player, COUNT( DISTINCT Pickup.id) AS score FROM Pickup "
								+ "WHERE Pickup.player=='"+playerName+"';";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Ramassage effectu�");
			});
			sqlThread.start();
			return;
		default:
			return;
		}
	}

}
