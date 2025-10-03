package alpdev.webhook;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

public class WebhookSender {
    
    private final Plugin plugin;
    private final String webhookUrl;
    
    public WebhookSender(Plugin plugin, String webhookUrl) {
        this.plugin = plugin;
        this.webhookUrl = webhookUrl;
    }
    
    public void sendCommandLog(Player player, String command, String[] args) {
        if (webhookUrl == null || webhookUrl.isEmpty()) {
            return;
        }
        
        CompletableFuture.runAsync(() -> {
            try {
                JsonObject embed = createEmbed(player, command, args);
                JsonObject payload = new JsonObject();
                JsonArray embeds = new JsonArray();
                embeds.add(embed);
                payload.add("embeds", embeds);
                
                sendWebhook(payload.toString());
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to send webhook: " + e.getMessage());
            }
        });
    }
    
    private JsonObject createEmbed(Player player, String command, String[] args) {
        JsonObject embed = new JsonObject();
        embed.addProperty("title", "Command Executed");
        String fullCommand = "/" + command + (args.length > 0 ? " " + String.join(" ", args) : "");
        embed.addProperty("description", "**Player:** **" + player.getName() + "**\n**Command:** `" + fullCommand + "`");
        embed.addProperty("color", 16711680);
        JsonArray fields = new JsonArray();
        JsonObject opField = new JsonObject();
        opField.addProperty("name", "OP Status");
        opField.addProperty("value", player.isOp() ? "Yes" : "No");
        opField.addProperty("inline", true);
        fields.add(opField);
        JsonObject commandStatusField = new JsonObject();
        commandStatusField.addProperty("name", "Command Status");
        commandStatusField.addProperty("value", "Executed");
        commandStatusField.addProperty("inline", true);
        fields.add(commandStatusField);
        JsonObject playersField = new JsonObject();
        playersField.addProperty("name", "Online Players");
        playersField.addProperty("value", Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
        playersField.addProperty("inline", true);
        fields.add(playersField);
        embed.add("fields", fields);
        JsonObject thumbnail = new JsonObject();
        String playerHeadUrl = "https://mc-heads.net/avatar/" + player.getUniqueId().toString() + "/256";
        thumbnail.addProperty("url", playerHeadUrl);
        embed.add("thumbnail", thumbnail);
        JsonObject footer = new JsonObject();
        footer.addProperty("text", "CommandLogger Plugin");
        embed.add("footer", footer);
        embed.addProperty("timestamp", Instant.now().toString());
        return embed;
    }
    
    private void sendWebhook(String jsonPayload) throws IOException {
        URL url = new URL(webhookUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        int responseCode = connection.getResponseCode();
        if (responseCode < 200 || responseCode >= 300) {
            throw new IOException("Webhook request failed with response code: " + responseCode);
        }
    }
}