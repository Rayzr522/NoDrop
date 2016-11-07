
package com.rayzr522.nodrop.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import com.rayzr522.nodrop.Config;
import com.rayzr522.nodrop.Msg;
import com.rayzr522.nodrop.NoDrop;

public class CommandNoDrop implements CommandExecutor {

    private NoDrop plugin;

    public CommandNoDrop(NoDrop plugin) {

        this.plugin = plugin;

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Config config = plugin.config();

        if (!sender.hasPermission(config.PERM_MANAGE)) {

            Msg.send(sender, "no-permission");
            return true;

        }

        if (args.length < 1) {

            PluginDescriptionFile pdf = plugin.getDescription();
            Msg.send(sender, "version-info", pdf.getName(), pdf.getVersion());
            return true;

        }

        String cmd = args[0].toLowerCase();

        if (cmd.equals("reload")) {

            plugin.reloadConfig();
            plugin.load();
            Msg.send(sender, "config-reloaded");

        } else {

            Msg.send(sender, "usage.nodrop");

        }

        return true;

    }

}
