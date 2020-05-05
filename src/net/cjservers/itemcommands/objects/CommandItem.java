package net.cjservers.itemcommands.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class CommandItem {
	
	private List<Material> materials = new ArrayList<Material>();
	private List<String> leftClickCommands = new ArrayList<String>();
	private List<String> rightClickCommands = new ArrayList<String>();
	private List<String> disabledWorlds = new ArrayList<String>();
	private int leftClickCooldown;
	private int rightClickCooldown;
	private String leftClickMessage;
	private String rightClickMessage;
	
	public CommandItem() {
	}
	
	public List<Material> getMaterials() {
		return materials;
	}
	
	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}
	
	public List<String> getLeftClickCommands() {
		return leftClickCommands;
	}
	
	public void setLeftClickCommands(List<String> leftClickCommands) {
		this.leftClickCommands = leftClickCommands;
	}
	
	public List<String> getRightClickCommands() {
		return rightClickCommands;
	}
	
	public void setRightClickCommands(List<String> rightClickCommands) {
		this.rightClickCommands = rightClickCommands;
	}
	
	public List<String> getDisabledWorlds() {
		return disabledWorlds;
	}
	
	public void setDisabledWorlds(List<String> disabledWorlds) {
		this.disabledWorlds = disabledWorlds;
	}
	
	public int getLeftClickCooldown() {
		return leftClickCooldown;
	}
	
	public void setLeftClickCooldown(int leftClickCooldown) {
		this.leftClickCooldown = leftClickCooldown;
	}
	
	public int getRightClickCooldown() {
		return rightClickCooldown;
	}
	
	public void setRightClickCooldown(int rightClickCooldown) {
		this.rightClickCooldown = rightClickCooldown;
	}
	
	public String getLeftClickMessage() {
		return leftClickMessage;
	}
	
	public void setLeftClickMessage(String message) {
		this.leftClickMessage = message;
	}
	
	public String getRightClickMessage() {
		return rightClickMessage;
	}
	
	public void setRightClickMessage(String message) {
		this.rightClickMessage = message;
	}
	
}
