package org.guigxs.quickinventory.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.guigxs.quickinventory.ConfigManager;
import org.guigxs.quickinventory.utils.CustomInventory;

import net.md_5.bungee.api.ChatColor;


public class CommandQI implements CommandExecutor{
	
	ConfigManager configManager = new ConfigManager();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg0, String[] args) {
		
		if(command.getName().equalsIgnoreCase("qi")){
			if (args.length == 0) {
				return false;
			}
			
			if (args[0].equalsIgnoreCase("see") || args[0].equalsIgnoreCase("seen") || args[0].equalsIgnoreCase("s")) {
				
				if(args.length == 3) {
					
					if(Bukkit.getPlayer(args[1]) != null) {
						
						Player targetPlayer = Bukkit.getPlayer(args[1]);
						
						
						for(GameMode gm : GameMode.values()) {

							if(gm.toString().equalsIgnoreCase(args[2])) {
								
								CommandGMI gmi = new CommandGMI();
								gmi.saveInventory(targetPlayer);
								
								CustomInventory customInventory = new CustomInventory();
								Inventory inventory = customInventory.newInventory(targetPlayer, GameMode.valueOf(args[2].toUpperCase()));

								if(sender instanceof Player) {
									Player player = (Player) sender;
		
									player.openInventory(inventory);


								}
								else {
									
									//TODO clean console view
									
									sender.sendMessage(Arrays.asList(inventory.getContents()).toString());
								}
								
								return true;
							}

						}
						
						sender.sendMessage(ChatColor.YELLOW + "Error, \"" + args[2] + "\" is not a gamemode. "
								+ "Please choose between : [Creative, Survival, Spectator, Adventure].");
						return true;
					} 

					else {
						sender.sendMessage(ChatColor.YELLOW + "Error, \"" + args[1] + "\" may not exist or is not connected.");
						return true;
					}
				}
				
				else {
					sender.sendMessage(ChatColor.RED + "Error, use : /qi see [player] [gamemode]");
				}
				
				return true;
			}
			
			else if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
				sender.sendMessage(ChatColor.YELLOW + "--------- " + ChatColor.WHITE + "Quick-Inventory help menu " 
						+ ChatColor.YELLOW + "--------------------");
				
				sender.sendMessage(ChatColor.GOLD + "/gmi (optional: [player] [gamemode]):"
						+ ChatColor.WHITE + " change the player's gamemode inventory");
				
				sender.sendMessage(ChatColor.GOLD + "/qi [clear | clean | cl] [player] [gamemode]:"
						+ ChatColor.WHITE + " clean the player's gamemode inventory");
				
				sender.sendMessage(ChatColor.GOLD + "/qi [see | seen | s] [player] [gamemode]:"
						+ ChatColor.WHITE + " see the player's gamemode inventory");
				
				//TODO display command list
				
				return true;
			}
			
			else if(args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("clean") || args[0].equalsIgnoreCase("cl")) {
				
				if(args.length == 3) {
					
					if(Bukkit.getPlayer(args[1]) != null) {
						
						Player targetPlayer = Bukkit.getPlayer(args[1]);
						
						for(GameMode gm : GameMode.values()) {

							if(gm.toString().equalsIgnoreCase(args[2])) {
																							
								if(configManager.cleanInventory(targetPlayer, gm)) {
								
									if(targetPlayer.getGameMode().toString().equalsIgnoreCase(gm.toString())) {
										targetPlayer.getInventory().clear();
									}
									
									sender.sendMessage(ChatColor.GREEN + targetPlayer.getName() +"'s inventory [" + gm.toString() + "] has been cleaned");
									
									return true;
								}
								else {
									sender.sendMessage(ChatColor.YELLOW + "Error, can't clean "+ targetPlayer.getName() +"'s inventory [" + gm.toString() + "] has");
									
									return true;
								}
							}

						}
						
						sender.sendMessage(ChatColor.YELLOW + "Error, \"" + args[2] + "\" is not a gamemode. "
								+ "Please choose between : [Creative, Survival, Spectator, Adventure].");
						
						return true;
					} 

					else {
						sender.sendMessage(ChatColor.YELLOW + "Error, \"" + args[1] + "\" may not exist or is not connected.");
						return true;
					}
				}
				
				else {
					sender.sendMessage(ChatColor.RED + "Error, use : /qi clear [player] [gamemode]");
					return true;
				}
			}

			sender.sendMessage("Unknown command, type \"/qi help\" or \"/qi ?\" to see the commands list");
		}

		return true;
	}
}
