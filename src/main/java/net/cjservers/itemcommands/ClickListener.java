package net.cjservers.itemcommands;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.gabytm.util.actions.ActionManager;
import net.cjservers.itemcommands.objects.CommandItem;
import net.cjservers.itemcommands.objects.Cooldown;
import net.cjservers.itemcommands.objects.Cooldown.ClickType;

public class ClickListener implements Listener {
	
	private ItemCommands plugin;
	final ActionManager actionManager;
	
	public ClickListener(ItemCommands plugin) {
		super();
		this.plugin = plugin;
		this.actionManager = new ActionManager(plugin);
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.useItemInHand() == Event.Result.DENY) {
			return;
		}
		
		for (CommandItem item : plugin.items) {
			Player player = e.getPlayer();
			for (String world : item.getDisabledWorlds()) {
				if (world.equalsIgnoreCase(player.getWorld().getName())) {
					return;
				}
			}
			if (item.getMaterials().contains(e.getMaterial())) {
				if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {
					boolean onCooldown = false;
					for (Cooldown c : plugin.cooldowns) {
						if (c.check(player.getUniqueId(), item, ClickType.LEFT)) {
							onCooldown = true;
							break;
						}
					}
					if (!onCooldown) {
						for (String action : item.getLeftClickActions()) {
							actionManager.execute(player, action);
						}
						Cooldown cooldown = new Cooldown(player.getUniqueId(), item, ClickType.LEFT);
						plugin.cooldowns.add(cooldown);
						new BukkitRunnable() {
							
							@Override
							public void run() {
								if (plugin.cooldowns.contains(cooldown)) {
									plugin.cooldowns.remove(cooldown);
								}
							}
						}.runTaskLaterAsynchronously(plugin, item.getLeftClickCooldown());
					} else {
						if (item.getLeftClickMessage() != null) {
							player.sendMessage(item.getLeftClickMessage());
						}
					}
					
				} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
					boolean onCooldown = false;
					for (Cooldown c : plugin.cooldowns) {
						if (c.check(player.getUniqueId(), item, ClickType.RIGHT)) {
							onCooldown = true;
							break;
						}
					}
					if (!onCooldown) {
						for (String action : item.getRightClickActions()) {
							actionManager.execute(player, action);
						}
						Cooldown cooldown = new Cooldown(player.getUniqueId(), item, ClickType.RIGHT);
						if (item.getRightClickCooldown() == 0) {
							return;
						}
						plugin.cooldowns.add(cooldown);
						new BukkitRunnable() {
							
							@Override
							public void run() {
								if (plugin.cooldowns.contains(cooldown)) {
									plugin.cooldowns.remove(cooldown);
								}
							}
						}.runTaskLaterAsynchronously(plugin, item.getRightClickCooldown());
					} else {
						if (item.getRightClickMessage() != null) {
							player.sendMessage(item.getRightClickMessage());
						}
					}
					
				}
				return;
			}
		}
	}
	
}
