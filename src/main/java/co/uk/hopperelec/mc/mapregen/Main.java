package co.uk.hopperelec.mc.mapregen;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {
    public static Plugin plugin = null;
    static List<List<Player>> parties = new ArrayList<>();
    static Map<Player,List<Player>> invites = new HashMap<>();
    StartCommand startCommand = new StartCommand();
    PartyCommands partyCommands = new PartyCommands();

    public static List<List<Player>> getParties() {return parties;}
    public static Map<Player,List<Player>> getInvites() {return invites;}
    public Plugin getPlugin() {return plugin;}

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(), this);
        plugin = this;
    }

    @Override
    public void onDisable() {}

    @Override
    public boolean onCommand(CommandSender author, Command cmd, String label, String[] args) {
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
                return partyCommands.command((Player) author, args);
            } else {
                author.sendMessage("§4[§cMapRegen§4]§r§7 Invalid party command.");
                return false;}
            return true;
        } else {return false;}
    }
}
