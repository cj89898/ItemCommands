package net.cjservers.itemcommands;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.cjservers.itemcommands.objects.CommandItem;
import net.cjservers.itemcommands.objects.Cooldown;
import net.cjservers.itemcommands.objects.Cooldown.ClickType;

public class ClickListener implements Listener {
	
	private ItemCommands plugin;
	
	public ClickListener(ItemCommands plugin) {
		super();
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.isCancelled() && e.getAction() != Action.RIGHT_CLICK_AIR) {
			return;
		}
		
		for (CommandItem item : plugin.items) {
			for (String world : item.getDisabledWorlds()) {
				if (world.equalsIgnoreCase(e.getPlayer().getWorld().getName())) {
					return;
				}
			}
			if (item.getMaterials().contains(e.getMaterial())) {
				if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {
					boolean onCooldown = false;
					for (Cooldown c : plugin.cooldowns) {
						if (c.check(e.getPlayer().getUniqueId(), item, ClickType.LEFT)) {
							return;
						}
					}
					if (!onCooldown) {
						for (String command : item.getLeftClickCommands()) {
							Bukkit.getServer().dispatchCommand(e.getPlayer(), command);
						}
						Cooldown cooldown = new Cooldown(e.getPlayer().getUniqueId(), item, ClickType.LEFT);
						plugin.cooldowns.add(cooldown);
						new BukkitRunnable() {
							
							@Override
							public void run() {
								if (plugin.cooldowns.contains(cooldown)) {
									plugin.cooldowns.remove(cooldown);
								}
							}
						}.runTaskLaterAsynchronously(plugin, item.getLeftClickCooldown());
					}
					
				} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
					boolean onCooldown = false;
					for (Cooldown c : plugin.cooldowns) {
						if (c.check(e.getPlayer().getUniqueId(), item, ClickType.RIGHT)) {
							return;
						}
					}
					if (!onCooldown) {
						for (String command : item.getLeftClickCommands()) {
							Bukkit.getServer().dispatchCommand(e.getPlayer(), command);
						}
						Cooldown cooldown = new Cooldown(e.getPlayer().getUniqueId(), item, ClickType.RIGHT);
						plugin.cooldowns.add(cooldown);
						new BukkitRunnable() {
							
							@Override
							public void run() {
								if (plugin.cooldowns.contains(cooldown)) {
									plugin.cooldowns.remove(cooldown);
								}
							}
						}.runTaskLaterAsynchronously(plugin, item.getRightClickCooldown());
					}
					
				}
				return;
			}
		}
	}
	
}
