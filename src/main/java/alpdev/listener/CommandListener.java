package alpdev.listener;

import alpdev.filter.CommandFilter;
import alpdev.webhook.WebhookSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

public class CommandListener implements Listener {
    
    private final Plugin plugin;
    private final WebhookSender webhookSender;
    private final CommandFilter commandFilter;
    private final boolean consoleLogging;
    
    public CommandListener(Plugin plugin, WebhookSender webhookSender, CommandFilter commandFilter, boolean consoleLogging) {
        this.plugin = plugin;
        this.webhookSender = webhookSender;
        this.commandFilter = commandFilter;
        this.consoleLogging = consoleLogging;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        String[] parts = message.substring(1).split(" ", 2);
        String command = parts[0];
        String[] args = parts.length > 1 ? parts[1].split(" ") : new String[0];
        if (!commandFilter.shouldLogCommand(command)) {
            return;
        }
        if (consoleLogging) {
            plugin.getLogger().info("Command executed by " + player.getName() + ": " + message);
        }
        webhookSender.sendCommandLog(player, command, args);
    }
}
