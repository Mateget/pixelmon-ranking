package pixelmonranking.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.FMLCommonHandler;
import pixelmonranking.PixelmonRanking;

public class SaveDataUtils extends WorldSavedData {
	
	private NBTTagCompound data;
	
	private static final String DATA_NAME = PixelmonRanking.MOD_ID + "_RANK_PLACEHOLDER";
	

    public SaveDataUtils() {
        super(DATA_NAME);
        data = new NBTTagCompound();
    }
    public SaveDataUtils(String name) {
        super(name);
    }

    @Override
	public void readFromNBT(NBTTagCompound nbt) {
    	
		this.data = nbt.getCompoundTag("rankplaceholder");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		
		nbt.setTag("rankplaceholder", data);
		return nbt;
	}
    
    public NBTTagCompound getData() {
    	
    	if(!data.hasKey("rankplaceholder")) {
    		return new NBTTagCompound();
    	} else {
    	
    		return data.getCompoundTag("rankplaceholder");
    	}
        
    }
    
    public void setData(NBTTagCompound nbtTagCompound) {
    	data.setTag("rankplaceholder", nbtTagCompound);
    	this.markDirty();
    }
    
    public void resetData() {
    	data.setTag("rankplaceholder", new NBTTagCompound());
    	this.markDirty();
    }
    
    public static SaveDataUtils getInstance() {
    	
		WorldServer world = FMLCommonHandler.instance().getMinecraftServerInstance().worlds[0];
		if (world != null) {
			
			WorldSavedData handler = world.getMapStorage().getOrLoadData(SaveDataUtils.class, DATA_NAME);
			if (handler == null) {
				
				handler = new SaveDataUtils();
				world.getMapStorage().setData(DATA_NAME, handler);
			}
			return (SaveDataUtils)handler;
		}
		return null;
	}  

}
