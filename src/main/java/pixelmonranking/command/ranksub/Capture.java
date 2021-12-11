package pixelmonranking.command.ranksub;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.PixelmonRanking;
import pixelmonranking.database.DatabaseCraft;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.PlayerScore;
import pixelmonranking.model.TopRank;
import pixelmonranking.utils.ChatUtils;

public class Capture {
	
	public static final String name = "capture";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("shiny","legendary","ditto","dw"));
	
	public static void returnTop10(EntityPlayerMP playerSender, String command) {
		final String playerName = playerSender.getName();
		switch (command.toLowerCase()) {
		case name:
			String request = "SELECT Capture.player, COUNT(DISTINCT Capture.id) AS score FROM Capture,Ignore "
					+ "WHERE Capture.player!=Ignore.player "
					+ "GROUP by Capture.player "
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
				    	String playerrequest = "SELECT Capture.player, COUNT( DISTINCT Capture.id) AS score FROM Capture "
								+ "WHERE Capture.player=='"+playerName+"';";
				    	PixelmonRanking.log.info(playerrequest);
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de pokémon capturé");
			});
			sqlThread.start();
			return;
		case "shiny":
			String request1 = "SELECT Capture.player, COUNT(DISTINCT Capture.id) AS score FROM Capture,Ignore "
					+ "WHERE Capture.player!=Ignore.player "
					+ "AND Capture.shiny "
					+ "GROUP by Capture.player "
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
				    	String playerrequest = "SELECT Capture.player, COUNT( DISTINCT Capture.id) AS score FROM Capture "
								+ "WHERE Capture.player=='"+playerName+"' "
								+ "AND Capture.shiny;";
				    	PixelmonRanking.log.info(playerrequest);
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Shiny capturé");
			});
			sqlThread1.start();
			return;
		case "legendary":
			String request2 = "SELECT Capture.player, COUNT(DISTINCT Capture.id) AS score FROM Capture,Ignore "
					+ "WHERE Capture.player!=Ignore.player "
					+ "AND Capture.leg "
					+ "GROUP by Capture.player "
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
				    	String playerrequest = "SELECT Capture.player, COUNT( DISTINCT Capture.id) AS score FROM Capture "
								+ "WHERE Capture.player=='"+playerName+"' "
								+ "AND Capture.leg;";
				    	PixelmonRanking.log.info(playerrequest);
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Légendaire capturé");
			});
			sqlThread2.start();	
			return;
		case "ditto":
			String request3 = "SELECT Capture.player, COUNT(DISTINCT Capture.id) AS score FROM Capture,Ignore "
					+ "WHERE Capture.player!=Ignore.player "
					+ "AND Capture.pokemon=='Ditto' "
					+ "GROUP by Capture.player "
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
				    	String playerrequest = "SELECT Capture.player, COUNT( DISTINCT Capture.id) AS score FROM Capture "
								+ "WHERE Capture.player=='"+playerName+"' "
								+ "AND Capture.pokemon=='Ditto';";
				    	PixelmonRanking.log.info(playerrequest);
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Métamorph capturé");
			});
			sqlThread3.start();
			return;
		case "dw":
			String request4 = "SELECT Capture.player, COUNT(DISTINCT Capture.id) AS score FROM Capture,Ignore "
					+ "WHERE Capture.player!=Ignore.player "
					+ "AND Capture.DW "
					+ "GROUP by Capture.player "
					+ "ORDER BY score DESC LIMIT(10);";
			PixelmonRanking.log.info(request4);
			Thread sqlThread4 = new Thread(() -> {
			    ResultSet result = DatabaseHandler.queryWithResult(request4);
			    TopRank ranks = new TopRank(playerName);
			    try {
			    	PixelmonRanking.log.info("Top 10 : start");
					while(result.next()) {
						PixelmonRanking.log.info("Top 10 : " + result.getString("Player") + " " +result.getInt("score"));
						ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
					}
					if(!ranks.hasPlayer()) {
				    	String playerrequest = "SELECT Capture.player, COUNT( DISTINCT Capture.id) AS score FROM Capture "
								+ "WHERE Capture.player=='"+playerName+"' "
								+ "AND Capture.DW;";
				    	PixelmonRanking.log.info(playerrequest);
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de pokémon Talent Caché capturé");
			});
			sqlThread4.start();
			return;
		default:
			break;
		}
	}

}
