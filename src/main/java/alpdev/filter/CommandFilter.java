package alpdev.filter;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class CommandFilter {
    
    private final Set<String> adminCommands;
    
    public CommandFilter(FileConfiguration config) {
        this.adminCommands = new HashSet<>();
        loadFromConfig(config);
    }
    
    private void loadFromConfig(FileConfiguration config) {
        List<String> commands = config.getStringList("commands");
        if (commands != null) {
            for (String command : commands) {
                adminCommands.add(command.toLowerCase());
            }
        }
    }
    
    public boolean shouldLogCommand(String command) {
        if (command == null || command.isEmpty()) {
            return false;
        }
        String lowerCommand = command.toLowerCase();
        return adminCommands.contains(lowerCommand);
    }
    
    public void reload(FileConfiguration config) {
        adminCommands.clear();
        loadFromConfig(config);
    }
    
    public Set<String> getAdminCommands() {
        return new HashSet<>(adminCommands);
    }
}
