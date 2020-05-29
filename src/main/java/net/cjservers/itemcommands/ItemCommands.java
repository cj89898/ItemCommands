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
			List<String> leftClickCommands = conf.getStringList(currentPath + "left_click_commands");
			List<String> rightClickCommands = conf.getStringList(currentPath + "right_click_commands");
			if (leftClickCommands.size() > 0) {
				List<String> leftClickActions = conf.getStringList(currentPath + "left_click_actions");
				
				for (String cmd : leftClickCommands) {
					leftClickActions.add("[PLAYER] " + cmd);
				}
				if (leftClickActions.size() > 0) {
					conf.set(currentPath + "left_click_actions", leftClickActions);
				}
				conf.set(currentPath + "left_click_commands", null);
				Utils.save(conf, "config.yml");
			}
			if (rightClickCommands.size() > 0) {
				List<String> rightClickActions = conf.getStringList(currentPath + "right_click_actions");
				for (String cmd : rightClickCommands) {
					rightClickActions.add("[PLAYER] " + cmd);
				}
				if (rightClickActions.size() > 0) {
					conf.set(currentPath + "right_click_actions", rightClickActions);
				}
				conf.set(currentPath + "right_click_commands", null);
				Utils.save(conf, "config.yml");
			}
			
			commandItem.setMaterials(materials);
			commandItem.setDisabledWorlds(conf.getStringList(currentPath + "disabled_worlds"));
			
			commandItem.setLeftClickActions(conf.getStringList(currentPath + "left_click_actions"));
			commandItem.setLeftClickMessage(Utils.color(conf.getString(currentPath + "left_click_message", null)));
			commandItem.setLeftClickCooldown(conf.getInt(currentPath + "left_click_cooldown", 0));
			
			commandItem.setRightClickActions(conf.getStringList(currentPath + "right_click_actions"));
			commandItem.setRightClickMessage(Utils.color(conf.getString(currentPath + "right_click_message", null)));
			commandItem.setRightClickCooldown(conf.getInt(currentPath + "right_click_cooldown", 0));
			
			items.add(commandItem);
		}
	}
	
}
