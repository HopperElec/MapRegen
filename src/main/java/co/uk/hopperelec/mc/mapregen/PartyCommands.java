package co.uk.hopperelec.mc.mapregen;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyCommands {
    public void command(Player author, String[] args) {
        if (args.length == 1 || args[1].equalsIgnoreCase("help")) {
            author.sendMessage("§0§l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            author.sendMessage("§e§l/mapregen party§r§4- §eShows this help message");
            author.sendMessage("§e§l/mapregen party help §r§4- §eShows this message");
            author.sendMessage("§e§l/mapregen party join (inviter)§r§4- §eIf you have been invited to a party by someone this command will accept it else it will request an invite");
            author.sendMessage("§e§l/mapregen party invite (invitee)§r§4- §eWill send an invite to the player");
            author.sendMessage("§e§l/mapregen party uninvite (invitee)§r§4- §eRemoves an invite sent to a player");
            author.sendMessage("§e§l/mapregen party leave§r§4- §eLeaves current party and forms a new one");
            author.sendMessage("§e§l/mapregen party list§r§4- §eLists players in your current party");
            author.sendMessage("§0§l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        } else if (args[1].equalsIgnoreCase("join")) {
            if (args.length == 3) {
                boolean online = false;
                Player inviter = null;
                List<Player> party = null;
                for (List<Player> tempParty : Main.getParties()) {
                    for (Player player : tempParty) {
                        if (player.getDisplayName().equalsIgnoreCase(args[2])) {
                            online = true;
                            inviter = player;
                            party = tempParty;
                        }
                    }
                }
                if (online) {
                    if (Main.getInvites().get(inviter).contains(author)) {
                        author.sendMessage("Accepting invite request from " + inviter.getDisplayName());
                        inviter.sendMessage(author.getDisplayName() + " has accepted your invite to your Map Regen party!");
                        Main.getInvites().get(inviter).remove(author);
                        party.add(author);
                        for (List<Player> tempParty : Main.getParties()) {
                            if (tempParty.contains(author)) {
                                tempParty.remove(author);
                                if (tempParty.size() == 0) {
                                    Main.getParties().remove(tempParty);
                                } else {
                                    for (Player player : tempParty) {
                                        player.sendMessage(author.getDisplayName() + " has moved to a new Map Regen party!");
                                    }
                                }
                            }
                        }
                        for (Player player : party) {
                            if (player != author) {
                                player.sendMessage(author.getDisplayName() + " has joined the party!");
                            }
                        }
                    } else {
                        author.sendMessage("Sending invite request to " + inviter.getDisplayName());
                        inviter.sendMessage(author.getDisplayName() + " would like to join your Map Regen party. Use /mapregen party invite " + author.getDisplayName());
                    }
                } else {
                    author.sendMessage("That player doesn't appear to be online!");
                }
            } else {
                author.sendMessage("/mapregen party join (inviter)");
            }
        } else if (args[1].equalsIgnoreCase("invite")) {
            if (args.length == 3) {
                boolean online = false;
                Player invitee = null;
                for (List<Player> party : Main.getParties()) {
                    for (Player player : party) {
                        if (player.getDisplayName().equalsIgnoreCase(args[2])) {
                            online = true;
                            invitee = player;
                        }
                    }
                }
                if (online) {
                    if (Main.getInvites().get(author).contains(invitee)) {
                        author.sendMessage("You've already invited this person to your party!");
                    } else {
                        invitee.sendMessage(author.getDisplayName() + " has invited you to their Map Regen party. Use /mapregen party join " + author.getDisplayName() + " to accept.");
                        for (List<Player> party : Main.getParties()) {
                            if (party.contains(author)) {
                                for (Player player : party) {
                                    player.sendMessage(author.getDisplayName() + " has invited " + invitee.getDisplayName() + " to the Map Regen party");
                                }
                            }
                        }
                    }
                } else {
                    author.sendMessage("That player doesn't appear to be online!");
                }
            } else {
                author.sendMessage("/mapregen party invite (invitee)");
            }
        } else if (args[1].equalsIgnoreCase("uninvite")) {
            if (args.length == 3) {
                boolean invited = false;
                for (Player invitee : Main.getInvites().get(author)) {
                    if (args[2].equalsIgnoreCase(invitee.getDisplayName())) {
                        Main.getInvites().get(author).remove(invitee);
                        invitee.sendMessage(author.getDisplayName() + " has removed their invite to you from their Map Regen party!");
                        for (List<Player> party : Main.getParties()) {
                            if (party.contains(author)) {
                                for (Player player : party) {
                                    player.sendMessage(author.getDisplayName() + " has removed their invite to " + invitee.getDisplayName() + " from the Map Regen party");
                                }
                            }
                        }
                        invited = true;
                    }
                }
                if (!invited) {
                    author.sendMessage("This player isn't in your invites!");
                }
            } else {
                author.sendMessage("/mapregen party uninvite (invitee)");
            }
        } else if (args[1].equalsIgnoreCase("leave")) {
            for (List<Player> party : Main.getParties()) {
                if (party.contains(author)) {
                    if (party.size() == 1) {
                        author.sendMessage("You weren't in a party!");
                    } else {
                        party.remove(author);
                        Main.getParties().add(new ArrayList<>(Collections.singletonList(author)));
                        author.sendMessage("You have been moved into a new party.");
                        for (Player player : party) {
                            player.sendMessage(author.getDisplayName() + " has moved to a new Map Regen party!");
                        }
                    }
                }
            }
        } else if (args[1].equalsIgnoreCase("list")) {
            StringBuilder message = new StringBuilder("§lMembers of the current party: " + author.getDisplayName());
            for (List<Player> party : Main.getParties()) {
                if (party.contains(author)) {
                    for (Player player : party) {
                        if (player != author) {
                            message.append(", ").append(player.getDisplayName());
                        }
                    }
                }
            }
            author.sendMessage(String.valueOf(message));
        } else {
            author.sendMessage("Invalid party command.");
        }
    }
}
