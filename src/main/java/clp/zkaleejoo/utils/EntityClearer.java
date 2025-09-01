package clp.zkaleejoo.utils;

import clp.zkaleejoo.ClearLagPlus;
import clp.zkaleejoo.config.MainConfigManager;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EntityClearer {

    private final ClearLagPlus plugin;
    private final MainConfigManager config;

    public EntityClearer(ClearLagPlus plugin) {
        this.plugin = plugin;
        this.config = plugin.getMainConfigManager();
    }

    public int clearEntities(boolean isManual, CommandSender sender) {
        int removedCount = 0;
        for (World world : Bukkit.getWorlds()) {
            if (!shouldProcessWorld(world)) continue;

            for (Entity entity : world.getEntities()) {
                if (shouldRemoveEntity(entity)) {
                    entity.remove();
                    removedCount++;
                }
            }
        }

        // Enviar mensaje de limpieza completada
        if (isManual) {
            String message = config.getManualClearMessage()
                    .replace("{player}", sender.getName())
                    .replace("{count}", String.valueOf(removedCount));
            Bukkit.broadcastMessage(MessageUtils.getColoredMessage(config.getPrefix() + message));
        } else {
            for (String line : config.getCompletedMessage()) {
                Bukkit.broadcastMessage(MessageUtils.getColoredMessage(
                        config.getPrefix() + line.replace("{count}", String.valueOf(removedCount))));
            }
        }

        return removedCount;
    }

    private boolean shouldProcessWorld(World world) {
        List<String> worlds = config.getEntitiesWorlds();
        return worlds.isEmpty() || worlds.contains(world.getName());
    }

    private boolean shouldRemoveEntity(Entity entity) {
        // Verificar tipo de entidad
        if (!config.getEntitiesTypes().contains(entity.getType().name())) {
            return false;
        }

        // Verificar exclusiones
        if (config.getEntitiesExclude().contains(entity.getType().name())) {
            return false;
        }

        // Verificar items con nombre
        if (config.getEntitiesIgnoreNamedItems() && entity instanceof Item) {
            Item item = (Item) entity;
            ItemStack itemStack = item.getItemStack();
            if (itemStack.hasItemMeta()) {
                ItemMeta meta = itemStack.getItemMeta();
                if (meta.hasDisplayName()) return false;
            }
        }

        // Verificar distancia desde spawn
        if (config.getEntitiesMinDistanceFromSpawn() > 0) {
            Location spawn = entity.getWorld().getSpawnLocation();
            if (entity.getLocation().distanceSquared(spawn) <
                    Math.pow(config.getEntitiesMinDistanceFromSpawn(), 2)) {
                return false;
            }
        }

        return true;
    }
}