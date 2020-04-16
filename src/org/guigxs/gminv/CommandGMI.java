package org.guigxs.gminv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.mojang.datafixers.FunctionType.Instance;
import com.mysql.fabric.xmlrpc.base.Array;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class CommandGMI implements CommandExecutor{

		@Override
		public boolean onCommand(CommandSender sender, Command command, String arg0, String[] args) {
			
			
			if (sender instanceof Player) {
				Player player = (Player)sender;
				
				System.out.println("command executed");
				
				ItemStack items[] = player.getInventory().getContents();
				
				if (!saveInv(player, items)) {
					System.out.println("error while saving inventory");
					return false;
				}
				
				//player.getInventory().clear();
				
				if (args.length == 0) {
					
					if (player.getGameMode() != GameMode.SURVIVAL) {
						//JSONObject inventory = getInv(player);
						ArrayList<String> gameModeInv = getGameModeInv(player, GameMode.SURVIVAL);
						System.out.println(gameModeInv);
						player.getInventory().clear();
						if (gameModeInv == null) {
							System.out.println("date for survival null, saving...");
						}
						else {
							updateInventory(player, gameModeInv);
							
						}
						player.setGameMode(GameMode.SURVIVAL);
					}
					
					else {
						ArrayList<String> gameModeInv = getGameModeInv(player, GameMode.CREATIVE);
						System.out.println(gameModeInv);
						player.getInventory().clear();
						if(gameModeInv == null) {
							System.out.println("data for creative null, saving...");
						}
						else {
							updateInventory(player, gameModeInv);
							
						}
						player.setGameMode(GameMode.CREATIVE);
						
					}

				}
				
				else {
					for (String word : args) {
						System.out.println(word);
					}
				}
				
			}
			
			return true;
		}
		
		public boolean updateInventory(Player player, ArrayList<String> gameModeInv) {
			player.getInventory().clear();
			System.out.println(gameModeInv.contains("void"));
			
			
			int i = 0;
			for(String item : gameModeInv) {
				if (!item.equals("void")) {
					System.out.println("Not void, unserializing...");
					player.getInventory().setItem(i, ItemStack.deserialize(new Gson().fromJson(item, Map.class)));
					i++;
				}
			}
			
			return true;
		}
		

		public boolean saveInv(Player player, ItemStack[] inventory) {
			
			List<String> items = new ArrayList<String>();
			
			for(ItemStack item : inventory) {
				
				if(item == null) {			
					items.add("void");
				}
				else {
					items.add(item.serialize().toString());
				}
			}
			
			JSONObject playerInventory;
			JSONObject playerGamemode;
			
			try {
				playerInventory = getInv(player);
				playerGamemode = (JSONObject) playerInventory.get(player.getUniqueId().toString());
				
			} catch (Exception e) {
				System.out.println("Error, file doesn't contain JSONObject, creating...");
				playerInventory = new JSONObject();
				playerGamemode = new JSONObject();
			}
			
			playerGamemode.put(player.getGameMode().toString(), items);
			playerInventory.put(player.getUniqueId().toString(), playerGamemode);
				
			try {
				FileWriter file = new FileWriter("plugins/GMInv/data.json");
				file.write(playerInventory.toJSONString());
				file.close();
				
				System.out.println("Writen !");
				
				return true;
				
			} 
			
			catch (IOException e) {
				e.printStackTrace();
				System.out.println("Wirte error");
				return false;
				
			}
		}
		
		public JSONObject getInv(Player player) {
			
			JSONParser parser = new JSONParser();
			
			try {
				
				Object object = parser.parse(new FileReader("plugins/GMInv/data.json"));
				JSONObject inventory = (JSONObject)object;
				JSONObject playerInventory = (JSONObject) inventory.get(player.getUniqueId().toString());
				
				return  inventory;
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("File not found");
				return null;
			} 
			catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error IO");
				return null;
			} 
			catch (ParseException e) {
				e.printStackTrace();
				System.out.println("Can't get the JSONObject");
				return null;
			}
			
		}
		
		public ArrayList<String> getGameModeInv(Player player, GameMode gameMode) {
			
			JSONParser parser = new JSONParser();
			
			try {
				
				Object object = parser.parse(new FileReader("plugins/GMInv/data.json"));
				JSONObject inventory = (JSONObject)object;
				JSONObject playerInventory = (JSONObject) inventory.get(player.getUniqueId().toString());
				ArrayList<String> playerGamemode = (ArrayList<String>) playerInventory.get(gameMode.toString());
				
				return  playerGamemode;
			}
			
			catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("File not found");
				return null;
			} 
			
			catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error IO");
				return null;
			} 
			
			catch (ParseException e) {
				e.printStackTrace();
				System.out.println("Can't get the JSONObject");
				return null;
			}
			
		}
}
