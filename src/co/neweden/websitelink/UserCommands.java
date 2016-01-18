package co.neweden.websitelink;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.neweden.websitelink.jsonstorage.PasswordReset;
import co.neweden.websitelink.jsonstorage.RegisterUser;

public class UserCommands implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Util.formatString("&cOnly players can run this command"));
			return true;
		}
		
		switch (command.getLabel().toLowerCase()) {
			case "register": registerCommand(sender, args);
			break;
			case "resetpassword": resetPasswordCommand(sender);
			break;
		}
		
		return true;
	}
	
	public void registerCommand(CommandSender sender, String[] args) {
		if (args.length != 1) {
			sender.sendMessage(Util.formatString(
					"&eIncorrect usage: &bYou need to provide one email address to register.\n" +
					"&eUsage: &b/register <email-address>"
			));
			return;
		}
		
		RegisterUser apiObject = User.register((Player) sender, args[0]);
		
		if (apiObject == null || apiObject.errorMsg != null) {
			sender.sendMessage(Util.formatString("&cAn internal error occurred while attempting to perform this command, please contact a member of staff."));
			return;
		}
		if (apiObject.userExists == true) {
			sender.sendMessage(Util.formatString("&cYou are already registered on the New Eden website."));
			sender.sendMessage(Util.formatString("&bYour login email address is: &a" + apiObject.user.email));
			sender.sendMessage(Util.formatString("&bYour username is: &a" + apiObject.user.name));
			sender.sendMessage(Util.formatString("&bIf you have forgotten your password type /resetpassword, or you can reset it on the website."));
			return;
		}
		if (apiObject.sessionID > 0) {
			sender.sendMessage(Util.formatString("&2&l&m============================================="));
			sender.sendMessage(Util.formatString(""));
			sender.sendMessage(Util.formatString("&aA registration email has been sent to &6" + args[0] + "&a, follow the instructions in the email to continue with the registration process."));
			sender.sendMessage(Util.formatString(""));
			sender.sendMessage(Util.formatString("&eIf you didn't get the email: &bCheck your spam or junk folder, add noreply@neweden.co to your safe-sender list, you can run this command again to send another email."));
			sender.sendMessage(Util.formatString(""));
			sender.sendMessage(Util.formatString("&eIf you entered the wrong email address or want to change the email addres: &bJust run this command again using the new email address, your email address will be updated in the database and a new email will be sent.\n&f\n"));
			sender.sendMessage(Util.formatString(""));
			sender.sendMessage(Util.formatString("&2&l&m============================================="));
		} else {
			sender.sendMessage(Util.formatString("&cUnable to create registatio token, an error occured, please contact an administrator."));
		}
	}
	
	public void resetPasswordCommand(CommandSender sender) {
		PasswordReset apiObject = User.resetPassword((Player) sender);
		
		if (apiObject == null || apiObject.errorMsg != null) {
			sender.sendMessage(Util.formatString("&cAn internal error occurred while attempting to perform this command, please contact a member of staff."));
			return;
		}
		if (apiObject.userExists == false) {
			sender.sendMessage(Util.formatString("&cA user on the website could not be found for you, have you registered? If not type /register for more info, if you have contact a member of staff for support."));
			return;
		}
		if (apiObject.tempPasswordLink != null) {
			sender.sendMessage(Util.formatString("&2&l&m============================================="));
			sender.sendMessage(Util.formatString(""));
			sender.sendMessage(Util.formatString("&aA password reset link has been generated and is shown below, this link will work only once!"));
			sender.sendMessage(Util.formatString(""));
			sender.sendMessage(Util.formatString("&bYour login email address is: &a" + apiObject.user.email));
			sender.sendMessage(Util.formatString("&bYour username is: &a" + apiObject.user.name));
			sender.sendMessage(Util.formatString(""));
			sender.sendMessage(Util.formatString("&6" + apiObject.tempPasswordLink));
			sender.sendMessage(Util.formatString(""));
			sender.sendMessage(Util.formatString("&2&l&m============================================="));
		} else {
			sender.sendMessage(Util.formatString("&cUnable to generate password reset link, an error occured, please contact an administrator."));
		}
	}
	
}
