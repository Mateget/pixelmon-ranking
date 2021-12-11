package pixelmonranking.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import pixelmonranking.PixelmonRanking;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.utils.PermissionUtils;

public class IgnoreCommand extends CommandBase {

    @Override
    public String getName() {
        return "rankignore";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return TextFormatting.RED+String.format("Usage: /rankignore <player>" );
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {	
    	if(args.length != 1) {
        	sender.sendMessage(new TextComponentString(getUsage(sender)));
    		return;
    	}
    	final String request = String.format("INSERT INTO Ignore(Player) VALUES('%s');", args[0]);
		PixelmonRanking.log.info(request);
		DatabaseHandler.threadQuery(request);
    	return;
    }

    
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return true;
	}

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return PermissionUtils.canUse(PixelmonRanking.MOD_ID + ".rankignore", sender);
    }
    
  
    
}
