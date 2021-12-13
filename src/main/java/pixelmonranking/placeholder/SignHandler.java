package pixelmonranking.placeholder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
import pixelmonranking.config.FileHandler;
import pixelmonranking.model.PlayerScore;
import pixelmonranking.model.SignPlaceholder;
import pixelmonranking.model.TopRank;
import pixelmonranking.utils.SkullUtils;

public class SignHandler {
	
	public static void initSignUpdater() {
		PixelmonRanking.log.info("Sign Updater Initialized");
		Runnable signUpdaterRunnable = new Runnable() {
		    public void run() {
		    	Thread signsUdpaterThread = new Thread(() -> {
				    setSigns();
				});
		    	signsUdpaterThread.start();
		    }
		};
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		exec.scheduleAtFixedRate(signUpdaterRunnable , 0, FileHandler.config.getUpdateTime(), TimeUnit.MINUTES);
		
	}
	
	public static void setSigns() {
		PixelmonRanking.log.info("Updating Signs");
		ArrayList<SignPlaceholder> signs =  FileHandler.config.getPlaceholders();
			for(SignPlaceholder placeholderLoc : signs) {
				switch (placeholderLoc.getRank()) {
				case Capture.name:
					if(!placeholderLoc.getSubrank().equals("")) {
						Capture.returnTop10(null, placeholderLoc.getSubrank(),placeholderLoc);
						return;
					}
					Capture.returnTop10(null, Capture.name,placeholderLoc);
					return;
				case CraftBall.name:
					if(!placeholderLoc.getSubrank().equals("")) {
						CraftBall.returnTop10(null, placeholderLoc.getSubrank(),placeholderLoc);
						return;
					}
					CraftBall.returnTop10(null, CraftBall.name,placeholderLoc);
					return;
				case DresseurSauvage.name:
					DresseurSauvage.returnTop10(null, DresseurSauvage.name,placeholderLoc);
					return;
				case Evolution.name:
					if(!placeholderLoc.getSubrank().equals("")) {
						Evolution.returnTop10(null, placeholderLoc.getSubrank(),placeholderLoc);
						return;
					}
					Evolution.returnTop10(null, Evolution.name,placeholderLoc);
					return;
				case ItemScore.name:
					if(!placeholderLoc.getSubrank().equals("")) {
						ItemScore.returnTop10(null, placeholderLoc.getSubrank(),placeholderLoc);
						return;
					}
					return;
				case Knockout.name:
					if(!placeholderLoc.getSubrank().equals("")) {
						Knockout.returnTop10(null, placeholderLoc.getSubrank(),placeholderLoc);
						return;
					}
					Knockout.returnTop10(null, Knockout.name,placeholderLoc);
					return;
				case Oeuf.name:
					if(!placeholderLoc.getSubrank().equals("")) {
						Oeuf.returnTop10(null, placeholderLoc.getSubrank(),placeholderLoc);
						return;
					}
					Oeuf.returnTop10(null, Oeuf.name,placeholderLoc);
					return;
				case Piaf.name:
					Piaf.returnTop10(null, Piaf.name,placeholderLoc);
					return;
				case Ramassage.name:
					Ramassage.returnTop10(null, Ramassage.name,placeholderLoc);
					return;
				case Recolte.name:
					if(!placeholderLoc.getSubrank().equals("")) {
						Recolte.returnTop10(null, placeholderLoc.getSubrank(),placeholderLoc);
						return;
					}
					return;
				default:
					break;
				}
			} 
	}
	
