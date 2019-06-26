package net.cjservers.pickaxecommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ClickListener implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.isCancelled() && e.getAction() != Action.RIGHT_CLICK_AIR) {
			return;
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (e.getItem() != null && e.getItem().getType().name().contains("PICKAXE")) {
				Player p = e.getPlayer();
				if (!Main.getInstance().cooldown.contains(p.getName())) {
					boolean send = true;
					for (String s : Main.getInstance().worlds) {
						if (s.equalsIgnoreCase(p.getWorld().getName())) {
							send = false;
							break;
						}
					}
					if (send) {
						Bukkit.getServer().dispatchCommand(p, Main.getInstance().conf.getString("command"));
						Main.getInstance().cooldown.add(p.getName());
						new BukkitRunnable() {
							
							@Override
							public void run() {
								if (Main.getInstance().cooldown.contains(p.getName())) {
									Main.getInstance().cooldown.remove(p.getName());
								}
							}
						}.runTaskLaterAsynchronously(Main.getInstance(),
								Main.getInstance().conf.getInt("cooldown") * 20);
					}
				}
			}
		}
	}
	
}
