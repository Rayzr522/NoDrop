
package com.rayzr522.nodrop;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.nodrop.cmd.CommandNoDrop;

public class NoDrop extends JavaPlugin implements Listener {

	// The logger (used for logging info to the console)
	// You can also use System.out.println like usual, and it'll print to the
	// server's console
	private Logger	logger;
	// The config file instance
	private Config	config;

	/**
	 * Called when the plugin is enabled.
	 */
	@Override
	public void onEnable() {

		// Get the logger
		logger = getLogger();

		// Initialize the Configuration class with this plugin's data path
		Configuration.init(this);
		// Attempt to load "messages.yml" from the JAR file if it doesn't exist
		if (!Configuration.loadFromJar("messages.yml")) {
			System.err.println("Failed to load config files!");
			System.err.println("Gonna go die in a hole now...");
			// We have failed
			Bukkit.getPluginManager().disablePlugin(this);
		}

		// Initialize the config instance
		config = new Config();

		load();

		// Register command handlers
		getCommand("nodrop").setExecutor(new CommandNoDrop(this));

		// Register this as an event handler
		getServer().getPluginManager().registerEvents(this, this);

		// Success!
		logger.info(versionText() + " enabled");

	}

	/**
	 * Load all the config files from disk. This does not perform
	 * plugin.reloadConfig().
	 */
	public void load() {

		Msg.load(Configuration.getConfig("messages.yml"));
		config.load("config.yml");

	}

	/**
	 * Save all data that must persist to the disk.
	 */
	public void save() {
		// Currently nothing
	}

	/**
	 * Get the config instance. Use this instead of getConfig().
	 * 
	 * @return The config instance for this plugin
	 */
	public Config config() {
		return config;
	}

	@Override
	public void onDisable() {

		save();
		logger.info(versionText() + " disabled");

	}

	public String versionText() {

		return getDescription().getName() + " v" + getDescription().getVersion();

	}

	// Prevent the various drop methods
	@EventHandler(priority = EventPriority.LOW)
	public void onItemDrop(PlayerDropItemEvent e) {
		if (!config.PREVENT_DROP) { return; }
		if (!config.worlds.contains(e.getPlayer().getWorld().getName())) { return; }
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onInventoryClick(InventoryClickEvent e) {
		if (!config.PREVENT_CLICK) { return; }
		if (!config.worlds.contains(e.getWhoClicked().getWorld().getName())) { return; }
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onDeath(PlayerDeathEvent e) {
		if (!config.PREVENT_DEATH) { return; }
		if (!config.worlds.contains(e.getEntity().getWorld().getName())) { return; }
		e.getDrops().clear();
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onDeath(PlayerSwapHandItemsEvent e) {
		if (!config.PREVENT_OFFHAND) { return; }
		if (!config.worlds.contains(e.getPlayer().getWorld().getName())) { return; }
		e.setCancelled(true);
	}

}
