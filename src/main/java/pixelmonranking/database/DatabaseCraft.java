package pixelmonranking.database;

import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import pixelmonranking.PixelmonRanking;

public class DatabaseCraft {
	
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
		PixelmonRanking.log.info(req);
		Thread sqlThread = new Thread(() -> {
		    DatabaseHandler.query(req);
		});
		sqlThread.start();
	}

}
