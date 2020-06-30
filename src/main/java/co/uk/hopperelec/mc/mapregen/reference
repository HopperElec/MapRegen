package uk.co.hopperelec.mc.beatsaber;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {
    List<Entity> minecarts = new ArrayList<>();
    List<Location> gens = new ArrayList<>();
    List<String> songs = new ArrayList<>();
    List<List<String>> data = new ArrayList<>();
    List<Integer> stage = new ArrayList<>();
    List<Integer> points = new ArrayList<>();

    @Override
    public void onEnable() {this.getServer().getPluginManager().registerEvents(this,this);}

    @Override
    public void onDisable() {reset();}

    void reset() {
        for (Location loc : gens) {
            for (int x=(int)loc.getX(); x <= (int)loc.getX()+128; x++) {
                loc.getWorld().getBlockAt(x,(int)loc.getY()-1,(int)loc.getZ()).setType(Material.AIR,false);
                loc.getWorld().getBlockAt(x,(int)loc.getY(),(int)loc.getZ()).setType(Material.AIR,false);}}
        for (Entity minecart : minecarts) {minecart.remove();}
        minecarts = new ArrayList<>();
        songs = new ArrayList<>();
        data = new ArrayList<>();}

    @Override
    public boolean onCommand(CommandSender author, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("beatsaber")) {
            if (args.length == 0) {
                author.sendMessage("§0§l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                author.sendMessage("§e§l/beatsaber §r§4- §eCore command for HopperElecYT's 'BeatSaber on a budget' minigame plugin");
                author.sendMessage("§e§l/beatsaber gen §r§4- §eGenerates the Beatsaber map");
                author.sendMessage("§0§l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");}

            else if (args[0].equalsIgnoreCase("gen")) {
                try {
                    File folder;
                    if (args.length == 1) {
                        folder = new File(new File(getDataFolder(),"Songs"),"cat");
                        songs.add("cat");}
                    else {
                        folder = new File(new File(getDataFolder(),"Songs"),args[1]);
                        songs.add(args[1]);}
                    List<String> tempData = new ArrayList<>();
                    for (File file : folder.listFiles()) {
                        String line;
                        StringBuilder lines = new StringBuilder();
                        BufferedReader filereader = new BufferedReader(new FileReader(file));
                        while ((line = filereader.readLine()) != null) {lines.append(line);}
                        tempData.add(lines.toString());}
                    data.add(tempData);
                    stage.add(0);

                    Location loc = ((Player) author).getLocation();
                    gens.add(loc);
                    for (int x=(int)loc.getX(); x <= (int)loc.getX()+128; x++) {
                        loc.getWorld().getBlockAt(x,(int)loc.getY()-1,(int)loc.getZ()).setType(Material.REDSTONE_BLOCK,false);
                        loc.getWorld().getBlockAt(x,(int)loc.getY(),(int)loc.getZ()).setType(Material.AIR,false);
                        loc.getWorld().getBlockAt(x,(int)loc.getY(),(int)loc.getZ()).setType(Material.POWERED_RAIL,true);}
//                    for (String file : tempData) {
//
//                    }
                    minecarts.add(loc.getWorld().spawnEntity(loc, EntityType.MINECART));
                    points.add(0);}
                catch (FileNotFoundException e) {e.printStackTrace();author.sendMessage("Invalid song name!");}
                catch (IOException e) {e.printStackTrace();}}

            else if (args[0].equalsIgnoreCase("reset")) {reset();}
            else {return false;}
            return true;}
        return false;}

    @EventHandler
    public void onMinecartMove(VehicleMoveEvent event) {
        if (minecarts.contains(event.getVehicle()) && event.getVehicle().isEmpty()) {event.getVehicle().teleport(event.getFrom());}}
    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent event) {
        if (minecarts.contains(event.getVehicle())) {
            int index = minecarts.indexOf(event.getVehicle());
            int velocity = data.get(index).get(stage.get(index)).charAt(0);
            event.getVehicle().setVelocity(new Vector(velocity,velocity,velocity));
            Player.Spigot player = ((Player)event.getVehicle().getPassengers().get(0)).spigot();

            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                System.out.println("Look mom I'm running with no hands!");
                player.sendMessage(
                        ChatMessageType.ACTION_BAR,
                        new TextComponent(Integer.toString(points.get(index))));
            }, 0L, 5L);}}
    @EventHandler
    public void onVehicleExit(VehicleExitEvent event) {
        if (minecarts.contains(event.getVehicle())) {
            Location loc = gens.get(minecarts.indexOf(event.getVehicle()));
            for (int x=(int)loc.getX(); x <= (int)loc.getX()+128; x++) {
                loc.getWorld().getBlockAt(x,(int)loc.getY()-1,(int)loc.getZ()).setType(Material.AIR,false);
                loc.getWorld().getBlockAt(x,(int)loc.getY(),(int)loc.getZ()).setType(Material.AIR,false);}
            gens.remove(minecarts.indexOf(event.getVehicle()));
            songs.remove(minecarts.indexOf(event.getVehicle()));
            data.remove(minecarts.indexOf(event.getVehicle()));
            minecarts.remove(event.getVehicle());
            event.getVehicle().remove();}
    }
}
