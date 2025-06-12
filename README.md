# BetterBungee

**BetterBungee** is a powerful Java plugin designed to link multiple BungeeCord proxies, enabling seamless communication and management of players and network-wide features across your Minecraft server infrastructure.

---

## Overview

BetterBungee is built for server administrators who need more than a single BungeeCord instance. Whether you're running a large network, require high availability, or want to synchronize data (such as player lists, messaging, or administration commands) across proxies, BetterBungee makes it easy and intuitive.

---

## Key Features

- **Proxy Linking:**  
  Effortlessly connect multiple BungeeCord proxies to synchronize player counts, online player lists, and more.

- **Unified Player List:**  
  See all players across every proxy as if they were on a single network.

- **Network-Wide Messaging:**  
  Send messages to any player on any proxy from any location in your network.

- **Remote Actions:**  
  Kick, move, or manage players across all connected proxies—no matter where a command is issued.

- **Extensible API:**  
  Developers can leverage an easy-to-use API for advanced integrations and custom network features.

- **RabbitMQ Support:**  
  Robust, low-latency event and message passing between proxies.

---

## How It Works

BetterBungee uses a messaging backend to link all your BungeeCord proxies together. Each proxy running the plugin will share player information and listen for commands sent from other proxies, ensuring data is always synchronized and administrative actions are instantly network-wide.

---

## Getting Started

### Prerequisites

- Java 8 or higher
- Multiple [BungeeCord](https://www.spigotmc.org/wiki/bungeecord/) proxies
- At least one RabbitMQ instance for messaging

### Installation

1. **Download or Clone:**
   ```sh
   git clone https://github.com/aureliancnx/BetterBungee.git
   cd BetterBungee
   ```

2. **Build:**
   - Using Maven:
     ```sh
     mvn clean package
     ```
   - Using Gradle (if a `build.gradle` is present):
     ```sh
     ./gradlew build
     ```

3. **Deploy:**
   - Place the generated `.jar` in the `/plugins` folder of every BungeeCord proxy you wish to link.
   - Configure your connection (see below).
   - Restart all proxies.

### Configuration

After the first run, a configuration file will be generated in the plugin directory.  
Configure connection details and enable/disable features as needed (e.g., RabbitMQ host/port, message settings, etc.).

---

## Usage

- **Player Sync:**  
  Player counts and details are automatically kept in sync across all proxies.

- **Network Messaging:**  
  Use provided commands or the API to send messages, alerts, and broadcasts to any or all players, regardless of their proxy.

- **Remote Administration:**  
  Kick, move, or manage players from any proxy, with changes reflected network-wide.

- **Developer Integration:**  
  Use the API packages to add custom features or automate network management.

---

## Documentation

- **JavaDoc:**  
  Full API documentation is available in the `/javadoc` folder.

---

## Contributing

Pull requests and suggestions are welcome!  
Please open an issue or PR if you want to contribute or report a bug.

---

## Author

- [aureliancnx](https://github.com/aureliancnx)
