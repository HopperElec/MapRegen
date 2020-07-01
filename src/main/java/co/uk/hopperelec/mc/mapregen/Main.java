package co.uk.hopperelec.mc.mapregen;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public final class Main extends JavaPlugin {
    public static Plugin plugin = null;
    static List<List<Player>> parties = new ArrayList<>();
    static Hashtable<Player,List<Player>> invites = new Hashtable<>();
    StartCommand startCommand = new StartCommand();
    PartyCommands partyCommands = new PartyCommands();

    public static List<List<Player>> getParties() {return parties;}
    public static Hashtable<Player,List<Player>> getInvites() {return invites;}

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
        plugin = this;
    }

    @Override
    public void onDisable() {}

    @Override
    public boolean onCommand(CommandSender author, Command cmd, String label, String[] args) { // Event when the player enters a command.
        if (author instanceof Player) {
            if (args.length == 0) {
                author.sendMessage("§0§l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                author.sendMessage("§e§l/mapregen §r§4- §eCore command for HopperElecYT's Map Regen Minigame plugin");
                author.sendMessage("§e§l/mapregen start §r§4- §eStarts a Map Regen game");
                author.sendMessage("§e§l/mapregen party §r§4- §eLists Map Regen party commands");
                author.sendMessage("§0§l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            } else if (args[0].equalsIgnoreCase("start")) {
                startCommand.command((Player) author);
            } else if (args[0].equalsIgnoreCase("party")) {
                partyCommands.command((Player) author, args);
            } else {return false;}
            return true;
        } else {return false;}
    }
}
