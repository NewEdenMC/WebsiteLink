package co.neweden.websitelink;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		API.main = this;
	}
	
	@Override
	public void onDisable() {
		Bukkit.getLogger().info(String.format("[%s] Disabled Version %s", getName(), getDescription().getVersion()));
	}
	
}
