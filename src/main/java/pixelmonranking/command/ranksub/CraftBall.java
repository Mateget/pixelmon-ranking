package pixelmonranking.command.ranksub;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.PixelmonRanking;
import pixelmonranking.database.DatabaseCraft;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.PlayerScore;
import pixelmonranking.model.TopRank;
import pixelmonranking.utils.ChatUtils;

public class CraftBall {
	
	public static final String name = "craftball";
	public static final ArrayList<String> subcommand = new ArrayList<String>(Arrays.asList("hyper", "love"));
	public static final String ballsum = ballsum();
	
	private static String ballsum() {
		String balls = "";
		for(EnumPokeballs ball : EnumPokeballs.values()) {
			balls += String.format("%s+", ball.name().replace(" ", ""));
		}
		
		return balls.substring(0, balls.length() - 1);
	}
	
	public static void returnTop10(EntityPlayerMP playerSender, String command) {
		DatabaseCraft.updatePlayerCraftedBalls( playerSender);
		final String playerName = playerSender.getName();
		switch (command.toLowerCase()) {
		case name:
			String request = "SELECT CraftBall.player, ("+ballsum+") AS score FROM CraftBall,Ignore "
					+ "WHERE CraftBall.player!=Ignore.player "
					+ "GROUP by CraftBall.player "
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
				    	String playerrequest = "SELECT CraftBall.player, ( "+ballsum+") AS score FROM CraftBall "
								+ "WHERE CraftBall.player=='"+playerName+"';";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Ball crafté");
			});
			sqlThread.start();
			return;
		case "hyper":
			String request1 = "SELECT CraftBall.player, (UltraBall) AS score FROM CraftBall,Ignore "
					+ "WHERE CraftBall.player!=Ignore.player "
					+ "GROUP by CraftBall.player "
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
				    	String playerrequest = "SELECT CraftBall.player, (UltraBall) AS score FROM CraftBall "
								+ "WHERE CraftBall.player=='"+playerName+"';";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus d'Hyper Ball crafté");
			});
			sqlThread1.start();
			return;
		case "love":
			String request2 = "SELECT CraftBall.player, (LoveBall) AS score FROM CraftBall,Ignore "
					+ "WHERE CraftBall.player!=Ignore.player "
					+ "GROUP by CraftBall.player "
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
				    	String playerrequest = "SELECT CraftBall.player, (LoveBall) AS score FROM CraftBall "
								+ "WHERE CraftBall.player=='"+playerName+"';";
				    	result = DatabaseHandler.queryWithResult(playerrequest);
						while(result.next()) {
							if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
						}
				    }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			   ChatUtils.sendRank(playerSender, ranks, "Le plus de Love Ball crafté");
			});
			sqlThread2.start();
			return;
		default:
			return;
		}
	}
	

}
