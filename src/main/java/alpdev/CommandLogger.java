package alpdev;

import alpdev.command.CommandHandler;
import alpdev.filter.CommandFilter;
import alpdev.listener.CommandListener;
import alpdev.webhook.WebhookSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandLogger extends JavaPlugin {
    
    private WebhookSender webhookSender;
    private CommandFilter commandFilter;
    private CommandHandler commandHandler;
    private CommandListener commandListener;
    private boolean consoleLogging;
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfiguration();
        registerListeners();
        registerCommands();
        getLogger().info("CommandLogger plugin has been enabled!");
        getLogger().info("Webhook URL: " + (getConfig().getString("webhook.url", "").isEmpty() ? "Not configured" : "Configured"));
        getLogger().info("Admin commands: " + commandFilter.getAdminCommands().size());
    }
    
    @Override
    public void onDisable() {
        getLogger().info("CommandLogger plugin has been disabled!");
    }
    
    private void loadConfiguration() {
        FileConfiguration config = getConfig();
        String webhookUrl = config.getString("webhook.url", "");
        consoleLogging = config.getBoolean("logging.console_log", true);
        webhookSender = new WebhookSender(this, webhookUrl);
        commandFilter = new CommandFilter(config);
        commandHandler = new CommandHandler(this, commandFilter, consoleLogging);
        commandListener = new CommandListener(this, webhookSender, commandFilter, consoleLogging);
    }
    
    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(commandListener, this);
    }
    
    private void registerCommands() {
        getCommand("commandlogger").setExecutor(commandHandler);
        getCommand("commandlogger").setTabCompleter(commandHandler);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandHandler.onCommand(sender, command, label, args);
    }
}