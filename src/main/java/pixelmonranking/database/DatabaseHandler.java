package pixelmonranking.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;

import pixelmonranking.PixelmonRanking;

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
	           statement.executeUpdate(requet);
	       } catch (SQLException e) {
	           e.printStackTrace();
	           System.out.println("Request error : " + requet);
	       }
	 
	   }
	public static ResultSet queryWithResult(String requet) {
	       ResultSet resultat = null;
	       try {
	           resultat = statement.executeQuery(requet);
	       } catch (SQLException e) {
	           e.printStackTrace();
	           System.out.println("Request error : " + requet);
	       }
	       return resultat;
	 
	   }	
	
}
