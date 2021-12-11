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

public class Oeuf {
	
	public static final String name = "oeuf";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("shiny","fullivs","ditto"));
	
	public static void returnTop10(EntityPlayerMP playerSender, String command) {
		final String playerName = playerSender.getName();
		switch (command.toLowerCase()) {
		case name:
			String request = "SELECT Hatch.player, COUNT(DISTINCT Hatch.id) AS score FROM Hatch,Ignore "
					+ "WHERE Hatch.player!=Ignore.player "
					+ "GROUP by Hatch.player "
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
				    	String playerrequest = "SELECT Hatch.player, COUNT( DISTINCT Hatch.id) AS score FROM Hatch "
								+ "WHERE Hatch.player=='"+playerName+"';";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de pokémon obtenu dans les oeufs");
			});
			sqlThread.start();
			return;
		case "shiny":
			String request1 = "SELECT Hatch.player, COUNT(DISTINCT Hatch.id) AS score FROM Hatch,Ignore "
					+ "WHERE Hatch.player!=Ignore.player "
					+ "AND Hatch.shiny "
					+ "GROUP by Hatch.player "
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
				    	String playerrequest = "SELECT Hatch.player, COUNT( DISTINCT Hatch.id) AS score FROM Hatch "
								+ "WHERE Hatch.player=='"+playerName+"' "
								+ "AND Hatch.shiny;";
				    	PixelmonRanking.log.info(playerrequest);
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Shiny obtenu dans les oeufs");
			});
			sqlThread1.start();
			return;
		case "fullivs":
			String request2 = "SELECT Hatch.player, COUNT(DISTINCT Hatch.id) AS score FROM Hatch,Ignore "
					+ "WHERE Hatch.player!=Ignore.player "
					+ "AND Hatch.isperfect "
					+ "GROUP by Hatch.player "
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
				    	String playerrequest = "SELECT Hatch.player, COUNT( DISTINCT Hatch.id) AS score FROM Hatch "
								+ "WHERE Hatch.player=='"+playerName+"' "
								+ "AND Hatch.isperfect;";
				    	PixelmonRanking.log.info(playerrequest);
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de 100 IVs obtenu dans les oeufs");
			});
			sqlThread2.start();
			return;
		case "ditto":
			String request3 = "SELECT Hatch.player, COUNT(DISTINCT Hatch.id) AS score FROM Hatch,Ignore "
					+ "WHERE Hatch.player!=Ignore.player "
					+ "AND Hatch.pokemon=='Ditto' "
					+ "GROUP by Hatch.player "
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
				    	String playerrequest = "SELECT Hatch.player, COUNT( DISTINCT Hatch.id) AS score FROM Hatch "
								+ "WHERE Hatch.player=='"+playerName+"' "
								+ "AND Hatch.pokemon=='Ditto';";
				    	PixelmonRanking.log.info(playerrequest);
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Métamorph obtenu dans les oeufs");
			});
			sqlThread3.start();
			return;
		default:
			return;
		}
	}
}
