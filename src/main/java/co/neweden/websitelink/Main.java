package co.neweden.websitelink;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		API.main = this;
		getCommand("register").setExecutor(new UserCommands());
		getCommand("resetpassword").setExecutor(new UserCommands());
	}
	
	@Override
	public void onDisable() {
		Bukkit.getLogger().info(String.format("[%s] Disabled Version %s", getName(), getDescription().getVersion()));
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Util.formatString(String.format("&a%s &eversion %s", getName(), getDescription().getVersion())));
			sender.sendMessage(Util.formatString("&aSub commands:"));
			sender.sendMessage(Util.formatString("&e- &breloadconfig"));
			return true;
		}
		if (args[0].equalsIgnoreCase("reloadconfig")) {
			reloadConfig();
			sender.sendMessage(Util.formatString("&aConfig reloaded"));
		}
		return true;
	}
	
}
