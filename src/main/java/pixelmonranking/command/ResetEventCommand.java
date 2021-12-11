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
import pixelmonranking.utils.PermissionUtils;
import pixelmonranking.utils.SaveDataUtils;

public class ResetEventCommand extends CommandBase {

    @Override
    public String getName() {
        return "resethuntevent";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return TextFormatting.RED+"Usage: /resethuntevent";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {	
    	SaveDataUtils.getInstance().resetData();
    	sender.sendMessage(new TextComponentString(TextFormatting.RED+"All players score were reset"));
    }

    
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		List<String> autocompleteList = new ArrayList<String>();	
		return autocompleteList;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return PermissionUtils.canUse(PixelmonRanking.MOD_ID + ".reset", sender);
    }
        
}
