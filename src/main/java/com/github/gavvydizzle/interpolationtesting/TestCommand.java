package com.github.gavvydizzle.interpolationtesting;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.Vector3f;

import java.util.Collections;
import java.util.List;

public class TestCommand implements TabExecutor {

    private final InterpolationTesting instance;

    public TestCommand(InterpolationTesting instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;

        if (args.length < 6) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /dit <duration> <delay> <y> <l-rot> <size> <r-rot>");
            return true;
        }

        try {
            int duration = Integer.parseInt(args[0]);
            int delay = Integer.parseInt(args[1]);
            double y = Double.parseDouble(args[2]);
            double lrot = Double.parseDouble(args[3]);
            double scale = Double.parseDouble(args[4]);
            double rrot = Double.parseDouble(args[5]);

            Location startLocation = ((Player) sender).getEyeLocation();

            ItemDisplay display = (ItemDisplay) ((Player) sender).getWorld().spawnEntity(startLocation, EntityType.ITEM_DISPLAY);
            display.setPersistent(false);
            display.setItemStack(new ItemStack(Material.STONE));
            display.setBrightness(new Display.Brightness(0, 15));

            display.setInterpolationDuration(duration);
            display.setInterpolationDelay(delay);

            display.setTransformation(new Transformation(
                    display.getTransformation().getTranslation().add(0, (float) y, 0),
                    display.getTransformation().getLeftRotation().rotateY((float) Math.toRadians(lrot)),
                    new Vector3f(1,1,1).mul((float) scale),
                    display.getTransformation().getRightRotation().rotateY((float) Math.toRadians(rrot))
            ));

            Bukkit.getScheduler().scheduleSyncDelayedTask(instance, display::remove, duration + 1);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Error");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.emptyList();
    }
}
