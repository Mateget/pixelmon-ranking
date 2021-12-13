package pixelmonranking.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;

public class SkullUtils {
	
	
	 public static ItemStack getCustomHead(String playerName) {	    	
	    	ItemStack customHead = new ItemStack(Items.SKULL, 1, 3);  
	    	NBTTagCompound nbt = new NBTTagCompound();
	    	nbt.setTag("SkullOwner", new NBTTagString(playerName));
	    	customHead.setTagCompound(nbt);
	    	return customHead;    	    
	    }
	 
	 public static String executeGet(String username) throws IOException {
		 	URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
		 	StringBuilder result = new StringBuilder();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				for (String line; (line = reader.readLine()) != null;) {
					result.append(line);
				}
			}
			
			JsonObject jsonObject = new JsonParser().parse(result.toString()).getAsJsonObject();
			JsonElement id = jsonObject.get("id");
			
			url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + id.getAsString());
			result = new StringBuilder();
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				for (String line; (line = reader.readLine()) != null;) {
					result.append(line);
				}
			}
			return result.toString();
		}

}
