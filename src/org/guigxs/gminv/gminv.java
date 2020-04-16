package org.guigxs.gminv;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class gminv extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("--------------");
		System.out.println("Starting GMInv");
		System.out.println("--------------");
		
		this.saveDefaultConfig();
		FileConfiguration config = this.getConfig();
		System.out.println(config.get("test"));
		
		this.getCommand("gmi").setExecutor(new CommandGMI());	
		
		
	}
	
	@Override
	public void onDisable() {
		System.out.println("--------------");
		System.out.println("Stopping GMInv");
		System.out.println("--------------");
	}
}
