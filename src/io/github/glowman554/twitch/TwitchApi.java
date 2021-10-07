package io.github.glowman554.twitch;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class TwitchApi
{
	public static String get_user_id(String username) throws IOException, JsonSyntaxException
	{
		// Make http request
		URL url = new URL("https://api.twitch.tv/kraken/users?login=" + username);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("client-id", "ekj09tcx24qymrl1wl5c6er2qjkpryz");
		con.setRequestProperty("accept", "application/vnd.twitchtv.v5+json");

		String response = "";
		
		for(byte b : con.getInputStream().readAllBytes())
		{
			response += (char) b;
		}

		Json json = Json.json();
		JsonNode root = json.parse(response);

		String _username = root.get("users").get(0).get("_id").asString();

		con.getInputStream().close();
		con.disconnect();

		return _username;
	}
}
