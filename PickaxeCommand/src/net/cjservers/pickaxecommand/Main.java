package net.cjservers.pickaxecommand;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	private static Main instance;
	private Utils utils;
	public String version;
	
	private File confYml;
	public FileConfiguration conf;
	
	public ArrayList<String> worlds;
	public ArrayList<String> cooldown;
	
	@Override
	public void onEnable() {
		instance = this;
		utils = new Utils(this);
		
		confYml = new File(getDataFolder(), "config.yml");
		
		fixConf();
		conf = Utils.getConfiguration("config.yml");
		
		version = getDescription().getVersion();
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new ClickListener(), this);
		
		worlds = new ArrayList<String>();
		cooldown = new ArrayList<String>();
		for (String s : conf.getStringList("disabled-worlds")) {
			worlds.add(s);
		}
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public Utils getUtils() {
		return utils;
	}
	
	public void fixConf() {
		if (!(confYml.exists())) {
			saveDefaultConfig();
			System.out.println("[PickaxeCommand] - Created config.yml");
		}
	}
	
	public void reloadConfigs() {
		conf = Utils.getConfiguration("config.yml");
	}
	
}
