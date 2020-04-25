package net.cjservers.itemcommands.objects;

import java.util.UUID;

public class Cooldown {
	
	public enum ClickType {
		LEFT, RIGHT
	}
	
	private UUID id;
	private CommandItem item;
	private ClickType type;
	
	public Cooldown(UUID id, CommandItem item, ClickType type) {
		super();
		this.id = id;
		this.item = item;
		this.type = type;
	}
	
	public boolean check(UUID id, CommandItem item, ClickType type) {
		return getId().equals(id) && getItem().equals(item) && getType().equals(type);
	}
	
	public UUID getId() {
		return id;
	}
	
	public CommandItem getItem() {
		return item;
	}
	
	public ClickType getType() {
		return type;
	}
	
}
