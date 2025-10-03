package alpdev.command;

import alpdev.filter.CommandFilter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {
    
    private final Plugin plugin;
    private final CommandFilter commandFilter;
    private final boolean consoleLogging;
    
    public CommandHandler(Plugin plugin, CommandFilter commandFilter, boolean consoleLogging) {
        this.plugin = plugin;
        this.commandFilter = commandFilter;
        this.consoleLogging = consoleLogging;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("commandlogger")) {
            if (!sender.hasPermission("commandlogger.use")) {
                sender.sendMessage("§cYou dont have permission to use this command");
                return true;
            }
            
            if (args.length == 0) {
                sender.sendMessage("§6CommandLogger Plugin v" + plugin.getDescription().getVersion());
                sender.sendMessage("§eUsage: /commandlogger <reload|info>");
                return true;
            }
            
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("commandlogger.reload")) {
                    sender.sendMessage("§cYou dont have permission to reload the plugin");
                    return true;
                }
                
                plugin.reloadConfig();
                sender.sendMessage("§aCommandLogger configuration reloaded");
                return true;
            }
            
            if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage("§6 CommandLogger Info ");
                sender.sendMessage("§eWebhook URL: " + (plugin.getConfig().getString("webhook.url", "").isEmpty() ? "§cNot configured" : "§aConfigured"));
                sender.sendMessage("§eCommands §f" + commandFilter.getAdminCommands().size());
                sender.sendMessage("§eConsole logging: " + (consoleLogging ? "§aYes" : "§cNo"));
                return true;
            }
            
            sender.sendMessage("§cUnknown subcommand! Use: /commandlogger <reload|info>");
            return true;
        }
        
        return false;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("commandlogger")) {
            if (!sender.hasPermission("commandlogger.use")) {
                return new ArrayList<>();
            }
            
            if (args.length == 1) {
                List<String> completions = new ArrayList<>();
                if (sender.hasPermission("commandlogger.reload")) {
                    completions.add("reload");
                }
                completions.add("info");
                return completions;
            }
        }
        return new ArrayList<>();
    }
}
