package pixelmonranking.command;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import pixelmonranking.PixelmonRanking;
import pixelmonranking.config.FileHandler;
import pixelmonranking.utils.PermissionUtils;

public class ReloadCommand extends CommandBase {

    @Override
    public String getName() {
        return "pixelmonranking";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "&cUsage: /pixelmonranking reload";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            FileHandler.readConfig();
            sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Config reloaded"));
            return;
        }
        sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: /pixelmonranking reload"));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibleArgs = new ArrayList<>();
        if (args.length == 1)
            possibleArgs.add("reload");
        return getListOfStringsMatchingLastWord(args, possibleArgs);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return PermissionUtils.canUse(PixelmonRanking.MOD_ID + ".reload", sender);
    }



}
