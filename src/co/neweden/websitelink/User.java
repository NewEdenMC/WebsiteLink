package co.neweden.websitelink;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import co.neweden.websitelink.jsonstorage.PasswordReset;
import co.neweden.websitelink.jsonstorage.RegisterUser;

public class User {
	
	public static RegisterUser register(Player player, String email) {
		Map<String, String> args = new HashMap<String, String>();
		args.put("uuid", player.getUniqueId().toString());
		args.put("email", email);
		return API.callAPIMethod("openRegistration", RegisterUser.class, args);
	}
	
	public static PasswordReset resetPassword(Player player) {
		Map<String, String> args = new HashMap<String, String>();
		args.put("uuid", player.getUniqueId().toString());
		return API.callAPIMethod("resetPassword", PasswordReset.class, args);
	}
	
}
