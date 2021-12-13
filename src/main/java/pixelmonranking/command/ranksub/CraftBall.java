package pixelmonranking.command.ranksub;

import java.util.ArrayList;
import java.util.Arrays;

import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;

import net.minecraft.entity.player.EntityPlayerMP;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.model.SignPlaceholder;

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
	
	public static void returnTop10(EntityPlayerMP playerSender, String command,SignPlaceholder placeholder) {
		String reqTop;
		String reqPlayer;
		switch (command.toLowerCase()) {
		case name:
			reqTop = "SELECT CraftBall.player, ("+ballsum+") AS score "
					+ "FROM CraftBall,Ignore "
					+ "WHERE CraftBall.player!=Ignore.player "
					+ "GROUP by CraftBall.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT CraftBall.player, ( "+ballsum+") AS score "
					+ "FROM CraftBall "
					+ "WHERE CraftBall.player=='%s';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus de Ball crafté");
			return;
		case "hyper":
			reqTop = "SELECT CraftBall.player, (UltraBall) AS score FROM CraftBall,Ignore "
					+ "WHERE CraftBall.player!=Ignore.player "
					+ "GROUP by CraftBall.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT CraftBall.player, (UltraBall) AS score FROM CraftBall "
					+ "WHERE CraftBall.player=='%s';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus d'Hyper ball crafté");
			return;
		case "love":
			reqTop = "SELECT CraftBall.player, (LoveBall) AS score FROM CraftBall,Ignore "
					+ "WHERE CraftBall.player!=Ignore.player "
					+ "GROUP by CraftBall.player "
					+ "ORDER BY score DESC LIMIT(10);";
			if(placeholder !=null) {
				DatabaseHandler.reqTop10Sign(reqTop, placeholder);
				return;
			}
			reqPlayer = "SELECT CraftBall.player, (LoveBall) AS score FROM CraftBall "
					+ "WHERE CraftBall.player=='%s';";
			DatabaseHandler.reqTop10SendPlayer(reqTop,reqPlayer,playerSender,"Le plus d'Hyper ball crafté");
			return;
		default:
			return;
		}
	}
	

}
