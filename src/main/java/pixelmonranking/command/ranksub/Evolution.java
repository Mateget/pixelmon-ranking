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

public class Evolution {
	
	public static final String name = "evolution";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("shiny"));
	
	public static void returnTop10(EntityPlayerMP playerSender, String command) {
		final String playerName = playerSender.getName();
		switch (command.toLowerCase()) {
		case name:
			String request = "SELECT Evolve.player, COUNT(DISTINCT Evolve.id) AS score FROM Evolve,Ignore "
					+ "WHERE Evolve.player!=Ignore.player "
					+ "GROUP by Evolve.player "
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
				    	String playerrequest = "SELECT Evolve.player, COUNT( DISTINCT Evolve.id) AS score FROM Evolve "
								+ "WHERE Evolve.player=='"+playerName+"';";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de pokémon évolué");
			});
			sqlThread.start();
			return;
		case "shiny":
			String request1 = "SELECT Evolve.player, COUNT(DISTINCT Evolve.id) AS score FROM Evolve,Ignore "
					+ "WHERE Evolve.player!=Ignore.player "
					+ "AND Evolve.shiny "
					+ "GROUP by Evolve.player "
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
				    	String playerrequest = "SELECT Evolve.player, COUNT( DISTINCT Evolve.id) AS score FROM Evolve "
								+ "WHERE Evolve.player=='"+playerName+"' "
								+ "AND Evolve.shiny;";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Shiny évolué");
			});
			sqlThread1.start();
			return;
		default:
			return;
		}
	}

}
