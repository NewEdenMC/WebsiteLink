package co.neweden.websitelink;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import co.neweden.websitelink.jsonstorage.PasswordResetObject;
import co.neweden.websitelink.jsonstorage.RegisterUserObject;
import co.neweden.websitelink.jsonstorage.UserObject;

public class User {
	
	public static RegisterUserObject register(Player player, String email) {
		Map<String, String> args = new HashMap<String, String>();
		args.put("uuid", player.getUniqueId().toString());
		args.put("email", email);
		return API.callAPIMethod("openRegistration", RegisterUserObject.class, args);
	}
	
	public static co.neweden.websitelink.jsonstorage.UserObject getUser(Player player) {
		Map<String, String> args = new HashMap<String, String>();
		args.put("uuid", player.getUniqueId().toString());
		return API.callAPIMethod("getUser", UserObject.class, args);
	}
	
	public static PasswordResetObject resetPassword(Player player) {
		Map<String, String> args = new HashMap<String, String>();
		args.put("uuid", player.getUniqueId().toString());
		return API.callAPIMethod("resetPassword", PasswordResetObject.class, args);
	}
	
}
