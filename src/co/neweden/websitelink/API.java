package co.neweden.websitelink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.craftbukkit.libs.com.google.gson.Gson;

import co.neweden.websitelink.jsonstorage.InterfaceObject;

import com.google.common.primitives.Primitives;

public class API {
	
	protected static Main main;
	
	public static <T> T callAPIMethod(String method, Class<T> object, Map<String, String> args) {
		String sArgs = "?key=" + main.getConfig().getString("APIkey");
		
		for (Entry<String, String> e : args.entrySet()) {
			sArgs = String.format("%s&%s=%s", sArgs, e.getKey(), e.getValue());
		}
		
		String apiPath = main.getConfig().getString("APIURL") + method;
		URL url = null;
		try {
			url = new URL(apiPath + sArgs);
		} catch (MalformedURLException e) {
			main.getLogger().log(Level.SEVERE, String.format("An API request was made however the URL that was constructed is not a vlaid URL, ensure that APIURL in the %s config has been set correctly.", main.getName()));
			main.getLogger().info("Requested URL: " + apiPath);
			return null;
		}
		BufferedReader br = null;
		String rawData = "";
		try {
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				rawData = rawData + line;
			}
		} catch (IOException e) {
			main.getLogger().log(Level.SEVERE, "An IO Exception has occurred when attempting to open a connection to the API or read data from the API");
			e.printStackTrace();
			return null;
		}
		
		Gson gson = new Gson();
		Object gsonObj = gson.fromJson(rawData, (Type) object);
		InterfaceObject ifObj = (InterfaceObject) gsonObj;
		
		if (ifObj.errorMsg != null) {
			main.getLogger().warning(String.format("The following error was returned from %s: %s", apiPath, ifObj.errorMsg));
			main.getLogger().info(String.format("URL Data sent: %s", url.toString()));
			main.getLogger().info(String.format("Raw API Response: %s", rawData));
		}
		
		return Primitives.wrap(object).cast(gsonObj);
	}
	
}
