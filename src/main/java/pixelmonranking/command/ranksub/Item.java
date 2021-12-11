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

public class Item {
	
	public static final String name = "item";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("bonbon", "chainerouge","fluteazure","masterball"));
	
	public static void returnTop10(EntityPlayerMP playerSender, String command) {
		final String playerName = playerSender.getName();
		switch (command.toLowerCase()) {
		case "bonbon":
			String request = "SELECT ItemUse.player, COUNT(DISTINCT ItemUse.id) AS score FROM ItemUse,Ignore "
					+ "WHERE ItemUse.player!=Ignore.player "
					+ "AND ItemUse.item=='Rare Candy' "
					+ "GROUP by ItemUse.player "
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
				    	String playerrequest = "SELECT ItemUse.player, COUNT( DISTINCT ItemUse.id) AS score FROM ItemUse "
								+ "WHERE ItemUse.player=='"+playerName+"' "
								+ "AND ItemUse.item=='Rare Candy' ;";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Super Bonbon utilisé");
			});
			sqlThread.start();
			return;
		case "chainerouge":
			String request1 = "SELECT ItemUse.player, COUNT(DISTINCT ItemUse.id) AS score FROM ItemUse,Ignore "
					+ "WHERE ItemUse.player!=Ignore.player "
					+ "AND ItemUse.item=='Red Chain' "
					+ "GROUP by ItemUse.player "
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
				    	String playerrequest = "SELECT ItemUse.player, COUNT( DISTINCT ItemUse.id) AS score FROM ItemUse "
								+ "WHERE ItemUse.player=='"+playerName+"' "
								+ "AND ItemUse.item=='Red Chain' ;";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Chaîne Rouge utilisé");
			});
			sqlThread1.start();
			return;
		case "fluteazure":
			String request2 = "SELECT ItemUse.player, COUNT(DISTINCT ItemUse.id) AS score FROM ItemUse,Ignore "
					+ "WHERE ItemUse.player!=Ignore.player "
					+ "AND ItemUse.item=='Azure Flute' "
					+ "GROUP by ItemUse.player "
					+ "ORDER BY score DESC LIMIT(10);";
			PixelmonRanking.log.info(request2);
			Thread sqlThread2 = new Thread(() -> {
			    ResultSet result = DatabaseHandler.queryWithResult(request2);
			    TopRank ranks = new TopRank(playerName);
			    try {
					while(result.next()) {
						ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
					}
					if(!ranks.hasPlayer()) {
				    	String playerrequest = "SELECT ItemUse.player, COUNT( DISTINCT ItemUse.id) AS score FROM ItemUse "
								+ "WHERE ItemUse.player=='"+playerName+"' "
								+ "AND ItemUse.item=='Azure Flute' ;";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Flute Azure utilisé");
			});
			sqlThread2.start();
			return;
		case "masterball":
			String request3 = "SELECT Capture.player, COUNT(DISTINCT Capture.id) AS score FROM Capture,Ignore "
					+ "WHERE Capture.player!=Ignore.player "
					+ "AND Capture.ball=='Master Ball' "
					+ "GROUP by Capture.player "
					+ "ORDER BY score DESC LIMIT(10);";
			PixelmonRanking.log.info(request3);
			Thread sqlThread3 = new Thread(() -> {
			    ResultSet result = DatabaseHandler.queryWithResult(request3);
			    TopRank ranks = new TopRank(playerName);
			    try {
					while(result.next()) {
						ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
					}
					if(!ranks.hasPlayer()) {
				    	String playerrequest = "SELECT Capture.player, COUNT( DISTINCT Capture.id) AS score FROM Capture "
								+ "WHERE Capture.player=='"+playerName+"' "
								+ "AND Capture.ball=='Master Ball';";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Master Ball utilisé");
			});
			sqlThread3.start();
			return;
		default:
			return;
		}
	}

}
