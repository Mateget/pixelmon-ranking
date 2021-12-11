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

public class Knockout {
	
	public static final String name = "ko";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("shiny","legendary","ditto"));

	public static void returnTop10(EntityPlayerMP playerSender, String command) {
		final String playerName = playerSender.getName();
		switch (command.toLowerCase()) {
		case name:
			String request = "SELECT Knockout.player, COUNT(DISTINCT Knockout.id) AS score FROM Knockout,Ignore "
					+ "WHERE Knockout.player!=Ignore.player "
					+ "GROUP by Knockout.player "
					+ "ORDER BY score DESC LIMIT(10);";
			PixelmonRanking.log.info(request);
			Thread sqlThread = new Thread(() -> {
			    ResultSet result = DatabaseHandler.queryWithResult(request);
			    TopRank ranks = new TopRank(playerName);
			    try {
			    	PixelmonRanking.log.info("Top 10 : start");
					while(result.next()) {
						PixelmonRanking.log.info("Top 10 : " + result.getString("Player") + " " +result.getInt("score"));
						ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
					}
					if(!ranks.hasPlayer()) {
				    	String playerrequest = "SELECT Knockout.player, COUNT( DISTINCT Knockout.id) AS score FROM Knockout "
								+ "WHERE Knockout.player=='"+playerName+"';";
				    	PixelmonRanking.log.info(playerrequest);
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de pokémon mis KO");
			});
			sqlThread.start();
			return;
		case "shiny":
			String request1 = "SELECT Knockout.player, COUNT(DISTINCT Knockout.id) AS score FROM Knockout,Ignore "
					+ "WHERE Knockout.player!=Ignore.player "
					+ "AND Knockout.shiny "
					+ "GROUP by Knockout.player "
					+ "ORDER BY score DESC LIMIT(10);";
			PixelmonRanking.log.info(request1);
			Thread sqlThread1 = new Thread(() -> {
			    ResultSet result = DatabaseHandler.queryWithResult(request1);
			    TopRank ranks = new TopRank(playerName);
			    try {
			    	PixelmonRanking.log.info("Top 10 : start");
					while(result.next()) {
						PixelmonRanking.log.info("Top 10 : " + result.getString("Player") + " " +result.getInt("score"));
						ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
					}
					if(!ranks.hasPlayer()) {
				    	String playerrequest = "SELECT Knockout.player, COUNT( DISTINCT Knockout.id) AS score FROM Knockout "
								+ "WHERE Knockout.player=='"+playerName+"' "
								+ "AND Knockout.shiny;";
				    	PixelmonRanking.log.info(playerrequest);
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Shiny mis KO");
			});
			sqlThread1.start();
			return;
		case "legendary":
			String request2 = "SELECT Knockout.player, COUNT(DISTINCT Knockout.id) AS score FROM Knockout,Ignore "
					+ "WHERE Knockout.player!=Ignore.player "
					+ "AND Knockout.leg "
					+ "GROUP by Knockout.player "
					+ "ORDER BY score DESC LIMIT(10);";
			PixelmonRanking.log.info(request2);
			Thread sqlThread2 = new Thread(() -> {
			    ResultSet result = DatabaseHandler.queryWithResult(request2);
			    TopRank ranks = new TopRank(playerName);
			    try {
			    	PixelmonRanking.log.info("Top 10 : start");
					while(result.next()) {
						PixelmonRanking.log.info("Top 10 : " + result.getString("Player") + " " +result.getInt("score"));
						ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
					}
					if(!ranks.hasPlayer()) {
				    	String playerrequest = "SELECT Knockout.player, COUNT( DISTINCT Knockout.id) AS score FROM Knockout "
								+ "WHERE Knockout.player=='"+playerName+"' "
								+ "AND Knockout.leg;";
				    	PixelmonRanking.log.info(playerrequest);
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Légendaire mis KO");
			});
			sqlThread2.start();
			return;
		case "ditto":
			String request3 = "SELECT Knockout.player, COUNT(DISTINCT Knockout.id) AS score FROM Knockout,Ignore "
					+ "WHERE Knockout.player!=Ignore.player "
					+ "AND Knockout.pokemon=='Ditto' "
					+ "GROUP by Knockout.player "
					+ "ORDER BY score DESC LIMIT(10);";
			PixelmonRanking.log.info(request3);
			Thread sqlThread3 = new Thread(() -> {
			    ResultSet result = DatabaseHandler.queryWithResult(request3);
			    TopRank ranks = new TopRank(playerName);
			    try {
			    	PixelmonRanking.log.info("Top 10 : start");
					while(result.next()) {
						PixelmonRanking.log.info("Top 10 : " + result.getString("Player") + " " +result.getInt("score"));
						ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
					}
					if(!ranks.hasPlayer()) {
				    	String playerrequest = "SELECT Knockout.player, COUNT( DISTINCT Knockout.id) AS score FROM Knockout "
								+ "WHERE Knockout.player=='"+playerName+"' "
								+ "AND Knockout.pokemon=='Ditto';";
				    	PixelmonRanking.log.info(playerrequest);
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Métamorph mis KO");
			});
			sqlThread3.start();
			return;
		default:
			return;
		}
	}
}
