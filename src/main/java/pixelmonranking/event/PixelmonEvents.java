package pixelmonranking.event;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.ApricornEvent;
import com.pixelmonmod.pixelmon.api.events.BeatTrainerEvent;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.events.BerryEvent;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.events.EggHatchEvent;
import com.pixelmonmod.pixelmon.api.events.EvolveEvent;
import com.pixelmonmod.pixelmon.api.events.FishingEvent;
import com.pixelmonmod.pixelmon.api.events.PickupEvent;
import com.pixelmonmod.pixelmon.api.events.PlayerActivateShrineEvent;
import com.pixelmonmod.pixelmon.api.events.RareCandyEvent;
import com.pixelmonmod.pixelmon.api.events.legendary.ArceusEvent;
import com.pixelmonmod.pixelmon.api.events.legendary.TimespaceEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;

import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pixelmonranking.PixelmonRanking;
import pixelmonranking.database.DatabaseHandler;


public class PixelmonEvents {
	
	private final boolean logSQL = false;
	
	public PixelmonEvents() {
		Pixelmon.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onFishReel(FishingEvent.Reel event) {
		if (event.optEntity.isPresent()) {
			final String req = String.format("INSERT INTO Peche(Player,Contenu,Pokemon) VALUES('%s','%s',%d);", 
					event.player.getName(),
					event.optEntity.get().getDisplayName().getFormattedText(),
					booleanToInt(event.isPokemon())
			);
			if(logSQL) PixelmonRanking.log.info(req);		
			Thread sqlThread = new Thread(() -> {
			    DatabaseHandler.query(req);
			});
			sqlThread.start();
		}
	}
	
	
	
	@SubscribeEvent
	public void onCapture(CaptureEvent.SuccessfulCapture event) {
		final String req = String.format("INSERT INTO Capture(Player,Pokemon,Shiny,DW,Leg,Ball,Form) VALUES('%s','%s',%d,%d,%d,'%s',%d);", 
				event.player.getName(),
				event.getPokemon().getPokemonName(),
				booleanToInt(event.getPokemon().getPokemonData().isShiny()),
				booleanToInt(event.getPokemon().getPokemonData().getAbilitySlot() == 2),
				booleanToInt(event.getPokemon().getPokemonData().isLegendary()),
				event.pokeball.getName(),
				event.getPokemon().getPokemonData().getForm()
		);
		if(logSQL) PixelmonRanking.log.info(req);		
		Thread sqlThread = new Thread(() -> {
		    DatabaseHandler.query(req);
		});
		sqlThread.start();
	}
	
	@SubscribeEvent
	public void onKnockout(BeatWildPixelmonEvent event) {
		final String req = String.format("INSERT INTO Knockout(Player,Pokemon,Shiny,Leg,Form) VALUES('%s','%s',%d,%d,%d);", 
				event.player.getName(),
				event.wpp.getName().getUnformattedText(),
				booleanToInt(((EntityPixelmon) event.wpp.getEntity()).getPokemonData().isShiny()),
				booleanToInt(((EntityPixelmon) event.wpp.getEntity()).getPokemonData().isLegendary()),
				((EntityPixelmon) event.wpp.getEntity()).getPokemonData().getForm()
		);
		if(logSQL) PixelmonRanking.log.info(req);		
		Thread sqlThread = new Thread(() -> {
		    DatabaseHandler.query(req);
		});
		sqlThread.start();
	}
	
	@SubscribeEvent
	public void onRetriveEgg(EggHatchEvent event) {
		final String req = String.format("INSERT INTO Hatch(Player,Pokemon,IsPerfect,Shiny,Form) VALUES('%s','%s',%d,%d,%d);", 
				event.pokemon.getOriginalTrainer(),
				event.pokemon.getDisplayName(),
				booleanToInt(event.pokemon.getIVs().getTotal() == 186),
				booleanToInt(event.pokemon.isShiny()),
				event.pokemon.getForm()
		);
		if(logSQL) PixelmonRanking.log.info(req);		
		Thread sqlThread = new Thread(() -> {
		    DatabaseHandler.query(req);
		});
		sqlThread.start();
	}
	
	@SubscribeEvent
	public void onEvolve(EvolveEvent.PostEvolve event) {
		final String req = String.format("INSERT INTO Evolve(Player,Pokemon,Shiny) VALUES('%s','%s',%d);", 
				event.player.getName(),
				event.pokemon.getDisplayName().getUnformattedText(),
				booleanToInt(event.pokemon.getPokemonData().isShiny())
		);
		if(logSQL) PixelmonRanking.log.info(req);		
		Thread sqlThread = new Thread(() -> {
		    DatabaseHandler.query(req);
		});
		sqlThread.start();
	}
	
	@SubscribeEvent
	public void onShrineEvent(PlayerActivateShrineEvent event) {
		final String req = String.format("INSERT INTO Shrine(Player) VALUES('%s');", 
				event.player.getName()
	    		);
		if(logSQL) PixelmonRanking.log.info(req);		
		Thread sqlThread = new Thread(() -> {
		    DatabaseHandler.query(req);
		});
		sqlThread.start();
	}
	
	@SubscribeEvent
	public void onRareCandyUse(RareCandyEvent event) {
		
		if(event.getResult() !=Result.DEFAULT ) return;
		final String req = String.format("INSERT INTO ItemUse(Player,Item) VALUES('%s','%s');", 
				event.player.getName(),
				"Rare Candy"

		);
		if(logSQL) PixelmonRanking.log.info(req);		
		Thread sqlThread = new Thread(() -> {
		    DatabaseHandler.query(req);
		});
		sqlThread.start();
	}
	
	
	@SubscribeEvent
	public void arceusSpawn(ArceusEvent.PlayFlute event) {
		final String req = String.format("INSERT INTO ItemUse(Player,Item) VALUES('%s','%s');", 
				event.getPlayer().getName(),
				"Azure Flute"
		);
		if(logSQL) PixelmonRanking.log.info(req);		
		Thread sqlThread = new Thread(() -> {
		    DatabaseHandler.query(req);
		});
		sqlThread.start();
	}
	
	@SubscribeEvent
	public void redChaineUse(TimespaceEvent.Summon.Post event) {
		if(event.getPixelmon().getSpecies() == EnumSpecies.Arceus) return;
		final String req = String.format("INSERT INTO ItemUse(Player,Item) VALUES('%s','%s');", 
				event.getPlayer().getName(),
				"Red Chain"
		);
		if(logSQL) PixelmonRanking.log.info(req);		
		Thread sqlThread = new Thread(() -> {
		    DatabaseHandler.query(req);
		});
		sqlThread.start();
	}
		
	@SubscribeEvent
	public void onPickup(PickupEvent event) {
		final String req =String.format("INSERT INTO Pickup(Player,Item) VALUES('%s','%s');", 
				event.player.getName().getUnformattedText(),
				event.stack.getDisplayName().replace("'", "")
		);
		if(logSQL) PixelmonRanking.log.info(req);		
		Thread sqlThread = new Thread(() -> {
		    DatabaseHandler.query(req);
		});
		sqlThread.start();
	}
	
	@SubscribeEvent
	public void onApricornHarvest(ApricornEvent.PickApricorn event) {
		final String req = String.format("INSERT INTO HarvestApricorn(Player,Item) VALUES('%s','%s');", 
				event.player.getName(),
				event.apricorn.name()
		);
		if(logSQL) PixelmonRanking.log.info(req);		
		Thread sqlThread = new Thread(() -> {
		    DatabaseHandler.query(req);
		});
		sqlThread.start();
	}
	
	@SubscribeEvent
	public void onBerryHarvest(BerryEvent.PickBerry event) {
		final String req =String.format("INSERT INTO HarvestBerry(Player,Item) VALUES('%s','%s');", 
				event.player.getName(),
				event.berry.name()
		);
		if(logSQL) PixelmonRanking.log.info(req);		
		Thread sqlThread = new Thread(() -> {
		    DatabaseHandler.query(req);
		});
		sqlThread.start();
	}
	
	@SubscribeEvent
	public void onTrainerDefeat(BeatTrainerEvent event) {
		final String req =String.format("INSERT INTO BeatTrainer(Player) VALUES('%s');", 
				event.player.getName()
	    		);
		if(logSQL) PixelmonRanking.log.info(req);		
		Thread sqlThread = new Thread(() -> {
		    DatabaseHandler.query(req);
		});
		sqlThread.start();
	}
	

	private int booleanToInt(boolean bool) {
		if(bool) return 1;
		return 0;
		
	}
}
	

