package pixelmonranking.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import pixelmonranking.PixelmonRanking;
import pixelmonranking.command.ranksub.Capture;
import pixelmonranking.command.ranksub.CraftBall;
import pixelmonranking.command.ranksub.DresseurSauvage;
import pixelmonranking.command.ranksub.Evolution;
import pixelmonranking.command.ranksub.ItemScore;
import pixelmonranking.command.ranksub.Knockout;
import pixelmonranking.command.ranksub.Oeuf;
import pixelmonranking.command.ranksub.Piaf;
import pixelmonranking.command.ranksub.Ramassage;
import pixelmonranking.command.ranksub.Recolte;
import pixelmonranking.database.DatabaseHandler;
import pixelmonranking.utils.PermissionUtils;

public class Rank extends CommandBase {

    @Override
    public String getName() {
        return "rank";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return TextFormatting.RED+String.format("Usage: /rank <%s|%s|%s|%s|%s|%s|%s|%s|%s|%s>", 
        		Capture.name,
        		CraftBall.name,
        		DresseurSauvage.name,
        		Evolution.name,
        		ItemScore.name,
        		Knockout.name,
        		Oeuf.name,
        		Piaf.name,
        		Ramassage.name,
        		Recolte.name
        		);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {	
    	EntityPlayerMP player = (EntityPlayerMP) sender;
    	if(args.length>=1) {
    		switch (args[0].toLowerCase()) {
			case Capture.name:
				if(args.length==2) {
					Capture.returnTop10(player, args[1],null);
					return;
				}
				Capture.returnTop10(player, Capture.name,null);
				return;
			case CraftBall.name:
				DatabaseHandler.updatePlayerCraftedBalls(player);
				if(args.length==2) {
					CraftBall.returnTop10(player, args[1],null);
					return;
				}
				CraftBall.returnTop10(player, CraftBall.name,null);
				return;
			case DresseurSauvage.name:
				DresseurSauvage.returnTop10(player, DresseurSauvage.name,null);
				return;
			case Evolution.name:
				if(args.length==2) {
					Evolution.returnTop10(player, args[1],null);
					return;
				}
				Evolution.returnTop10(player, Evolution.name,null);
				return;
			case ItemScore.name:
				if(args.length==2) {
					ItemScore.returnTop10(player, args[1],null);
					return;
				}
				
				String subCommand = "bonbon|chainerouge|fluteazure|masterball";
				sender.sendMessage(new TextComponentString(TextFormatting.RED+String.format("Usage: /rank %s <%s>",ItemScore.name,subCommand)));
				return;
			case Knockout.name:
				if(args.length==2) {
					Knockout.returnTop10(player, args[1],null);
					return;
				}
				Knockout.returnTop10(player, Knockout.name,null);
				return;
			case Oeuf.name:
				if(args.length==2) {
					Oeuf.returnTop10(player, args[1],null);
					return;
				}
				Oeuf.returnTop10(player, Oeuf.name,null);
				return;
			case Piaf.name:
				Piaf.returnTop10(player, Piaf.name,null);
				return;
			case Ramassage.name:
				Ramassage.returnTop10(player, Ramassage.name,null);
				return;
			case Recolte.name:
				if(args.length==2) {
					Recolte.returnTop10(player, args[1],null);
					return;
				}
				String subCommand2 = "baies|noigrumes";
				sender.sendMessage(new TextComponentString(TextFormatting.RED+String.format("Usage: /rank %s <%s>",Recolte.name,subCommand2)));
				return;
			default:
				sender.sendMessage(new TextComponentString(getUsage(sender)));
				break;
			}
    	}
    	
    }

    
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		List<String> autocompleteList = new ArrayList<String>();
		if(args.length==1) {
			autocompleteList.add(Capture.name);
			autocompleteList.add(CraftBall.name);
			autocompleteList.add(DresseurSauvage.name);
			autocompleteList.add(Evolution.name);
			autocompleteList.add(ItemScore.name);
			autocompleteList.add(Knockout.name);
			autocompleteList.add(Oeuf.name);
			autocompleteList.add(Piaf.name);
			autocompleteList.add(Ramassage.name);
			autocompleteList.add(Recolte.name);
			return getListOfStringsMatchingLastWord(args, autocompleteList);
		}
		if(args.length ==2 ) {
			switch (args[0].toLowerCase()) {
			case Capture.name:
				return getListOfStringsMatchingLastWord(args, Capture.subcommand);
			case CraftBall.name:
				return getListOfStringsMatchingLastWord(args, CraftBall.subcommand);
			case DresseurSauvage.name:
				return getListOfStringsMatchingLastWord(args, DresseurSauvage.subcommand);
			case Evolution.name:
				return getListOfStringsMatchingLastWord(args, Evolution.subcommand);
			case ItemScore.name:
				return getListOfStringsMatchingLastWord(args, ItemScore.subcommand);
			case Knockout.name:
				return getListOfStringsMatchingLastWord(args, Knockout.subcommand);
			case Oeuf.name:
				return getListOfStringsMatchingLastWord(args, Oeuf.subcommand);
			case Piaf.name:
				return getListOfStringsMatchingLastWord(args, Piaf.subcommand);
			case Ramassage.name:
				return getListOfStringsMatchingLastWord(args, Ramassage.subcommand);
			case Recolte.name:
				return getListOfStringsMatchingLastWord(args, Recolte.subcommand);
			default:
				break;
			}
		}
		return getListOfStringsMatchingLastWord(args, autocompleteList);
	}


    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return PermissionUtils.canUse(PixelmonRanking.MOD_ID + ".rank", sender);
    }
}
