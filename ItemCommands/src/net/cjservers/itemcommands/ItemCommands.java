package net.cjservers.itemcommands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import net.cjservers.itemcommands.objects.CommandItem;
import net.cjservers.itemcommands.objects.Cooldown;

public class ItemCommands extends JavaPlugin {
	
	static {
		ConfigurationSerialization.registerClass(CommandItem.class, "CommandItem");
	}
	
	private static ItemCommands instance;
	private Utils utils;
	public String version;
	
	private File confYml;
	public FileConfiguration conf;
	
	public List<CommandItem> items;
	public List<Cooldown> cooldowns;
	
	@Override
	public void onEnable() {
		instance = this;
		utils = new Utils(this);
		items = new ArrayList<CommandItem>();
		cooldowns = new ArrayList<Cooldown>();
		
		confYml = new File(getDataFolder(), "config.yml");
		
		fixConf();
		conf = Utils.getConfiguration("config.yml");
		
		version = getDescription().getVersion();
		
		getServer().getPluginManager().registerEvents(new ClickListener(instance), this);
		
		ConfigurationSection configItems = conf.getConfigurationSection("items");
		for (String item : configItems.getKeys(false)) {
			ConfigurationSection ci = configItems.getConfigurationSection(item);
			items.add(CommandItem.deserialize(ci));
		}
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static ItemCommands getInstance() {
		return instance;
	}
	
	public Utils getUtils() {
		return utils;
	}
	
	public void fixConf() {
		if (!(confYml.exists())) {
			saveDefaultConfig();
			Bukkit.getLogger().info("[ItemCommands] - Created config.yml");
		}
	}
	
	public void reloadConfigs() {
		conf = Utils.getConfiguration("config.yml");
	}
	
}
