package org.guigxs.quickInv;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class QuickInv extends JavaPlugin{

	public File customConfigFile;
	ConfigManager configManager;
	
	@Override
	public void onEnable() {
		
		loadConfiguration();
		
		System.out.println("---------------------");
		System.out.println("| Starting QuickInv |");
		System.out.println("---------------------");
		
		this.getCommand("qi").setExecutor(new CommandQI());
		this.getCommand("gmi").setExecutor(new CommandGMI());	
		
		
	}
	
	@Override
	public void onDisable() {
		System.out.println("---------------------");
		System.out.println("| Stopping QuickInv |");
		System.out.println("---------------------");
	}
	
	public void loadConfiguration() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
}
