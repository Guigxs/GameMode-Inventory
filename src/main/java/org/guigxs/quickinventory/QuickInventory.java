package org.guigxs.quickinventory;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;
import org.guigxs.quickinventory.commands.*;

public class QuickInventory extends JavaPlugin{

	public File customConfigFile;
	ConfigManager configManager;
	
	@Override
	public void onEnable() {
		
		loadConfiguration();
		
		System.out.println("---------------------------------");
		System.out.println("| Starting Quick-Inventory v1.0 |");
		System.out.println("---------------------------------");
		System.out.println();
		this.getCommand("qi").setExecutor(new CommandQI());
		this.getCommand("gmi").setExecutor(new CommandGMI());	
		
	}
	
	@Override
	public void onDisable() {
		System.out.println("----------------------------");
		System.out.println("| Stopping Quick-Inventory |");
		System.out.println("----------------------------");
	}
	
	public void loadConfiguration() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
}
