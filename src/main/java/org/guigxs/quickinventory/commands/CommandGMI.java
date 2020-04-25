package org.guigxs.quickinventory.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.guigxs.quickinventory.ConfigManager;

import net.md_5.bungee.api.ChatColor;


public class CommandGMI implements CommandExecutor{
		
		ConfigManager configManager = new ConfigManager();
		
		@Override
		public boolean onCommand(CommandSender sender, Command command, String arg0, String[] args) {
			
			configManager.reloadInventory();
			
			if(command.getName().equalsIgnoreCase("gmi")) {	
				
				if (args.length == 0) {
					
					if (sender instanceof Player) {
						Player player = (Player) sender;
						
						return toggleGameMode(player);
					}
				}
				
				else if(args.length == 1) {
					
					if(Bukkit.getPlayer(args[0]) != null) {
						Player player = Bukkit.getPlayer(args[0]);

						//TODO check permissions gmi.others
						
						return toggleGameMode(player);
						
					}
					
					else {
						sender.sendMessage(ChatColor.YELLOW + "Error, \"" + args[0] + "\" may not exist or is not connected.");
						return true;
					}
				}
				
				else if(args.length == 2) {
					
					if(Bukkit.getPlayer(args[0]) != null) {
						
						Player player = Bukkit.getPlayer(args[0]);
						
						try {
							GameMode gamemode = GameMode.valueOf(args[1].toUpperCase());
							
							return changeGameMode(player, gamemode);
						} catch(Exception e) {
							sender.sendMessage(ChatColor.YELLOW + "Error, \"" + args[1] + "\" is not a gamemode. "
									+ "Please choose between : [Creative, Survival, Spectator, Adventure].");
							
							return true;
						}
					}
					
					else {
						sender.sendMessage(ChatColor.YELLOW + "Error, \"" + args[0] + "\" may not exist or is not connected.");
						return true;
					}
				}
			}

			return false;
		}
		
		public boolean toggleGameMode(Player player) {
			
			if (player.getGameMode() != GameMode.SURVIVAL) {
				
				return changeGameMode(player, GameMode.SURVIVAL);
			}
			else {
				
				return changeGameMode(player, GameMode.CREATIVE);
			}
		}
		
		public boolean changeGameMode(Player player, GameMode gamemode) {
			if (!saveInventory(player)) {
				player.sendMessage(ChatColor.GREEN + "Error while saving your inventory.");
				
				return false;
			}
			
			updateInventory(player, gamemode);
			player.setGameMode(gamemode);
			
			return true;
			
		}
		
		public void updateInventory(Player player, GameMode gamemode) {
			
			player.sendMessage(ChatColor.YELLOW + "Loading " + gamemode.toString().toLowerCase() + " inventory...");
			HashMap<String, Object> playerInfos = configManager.loadInventory(player, gamemode);
			
			double xp = (double) playerInfos.get("xp");
			int level = (int) playerInfos.get("level");
			double health = (double) playerInfos.get("health");
			int foodlvl = (int) playerInfos.get("food-level");
			ItemStack[] is = (ItemStack[]) playerInfos.get("inventory");
			
			player.setExp((float) xp);
			player.setLevel(level);
			player.setHealth(health);
			player.setFoodLevel(foodlvl);
			player.getInventory().setContents(is);

		}
		

		public boolean saveInventory(Player player) {
			player.sendMessage(ChatColor.GREEN + "Inventory saved !");
			return configManager.saveInventory(player);
		}
			
}
