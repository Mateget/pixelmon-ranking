package pixelmonranking;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pixelmonmod.pixelmon.Pixelmon;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import pixelmonranking.command.IgnoreCommand;
import pixelmonranking.command.RankCommand;
import pixelmonranking.command.ReloadCommand;
import pixelmonranking.command.ResetEventCommand;
import pixelmonranking.config.FileHandler;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.event.MinecraftEvents;
import pixelmonranking.event.PixelmonEvents;

@Mod(
        modid = PixelmonRanking.MOD_ID,
        name = PixelmonRanking.MOD_NAME,
        version = PixelmonRanking.VERSION,
        acceptableRemoteVersions = "*",
        dependencies = "after:" + Pixelmon.MODID,
        acceptedMinecraftVersions = "[1.12.2]",
        useMetadata = true
    )

public class PixelmonRanking {

    public static final String MOD_ID = "pixelmonranking";
    public static final String MOD_NAME = "Pixelmon Ranking";
    public static final String VERSION = "1.0";
    public static Logger log = LogManager.getLogger(MOD_NAME);
    public static File configDir;
    public static File configFile;
    public static PixelmonEvents pixelmonevents;
    public static MinecraftEvents minecraftevents;
    public static String dblocation;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent e) {
        log.info("Booting up " + MOD_NAME + " - " + VERSION);
        configDir = new File(e.getModConfigurationDirectory() + "/" + MOD_ID);
        configDir.mkdir();
        configFile = new File(configDir, "config.json");
        FileHandler.readConfig();
        FileHandler.creationCheck();
        FileHandler.writeConfig();
        minecraftevents = new MinecraftEvents();
        pixelmonevents = new PixelmonEvents();
        log.info("Events registered");
        
        
        dblocation = e.getModConfigurationDirectory() + "/" + MOD_ID+"/data.db";
        DatabaseHandler.connect();
        DatabaseHandler.init();

        
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent e) {
    	e.registerServerCommand(new IgnoreCommand());
        e.registerServerCommand(new RankCommand());
        e.registerServerCommand(new ReloadCommand());
        e.registerServerCommand(new ResetEventCommand());
    }

}
