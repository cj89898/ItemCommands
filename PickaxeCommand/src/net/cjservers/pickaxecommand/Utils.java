package net.cjservers.pickaxecommand;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Utils {
	
	Main plugin;

	public Utils(Main plugin) {
		this.plugin = plugin;
	}
	
	private static final String DIRECTORY = "plugins/PickaxeCommand/";
	
	private static File getFile(String name) throws IOException {
		File file = new File(DIRECTORY, name);

		return file.createNewFile() ? file : file.exists() ? file : null;
	}
	
	public static FileConfiguration getConfiguration(String name) {
		try {
			File file = getFile(name);

			if (file != null) {
				return YamlConfiguration.loadConfiguration(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void save(FileConfiguration configuration, String name) {
		try {
			File file = getFile(name);

			if (file != null) {
				configuration.save(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void reload(FileConfiguration configuration, String name) {
		try {
			File file = getFile(name);

			if (file != null) {
				configuration = YamlConfiguration.loadConfiguration(file);
				//configuration.save(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
