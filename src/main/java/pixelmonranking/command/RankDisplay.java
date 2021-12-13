package pixelmonranking.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import pixelmonranking.PixelmonRanking;
import pixelmonranking.placeholder.SignHandler;
import pixelmonranking.utils.PermissionUtils;

public class RankDisplay  extends CommandBase{

	@Override
	public String getName() {
		return "rankdisplay";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return TextFormatting.RED+String.format("Usage: /rankdisplay <update>" );
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length ==1) {
			switch (args[0]) {
			case "update":
				SignHandler.setSigns();
				sender.sendMessage(new TextComponentString(TextFormatting.AQUA+"Plop update"));
				break;
			default:
				break;
			}
			
		}
		
		sender.sendMessage(new TextComponentString(getUsage(sender)));
		
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		List<String> possibleArgs = new ArrayList<>();
		if(args.length==1) possibleArgs.add("update");
		return getListOfStringsMatchingLastWord(args, possibleArgs);
	}
	
	@Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return PermissionUtils.canUse(PixelmonRanking.MOD_ID + ".rankdisplay", sender);
    }

}
