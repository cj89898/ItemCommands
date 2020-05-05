package net.cjservers.itemcommands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.cjservers.itemcommands.objects.CommandItem;
import net.cjservers.itemcommands.objects.Cooldown;

public class ItemCommands extends JavaPlugin {
	
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
		
		reloadConfigs();
		
		version = getDescription().getVersion();
		
		getServer().getPluginManager().registerEvents(new ClickListener(instance), this);
		getServer().getPluginCommand("itemcommands").setExecutor(new ItemCommandsCommand(instance));
		
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
	
	private void fixConf() {
		if (!(confYml.exists())) {
			saveDefaultConfig();
			Bukkit.getLogger().info("[ItemCommands] - Created config.yml");
		}
	}
	
	public void reloadConfigs() {
		fixConf();
		conf = Utils.getConfiguration("config.yml");
		
		for (String item : conf.getConfigurationSection("items").getKeys(false)) {
			String currentPath = "items." + item + ".";
			CommandItem commandItem = new CommandItem();
			List<Material> materials = new ArrayList<Material>();
			List<String> strings = conf.getStringList(currentPath + "materials");
			for (String s : strings) {
				if (Material.matchMaterial(s) == null) {
					Bukkit.getLogger().warning("Invalid material: " + s + " for item " + item);
				} else {
					materials.add(Material.matchMaterial(s));
				}
			}
			commandItem.setMaterials(materials);
			commandItem.setDisabledWorlds(conf.getStringList(currentPath + "disabled_worlds"));
			
			commandItem.setLeftClickCommands(conf.getStringList(currentPath + "left_click_commands"));
			commandItem.setLeftClickMessage(Utils.color(conf.getString(currentPath + "left_click_message", null)));
			commandItem.setLeftClickCooldown(conf.getInt(currentPath + "left_click_cooldown", 0));
			
			commandItem.setRightClickCommands(conf.getStringList(currentPath + "right_click_commands"));
			commandItem.setRightClickMessage(Utils.color(conf.getString(currentPath + "right_click_message", null)));
			commandItem.setRightClickCooldown(conf.getInt(currentPath + "right_click_cooldown", 0));
			
			items.add(commandItem);
		}
	}
	
}
