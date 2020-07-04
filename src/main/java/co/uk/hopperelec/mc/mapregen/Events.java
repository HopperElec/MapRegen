package co.uk.hopperelec.mc.mapregen;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Events implements Listener {
    String pre = "§4[§cMapRegen§4]§r§7 ";

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("§7HopperElecYT: OMG HI I CAN'T BELIEVE IT'S ACTUALLY "+event.getPlayer().getDisplayName()+"!!!11!!1!");
        Main.getParties().add(new ArrayList<>(Collections.singletonList(event.getPlayer())));
        Main.getInvites().put(event.getPlayer(), new ArrayList<>());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        for (List<Player> party : Main.getParties()) {
            if (party.contains(event.getPlayer())) {
                party.remove(event.getPlayer());
                if (party.size() == 0) {Main.getParties().remove(party);
                } else {
                    for (Player player : party) {
                        player.sendMessage(pre+event.getPlayer().getDisplayName()+" has been removed from your Map Regen party as they left the server!");}}}}
        for (Player player : Main.getInvites().keySet()) {
            if (Main.getInvites().get(player).contains(event.getPlayer())) {
                Main.getInvites().get(player).remove(event.getPlayer());
                player.sendMessage(pre+"Your Map Regen party invite to "+event.getPlayer().getDisplayName()+" has expired as they left the server!");}}
    }
}
