package pixelmonranking.event;


import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import pixelmonranking.database.DatabaseHandler;

public class MinecraftEvents {
		
	public MinecraftEvents() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void on(PlayerLoggedInEvent event){
		DatabaseHandler.updatePlayerCraftedBalls( (EntityPlayerMP) event.player);
	}
	
	@SubscribeEvent
	public void on(PlayerLoggedOutEvent event){
		DatabaseHandler.updatePlayerCraftedBalls( (EntityPlayerMP) event.player);
	}
}
