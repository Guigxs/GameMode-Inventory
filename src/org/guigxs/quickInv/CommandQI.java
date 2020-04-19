package org.guigxs.quickInv;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandQI implements CommandExecutor{
	
	ConfigManager configManager = new ConfigManager();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg0, String[] args) {
		
		if(command.getName().equalsIgnoreCase("qi")){
			System.out.println("/qi executed");
			
			if (args[0].equalsIgnoreCase("see")) {

				Player targetPlayer = Bukkit.getPlayer(args[1]);
				
				System.out.println("Target player : " + targetPlayer);
				
				if(sender instanceof Player) {
					Player player = (Player) sender;
					
					// TODO save targetPlayer inventory
					
					PlayerInventory inventory = new PlayerInventory();
					inventory.newInventory(player, targetPlayer, GameMode.valueOf(args[2].toUpperCase()));
					
					//Inventory targetPlayerInventory = Bukkit.createInventory(null, 9);
					
				}
			}
		}
		
		
		
		
		return false;
	}
}
