package pixelmonranking.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import pixelmonranking.PixelmonRanking;
import pixelmonranking.model.PlayerScore;
import pixelmonranking.model.SignPlaceholder;
import pixelmonranking.model.TopRank;
import pixelmonranking.placeholder.SignHandler;
import pixelmonranking.utils.ChatUtils;

public class DatabaseHandler {

	private static Connection connection = null;
	private static Statement statement = null;

	public DatabaseHandler(String dBPath) {
		
	}

	public static void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + PixelmonRanking.dblocation);
			statement = connection.createStatement();
			PixelmonRanking.log.info("Database successfully connected");
		} catch (ClassNotFoundException notFoundException) {
			notFoundException.printStackTrace();
			PixelmonRanking.log.info("Database connection error");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			PixelmonRanking.log.info("Database connection error");
		}
	}

	public void close() {
		try {
			connection.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void init() {
		
		query("CREATE TABLE IF NOT EXISTS Peche"
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Player varchar(40),"
				+ "Contenu varchar(255),"
				+ "Pokemon boolean"
				+ ");");

		query("CREATE TABLE IF NOT EXISTS Capture"
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Player varchar(40),"
				+ "Pokemon varchar(255),"
				+ "Shiny boolean,"
				+ "DW boolean,"
				+ "Leg boolean,"
				+ "Ball varchar(40),"
				+ "Form int"
				+ ");");
		
		query("CREATE TABLE IF NOT EXISTS Knockout"
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Player varchar(40),"
				+ "Pokemon varchar(255),"
				+ "Shiny boolean,"
				+ "Leg boolean,"
				+ "Form int"
				+ ");");
		
		String balls = "";
		for(EnumPokeballs ball : EnumPokeballs.values()) {
			balls += String.format(",%s int", ball.name().replace(" ", ""));
		}
		query("CREATE TABLE IF NOT EXISTS CraftBall"
				+ "(Player varchar(40) PRIMARY KEY"
				+ balls
				+ ");");
				
		query("CREATE TABLE IF NOT EXISTS Hatch"
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Player varchar(40),"
				+ "Pokemon varchar(255),"
				+ "IsPerfect boolean,"
				+ "Shiny boolean,"
				+ "Form int"
				+ ");");
		
		query("CREATE TABLE IF NOT EXISTS Pickup"
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Player varchar(40),"
				+ "Item varchar(255)"
				+ ");");
		
		query("CREATE TABLE IF NOT EXISTS BeatTrainer"
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Player varchar(40)"
				+ ");");
		
		query("CREATE TABLE IF NOT EXISTS Evolve"
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Player varchar(40),"
				+ "Pokemon varchar(255),"
				+ "Shiny boolean"
				+ ");");
		
		query("CREATE TABLE IF NOT EXISTS Shrine"
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Player varchar(40)"
				+ ");");
		
		query("CREATE TABLE IF NOT EXISTS HarvestApricorn"
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Player varchar(40),"
				+ "Item varchar(255)"
				+ ");");
		
		query("CREATE TABLE IF NOT EXISTS HarvestBerry"
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Player varchar(40),"
				+ "Item varchar(255)"
				+ ");");
		
		query("CREATE TABLE IF NOT EXISTS ItemUse"
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Player varchar(40),"
				+ "Item varchar(255)"
				+ ");");
				
		query("CREATE TABLE IF NOT EXISTS Ignore"
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Player varchar(40)"
				+ ");");
		
	}
	
	public static void threadQuery(String request) {
		Thread sqlThread = new Thread(() -> {
		    query(request);
		});
		sqlThread.start();
	}
	
	public static void query(String requet) {
	       try {
	    	   Statement statement = connection.createStatement();
	    	   statement.executeUpdate(requet);
	    	   statement.closeOnCompletion();
	       } catch (SQLException e) {
	           e.printStackTrace();
	           System.out.println("Request error : " + requet);
	       }
	 
	   }
	public static ResultSet queryWithResult(String requet) {
	       try {
	    	   Statement statement = connection.createStatement();
	    	   ResultSet resultat = statement.executeQuery(requet);
	    	   statement.closeOnCompletion();
	    	   return resultat;
	       } catch (SQLException e) {
	           e.printStackTrace();
	           System.out.println("Request error : " + requet);
	       }
	       return null;
	 
	   }	
	
	public static void updatePlayerCraftedBalls(EntityPlayerMP player) {
		String balls = "";
		String stats = "";
		for(EnumPokeballs ball : EnumPokeballs.values()) {
			balls += String.format(",%s", ball.name().replace(" ", ""));
			StatBase statbase = StatList.getCraftStats(ball.getItem());
			if( statbase!=null ) stats += String.format(",%d", player.getStatFile().readStat(statbase));
			else stats += String.format(",%d", 0);
		}
		final String ballsString = balls;
		final String statsString = stats;
		final String req = String.format("REPLACE INTO CraftBall(Player%s) VALUES('%s'%s);", 
				ballsString,
				player.getName(),
				statsString,
				player.getName()
		);
		//PixelmonRanking.log.info(req);
		Thread sqlThread = new Thread(() -> {
		    query(req);
		});
		sqlThread.start();
	}
	
	public static void reqTop10SendPlayer(String reqTop,String reqPlayer,EntityPlayerMP player,String title) {
		Thread sqlThread = new Thread(() -> {
		    ResultSet result = queryWithResult(reqTop);
		    if(result==null) {
		    	PixelmonRanking.log.info("Request Error : Top 10 " + title);
		    	player.sendMessage(new TextComponentString(TextFormatting.RED+"Une erreur est survenu, contacez un administrateur"));
		    	return;
		    }
		    TopRank ranks = new TopRank();
		    try {
				while(result.next()) {
					ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
				}
				if(player!=null && !ranks.hasPlayer(player.getName())) {
			    	result = queryWithResult(String.format(reqPlayer,player.getName()));
			    	if(result==null) {
				    	PixelmonRanking.log.info("Request Error : " + player.getName() + " " + title);
				    	player.sendMessage(new TextComponentString(TextFormatting.RED+"Une erreur est survenu, contacez un administrateur"));
				    	return;
				    }
					while(result.next()) {
						if(result.getString("Player")!=null) ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
					}
			    }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		   if(player!=null ) ChatUtils.sendRank(player, ranks, title);
		});
		sqlThread.start();
	}
	
	public static void reqTop10Sign(String reqTop,SignPlaceholder placeholder) {
		Thread sqlThread = new Thread(() -> {
		    ResultSet result = queryWithResult(reqTop);
		    if(result==null) {
		    	PixelmonRanking.log.info("Request Error : " + placeholder.getRank() + " " + placeholder.getSubrank());
		    	return;
		    }
		    TopRank ranks = new TopRank();
		    try {
				while(result.next()) {
					ranks.add(new PlayerScore(result.getString("Player"), result.getInt("score")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		  try {
			SignHandler.updateSign(ranks, placeholder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		});
		sqlThread.start();
	}
	
}
