package net.cjservers.itemcommands.objects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("CommandItem")
public class CommandItem implements ConfigurationSerializable {
	
	private List<Material> materials = new ArrayList<Material>();
	private List<String> leftClickCommands = new ArrayList<String>();
	private List<String> rightClickCommands = new ArrayList<String>();
	private List<String> disabledWorlds = new ArrayList<String>();
	private int leftClickCooldown;
	private int rightClickCooldown;
	
	public List<Material> getMaterials() {
		return materials;
	}
	
	public List<String> getLeftClickCommands() {
		return leftClickCommands;
	}
	
	public List<String> getRightClickCommands() {
		return rightClickCommands;
	}
	
	public List<String> getDisabledWorlds() {
		return disabledWorlds;
	}
	
	public int getLeftClickCooldown() {
		return leftClickCooldown;
	}
	
	public int getRightClickCooldown() {
		return rightClickCooldown;
	}
	
	public CommandItem(List<Material> materials, List<String> leftClickCommands, List<String> rightClickCommands,
			List<String> disabledWorlds, int leftClickCooldown, int rightClickCooldown) {
		super();
		this.materials = materials;
		this.leftClickCommands = leftClickCommands;
		this.rightClickCommands = rightClickCommands;
		this.disabledWorlds = disabledWorlds;
		this.leftClickCooldown = leftClickCooldown;
		this.rightClickCooldown = rightClickCooldown;
	}
	
	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("materials", materials);
		result.put("left_click_commands", leftClickCommands);
		result.put("right_click_commands", rightClickCommands);
		result.put("disabled_worlds", disabledWorlds);
		result.put("left_click_cooldown", leftClickCooldown);
		result.put("right_click_cooldown", rightClickCooldown);
		return result;
	}
	
	public static CommandItem deserialize(ConfigurationSection configurationSection) {
		List<Material> materials = new ArrayList<Material>();
		List<String> strings = configurationSection.getStringList("materials");
		for (String s : strings) {
			if (Material.matchMaterial(s) == null) {
				Bukkit.getLogger()
						.warning("Invalid material: " + s + " for item" + configurationSection.getCurrentPath());
			} else {
				materials.add(Material.matchMaterial(s));
			}
		}
		List<String> leftClickCommands = configurationSection.getStringList("left_click_commands");
		List<String> rightClickCommands = configurationSection.getStringList("right_click_commands");
		List<String> disabledWorlds = configurationSection.getStringList("disabled_worlds");
		int leftClickCooldown = configurationSection.getInt("left_click_cooldown");
		int rightClickCooldown = configurationSection.getInt("right_click_cooldown");
		return new CommandItem(materials, leftClickCommands, rightClickCommands, disabledWorlds, leftClickCooldown,
				rightClickCooldown);
	}
	
}
