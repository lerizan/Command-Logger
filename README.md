# CommandLogger Plugin 


<img width="531" height="210" alt="image" src="https://github.com/user-attachments/assets/0e496dd4-0a80-40f0-b743-95352963efc8" />


## Features

- **Discord Webhook Integration**: Sends command logs to Discord with rich embeds
- **Configurable Command Filtering**: Monitor only specific commands or ignore unwanted ones
- **Detailed Information**: Includes player location world, game mode server info and more
- **Console Logging**: Optional console logging for debugging
- **Permission System**: Proper permission handling for admin commands


## Commands

- `/commandlogger` - Show plugin information
- `/commandlogger reload` - Reload the configuration (requires permission)
- `/commandlogger info` - Show detailed plugin information


### Command Filtering

- **Monitored Commands**: Only these commands will be logged (if list is empty all commands are logged)
- **Ignored Commands**: These commands will never be logged, even if they're in the monitored list
- **Case Insensitive**: Command matching is case-insensitive


## Requirements

- Minecraft 1.21+
- Spigot/Paper server
- Java 21+


- Do not use /login