	public static void updateSign(TopRank ranks, SignPlaceholder placeholder) throws IOException {
		WorldServer world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(placeholder.getWorldID());
		
		if(placeholder.getSigns().size()<3)  {
			PixelmonRanking.log.info("Error updateSign " + placeholder.getRank() + " " + placeholder.getSubrank());
			PixelmonRanking.log.info("3 Signs must be provided in config");
			return;
		}
		if(placeholder.getHeads().size()<3)  {
			PixelmonRanking.log.info("Error updateSign " + placeholder.getRank() + " " + placeholder.getSubrank());
			PixelmonRanking.log.info("3 Heads must be provided in config");
			return;
		}
		for(int i =0; i<3 ;i++) {
			//Sign
			BlockPos blockposSign = new BlockPos(placeholder.getSigns().get(i).getX(),placeholder.getSigns().get(i).getY(), placeholder.getSigns().get(i).getZ());	
			IBlockState stateSign = world.getBlockState(blockposSign);
			TileEntity tileEntitySign = world.getTileEntity(blockposSign);
			if(tileEntitySign!=null ) {
				if(tileEntitySign instanceof TileEntitySign) {
					TileEntitySign sign = (TileEntitySign) tileEntitySign;
					sign.markDirty();
					sign.signText[0] = new TextComponentString(TextFormatting.RED+""+TextFormatting.STRIKETHROUGH+"------------");
					sign.signText[3] = new TextComponentString(TextFormatting.RED+""+TextFormatting.STRIKETHROUGH+"------------");
					PlayerScore playerScore = ranks.get(i);
					if(playerScore!=null) {
						sign.signText[1] = new TextComponentString(TextFormatting.GREEN+playerScore.getPlayer());
						sign.signText[2] = new TextComponentString(TextFormatting.YELLOW+""+playerScore.getScore());
					} else {
						sign.signText[1] = new TextComponentString(TextFormatting.GRAY+"No player");
						sign.signText[2] = new TextComponentString(TextFormatting.GRAY+"");
						
					}
					world.notifyBlockUpdate(blockposSign, stateSign, stateSign, 3);
				}
			} else {
				PixelmonRanking.log.info("TileEntity null : " + blockposSign.toString());
			}
			//Head
			BlockPos blockposHead = new BlockPos(placeholder.getHeads().get(i).getX(),placeholder.getHeads().get(i).getY(), placeholder.getHeads().get(i).getZ());	
			IBlockState stateHead = world.getBlockState(blockposHead);
			TileEntity tileEntityHead = world.getTileEntity(blockposHead);

			if(tileEntityHead!=null ) {
				if(tileEntityHead instanceof TileEntitySkull) {
					TileEntitySkull head = (TileEntitySkull) tileEntityHead;
					
					PlayerScore playerScore = ranks.get(i);
					if(playerScore!=null) {
						
									
						ItemStack item = SkullUtils.getCustomHead(playerScore.getPlayer());
						GameProfile gameprofile = null;

                        if (item.hasTagCompound())
                        {
                            NBTTagCompound nbttagcompound = item.getTagCompound();

                            if (nbttagcompound.hasKey("SkullOwner", 10))
                            {
                                gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
                            }
                        }
                        String response;
                        response = SkullUtils.executeGet(playerScore.getPlayer());
						JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
						JsonElement id = jsonObject.get("id");
						JsonElement nameJson = jsonObject.get("name");
						JsonArray properties = (JsonArray) jsonObject.get("properties");
						JsonObject texturesJson = properties.get(0).getAsJsonObject();
						JsonElement textureValue = texturesJson.get("value");
						
						String skinBase64 = textureValue.getAsString();

						String uuid = String.format("%s-%s-%s-%s-%s", 
								id.getAsString().substring(0, 8),
								id.getAsString().substring(8, 12),
								id.getAsString().substring(12, 16),
								id.getAsString().substring(16, 20),
								id.getAsString().substring(20, 32)
						);

						String name = nameJson.getAsString();
						
						head.setPlayerProfile(gameprofile);
						NBTTagCompound texture = new NBTTagCompound();
						texture.setString("Value", skinBase64);
						NBTTagList textures = new NBTTagList();
						textures.appendTag(texture);
						NBTTagCompound owner = new NBTTagCompound();
						owner.setString("Id", uuid.toString());
						owner.setString("Name", name);
						owner.setTag("Properties", textures);
    
						NBTTagCompound tag = head.serializeNBT();
						tag.setTag("Owner", owner);
						//PixelmonRanking.log.info("Tag : "+ tag.toString());
						head.handleUpdateTag(tag);
                        
                    }
					world.notifyBlockUpdate(blockposHead, stateHead, stateHead, 3);
				} else {
					PixelmonRanking.log.info("Not a head"+ blockposHead.toString());
				}
			} else {
				PixelmonRanking.log.info("TileEntity null : " + blockposHead.toString());
			} 
		}
		
	}

}
