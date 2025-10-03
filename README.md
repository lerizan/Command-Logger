# CommandLogger Plugin

A professional Minecraft plugin that logs player commands and sends them to Discord webhooks with detailed information including player heads as thumbnails.

## Features

- **Discord Webhook Integration**: Sends command logs to Discord with rich embeds
- **Player Head Thumbnails**: Shows the player's Minecraft head as thumbnail
- **Configurable Command Filtering**: Monitor only specific commands or ignore unwanted ones
- **Detailed Information**: Includes player location, world, game mode, server info, and more
- **Console Logging**: Optional console logging for debugging
- **Admin Commands**: Built-in commands for plugin management
- **Permission System**: Proper permission handling for admin commands

## Installation

1. Download the plugin JAR file
2. Place it in your server's `plugins` folder
3. Start your server to generate the config file
4. Configure the webhook URL in `config.yml`
5. Restart your server

## Configuration

### config.yml

```yaml
# Webhook settings
webhook:
  url: "https://discord.com/api/webhooks/YOUR_WEBHOOK_URL_HERE"
  enabled: true

# Command filtering
commands:
  # Commands to monitor (without / prefix)
  monitored:
    - "say"
    - "tell"
    - "msg"
    - "w"
    - "me"
    - "help"
    - "spawn"
    - "home"
    - "tp"
    - "teleport"
    - "gamemode"
    - "give"
    - "kill"
    - "ban"
    - "kick"
    - "op"
    - "deop"
    - "whitelist"
    - "pardon"
    - "unban"
  
  # Commands to ignore
  ignored:
    - "login"
    - "register"
    - "changepassword"
    - "l"
    - "afk"

# Discord embed settings
discord:
  bot_name: "Command Logger"
  embed_color: "16711680"  # Red color
  show_player_head: true
  show_server_info: true
  show_timestamp: true

# Logging settings
logging:
  console_log: true
  log_level: "INFO"
```

## Commands

- `/commandlogger` - Show plugin information
- `/commandlogger reload` - Reload the configuration (requires permission)
- `/commandlogger info` - Show detailed plugin information

## Permissions

- `commandlogger.use` - Access to CommandLogger commands (default: op)
- `commandlogger.reload` - Reload configuration (default: op)

## Discord Webhook Setup

1. Go to your Discord server settings
2. Navigate to Integrations → Webhooks
3. Create a new webhook
4. Copy the webhook URL
5. Paste it in the `config.yml` file under `webhook.url`

## Features in Detail

### Discord Embed Information

The webhook sends rich embeds containing:
- **Player Name**: Who executed the command
- **Command**: The full command with arguments
- **Player UUID**: For identification
- **World**: Which world the player is in
- **Location**: Exact coordinates (X, Y, Z)
- **Server Name**: Your server name
- **Online Players**: Current player count
- **Game Mode**: Player's current game mode
- **Player Head**: Thumbnail showing player's Minecraft head
- **Timestamp**: When the command was executed

### Command Filtering

- **Monitored Commands**: Only these commands will be logged (if list is empty all commands are logged)
- **Ignored Commands**: These commands will never be logged, even if they're in the monitored list
- **Case Insensitive**: Command matching is case-insensitive


## Building from Source

1. Clone the repository
2. Run `./gradlew build` (Linux/Mac) or `gradlew.bat build` (Windows)
3. Find the JAR file in `build/libs/`

## Requirements

- Minecraft 1.21+
- Spigot/Paper server
- Java 21+

## Support

For issues or feature requests please create an issue in the repository.


